package com.fiap.skillup.messaging;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class RabbitConfig {

    public static final String EXCHANGE_RECOMENDACOES = "recomendacoes.exchange";
    public static final String QUEUE_RECOMENDACOES = "recomendacoes.queue";
    public static final String ROUTING_RECOMENDACOES = "recomendacoes.solicitada";

    @Bean
    public TopicExchange recomendacoesExchange() {
        return new TopicExchange(EXCHANGE_RECOMENDACOES, true, false);
    }

    @Bean
    public Queue recomendacoesQueue() {
        return new Queue(QUEUE_RECOMENDACOES, true);
    }

    @Bean
    public Binding bindingRecomendacoes(Queue recomendacoesQueue, TopicExchange recomendacoesExchange) {
        return BindingBuilder
                .bind(recomendacoesQueue)
                .to(recomendacoesExchange)
                .with(ROUTING_RECOMENDACOES);
    }

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, Jackson2JsonMessageConverter converter) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(converter);
        return template;
    }
}
