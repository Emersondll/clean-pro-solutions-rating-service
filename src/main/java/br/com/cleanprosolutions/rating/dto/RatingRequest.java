package br.com.cleanprosolutions.rating.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Request DTO for creating a rating.
 *
 * @param reviewerId ID of the user submitting the review
 * @param reviewedId ID of the user receiving the review
 * @param contractId ID of the service contract
 * @param score      Score from 1 to 5
 * @param comment    Optional textual comment
 *
 * @author Emerson Lima
 * @since 1.0.0
 */
public record RatingRequest(
        @NotBlank(message = "Reviewer ID is required")
        String reviewerId,

        @NotBlank(message = "Reviewed ID is required")
        String reviewedId,

        @NotBlank(message = "Contract ID is required")
        String contractId,

        @NotNull(message = "Score is required")
        @Min(value = 1, message = "Score must be at least 1")
        @Max(value = 5, message = "Score must be at most 5")
        Integer score,

        String comment
) {}
