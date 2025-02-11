package com.java.bot.handler;

import com.java.bot.bot.Command;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CommandHandlerTest {

    @Mock
    private Command command;

    @Mock
    private Map<String, Command> commands;

    @InjectMocks
    private CommandHandler commandHandler;

    @Test
    void testGet() {
        when(commands.get(any(String.class))).thenReturn(command);
        assertEquals(command, commandHandler.get("/start"));
    }

    @Test
    void testGetNotExists() {
        when(commands.get(any(String.class))).thenReturn(null);
        assertNull(commandHandler.get("/command"));
    }

    @Test
    void testGets() {
        assertEquals(commands, commandHandler.gets());
    }

    @Test
    void testGetsReturnsNonNull() {
        assertNotNull(commandHandler.gets());
    }

    @Test
    void testGetsEmptyMap() {
        when(commands.isEmpty()).thenReturn(true);
        assertTrue(commandHandler.gets().isEmpty());
    }
}
