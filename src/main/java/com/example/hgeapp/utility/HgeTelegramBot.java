package com.example.hgeapp.utility;

import com.example.hgeapp.exceptions.ServicesException;
import com.example.hgeapp.exceptions.ValidatorException;
import com.example.hgeapp.services.CityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class HgeTelegramBot extends TelegramLongPollingBot {

    private final static Logger log = LoggerFactory.getLogger(HgeTelegramBot.class);
    private final String MESSAGE_HELLO = "Hello, use command '/stop' if you want stop bot";
    private String botUsername;
    private String botToken;
    private final CityService cityService;
    private boolean isStart = false;

    public HgeTelegramBot(CityService cityService) {
        this.cityService = cityService;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.getMessage().isCommand() && update.getMessage().getText().equals("/start")) {
            sendMessage(update.getMessage().getChatId().toString(), MESSAGE_HELLO);
            isStart = true;
        } else if (isStart && update.hasMessage() && !update.getMessage().isCommand() && update.getMessage().hasText()) {
            try {
                cityService
                        .getCity(update.getMessage().getText())
                        .getNotes()
                        .stream()
                        .forEach(note -> sendMessage(update.getMessage().getChatId().toString(), note));
            } catch (ServicesException | ValidatorException ex) {
                sendMessage(update.getMessage().getChatId().toString(), String.format("Sorry, this  %s city not found.",
                        update.getMessage().getText()));
            }
        }
        if (update.getMessage().isCommand() && update.getMessage().getText().equals("/stop")) {
            sendMessage(update.getMessage().getChatId().toString(), "good buy.");
            isStart = false;
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
        return botUsername;
    }

    public void setBotUsername(String botUsername) {
        this.botUsername = botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    public void setBotToken(String botToken) {
        this.botToken = botToken;
    }
}
