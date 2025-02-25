package com.java.bot.handler;

import com.java.bot.bot.Command;
import com.pengrad.telegrambot.model.BotCommand;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MenuHandlerTest {

    @Mock
    private Command command;

    @Mock
    private CommandHandler commandHandler;

    @Test
    void testGets() {
        Map<String, Command> commands = buildCommands();

        when(commandHandler.gets()).thenReturn(commands);

        MenuHandler menuHandler = new MenuHandler(commandHandler);

        BotCommand[] botCommands = menuHandler.gets();

        assertEquals(commands.size(), botCommands.length);
    }

    @Test
    void testGetsEmpty() {
        when(commandHandler.gets()).thenReturn(new HashMap<>());

        MenuHandler menuHandler = new MenuHandler(commandHandler);

        BotCommand[] botCommands = menuHandler.gets();

        assertEquals(0, botCommands.length);
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
}
