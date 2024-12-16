package com.assignmnt.ice.dto;

import com.assignmnt.ice.entity.Album;

public class CreateAlbumResponse {
    public static CreateAlbumResponse from(Album track) {
        return new CreateAlbumResponse();
    }
}
