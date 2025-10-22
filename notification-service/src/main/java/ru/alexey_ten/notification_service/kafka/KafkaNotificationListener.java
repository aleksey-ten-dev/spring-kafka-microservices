package ru.alexey_ten.notification_service.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.alexey_ten.common.kafka.UserEvent;
import ru.alexey_ten.notification_service.service.EmailService;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaNotificationListener {

    private final EmailService emailService;

    @KafkaListener(topics = "user-events", groupId = "notification-group")
    public void listen(UserEvent event) {
        log.info("Received user event {}", event);

        switch (event.getOperation()) {
            case "CREATE" ->
                emailService.sendAccountCreatedEmail(event.getEmail(), event.getName());
            case "DELETE" ->
                emailService.sendAccountDeletedEmail(event.getEmail(), event.getName());
            case "UPDATE" ->
                log.info("User updated: {} - {}", event.getEmail(), event.getName());
            default ->
                log.warn("Unknown operation {}", event.getOperation());
        }
    }
}
