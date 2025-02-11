package com.java.bot.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class CommandUtilsTest {

    @Test
    void testPrivateConstructor() {
        InvocationTargetException exception = assertThrows(InvocationTargetException.class, () -> {
            Constructor<CommandUtils> constructor = CommandUtils.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            constructor.newInstance();
        });

        assertThrows(UnsupportedOperationException.class, () -> {
            throw exception.getCause();
        });
    }

    @Test
    void testBelongCommandWithValidCommand() {
        String command = "/start";
        assertTrue(CommandUtils.belongCommand(command));
    }

    @Test
    void testBelongCommandWithInvalidCommand() {
        String command = "/invalid";
        assertFalse(CommandUtils.belongCommand(command));
    }

    @Test
    void testBelongCommandWithEmptyCommand() {
        String command = "";
        assertFalse(CommandUtils.belongCommand(command));
    }
}
