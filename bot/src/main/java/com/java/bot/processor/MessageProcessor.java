package com.java.bot.processor;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

public interface MessageProcessor {

    SendMessage process(Update update);
}
