package com.java.bot.command;

import com.java.bot.processor.Command;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;

@Component
public class Untrack implements Command {

    private static final String NAME = "/untrack";
    private static final String DESCRIPTION = "untracks links";

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
