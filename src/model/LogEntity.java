package model;

import java.time.LocalDateTime;

public record LogEntity(
    LocalDateTime timestamp,
    LogLevel level,
    String message
){}
