package ru.alexey_ten.notification_service.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import ru.alexey_ten.notification_service.config.TestKafkaConfig;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("prod")
@Import(TestKafkaConfig.class)
public class RealEmailServiceIntegrationTest {

    @Autowired
    private EmailService emailService;

    @Test
    void testRealEmailServiceIsUsed() {
        assertInstanceOf(RealEmailService.class, emailService, "Should use RealEmailService in test profile");
    }
}
