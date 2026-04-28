package br.com.cleanprosolutions.rating.service.impl;

import br.com.cleanprosolutions.rating.document.Rating;
import br.com.cleanprosolutions.rating.dto.RatingRequest;
import br.com.cleanprosolutions.rating.event.dto.RatingCreatedEvent;
import br.com.cleanprosolutions.rating.exception.DuplicateRatingException;
import br.com.cleanprosolutions.rating.repository.RatingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit tests for {@link RatingServiceImpl}.
 *
 * @author Clean Pro Solutions Team
 * @since 1.0.0
 */
@ExtendWith(MockitoExtension.class)
class RatingServiceImplTest {

    @Mock
    private RatingRepository repository;

    @Mock
    private RabbitTemplate rabbitTemplate;

    @InjectMocks
    private RatingServiceImpl service;

    private RatingRequest request;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(service, "ratingExchange", "rating.exchange");
        ReflectionTestUtils.setField(service, "ratingCreatedRoutingKey", "rating.created");

        request = new RatingRequest("reviewer-1", "reviewed-2", "contract-1", 5, "Great service!");
    }

    @Test
    @DisplayName("shouldSubmitRatingAndPublishEvent")
    void shouldSubmitRatingAndPublishEvent() {
        when(repository.findByContractIdAndReviewerId("contract-1", "reviewer-1"))
                .thenReturn(Optional.empty());

        final Rating savedRating = new Rating("rating-1", "reviewer-1", "reviewed-2", "contract-1", 5, "Great service!");
        when(repository.save(any(Rating.class))).thenReturn(savedRating);

        final Rating result = service.submitRating(request);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo("rating-1");
        assertThat(result.getScore()).isEqualTo(5);

        verify(repository).save(any(Rating.class));
        verify(rabbitTemplate).convertAndSend(
                eq("rating.exchange"),
                eq("rating.created"),
                any(RatingCreatedEvent.class)
        );
    }

    @Test
    @DisplayName("shouldThrowExceptionWhenDuplicateRating")
    void shouldThrowExceptionWhenDuplicateRating() {
        when(repository.findByContractIdAndReviewerId("contract-1", "reviewer-1"))
                .thenReturn(Optional.of(new Rating()));

        assertThatThrownBy(() -> service.submitRating(request))
                .isInstanceOf(DuplicateRatingException.class)
                .hasMessageContaining("already reviewed");
    }

    @Test
    @DisplayName("shouldCalculateAverageScore")
    void shouldCalculateAverageScore() {
        final Rating r1 = new Rating("r1", "r-1", "reviewed-2", "c1", 4, "");
        final Rating r2 = new Rating("r2", "r-2", "reviewed-2", "c2", 5, "");

        when(repository.findByReviewedId("reviewed-2")).thenReturn(List.of(r1, r2));

        final Double average = service.calculateAverageScore("reviewed-2");

        assertThat(average).isEqualTo(4.5);
    }

    @Test
    @DisplayName("shouldReturnZeroWhenNoRatings")
    void shouldReturnZeroWhenNoRatings() {
        when(repository.findByReviewedId("reviewed-2")).thenReturn(List.of());

        final Double average = service.calculateAverageScore("reviewed-2");

        assertThat(average).isEqualTo(0.0);
    }
}
