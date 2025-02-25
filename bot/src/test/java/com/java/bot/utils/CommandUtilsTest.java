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
    void testCommandAllowedWithoutAuth() {
        String command = "/start";
        assertTrue(CommandUtils.commandAllowedWithoutAuth(command));
    }

    @Test
    void testCommandAllowedWithoutAuthWithInvalidCommand() {
        String command = "/invalid";
        assertFalse(CommandUtils.commandAllowedWithoutAuth(command));
    }

    @Test
    void testCommandAllowedWithoutAuthWithEmptyCommand() {
        String command = "";
        assertFalse(CommandUtils.commandAllowedWithoutAuth(command));
    }
}
