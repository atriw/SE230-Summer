package com.example.ktws.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    private final static String REQUEST_QUEUE_NAME = "requestQueue";

//    private final static String PICTURE_QUEUE_NAME = "pictureQueue";

    private final static String INFO_QUEUE_NAME = "infoQueue";

    private final static String EXCHANGE_NAME = "exchange";

    private final static String REQUEST_QUEUE_ROUTING_KEY = "request";

//    private final static String PICTURE_QUEUE_ROUTING_KEY = "picture";

    private final static String INFO_QUEUE_ROUTING_KEY = "info";

    @Bean
    public Queue requestQueue() {
        return new Queue(REQUEST_QUEUE_NAME);
    }

//    @Bean
//    public Queue pictureQueue() {
//        return new Queue(PICTURE_QUEUE_NAME);
//    }

    @Bean
    public Queue infoQueue() {
        return new Queue(INFO_QUEUE_NAME);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    Binding bindingExchangeRequest(Queue requestQueue, TopicExchange exchange) {
        return BindingBuilder.bind(requestQueue).to(exchange).with(REQUEST_QUEUE_ROUTING_KEY);
    }

//    @Bean
//    Binding bindingExchangePicture(Queue pictureQueue, TopicExchange exchange) {
//        return BindingBuilder.bind(pictureQueue).to(exchange).with(PICTURE_QUEUE_ROUTING_KEY);
//    }

    @Bean
    Binding bindingExchangeInfo(Queue infoQueue, TopicExchange exchange) {
        return BindingBuilder.bind(infoQueue).to(exchange).with(INFO_QUEUE_ROUTING_KEY);
    }

}