package com.java.bot.command;

import com.java.bot.service.StateService;
import com.java.bot.state.State;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TrackCommandTest {

    @Mock
    private Chat chat;

    @Mock
    private Update update;

    @Mock
    private Message message;

    @Mock
    private StateService stateService;

    @InjectMocks
    private TrackCommand trackCommand;

    @Test
    void testCommand() {
        String name = trackCommand.command();
        assertEquals("/track", name);
    }

    @Test
    void testDescription() {
        String description = trackCommand.description();
        assertEquals("tracks links", description);
    }

    @Test
    void testHandle() {
        setUp();
        String expected = "Enter URL to track.";

        SendMessage response = trackCommand.handle(update);

        verify(stateService).setState(State.TRACK);

        assertEquals(expected, response.getParameters().get("text"));
    }

    private void setUp() {
        when(update.message()).thenReturn(message);
        when(update.message().chat()).thenReturn(chat);
        when(update.message().chat().id()).thenReturn(123L);
    }
}
