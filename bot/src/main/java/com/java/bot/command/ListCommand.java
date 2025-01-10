package com.java.bot.command;

import com.java.bot.aspect.BotCommand;
import com.java.bot.bot.Command;
import com.java.bot.service.LinkService;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component("/list")
@RequiredArgsConstructor
public class ListCommand implements Command {

    private static final String NAME = "/list";
    private static final String HEADER = "Tracked links:";
    private static final String MESSAGE = "No tracked links.";
    private static final String DESCRIPTION = "displays list of tracked references";

    private final LinkService linkService;

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

    public String buildMessage(Long userId) {
        Set<String> urls = linkService.gets(userId);
        return urls.isEmpty()
            ? MESSAGE
            : urls.stream().collect(Collectors.joining("\n", HEADER + "\n", ""));
    }
}
