package ru.alexey_ten.user_service.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import ru.alexey_ten.common.kafka.UserEvent;

@Component
@RequiredArgsConstructor
public class UserEventProducer {

    private final KafkaTemplate<String, UserEvent> kafkaTemplate;

    @Value("${app.kafka.topic}")
    private String topic;

    public void sendUserEvent(UserEvent event) {
        kafkaTemplate.send(topic, event);
    }
}
