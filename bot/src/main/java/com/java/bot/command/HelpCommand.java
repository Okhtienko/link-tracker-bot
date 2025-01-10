package com.java.bot.command;

import com.java.bot.aspect.BotCommand;
import com.java.bot.bot.Command;
import com.java.bot.handler.CommandHandler;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component("/help")
public class HelpCommand implements Command {

    private static final String NAME = "/help";
    private static final String HEADER = "Available commands:";
    private static final String DESCRIPTION = "displays a command window";

    private final CommandHandler commandHandler;

    @Autowired
    public HelpCommand(@Lazy CommandHandler commandHandler) {
        this.commandHandler = commandHandler;
    }

    @Override
    public String command() {
        return NAME;
    }

    @Override
    public String description() {
        return DESCRIPTION;
    }

    @Override
    @BotCommand
    public SendMessage handle(Update update) {
        Long id = update.message().chat().id();
        String message = buildMessage();

        return new SendMessage(id, message);
    }

    private String buildMessage() {
        return commandHandler.gets()
            .entrySet()
            .stream()
            .map(entry -> entry.getKey() + " - " + entry.getValue().description())
            .collect(Collectors.joining("\n", HEADER + "\n", ""));
    }
}
