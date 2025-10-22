package ru.alexey_ten.notification_service.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Slf4j
public class EmailServiceIntegrationTest {

    @Autowired
    private EmailService emailService;

    @Test
    void testSendAccountCreatedEmail_Integration() {
        assertDoesNotThrow(() -> emailService.sendAccountCreatedEmail("test@example.com", "Иван Иванов"));
    }

    @Test
    void testSendAccountDeletedEmail_Integration() {
        assertDoesNotThrow(() -> emailService.sendAccountDeletedEmail("delete@example.com", "Петр Петров"));
    }

    @Test
    void testSendCustomEmail_Integration() {
        assertDoesNotThrow(() -> emailService.sendCustomEmail("custom@example.com", "Тест", "Сообщение"));
    }
}
