package com.example.DS_Assignment1_B2_Devices.rabbitmq;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${rabbitmq.queue.nameD}")
    private String queue;
    @Value("${rabbitmq.queueJSON.nameD}")
    private String jsonQueue;
    @Value("${rabbitmq.exchange.nameD}")
    private String exchange;
    @Value("${rabbitmq.key.nameD}")
    private String key;
    @Value("${rabbitmq.keyJSON.nameD}")
    private String keyJSON;


    //Spring bean for rabbitmq queue
//
    @Bean
    public Queue queueJSON(){
        return new Queue(jsonQueue);
    }
    @Bean
    public Queue queue(){
        return new Queue(queue);
    }
    @Bean
    public TopicExchange exchange(){
        return new TopicExchange(exchange);
    }

    @Bean
    public Binding binding(){
        return BindingBuilder.bind(queue())
                .to(exchange())
                .with(key);
    }

    //    @Bean
//    public Binding bindingJSON(){
//        return BindingBuilder.bind(queueJSON())
//                .to(exchange())
//                .with(keyJSON);
//    }
//
    @Bean
    public MessageConverter converter(){
        return new Jackson2JsonMessageConverter(objectMapper());
    }
    //
    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper;
    }


//    @Bean
//    public MessageConverter converter(ObjectMapper objectMapper) {
//        return new Jackson2JsonMessageConverter(objectMapper);
//    }
//
//    @Bean
//    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory, MessageConverter messageConverter) {
//        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
//        rabbitTemplate.setMessageConverter(messageConverter);
//        return rabbitTemplate;
//    }
//
//    @Bean
//    public ObjectMapper objectMapper() {
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.registerModule(new JavaTimeModule());
//        return objectMapper;
//    }
//@Bean
//@Primary
//public ObjectMapper objectMapper() {
//    return new ObjectMapper().disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
//            .findAndRegisterModules();
//}



}
