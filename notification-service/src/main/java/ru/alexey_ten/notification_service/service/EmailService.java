package ru.alexey_ten.notification_service.service;

public interface EmailService {
    void sendEmail(String to, String subject, String body);
    void sendAccountCreatedEmail(String email, String name);
    void sendAccountDeletedEmail(String email, String name);
    void sendCustomEmail(String email, String subject, String message);
}
