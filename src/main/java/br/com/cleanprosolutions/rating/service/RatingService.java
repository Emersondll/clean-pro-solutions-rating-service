package br.com.cleanprosolutions.rating.service;

import br.com.cleanprosolutions.rating.document.Rating;
import br.com.cleanprosolutions.rating.dto.RatingRequest;

import java.util.List;

/**
 * Service contract for rating operations.
 *
 * @author Clean Pro Solutions Team
 * @since 1.0.0
 */
public interface RatingService {

    /**
     * Submits a new rating and publishes a RatingCreatedEvent.
     *
     * @param request rating details
     * @return created rating
     */
    Rating submitRating(RatingRequest request);

    /**
     * Lists all ratings received by a specific user/contractor.
     *
     * @param reviewedId ID of the reviewed entity
     * @return list of ratings
     */
    List<Rating> findByReviewedId(String reviewedId);

    /**
     * Calculates the average score for a specific user/contractor.
     *
     * @param reviewedId ID of the reviewed entity
     * @return the average score, or 0.0 if no ratings exist
     */
    Double calculateAverageScore(String reviewedId);
}
