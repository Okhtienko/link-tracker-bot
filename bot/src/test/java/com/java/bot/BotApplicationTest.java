package com.java.bot;

import com.java.bot.handler.LinkTrackerBot;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class BotApplicationTest {

    @Mock
    private LinkTrackerBot linkTrackerBot;

    @InjectMocks
    private BotApplication botApplication;

    @Test
    void testRun() {
        botApplication.run();
        verify(linkTrackerBot).start();
    }

    @Test
    void testContextLoads() {
        assertDoesNotThrow(() -> BotApplication.main(new String[]{}));
    }
}
