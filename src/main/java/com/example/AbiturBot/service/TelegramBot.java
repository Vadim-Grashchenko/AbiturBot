package com.example.AbiturBot.service;

import com.example.AbiturBot.config.BotConfig;
import com.example.AbiturBot.model.Faculty;
import com.example.AbiturBot.model.University;
import com.example.AbiturBot.model.User;
import com.example.AbiturBot.repository.UserRepository;
import com.vdurmont.emoji.EmojiParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class TelegramBot extends TelegramLongPollingBot {

    final BotConfig botConfig;
    private final UniversityService universityService;
    private final KeyboardServce keyboardServce;
    private final MailSenderService mailSenderService;
    String callBackData = null;

    @Autowired
    UserRepository userRepository;

    public TelegramBot(BotConfig botConfig, UniversityService universityService, KeyboardServce keyboardServce, MailSenderService mailSenderService) {
        this.botConfig = botConfig;
        this.universityService = universityService;
        this.keyboardServce = keyboardServce;
        this.mailSenderService = mailSenderService;
        List<BotCommand> listOfCommands = new ArrayList<>();
        listOfCommands.add(new BotCommand("/start", "Приветственное сообщение и начало работы"));
        try {
            this.execute(new SetMyCommands(listOfCommands, new BotCommandScopeDefault(), null));
        }
        catch(TelegramApiException e) {
            log.error("Error settings bot's command list:  " + e.getMessage());
        }
    }
    @Override
    public String getBotUsername() {
        return botConfig.getBotName();
    }

    @Override
    public String getBotToken() {
        return botConfig.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();
            SendMessage message = new SendMessage();

            switch (messageText) {
                case "/start":
                    startCommandReceived(chatId, update.getMessage().getChat().getFirstName());
                    registerUser(update.getMessage());
                    break;
                case "Университеты Республики Беларусь":
                    listUniversityButton(chatId, message);
                    executeMessage(message);
                    break;
                case "Пройти анкетирование":
                    //pool(chatId);
                    break;
                case "Факультеты":
                    listFacultiesButton(chatId, message, callBackData);
                    break;
                case "Отправить сообщение на почту":
                    mailSenderService.sendEmail(1, "Привет", "cixin61181@glumark.com");
                    break;
                default:
                    sendMessage(chatId, "Sorry, command was not recognized");
            }
        } else if (update.hasCallbackQuery()) {
            long chatId = update.getCallbackQuery().getMessage().getChatId();
            SendMessage message = new SendMessage();
            message.setText("Хороший выбор!");
            message.setChatId(String.valueOf(chatId));
            callBackData = update.getCallbackQuery().getData();
            // Изменяем клавиатуру в зависимости от нажатой кнопки
            keyboardServce.universityKeyBoard(message);
            executeMessage(message);
        }
    }

    private void registerUser(Message msg) {
        if (userRepository.findById(msg.getChatId()).isEmpty()) {
            long chatId = msg.getChatId();
            Chat chat = msg.getChat();

            User user = new User();

            user.setChatId(chatId);
            user.setFirstName(chat.getFirstName());
            user.setLastName(chat.getLastName());
            user.setUserName(chat.getUserName());
            user.setRegisteredAt(new Timestamp(System.currentTimeMillis()));

            userRepository.save(user);
            log.info("user saved " + user);
        }
    }

    private void startCommandReceived(long chatId, String name) {
        String answer = EmojiParser.parseToUnicode("Привет, " + name + ", рад познакомиться!" + " :blush: ");
        log.info("Replied to user " + name);
        sendMessage(chatId, answer);
    }

    private void sendMessage(long chatId, String textToSend) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);

        keyboardServce.startKeybord(message);

        try{
            execute(message);
        }
        catch (TelegramApiException e) {
            log.error("Error occured: " + e.getMessage());
        }
    }

    private void listUniversityButton(long chatId, SendMessage message) {

        message.setChatId(String.valueOf(chatId));
        message.setText("Cписок университетов");
        List<University> universityList = universityService.getAll();
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();

        for (int i = 0; i < universityList.size(); i++) {
            List<InlineKeyboardButton> row1 = new ArrayList<>();
            InlineKeyboardButton but = new InlineKeyboardButton();
            but.setText(universityList.get(i).getName());
            but.setCallbackData(String.valueOf(universityList.get(i).getId()));
            row1.add(but);
            rows.add(row1);

            inlineKeyboardMarkup.setKeyboard(rows);
        }
        message.setReplyMarkup(inlineKeyboardMarkup);
    }

    private void listFacultiesButton(long chatId, SendMessage message, String callBackData) {

        message.setChatId(String.valueOf(chatId));
        message.setText("Держи список факультетов");
        University university = universityService.universityById(Long.parseLong(callBackData));
        List<Faculty> faculties = university.getFaculties().stream().distinct().collect(Collectors.toList());
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        for (int i = 0; i < faculties.size(); i++) {
            List<InlineKeyboardButton> row1 = new ArrayList<>();
            InlineKeyboardButton but = new InlineKeyboardButton();
            but.setText(faculties.get(i).getName());
            but.setCallbackData(String.valueOf(faculties.get(i).getId()));
            row1.add(but);
            rows.add(row1);

            inlineKeyboardMarkup.setKeyboard(rows);
        }
        message.setReplyMarkup(inlineKeyboardMarkup);
        executeMessage(message);
    }

    public void sendMessageToEmail(){

    }

    public void executeMessage(SendMessage message) {
        try{
            execute(message);
        }
        catch (TelegramApiException e) {
            log.error("Error occured: " + e.getMessage());
        }
    }
}
