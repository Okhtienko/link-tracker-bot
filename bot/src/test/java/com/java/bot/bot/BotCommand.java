package com.java.bot.bot;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

public class BotCommand implements Command{

    @Override
    public String command() {
        return "/start";
    }

    @Override
    public String description() {
        return "description";
    }

    @Override
    public SendMessage handle(Update update) {
        return new SendMessage(123L, "message");
    }
}
