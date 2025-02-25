package com.java.bot.utils;

import com.java.bot.state.MessageState;
import com.pengrad.telegrambot.request.SendMessage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MessageUtilsTest {

    @Test
    void testPrivateConstructor() {
        InvocationTargetException exception = assertThrows(InvocationTargetException.class, () -> {
            Constructor<MessageUtils> constructor = MessageUtils.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            constructor.newInstance();
        });

        assertThrows(UnsupportedOperationException.class, () -> {
            throw exception.getCause();
        });
    }

    @Test
    void testBuildMessage() {
        Long id = 123L;
        String message = "Message";
        MessageState state = mock(MessageState.class);

        when(state.getMessage()).thenReturn(message);

        SendMessage result = MessageUtils.buildMessage(id, state);

        assertEquals(message, result.getParameters().get("text"));
    }
}
