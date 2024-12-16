package com.assignmnt.ice.dto;

import com.assignmnt.ice.model.Track;

import java.util.List;

public class CreateTrackResponse {
    public static CreateTrackResponse from(List<Track> tracks) {
        return new CreateTrackResponse();
    }
}
