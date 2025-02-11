package com.java.bot.handler;

import com.java.bot.bot.Command;
import com.java.bot.service.LinkService;
import com.java.bot.service.StateService;
import com.java.bot.state.State;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.request.SendMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MessageProcessorHandlerTest {

    @Mock
    private Chat chat;

    @Mock
    private User user;

    @Mock
    private Update update;

    @Mock
    private Command command;

    @Mock
    private Message message;

    @Mock
    private LinkService linkService;

    @Mock
    private StateService stateService;

    @Mock
    private CommandHandler commandHandler;

    @InjectMocks
    private MessageProcessorHandler messageProcessorHandler;

    @BeforeEach
    void setUp() {
        when(update.message()).thenReturn(message);
        when(message.chat()).thenReturn(chat);
        when(chat.id()).thenReturn(123L);
        when(message.from()).thenReturn(user);
        when(user.id()).thenReturn(321L);
        when(message.text()).thenReturn("https://github.com/sanyarnd/tinkoff-java-course-2023/");
    }


    @Test
    void testHandleTrackCommand() {
        when(stateService.getState()).thenReturn(State.TRACK);
        when(linkService.validate(any(String.class))).thenReturn(true);

        doNothing().when(linkService).save(any(), any());
        doNothing().when(stateService).reset();

        SendMessage response = messageProcessorHandler.process(update);

        assertEquals("URL added for tracking.", response.getParameters().get("text"));
    }

    @Test
    void testHandleTrackCommandWithInvalidURL() {
        when(stateService.getState()).thenReturn(State.TRACK);
        when(linkService.validate(any(String.class))).thenReturn(false);

        SendMessage response = messageProcessorHandler.process(update);

        assertEquals(
            "Incorrect URL. Enter the correct URL, the bot supports github and stackoverflow URLs.",
            response.getParameters().get("text")
        );
    }

    @Test
    void testHandleUntrackCommandWithInvalidURL() {
        when(stateService.getState()).thenReturn(State.UNTRACK);
        when(linkService.validate(any(String.class))).thenReturn(false);

        SendMessage response = messageProcessorHandler.process(update);

        assertEquals(
            "Incorrect URL. Enter the correct URL, the bot supports github and stackoverflow URLs.",
            response.getParameters().get("text")
        );
    }

    @Test
    void testHandleUntrackCommand() {
        Set<String> urls = mock(Set.class);

        when(stateService.getState()).thenReturn(State.UNTRACK);
        when(linkService.validate(any(String.class))).thenReturn(true);
        when(linkService.gets(any(Long.class))).thenReturn(urls);
        when(urls.contains(any(String.class))).thenReturn(true);

        SendMessage response = messageProcessorHandler.process(update);

        assertEquals("URL removed from tracking.", response.getParameters().get("text"));
    }

    @Test
    void testUntrackCommandNotTracked() {
        Set<String> urls = mock(Set.class);

        when(stateService.getState()).thenReturn(State.UNTRACK);
        when(linkService.validate(any(String.class))).thenReturn(true);
        when(linkService.gets(any(Long.class))).thenReturn(urls);
        when(urls.contains(any(String.class))).thenReturn(false);

        SendMessage response = messageProcessorHandler.process(update);

        assertEquals("URL is not tracked.", response.getParameters().get("text"));
    }

    @Test
    void testHandleUnknownCommand() {
        when(stateService.getState()).thenReturn(State.COMMAND);
        when(commandHandler.get(any(String.class))).thenReturn(command);
        when(command.supports(any(Update.class))).thenReturn(false);

        SendMessage response = messageProcessorHandler.process(update);

        assertEquals(
            "Unknown command. Type /help displays a command window.",
            response.getParameters().get("text")
        );
    }

    @Test
    void testHandleCommandWithValidCommand() {
        when(stateService.getState()).thenReturn(State.COMMAND);
        when(commandHandler.get(any(String.class))).thenReturn(command);
        when(command.supports(any(Update.class))).thenReturn(true);
        when(command.handle(update)).thenReturn(mock(SendMessage.class));

        SendMessage response = messageProcessorHandler.process(update);

        assertNotNull(response);
        verify(command).supports(update);
        verify(command).handle(update);
    }
}
