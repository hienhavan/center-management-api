package org.example.quanlytrungtam.config.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.example.quanlytrungtam.config.jwt.JwtResponse;
import org.example.quanlytrungtam.email.SendEmailRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConfig {

    @Bean
    public ProducerFactory<String, Object> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public ConsumerFactory<String, JwtResponse> consumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092"); // Địa chỉ Kafka broker
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "user-group"); // ID của group consumer
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class); // Deserializer cho key
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class); // Deserializer cho value
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*"); // Cho phép deserializing các lớp trong package bất kỳ
        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), new JsonDeserializer<>(JwtResponse.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, JwtResponse> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, JwtResponse> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }

    @Bean
    public ConsumerFactory<String, SendEmailRequest> consumerFactoryEmail() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092"); // Địa chỉ Kafka broker
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "user-group"); // ID của group consumer
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class); // Deserializer cho key
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class); // Deserializer cho value
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*"); // Cho phép deserializing các lớp trong package bất kỳ
        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), new JsonDeserializer<>(SendEmailRequest.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, SendEmailRequest> kafkaListenerContainerFactoryEmail() {
        ConcurrentKafkaListenerContainerFactory<String, SendEmailRequest> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactoryEmail());
        return factory;
    }
}
