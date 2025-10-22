package ru.alexey_ten.user_service.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import ru.alexey_ten.common.kafka.UserEvent;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserEventProducer {

    private final KafkaTemplate<String, UserEvent> kafkaTemplate;

    @Value("${app.kafka.topic}")
    private String topic;

    public void sendUserEvent(UserEvent event) {
        if (event.getId() == null) {
            event.setId(UUID.randomUUID());
        }
        kafkaTemplate.send(topic, event);
    }
}
