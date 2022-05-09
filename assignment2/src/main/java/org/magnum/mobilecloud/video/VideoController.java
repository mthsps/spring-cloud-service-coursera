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

package org.magnum.mobilecloud.video;

import com.google.common.collect.Lists;
import net.bytebuddy.pool.TypePool;
import org.magnum.mobilecloud.video.client.VideoSvcApi;
import org.magnum.mobilecloud.video.repository.Video;
import org.magnum.mobilecloud.video.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.security.Principal;
import java.util.*;

import static org.magnum.mobilecloud.video.client.VideoSvcApi.*;

@Controller
public class VideoController {

	/**
	 * You will need to create one or more Spring controllers to fulfill the
	 * requirements of the assignment. If you use this file, please rename it
	 * to something other than "AnEmptyController"
	 *
	 *
		 ________  ________  ________  ________          ___       ___  ___  ________  ___  __
		|\   ____\|\   __  \|\   __  \|\   ___ \        |\  \     |\  \|\  \|\   ____\|\  \|\  \
		\ \  \___|\ \  \|\  \ \  \|\  \ \  \_|\ \       \ \  \    \ \  \\\  \ \  \___|\ \  \/  /|_
		 \ \  \  __\ \  \\\  \ \  \\\  \ \  \ \\ \       \ \  \    \ \  \\\  \ \  \    \ \   ___  \
		  \ \  \|\  \ \  \\\  \ \  \\\  \ \  \_\\ \       \ \  \____\ \  \\\  \ \  \____\ \  \\ \  \
		   \ \_______\ \_______\ \_______\ \_______\       \ \_______\ \_______\ \_______\ \__\\ \__\
		    \|_______|\|_______|\|_______|\|_______|        \|_______|\|_______|\|_______|\|__| \|__|

	 *
	 */

	@Autowired
	public VideoRepository videoRepository;

	@RequestMapping(value="/go",method=RequestMethod.GET)
	public @ResponseBody String goodLuck(){
		return "Good Luck!";
	}

	@RequestMapping(value= VIDEO_SVC_PATH, method = RequestMethod.GET)
	public @ResponseBody Collection<Video> getVideoList() {
		return Lists.newArrayList(videoRepository.findAll());
	}

	@RequestMapping(value= VIDEO_SVC_PATH, method = RequestMethod.POST)
	public ResponseEntity<Video> post(@RequestBody Video video) {
		return ResponseEntity.status(HttpStatus.OK).body(videoRepository.save(video));
	}

	@RequestMapping(value= VIDEO_SVC_PATH+"/{id}", method = RequestMethod.GET)
	public ResponseEntity<Video>  getVideo(@PathVariable("id") long id) {
		if (videoRepository.findById(id).isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(videoRepository.getOne(id));
		}
	}

	@RequestMapping(value = VIDEO_SVC_PATH + "/{id}/like", method = RequestMethod.POST)
	public ResponseEntity<HttpStatus> likeVideo(@PathVariable("id") long id, Principal user) {
		if (videoRepository.findById(id).isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		Video video = videoRepository.getOne(id);
		String username = user.getName();
		if (video.getLikedBy().contains(username)) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} else {
			video.setLikes(video.getLikes() + 1);
			video.setLikedBy(new HashSet<>(List.of(username)));
			videoRepository.save(video);
			return new ResponseEntity<>(HttpStatus.OK);
		}
	}

	@RequestMapping(value = VIDEO_SVC_PATH + "/{id}/unlike", method = RequestMethod.POST)
	public ResponseEntity<HttpStatus> unlikeVideo(@PathVariable("id") long id, Principal user) {
		if (videoRepository.findById(id).isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		Video video = videoRepository.getOne(id);
		String username = user.getName();
		if (!video.getLikedBy().contains(username)) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} else {
			video.setLikes(video.getLikes() - 1);
			video.getLikedBy().remove(username);
			videoRepository.save(video);
			return new ResponseEntity<>(HttpStatus.OK);
		}
	}

	@RequestMapping(value=VIDEO_TITLE_SEARCH_PATH, method=RequestMethod.GET)
	public @ResponseBody Collection<Video> findByTitle(@RequestParam(TITLE_PARAMETER) String title) {
		return videoRepository.findByName(title);
	}

	@RequestMapping(value=VIDEO_DURATION_SEARCH_PATH, method=RequestMethod.GET)
	public @ResponseBody Collection<Video> findByDuration(@RequestParam(DURATION_PARAMETER) long duration) {
		return videoRepository.findByDurationLessThan(duration);
	}

}
