package com.java.bot.handler;

import com.java.bot.configuration.ApplicationConfig;
import com.pengrad.telegrambot.ExceptionHandler;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.TelegramException;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class LinkTrackerBotTest {

    @Mock
    private Update update;

    @Mock
    private MenuHandler menu;

    @Mock
    private TelegramBot telegramBot;

    @Mock
    private SendMessage sendMessage;

    @Mock
    private BaseRequest baseRequest;

    @Mock
    private ApplicationConfig config;

    @Mock
    private SendResponse sendResponse;

    private LinkTrackerBot linkTrackerBot;

    @Mock
    private TelegramException telegramException;

    @Mock
    private MessageProcessorHandler messageProcessorHandler;

    @BeforeEach
    void setUp() {
        when(config.telegramToken()).thenReturn("testToken");
        when(menu.gets()).thenReturn(new BotCommand[]{});

        linkTrackerBot = new LinkTrackerBot(config,messageProcessorHandler, menu);
        linkTrackerBot.setTelegramBot(telegramBot);
    }

    @Test
    void testProcess() {
        when(messageProcessorHandler.process(update)).thenReturn(sendMessage);
        when(telegramBot.execute(sendMessage)).thenReturn(sendResponse);
        when(sendResponse.isOk()).thenReturn(true);

        int count = linkTrackerBot.process(List.of(update));

        assertEquals(1, count);
    }

    @Test
    void testClose() {
        linkTrackerBot.close();
        verify(telegramBot).shutdown();
    }

    @Test
    void testExecute() {
        linkTrackerBot.execute(baseRequest);
        verify(telegramBot).execute(any(BaseRequest.class));
    }

    @Test
    void testStart() {
        linkTrackerBot.start();
        verify(telegramBot).setUpdatesListener(any(UpdatesListener.class), any(ExceptionHandler.class));
    }

    @Test
    void testUpdatesListener() {
        ArgumentCaptor<UpdatesListener> listenerCaptor = ArgumentCaptor.forClass(UpdatesListener.class);

        linkTrackerBot.start();

        verify(telegramBot).setUpdatesListener(listenerCaptor.capture(), any(ExceptionHandler.class));

        int count = listenerCaptor.getValue().process(List.of(update));

        assertEquals(UpdatesListener.CONFIRMED_UPDATES_ALL, count);
    }

    @Test
    void testHandlerNetworkError() {
        ArgumentCaptor<ExceptionHandler> handlerCaptor = ArgumentCaptor.forClass(ExceptionHandler.class);

        linkTrackerBot.start();

        verify(telegramBot).setUpdatesListener(any(), handlerCaptor.capture());

        handlerCaptor.getValue().onException(telegramException);
    }

    @Test
    void testHandlerTelegramError() {
        ArgumentCaptor<ExceptionHandler> handlerCaptor = ArgumentCaptor.forClass(ExceptionHandler.class);

        when(telegramException.response()).thenReturn(sendResponse);
        when(sendResponse.errorCode()).thenReturn(400);
        when(sendResponse.description()).thenReturn("Bad request");

        linkTrackerBot.start();
        verify(telegramBot).setUpdatesListener(any(), handlerCaptor.capture());

        handlerCaptor.getValue().onException(telegramException);
    }
}
