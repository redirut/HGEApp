package com.example.hgeapp.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.ApiContextInitializer;

@RestController
public class TelegramController {

    private final static Logger log = LoggerFactory.getLogger(TelegramController.class);

    {
        ApiContextInitializer.init();
        log.info("Telegram bot init.");
    }
}
