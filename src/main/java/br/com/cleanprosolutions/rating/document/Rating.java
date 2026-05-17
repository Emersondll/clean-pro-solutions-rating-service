package br.com.cleanprosolutions.rating.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

/**
 * MongoDB document representing a rating given by a user or contractor.
 *
 * @author Emerson Lima
 * @since 1.0.0
 */
@Document(collection = "ratings")
public class Rating {

    @Id
    private String id;

    private String reviewerId;
    private String reviewedId;
    private String contractId;

    private Integer score;
    private String comment;

    private Instant createdAt;

    public Rating() {
    }

    public Rating(final String id, final String reviewerId, final String reviewedId,
                  final String contractId, final Integer score, final String comment) {
        this.id = id;
        this.reviewerId = reviewerId;
        this.reviewedId = reviewedId;
        this.contractId = contractId;
        this.score = score;
        this.comment = comment;
    }

    public String getId() { return id; }
    public void setId(final String id) { this.id = id; }

    public String getReviewerId() { return reviewerId; }
    public void setReviewerId(final String reviewerId) { this.reviewerId = reviewerId; }

    public String getReviewedId() { return reviewedId; }
    public void setReviewedId(final String reviewedId) { this.reviewedId = reviewedId; }

    public String getContractId() { return contractId; }
    public void setContractId(final String contractId) { this.contractId = contractId; }

    public Integer getScore() { return score; }
    public void setScore(final Integer score) { this.score = score; }

    public String getComment() { return comment; }
    public void setComment(final String comment) { this.comment = comment; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(final Instant createdAt) { this.createdAt = createdAt; }
}
