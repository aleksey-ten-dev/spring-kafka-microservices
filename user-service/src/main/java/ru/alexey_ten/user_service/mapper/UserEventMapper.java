package ru.alexey_ten.user_service.mapper;

import org.springframework.stereotype.Component;
import ru.alexey_ten.common.kafka.UserEvent;
import ru.alexey_ten.user_service.model.User;

import java.util.UUID;

@Component
public class UserEventMapper {
    public UserEvent toEvent(User user, String operation) {
        return UserEvent.builder()
                .id(UUID.randomUUID())
                .email(user.getEmail())
                .name(user.getName())
                .operation(operation)
                .build();
    }
}
