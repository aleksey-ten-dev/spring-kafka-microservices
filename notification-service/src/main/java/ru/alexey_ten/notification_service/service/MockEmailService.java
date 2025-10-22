package ru.alexey_ten.notification_service.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Primary
@Profile({"default", "docker", "test"})
@Slf4j
public class MockEmailService implements EmailService {

    @Override
    public void sendEmail(String to, String subject, String body) {
        log.info("=== MOCK EMAIL SENT ===");
        log.info("To: {}", to);
        log.info("Subject: {}", subject);
        log.info("Body: {}", body);
        log.info("=======================");
    }

    @Override
    public void sendAccountCreatedEmail(String email, String name) {
        String subject = "Добро пожаловать!";
        String body = String.format("""
                Здравствуйте, %s!
                Ваш аккаунт был успешно создан.
                """, name != null ? name : "пользователь");

        sendEmail(email, subject, body);
    }

    @Override
    public void sendAccountDeletedEmail(String email, String name) {
        String subject = "Аккаунт удален";
        String body = String.format("""
                Здравствуйте, %s!
                Ваш аккаунт был удален.
                """, name != null ? name : "пользователь");

        sendEmail(email, subject, body);
    }

    @Override
    public void sendCustomEmail(String email, String subject, String message) {
        String body = String.format("""
                Здравствуйте!
                %s
                """, message);

        sendEmail(email, subject, body);
    }
}
