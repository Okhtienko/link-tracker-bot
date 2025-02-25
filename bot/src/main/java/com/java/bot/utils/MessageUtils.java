package com.java.bot.utils;

import com.java.bot.state.MessageState;
import com.pengrad.telegrambot.request.SendMessage;

public final class MessageUtils {

    private MessageUtils() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    public static SendMessage buildMessage(Long id, MessageState type) {
        return new SendMessage(id, type.getMessage());
    }
}
