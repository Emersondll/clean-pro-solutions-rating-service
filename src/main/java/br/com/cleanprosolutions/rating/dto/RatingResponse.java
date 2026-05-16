package br.com.cleanprosolutions.rating.dto;

import java.time.Instant;

/**
 * Response DTO for rating queries.
 *
 * @param id         rating ID
 * @param reviewerId user who submitted the rating
 * @param reviewedId user/contractor who was rated
 * @param contractId associated contract
 * @param score      score (1–5)
 * @param comment    optional comment
 * @param createdAt  creation timestamp
 *
 * @author Clean Pro Solutions Team
 * @since 1.0.0
 */
public record RatingResponse(
        String id,
        String reviewerId,
        String reviewedId,
        String contractId,
        Integer score,
        String comment,
        Instant createdAt
) {}
