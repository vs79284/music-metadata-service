package com.assignmnt.ice.model;

import java.time.LocalDate;
import java.util.List;

public record Album(Long id,
                    String title,
                    String artist,
                    LocalDate releaseDate,
                    List<Track> tracks) {

    public boolean isReleased() {
        return releaseDate != null && releaseDate.isBefore(LocalDate.now());
    }
}
