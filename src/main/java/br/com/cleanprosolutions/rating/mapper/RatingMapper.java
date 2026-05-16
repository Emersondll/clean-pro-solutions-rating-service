package br.com.cleanprosolutions.rating.mapper;

import br.com.cleanprosolutions.rating.document.Rating;
import br.com.cleanprosolutions.rating.dto.RatingResponse;
import org.springframework.stereotype.Component;

/**
 * Maps between {@link Rating} and {@link RatingResponse}.
 *
 * @author Clean Pro Solutions Team
 * @since 1.0.0
 */
@Component
public class RatingMapper {

    /**
     * Maps a {@link Rating} document to a {@link RatingResponse}.
     */
    public RatingResponse toResponse(final Rating rating) {
        return new RatingResponse(
                rating.getId(),
                rating.getReviewerId(),
                rating.getReviewedId(),
                rating.getContractId(),
                rating.getScore(),
                rating.getComment(),
                rating.getCreatedAt()
        );
    }
}
