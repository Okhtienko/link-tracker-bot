package com.java.bot.command;

import com.java.bot.service.LinkService;
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
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ListCommandTest {

    @Mock
    private Chat chat;

    @Mock
    private User user;

    @Mock
    private Update update;

    @Mock
    private Message message;

    @Mock
    private LinkService linkService;

    @InjectMocks
    private ListCommand listCommand;

    @Test
    void testCommand() {
        String name = listCommand.command();
        assertEquals("/list", name);
    }

    @Test
    void testDescription() {
        String description = listCommand.description();
        assertEquals("displays list of tracked references", description);
    }

    @Test
    void testHandleUrlsWhenExist() {
        setUp();

        Set<String> urls = buildUrls();

        when(linkService.gets(321L)).thenReturn(urls);

        SendMessage response = listCommand.handle(update);
        String expected = urls.stream().collect(Collectors.joining("\n", "Tracked links:\n", ""));

        assertEquals(expected, response.getParameters().get("text"));
    }

    @Test
    void testHandleEmptyUrls() {
        setUp();

        when(linkService.gets(321L)).thenReturn(new HashSet<>());

        SendMessage response = listCommand.handle(update);

        assertEquals("No tracked links.", response.getParameters().get("text"));
    }

    private void setUp() {
        when(update.message()).thenReturn(message);
        when(update.message().chat()).thenReturn(chat);
        when(update.message().chat().id()).thenReturn(123L);
        when(update.message().from()).thenReturn(user);
        when(update.message().from().id()).thenReturn(321L);
    }

    private Set<String> buildUrls() {
        return Set.of(
            "https://github.com/sanyarnd/tinkoff-java-course-2023/",
            "https://stackoverflow.com/questions/1642028/what-is-the-operator-in-c"
        );
    }
}
