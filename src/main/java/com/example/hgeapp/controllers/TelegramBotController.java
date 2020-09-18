package com.example.hgeapp.controllers;

import com.example.hgeapp.services.CityService;
import com.example.hgeapp.models.HgeTelegramBot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.generics.BotSession;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@RestController
public class TelegramBotController {

    private final static Logger LOG = LoggerFactory.getLogger(TelegramBotController.class);
    private final TelegramBotsApi telegramBotsApi;
    private final CityService cityService;
    private BotSession botSession;

    public TelegramBotController(CityService cityService) {
        this.cityService = cityService;
        telegramBotsApi = new TelegramBotsApi();
        botSession = new DefaultBotSession();
        ApiContextInitializer.init();
    }

    @RequestMapping(value = "/initPollingBot/{name}", method = RequestMethod.POST)
    ResponseEntity<?> initTelegramBot(@PathVariable String name, @RequestBody String token) {
        try {
            if (!botSession.isRunning()) {
                botSession = telegramBotsApi.registerBot(new HgeTelegramBot(cityService, name, token));
                LOG.info(String.format("bot witch %s name registered", name));
            } else {
                LOG.info(String.format("bot witch %s name is running", name));
                return new ResponseEntity<>("\nnon initialised, bot is running", HttpStatus.NOT_ACCEPTABLE);
            }
        } catch (TelegramApiRequestException e) {
            return new ResponseEntity<>(String.format("\nbad, bot is not worked(%s)", e.getMessage()), HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity<>("\ngood, bot registered and worked", HttpStatus.ACCEPTED);
    }
}
