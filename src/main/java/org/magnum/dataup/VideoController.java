/*
 * 
 * Copyright 2014 Jules White
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */
package org.magnum.dataup;

import org.magnum.dataup.model.Video;
import org.magnum.dataup.model.VideoStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class VideoController {

    public static List<Video> videos = new ArrayList<>();
    private VideoFileManager videoDataMgr;

	@RequestMapping(value = "/video", method = RequestMethod.GET)
    public @ResponseBody List<Video> getAll () {
        return videos;
    }

    @RequestMapping(value = "/video", method = RequestMethod.POST)
    public @ResponseBody Video post (@RequestBody Video video) {
        video = Video.create()
                .withContentType(video.getContentType())
                .withDuration(video.getDuration())
                .withSubject(video.getSubject())
                .withTitle(video.getTitle())
                .build();
        GenerateId.save(video);
        video.setDataUrl(GenerateUrl.getUrlBaseForLocalServer());
        videos.add(video);
        return video;
    }

    @RequestMapping(value="/video/{id}/data", method = RequestMethod.POST)
    public @ResponseBody VideoStatus post(@PathVariable("id") Long id,
                                          @RequestParam("data") MultipartFile videoData) throws IOException {

        for (Video video : videos) {
            if (video.getId() == id) {
                    videoDataMgr = VideoFileManager.get();
                    videoDataMgr.saveVideoData(video, videoData.getInputStream());
                    return new VideoStatus(VideoStatus.VideoState.READY);
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    // GET /video/{id}/data
    @RequestMapping(value ="/video/{id}/data", method = RequestMethod.GET)
    public ResponseEntity get(@PathVariable("id") Long id,
                              HttpServletResponse response) throws IOException {

        for (Video video : videos) {
            HttpHeaders headers = new HttpHeaders();
            if (video.getId() == id && videoDataMgr.hasVideoData(video)) {
                    videoDataMgr = VideoFileManager.get();
                    videoDataMgr.copyVideoData(video, response.getOutputStream());
                    return new ResponseEntity<>(headers, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
