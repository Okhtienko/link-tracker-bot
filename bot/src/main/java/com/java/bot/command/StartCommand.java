package com.java.bot.command;

import com.java.bot.aspect.BotCommand;
import com.java.bot.bot.Command;
import com.java.bot.service.UserService;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component("/start")
@RequiredArgsConstructor
public class StartCommand implements Command {

    private static final String NAME = "/start";
    private static final String DESCRIPTION = "user registration";

    private static final String REGISTERED_MESSAGE =
        """
        This user is already registered and has access to all bot functions.
        Type /help to see a list of commands.
        """;

    private static final String WELCOME_MESSAGE =
        """
        Welcome to the Bot! You are now registered and have access to all bot features.
        Type /help to see a list of commands.
        """;

    private final UserService userService;

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
        Long userId = update.message().from().id();
        String message = buildMessage(userId);

        return new SendMessage(id, message);
    }

    private String buildMessage(Long userId) {
        if (!userService.exists(userId)) {
            userService.save(userId);
            return WELCOME_MESSAGE;
        }

        return REGISTERED_MESSAGE;
    }
}
