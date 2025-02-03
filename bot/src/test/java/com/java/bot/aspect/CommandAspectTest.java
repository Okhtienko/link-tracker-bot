package com.java.bot.aspect;

import com.java.bot.service.UserService;
import com.java.bot.utils.CommandUtils;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.request.SendMessage;
import org.aspectj.lang.ProceedingJoinPoint;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CommandAspectTest {

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
    private CommandAspect commandAspect;

    @Mock
    private ProceedingJoinPoint joinPoint;

    private MockedStatic<CommandUtils> mockedCommandUtils;

    @BeforeEach
    void setUp() {
        mockedCommandUtils = mockStatic(CommandUtils.class);

        when(update.message()).thenReturn(message);
        when(update.message().chat()).thenReturn(chat);
        when(chat.id()).thenReturn(123L);
        when(update.message().from()).thenReturn(user);
        when(user.id()).thenReturn(321L);
    }

    @AfterEach
    void threadDown() {
        mockedCommandUtils.close();
    }

    @Test
    void testCheckPermissionNotBelongCommand() throws Throwable {
        when(update.message().text()).thenReturn("/unknown");
        when(userService.exists(321L)).thenReturn(false);
        when(joinPoint.getArgs()).thenReturn(new Object[]{update});

        mockedCommandUtils.when(() -> CommandUtils.belongCommand("/unknown")).thenReturn(false);

        SendMessage result = (SendMessage) commandAspect.checkPermission(joinPoint);

        assertEquals("Please register using the /start command.", result.getParameters().get("text"));
        verify(joinPoint, never()).proceed();
    }

    @Test
    void testCheckPermissionBelongCommand() throws Throwable {
        when(update.message().text()).thenReturn("/start");
        when(userService.exists(321L)).thenReturn(true);
        when(joinPoint.getArgs()).thenReturn(new Object[]{update});
        when(joinPoint.proceed()).thenReturn(true);

        mockedCommandUtils.when(() -> CommandUtils.belongCommand("/start")).thenReturn(true);

        assertTrue((Boolean) commandAspect.checkPermission(joinPoint));
        verify(joinPoint).proceed();
    }

    @Test
    void testCheckPermissionProceedThrowsException() throws Throwable {
        when(update.message().text()).thenReturn("/start");
        when(userService.exists(321L)).thenReturn(true);
        when(joinPoint.getArgs()).thenReturn(new Object[]{update});
        when(joinPoint.proceed()).thenThrow(new RuntimeException());

        mockedCommandUtils.when(() -> CommandUtils.belongCommand("/start")).thenReturn(true);

        assertThrows(RuntimeException.class, () -> commandAspect.checkPermission(joinPoint));
    }
}
