package com.java.bot.bot;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CommandTest {

    @Mock
    private Update update;

    @Mock
    private Message message;

    @Spy
    private BotCommand command;

    @Test
    void testSupports() {
        when(update.message()).thenReturn(message);
        when(update.message().text()).thenReturn("/start");

        assertTrue(command.supports(update));
    }
}
