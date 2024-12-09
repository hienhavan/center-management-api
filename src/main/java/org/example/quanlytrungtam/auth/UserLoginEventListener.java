package org.example.quanlytrungtam.auth;

import org.example.quanlytrungtam.config.jwt.JwtResponse;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class UserLoginEventListener {
    private final RedisTemplate<String, Object> redisTemplate;

    public UserLoginEventListener(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @KafkaListener(topics = "login-topic", groupId = "user-group", containerFactory = "kafkaListenerContainerFactory")
    public void handleLoginEvent(@Payload JwtResponse event) {
        String redisKey = "user:" + event.getId() + ":tokens";
//        redisTemplate.opsForSet().remove(redisKey, "*");
//        redisTemplate.opsForSet().add(redisKey, event.getToken());
//        System.out.println(redisTemplate.keys(redisKey));
        redisTemplate.delete(redisKey);
        redisTemplate.opsForValue().set(redisKey, event.getToken());
    }
}
