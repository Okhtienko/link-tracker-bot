package com.java.bot.handler;

import com.java.bot.configuration.ApplicationConfig;
import com.java.bot.processor.Bot;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.TelegramException;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.response.BaseResponse;
import java.util.List;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LinkTrackerBot implements Bot {

    private final TelegramBot telegramBot;
    private final MessageProcessorHandler messageProcessorHandler;

    @Autowired
    public LinkTrackerBot(ApplicationConfig config, MessageProcessorHandler messageProcessorHandler) {
        this.messageProcessorHandler = messageProcessorHandler;
        this.telegramBot = new TelegramBot(config.telegramToken());
    }

    @Override
    public <T extends BaseRequest<T, R>, R extends BaseResponse> void execute(BaseRequest<T, R> request) {
        telegramBot.execute(request);
    }

    @Override
    public int process(List<Update> updates) {
        return (int) updates.stream()
            .map(messageProcessorHandler::process)
            .filter(Objects::nonNull)
            .filter(message -> telegramBot.execute(message).isOk())
            .count();
    }

    @Override
    public void start() {
        telegramBot.setUpdatesListener(updates -> {
            process(updates);
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        }, this::handleError);
    }

    @Override
    public void close() {
        telegramBot.shutdown();
    }

    private void handleError(TelegramException exception) {
        if (exception.response() != null) {
            log.error(
                "Telegram error: {} - {}", exception.response().errorCode(), exception.response().description()
            );
        } else {
            log.error("Network error: {}", exception.getMessage());
        }
    }
}
