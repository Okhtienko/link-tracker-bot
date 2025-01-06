package com.java.bot.handler;

import com.java.bot.bot.Command;
import com.java.bot.bot.MessageProcessor;
import com.java.bot.service.LinkService;
import com.java.bot.service.StateService;
import com.java.bot.state.MessageState;
import com.java.bot.state.State;
import com.java.bot.utils.MessageUtils;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessageProcessorHandler implements MessageProcessor {

    private final LinkService linkService;
    private final StateService stateService;
    private final CommandHandler commandHandler;

    @Override
    public SendMessage process(Update update) {
        State state = stateService.getState();
        Long id = update.message().chat().id();
        Long userId = update.message().from().id();
        String url = update.message().text();

        return switch (state) {
            case TRACK -> handleTrackCommand(id, userId, url);
            case UNTRACK -> handleUntrackCommand(id, userId, url);
            default -> handleUnknownCommand(update);
        };
    }

    private SendMessage handleUntrackCommand(Long id, Long userId, String url) {
        if (!linkService.validate(url)) {
            return MessageUtils.buildMessage(id, MessageState.ERROR);
        }

        if (!linkService.gets(userId).contains(url)) {
            return MessageUtils.buildMessage(id, MessageState.ERROR_UNTRACK);
        }

        linkService.remove(url, userId);
        stateService.reset();

        return MessageUtils.buildMessage(id, MessageState.UNTRACK);
    }

    private SendMessage handleTrackCommand(Long id, Long userId, String url) {
        if (linkService.validate(url)) {
            linkService.save(url, userId);
            stateService.reset();

            return MessageUtils.buildMessage(id, MessageState.TRACK);
        }

        return MessageUtils.buildMessage(id, MessageState.ERROR);
    }

    private SendMessage handleUnknownCommand(Update update) {
        Long id = update.message().chat().id();
        String message = update.message().text();
        Command command = commandHandler.get(message);

        return command.supports(update)
            ? command.handle(update)
            : MessageUtils.buildMessage(id, MessageState.UNKNOWN);
    }
}
