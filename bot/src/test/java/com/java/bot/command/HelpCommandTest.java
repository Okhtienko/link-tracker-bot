package com.java.bot.command;

import com.java.bot.bot.Command;
import com.java.bot.handler.CommandHandler;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Map;
import java.util.stream.Collectors;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HelpCommandTest {

    @Mock
    private Chat chat;

    @Mock
    private Update update;

    @Mock
    private Command command;

    @Mock
    private Message message;

    @InjectMocks
    private HelpCommand helpCommand;

    @Mock
    private CommandHandler commandHandler;

    @Test
    void testCommand() {
        String name = helpCommand.command();
        assertEquals("/help", name);
    }

    @Test
    void testDescription() {
        String description = helpCommand.description();
        assertEquals("displays a command window",description);
    }

    @Test
    void testHandleWithCommands() {
        Map<String, Command> commands = buildCommands();

        setUp();

        when(commandHandler.gets()).thenReturn(commands);

        SendMessage response = helpCommand.handle(update);
        String expected = buildMessage();

        assertEquals(expected, response.getParameters().get("text"));
    }

    @Test
    void testHandleWithNullUpdate() {
        assertThrows(NullPointerException.class, () -> helpCommand.handle(null));
    }

    @Test
    void testHandleWithNullMessage() {
        when(update.message()).thenReturn(null);
        assertThrows(NullPointerException.class, () -> helpCommand.handle(update));
    }

    private void setUp() {
        when(update.message()).thenReturn(message);
        when(update.message().chat()).thenReturn(chat);
        when(update.message().chat().id()).thenReturn(123L);
    }

    private Map<String, Command> buildCommands() {
        return Map.of(
            "/start", command,
            "/help", command,
            "/list", command,
            "/track", command,
            "/untrack", command
        );
    }

    private String buildMessage() {
        return commandHandler.gets()
            .entrySet()
            .stream()
            .map(entry -> entry.getKey() + " - " + entry.getValue().description())
            .collect(Collectors.joining("\n", "Available commands:\n", ""));
    }
}
