package com.example.hgeapp.utility;

import com.example.hgeapp.repository.CityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.NoSuchElementException;

@Component
public class HgeTelegramBot extends TelegramLongPollingBot {

    private final static Logger log = LoggerFactory.getLogger(HgeTelegramBot.class);
    private final String BOT_USERNAME = "HGEApp_Bot";
    private final String BOT_TOKEN = "1228169883:AAFKD44bENBrpgvwn_4259DPZTKsYE7-rOE";

    @Autowired
    private CityRepository cityRepository;

    @Override
    public void onUpdateReceived(Update update) {
        String message = "Hello";
        if (update.getMessage().isCommand() && update.getMessage().getText().equals("/start")) {
            sendMessage(update.getMessage().getChatId().toString(), message);
        }
        else if (update.hasMessage() && update.getMessage().hasText()) {
            try {
                cityRepository
                    .findByName(update.getMessage().getText())
                    .get()
                    .getNotes()
                    .stream()
                    .forEach(note -> sendMessage(update.getMessage().getChatId().toString(), note));
            } catch (NoSuchElementException ex) {
                message = "Sorry, this " + update.getMessage().getText() + " city not found.";
                sendMessage(update.getMessage().getChatId().toString(), message);
            }
        }
    }

    public synchronized void sendMessage(String chatId, String message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(chatId);
        sendMessage.setText(message);
        try {
            execute(sendMessage);
        } catch (TelegramApiException ex) {
            log.error("sendMessage: error: " + ex.getMessage());
        }
    }

    @Override
    public String getBotUsername() {
        return BOT_USERNAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }
}
