package org.example.quanlytrungtam.email;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class EmailConsumer {

    @Autowired
    private EmailService emailService;

    @KafkaListener(topics = "email-topic", groupId = "email-group")
    public void consume(@Payload SendEmailRequest message) {
        try {
            String to = message.getTo();
            String subject = message.getSubject();
            String text = message.getText();
            emailService.sendEmail(to, subject, text);
            System.out.println("gui email thanh cong: " + to);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("gui email that bai: " + message);
        }
    }

    @KafkaListener(topics = "send-new-password-topic", groupId = "find-password-group")
    public void consumeListenerCodePassWord(@Payload SendEmailRequest message) {
        try {
            String to = message.getTo();
            String subject = message.getSubject();
            String text = message.getText();
            emailService.sendEmail(to, subject, text);
            System.out.println("gui email thanh cong: " + to);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("gui email that bai: " + message);
        }
    }

    @KafkaListener(topics = "send-code-password-topic", groupId = "find-password-group")
    public void consumeListenerNewPassWord(@Payload SendEmailRequest message) {
        try {
            String to = message.getTo();
            String subject = message.getSubject();
            String text = message.getText();
            emailService.sendEmail(to, subject, text);
            System.out.println("gui email thanh cong: " + to);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("gui email that bai: " + message);
        }
    }
}
