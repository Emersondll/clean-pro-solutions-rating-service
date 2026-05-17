package br.com.cleanprosolutions.rating.controller;

import br.com.cleanprosolutions.rating.dto.RatingRequest;
import br.com.cleanprosolutions.rating.dto.RatingResponse;
import br.com.cleanprosolutions.rating.service.RatingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * Unit tests for {@link RatingController}.
 *
 * @author Emerson Lima
 * @since 1.0.0
 */
@ExtendWith(MockitoExtension.class)
class RatingControllerTest {

    @Mock
    private RatingService service;

    @InjectMocks
    private RatingController controller;

    private RatingRequest request;
    private RatingResponse response;

    @BeforeEach
    void setUp() {
        request = new RatingRequest("reviewer-1", "contractor-1", "contract-1", 5, "Excellent!");
        response = new RatingResponse("rating-1", "reviewer-1", "contractor-1", "contract-1", 5, "Excellent!", Instant.now());
    }

    @Test
    @DisplayName("shouldSubmitRatingAndReturn201")
    void shouldSubmitRatingAndReturn201() {
        when(service.submitRating(any(RatingRequest.class))).thenReturn(response);

        final ResponseEntity<RatingResponse> result = controller.submitRating(request);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(result.getBody()).isEqualTo(response);
    }

    @Test
    @DisplayName("shouldGetRatingsByReviewedId")
    void shouldGetRatingsByReviewedId() {
        when(service.findByReviewedId("contractor-1")).thenReturn(List.of(response));

        final ResponseEntity<List<RatingResponse>> result = controller.getByReviewedId("contractor-1");

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).hasSize(1);
    }

    @Test
    @DisplayName("shouldGetAverageScore")
    void shouldGetAverageScore() {
        when(service.calculateAverageScore("contractor-1")).thenReturn(4.8);

        final ResponseEntity<Double> result = controller.getAverageScore("contractor-1");

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(4.8);
    }
}
