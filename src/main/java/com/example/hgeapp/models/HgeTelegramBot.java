package com.example.hgeapp.models;

import com.example.hgeapp.exceptions.ServicesException;
import com.example.hgeapp.exceptions.ValidatorException;
import com.example.hgeapp.services.CityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class HgeTelegramBot extends TelegramLongPollingBot {

    private final static Logger LOG = LoggerFactory.getLogger(HgeTelegramBot.class);
    private final String MESSAGE_HELLO = "Hello %s, use command '/stop' if you want stop bot";
    private String botUsername;
    private String botToken;
    private final CityService cityService;
    private boolean isStart = false;
    private Message message;

    public HgeTelegramBot(CityService cityService, String botUsername, String botToken) {
        this.cityService = cityService;
        this.botUsername = botUsername;
        this.botToken = botToken;
        this.message = new Message();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if(update.hasMessage()){
            message = update.getMessage();
        }
        if (message.isCommand() && message.getText().equals("/start")) {
            sendMessage(message.getChatId().toString(), String.format(MESSAGE_HELLO,message.getChat().getUserName()));
            isStart = true;
        } else if (isStart && !message.isCommand() && message.hasText()) {
            sendingCityNotes(message);
        }
        if (message.isCommand() && message.getText().equals("/stop")) {
            sendMessage(update.getMessage().getChatId().toString(), "good buy.");
            isStart = false;
        }
    }

    private void sendingCityNotes(Message message) {
        try {
            cityService
                    .getCity(message.getText())
                    .getNotes()
                    .stream()
                    .forEach(note -> sendMessage(message.getChatId().toString(), note));
        } catch (ServicesException | ValidatorException ex) {
            sendMessage(message.getChatId().toString(), String.format("Sorry, this  %s city not found.", message.getText()));
        }
    }

    private synchronized void sendMessage(String chatId, String message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(chatId);
        sendMessage.setText(message);
        try {
            execute(sendMessage);
        } catch (TelegramApiException ex) {
            LOG.error("sendMessage: TelegramApiException: " + ex.getMessage());
        }
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }
}
