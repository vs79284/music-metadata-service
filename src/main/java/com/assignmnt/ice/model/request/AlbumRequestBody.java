package com.assignmnt.ice.model.request;

import java.time.LocalDate;

public record AlbumRequestBody(String title, String artist, LocalDate releaseDate) {

}
