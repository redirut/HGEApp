package com.example.hgeapp.controllers;

import com.example.hgeapp.services.CityService;
import com.example.hgeapp.utility.HgeTelegramBot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.generics.LongPollingBot;
import org.telegram.telegrambots.meta.generics.WebhookBot;
import org.telegram.telegrambots.starter.TelegramBotInitializer;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TelegramBotController {

    private final static Logger LOG = LoggerFactory.getLogger(TelegramBotController.class);
    private List<LongPollingBot> pollingBots = new ArrayList<>();
    private List<WebhookBot> webhookBots = new ArrayList<>();
    private final CityService cityService;

    public TelegramBotController(CityService cityService) {
        this.cityService = cityService;
        ApiContextInitializer.init();
    }

    @RequestMapping(value = "/init/{name}", method = RequestMethod.POST)
    ResponseEntity<?> initTelegramBot(@PathVariable String name, @RequestBody String token) {
        TelegramBotInitializer telegramBotInitializer = new TelegramBotInitializer(new TelegramBotsApi(), pollingBots, webhookBots);
        addPollingBot(name, token);
        telegramBotInitializer.afterPropertiesSet();
        return new ResponseEntity<>("\ngood bot is worked",HttpStatus.ACCEPTED);
    }

    private void addPollingBot(String name, String token) {
        HgeTelegramBot bot = new HgeTelegramBot(cityService);
        bot.setBotUsername(name);
        bot.setBotToken(token);
        pollingBots.add(bot);
    }
}
