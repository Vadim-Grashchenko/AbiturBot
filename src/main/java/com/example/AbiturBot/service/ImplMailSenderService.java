package com.example.AbiturBot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class ImplMailSenderService implements MailSenderService{

    @Autowired
    private JavaMailSender javaMailSender;


    @Value("${spring.mail.username}")
    private String emailFrom;

    public void sendEmail(long id, String sendText, String mail) {

        String subject = "Сообщение от пользователя AbiturBot";

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(emailFrom);
        mailMessage.setTo(mail);
        mailMessage.setSubject(subject);
        mailMessage.setText(sendText);

        javaMailSender.send(mailMessage);
    }
}
