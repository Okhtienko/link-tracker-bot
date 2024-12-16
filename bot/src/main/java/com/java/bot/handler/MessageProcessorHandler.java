package com.java.bot.handler;

import com.java.bot.processor.MessageProcessor;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;

@Component
public class MessageProcessorHandler implements MessageProcessor {

    @Override
    public SendMessage process(Update update) {
        return null;
    }
}
