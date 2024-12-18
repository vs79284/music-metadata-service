package com.assignmnt.ice.model.request;

import java.time.Duration;
import java.time.LocalDate;

public record TrackRequest(String title,
                           Duration duration,
                           LocalDate releaseDate) {
}
