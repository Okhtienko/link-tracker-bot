package com.java.bot.handler;

import com.java.bot.processor.Command;
import com.pengrad.telegrambot.model.BotCommand;
import org.springframework.stereotype.Component;

@Component
public class MenuHandler {

    private final BotCommand[] commands;

    public MenuHandler(CommandHandler commands) {
        this.commands = convert(commands);
    }

    public BotCommand[] gets() {
        return commands;
    }

    private BotCommand[] convert(CommandHandler commands) {
        return commands.gets()
            .values()
            .stream()
            .map(this::toBotCommand)
            .toArray(BotCommand[]::new);
    }

    private BotCommand toBotCommand(Command command) {
        return new BotCommand(command.command(), command.description());
    }
}
