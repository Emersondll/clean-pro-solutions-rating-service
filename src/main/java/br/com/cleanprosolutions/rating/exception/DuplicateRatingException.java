package br.com.cleanprosolutions.rating.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when a user tries to rate the same contract multiple times.
 *
 * @author Clean Pro Solutions Team
 * @since 1.0.0
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class DuplicateRatingException extends RuntimeException {

    public DuplicateRatingException(final String message) {
        super(message);
    }
}
