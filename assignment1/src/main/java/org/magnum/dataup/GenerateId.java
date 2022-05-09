package org.magnum.dataup;

import org.magnum.dataup.model.Video;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class GenerateId {

    private static final AtomicLong currentId = new AtomicLong(0L);

    private static final Map<Long, Video> videos = new HashMap<Long, Video>();

    public static Video save(Video entity) {
        checkAndSetId(entity);
        videos.put(entity.getId(), entity);
        return entity;
    }

    private static void checkAndSetId(Video entity) {
        if(entity.getId() == 0) {
            entity.setId(currentId.incrementAndGet());
        }
    }
}

