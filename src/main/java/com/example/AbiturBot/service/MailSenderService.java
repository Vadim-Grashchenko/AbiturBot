package com.example.AbiturBot.service;

public interface MailSenderService {
    void sendEmail(long id, String sendText, String mail);
}
