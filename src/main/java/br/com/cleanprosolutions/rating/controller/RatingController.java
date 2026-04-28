package br.com.cleanprosolutions.rating.controller;

import br.com.cleanprosolutions.rating.document.Rating;
import br.com.cleanprosolutions.rating.dto.RatingRequest;
import br.com.cleanprosolutions.rating.service.RatingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST controller for ratings.
 *
 * @author Clean Pro Solutions Team
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/ratings")
@RequiredArgsConstructor
@Tag(name = "Ratings", description = "Endpoints for creating and retrieving ratings")
public class RatingController {

    private final RatingService service;

    @PostMapping
    @Operation(summary = "Submit a new rating")
    public ResponseEntity<Rating> submitRating(@Valid @RequestBody final RatingRequest request) {
        log.info("POST /ratings");
        return ResponseEntity.status(HttpStatus.CREATED).body(service.submitRating(request));
    }

    @GetMapping("/reviewed/{reviewedId}")
    @Operation(summary = "Get all ratings received by a specific user/contractor")
    public ResponseEntity<List<Rating>> getByReviewedId(@PathVariable final String reviewedId) {
        log.info("GET /ratings/reviewed/{}", reviewedId);
        return ResponseEntity.ok(service.findByReviewedId(reviewedId));
    }

    @GetMapping("/reviewed/{reviewedId}/average")
    @Operation(summary = "Get the average score for a user/contractor")
    public ResponseEntity<Double> getAverageScore(@PathVariable final String reviewedId) {
        log.info("GET /ratings/reviewed/{}/average", reviewedId);
        return ResponseEntity.ok(service.calculateAverageScore(reviewedId));
    }
}
