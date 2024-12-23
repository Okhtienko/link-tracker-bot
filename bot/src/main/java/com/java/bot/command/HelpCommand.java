package com.java.bot.command;

import com.java.bot.processor.Command;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;

@Component
public class Help implements Command {

    private static final String NAME = "/help";
    private static final String DESCRIPTION = "displays a command window";

    @Override
    public String command() {
        return NAME;
    }

    @Override
    public String description() {
        return DESCRIPTION;
    }

    @Override
    public SendMessage handle(Update update) {
        return null;
    }
}
