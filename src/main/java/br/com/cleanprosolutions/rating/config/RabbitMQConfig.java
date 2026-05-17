package br.com.cleanprosolutions.rating.config;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RabbitMQ configuration for the rating service.
 *
 * <p>Configures the exchange to publish {@code RatingCreated} events.</p>
 *
 * @author Emerson Lima
 * @since 1.0.0
 */
@Configuration
public class RabbitMQConfig {

    @Value("${rabbitmq.exchange.rating:rating.exchange}")
    private String ratingExchange;

    @Bean
    public TopicExchange ratingExchange() {
        return new TopicExchange(ratingExchange, true, false);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
        final RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jsonMessageConverter());
        return template;
    }
}
