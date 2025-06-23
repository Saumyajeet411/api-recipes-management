package com.learn.app.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Error response model for handling API errors")
public class ErrorResponse {
    @Schema(description = "Timestamp of the error occurrence", example = "2023-10-01T12:00:00Z")
    private OffsetDateTime timestamp;
    @Schema(description = "HTTP status code of the error", example = "404")
    private Integer status;
    @Schema(description = "Error type or name", example = "Not Found")
    private String error;
    @Schema(description = "Detailed error message", example = "Recipie not found")
    private String message;
}
