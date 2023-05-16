package com.example.AbiturBot.service;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface KeyboardServce {
    SendMessage startKeybord(SendMessage mesage);
    SendMessage universityKeyBoard(SendMessage message);
    SendMessage facultiesKeyBoard(SendMessage message);
}
