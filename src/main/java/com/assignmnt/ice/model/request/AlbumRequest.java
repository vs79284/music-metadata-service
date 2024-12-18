package com.assignmnt.ice.model.request;

import java.time.LocalDate;

public record AlbumRequest(String title, String artist, LocalDate releaseDate) {
}
