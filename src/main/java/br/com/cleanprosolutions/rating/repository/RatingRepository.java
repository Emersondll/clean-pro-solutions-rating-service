package br.com.cleanprosolutions.rating.repository;

import br.com.cleanprosolutions.rating.document.Rating;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for {@link Rating}.
 *
 * @author Clean Pro Solutions Team
 * @since 1.0.0
 */
@Repository
public interface RatingRepository extends MongoRepository<Rating, String> {

    /**
     * Finds a rating by contract and reviewer to prevent duplicate ratings.
     *
     * @param contractId the contract ID
     * @param reviewerId the reviewer ID
     * @return optional rating
     */
    Optional<Rating> findByContractIdAndReviewerId(String contractId, String reviewerId);

    /**
     * Finds all ratings received by a specific user or contractor.
     *
     * @param reviewedId the ID of the user/contractor being reviewed
     * @return list of ratings
     */
    List<Rating> findByReviewedId(String reviewedId);
}
