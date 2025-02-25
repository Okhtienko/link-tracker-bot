package com.java.bot.service;

import com.java.bot.state.State;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class StateServiceTest {

    @InjectMocks
    private StateService stateService;

    @Test
    void testResetState() {
        stateService.reset();
        assertEquals(State.COMMAND, stateService.getState());
    }
}
