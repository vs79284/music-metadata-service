package com.assignmnt.ice.exceptions;

import java.time.LocalDateTime;

public record ErrorResponse(int status, String details, String message, LocalDateTime localDateTime) {
}
