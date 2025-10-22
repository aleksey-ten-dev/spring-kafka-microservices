package ru.alexey_ten.user_service.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.alexey_ten.common.kafka.UserEvent;
import ru.alexey_ten.user_service.kafka.UserEventProducer;
import ru.alexey_ten.user_service.model.User;
import ru.alexey_ten.user_service.repository.UserRepository;
import ru.alexey_ten.user_service.service.exception.UserNotFoundException;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final UserEventProducer userEventProducer;

    public User createUser(User user) {
        User savedUser = userRepository.save(user);
        userEventProducer.sendUserEvent(new UserEvent("CREATE", savedUser.getEmail(), savedUser.getName()));
        log.info("User created: {}", savedUser);

        return savedUser;
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    public User updateUser(Long id, User userDetails) {
        User user = getUserById(id);

        user.setName(userDetails.getName());
        user.setEmail(userDetails.getEmail());

        User updatedUser = userRepository.save(user);
        userEventProducer.sendUserEvent(new UserEvent("UPDATE", updatedUser.getName(), updatedUser.getName()));
        log.info("User updated: {}", updatedUser);

        return updatedUser;
    }

    public void deleteUser(Long id) {
        userRepository.findById(id).ifPresent( user -> {
            userRepository.delete(user);
            userEventProducer.sendUserEvent(new UserEvent("DELETE", user.getEmail(), user.getName()));
            log.info("User deleted: {}", user);
        });
    }
}
