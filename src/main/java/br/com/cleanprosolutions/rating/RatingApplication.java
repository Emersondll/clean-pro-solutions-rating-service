package br.com.cleanprosolutions.rating;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Main entry point for the Rating Service.
 *
 * <p>Handles reviews submitted by clients and contractors in
 * the Clean Pro Solutions ecosystem.</p>
 *
 * @author Emerson Lima
 * @version 1.0.0
 * @since 1.0.0
 */
@SpringBootApplication
@EnableDiscoveryClient
public class RatingApplication {

    public static void main(final String[] args) {
        SpringApplication.run(RatingApplication.class, args);
    }
}
