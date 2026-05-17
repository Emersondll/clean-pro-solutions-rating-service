package br.com.cleanprosolutions.rating.event.dto;

import java.time.Instant;

/**
 * Event published when a new rating is created.
 *
 * <p>Consumable by the {@code user-service} to update the general
 * average rating of the reviewed user/contractor.</p>
 *
 * @param eventId    Unique event ID
 * @param ratingId   The created rating ID
 * @param reviewedId The ID of the reviewed user
 * @param score      The rating score given
 * @param timestamp  Event creation timestamp
 *
 * @author Emerson Lima
 * @since 1.0.0
 */
public record RatingCreatedEvent(
        String eventId,
        String ratingId,
        String reviewedId,
        Integer score,
        Instant timestamp
) {}
