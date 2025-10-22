package ru.alexey_ten.notification_service.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Profile("prod")
@Slf4j
@RequiredArgsConstructor
public class RealEmailService implements EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String mailFrom;

    @Override
    public void sendEmail(String to, String subject, String body) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(subject);
            message.setText(body);
            message.setFrom(mailFrom);

            mailSender.send(message);
            log.info("Email sent successfully to: {}", to);
        } catch (Exception e) {
            log.error("Error sending email to: {}", to, e);
        }
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
