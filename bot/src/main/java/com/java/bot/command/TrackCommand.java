package com.java.bot.command;

import com.java.bot.bot.Command;
import com.java.bot.service.StateService;
import com.java.bot.state.State;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component("/track")
@RequiredArgsConstructor
public class TrackCommand implements Command {

    private static final String NAME = "/track";
    private static final String DESCRIPTION = "tracks links";
    private static final String MESSAGE = "Enter URL to track.";

    private final StateService stateService;

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
        Long id = update.message().chat().id();
        stateService.setState(State.TRACK);

        return new SendMessage(id, MESSAGE);
    }
}
