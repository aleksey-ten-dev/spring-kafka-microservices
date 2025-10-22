package ru.alexey_ten.notification_service.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class EmailServiceContextTest {

    @Autowired
    private EmailService emailService;

    @Test
    void testContextLoads() {
        assertNotNull(emailService, "EmailService should be injected");
    }

    @Test
    void testMockEmailServiceIsUsed() {
        assertInstanceOf(MockEmailService.class, emailService, "Should use MockEmailService in test profile");
    }
}
