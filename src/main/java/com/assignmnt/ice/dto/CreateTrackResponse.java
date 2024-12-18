package com.assignmnt.ice.dto;

import com.assignmnt.ice.model.Track;
import com.assignmnt.ice.entity.Album;
import java.util.List;

public class CreateTrackResponse {
    public static CreateTrackResponse from(Album album) {
        return new CreateTrackResponse();
    }
}
