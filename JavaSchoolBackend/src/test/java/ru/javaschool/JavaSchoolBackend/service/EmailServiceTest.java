package ru.javaschool.JavaSchoolBackend.service;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class EmailServiceTest {

    @Mock
    JavaMailSender mailSender;

    @InjectMocks
    EmailService emailService;


    @Test
    @DisplayName("Send message test")
    void sendMessageTest() {
        String email = "example@gmail.com";
        String subject = "subject";
        String body = "body";
        emailService.sendMessage(email, subject, body);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("marinakrayukh@gmail.com");
        message.setTo(email);
        message.setText(body);
        message.setSubject(subject);
        verify(mailSender, times(1)).send(message);
    }
}
