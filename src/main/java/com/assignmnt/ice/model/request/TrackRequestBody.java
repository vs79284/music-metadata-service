package com.assignmnt.ice.model.request;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Date;

public record TrackRequestBody(String title,
                               Duration duration,
                               LocalDate releaseDate) {
}
