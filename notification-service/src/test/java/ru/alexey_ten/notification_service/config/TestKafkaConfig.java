package ru.alexey_ten.notification_service.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;

@TestConfiguration
public class TestKafkaConfig {
    @Bean
    public KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry() {
        return new KafkaListenerEndpointRegistry() {
            @Override
            public void start() {
            }

            @Override
            public void stop() {
            }

            @Override
            public boolean isRunning() {
                return false;
            }
        };
    }
}
