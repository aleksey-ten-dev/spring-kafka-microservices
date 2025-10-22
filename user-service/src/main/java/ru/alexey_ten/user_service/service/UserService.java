package ru.alexey_ten.user_service.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.alexey_ten.user_service.kafka.UserEventProducer;
import ru.alexey_ten.user_service.mapper.UserEventMapper;
import ru.alexey_ten.user_service.model.User;
import ru.alexey_ten.user_service.repository.UserRepository;
import ru.alexey_ten.user_service.service.exception.UserAlreadyExistsException;
import ru.alexey_ten.user_service.service.exception.UserNotFoundException;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final UserEventProducer userEventProducer;
    private final UserEventMapper eventMapper;

    public User createUser(User user) {
        userRepository.findByEmail(user.getEmail())
                .ifPresent(existingUser -> {
                    throw new UserAlreadyExistsException(existingUser.getEmail());
                });

        User savedUser = userRepository.save(user);
        userEventProducer.sendUserEvent(eventMapper.toEvent(savedUser, "CREATE"));
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
        userEventProducer.sendUserEvent(eventMapper.toEvent(updatedUser, "UPDATE"));
        log.info("User updated: {}", updatedUser);

        return updatedUser;
    }

    public void deleteUser(Long id) {
        userRepository.findById(id).ifPresent(user -> {
            userRepository.delete(user);
            userEventProducer.sendUserEvent(eventMapper.toEvent(user, "DELETE"));
            log.info("User deleted: {}", user);
        });
    }
}
