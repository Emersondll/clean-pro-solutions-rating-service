package br.com.cleanprosolutions.rating.service.impl;

import br.com.cleanprosolutions.rating.document.Rating;
import br.com.cleanprosolutions.rating.dto.RatingRequest;
import br.com.cleanprosolutions.rating.dto.RatingResponse;
import br.com.cleanprosolutions.rating.event.dto.RatingCreatedEvent;
import br.com.cleanprosolutions.rating.exception.DuplicateRatingException;
import br.com.cleanprosolutions.rating.mapper.RatingMapper;
import br.com.cleanprosolutions.rating.repository.RatingRepository;
import br.com.cleanprosolutions.rating.service.RatingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

/**
 * Implementation of {@link RatingService}.
 *
 * <p>Mapping responsibilities are delegated to {@link RatingMapper}.</p>
 *
 * @author Clean Pro Solutions Team
 * @since 1.0.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RatingServiceImpl implements RatingService {

    private final RatingRepository repository;
    private final RabbitTemplate rabbitTemplate;
    private final RatingMapper mapper;

    @Value("${rabbitmq.exchange.rating:rating.exchange}")
    private String ratingExchange;

    @Value("${rabbitmq.routing-key.rating-created:rating.created}")
    private String ratingCreatedRoutingKey;

    @Override
    public RatingResponse submitRating(final RatingRequest request) {
        log.info("Submitting rating — reviewer: {}, reviewed: {}, contract: {}",
                request.reviewerId(), request.reviewedId(), request.contractId());

        if (repository.findByContractIdAndReviewerId(request.contractId(), request.reviewerId()).isPresent()) {
            throw new DuplicateRatingException("You have already reviewed this contract.");
        }

        final Rating rating = new Rating(
                UUID.randomUUID().toString(),
                request.reviewerId(),
                request.reviewedId(),
                request.contractId(),
                request.score(),
                request.comment()
        );
        rating.setCreatedAt(Instant.now());

        final Rating saved = repository.save(rating);
        log.info("Rating saved — id: {}", saved.getId());

        publishRatingCreatedEvent(saved);
        return mapper.toResponse(saved);
    }

    @Override
    public List<RatingResponse> findByReviewedId(final String reviewedId) {
        log.info("Fetching ratings for reviewedId: {}", reviewedId);
        return repository.findByReviewedId(reviewedId).stream().map(mapper::toResponse).toList();
    }

    @Override
    public Double calculateAverageScore(final String reviewedId) {
        log.info("Calculating average score for reviewedId: {}", reviewedId);
        final List<Rating> ratings = repository.findByReviewedId(reviewedId);
        return ratings.isEmpty()
                ? 0.0
                : ratings.stream().mapToInt(Rating::getScore).average().orElse(0.0);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Private helpers
    // ─────────────────────────────────────────────────────────────────────────

    private void publishRatingCreatedEvent(final Rating rating) {
        final RatingCreatedEvent event = new RatingCreatedEvent(
                UUID.randomUUID().toString(),
                rating.getId(),
                rating.getReviewedId(),
                rating.getScore(),
                Instant.now()
        );
        log.info("Publishing RatingCreatedEvent — ratingId: {}", rating.getId());
        rabbitTemplate.convertAndSend(ratingExchange, ratingCreatedRoutingKey, event);
    }
}
