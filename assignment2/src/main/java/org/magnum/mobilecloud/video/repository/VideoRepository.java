package org.magnum.mobilecloud.video.repository;

import org.magnum.mobilecloud.video.client.VideoSvcApi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {

    public Collection<Video> findByName(@Param(VideoSvcApi.TITLE_PARAMETER) String title);

    public Collection<Video> findByDurationLessThan(@Param(VideoSvcApi.DURATION_PARAMETER) long duration);

}
