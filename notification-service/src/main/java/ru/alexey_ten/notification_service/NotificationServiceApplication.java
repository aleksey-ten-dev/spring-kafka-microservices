package ru.alexey_ten.notification_service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.concurrent.TimeUnit;

@SpringBootApplication
@Slf4j
public class NotificationServiceApplication {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public static void main(String[] args) {
        SpringApplication.run(NotificationServiceApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void checkKafkaConnection() {
        log.info("Checking kafka connection...");
        try {
            kafkaTemplate.send("health-check", "test").get(10, TimeUnit.SECONDS);
        } catch (Exception e) {
            log.warn("Kafka connection check failed: {}", e.getMessage());
        }
    }
}
