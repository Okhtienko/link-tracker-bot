package com.java.bot.service;

import com.java.bot.processor.StateProcessor;
import com.java.bot.state.State;
import lombok.Data;
import org.springframework.stereotype.Service;

@Data
@Service
public class StateService implements StateProcessor {

    private State state = State.COMMAND;

    @Override
    public void reset() {
        state = State.COMMAND;
    }
}
