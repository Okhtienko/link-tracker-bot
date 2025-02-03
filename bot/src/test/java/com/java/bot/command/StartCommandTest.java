package com.java.bot.command;

import com.java.bot.service.UserService;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.request.SendMessage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StartCommandTest {

    @Mock
    private Chat chat;

    @Mock
    private User user;

    @Mock
    private Update update;

    @Mock
    private Message message;

    @Mock
    private UserService userService;

    @InjectMocks
    private StartCommand startCommand;

    @Test
    void testCommand() {
        String name = startCommand.command();
        assertEquals("/start", name);
    }

    @Test
    void testDescription() {
        String description = startCommand.description();
        assertEquals("user registration", description);
    }

    @Test
    void testHandleAlreadyRegisteredUser() {
        setUp();
        String expected =
            """
            This user is already registered and has access to all bot functions.
            Type /help to see a list of commands.
            """;

        when(userService.exists(321L)).thenReturn(true);

        SendMessage response = startCommand.handle(update);

        assertEquals(expected, response.getParameters().get("text"));
    }

    @Test
    void testHandleNewUserRegistration() {
        setUp();
        String expected =
            """
            Welcome to the Bot! You are now registered and have access to all bot features.
            Type /help to see a list of commands.
            """;

        when(userService.exists(321L)).thenReturn(false);

        SendMessage response = startCommand.handle(update);

        assertEquals(expected, response.getParameters().get("text"));
    }

    private void setUp() {
        when(update.message()).thenReturn(message);
        when(update.message().chat()).thenReturn(chat);
        when(update.message().chat().id()).thenReturn(123L);
        when(update.message().from()).thenReturn(user);
        when(update.message().from().id()).thenReturn(321L);
    }
}
