package com.java.bot.handler;

import com.java.bot.processor.Command;
import com.java.bot.processor.CommandProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class CommandHandler implements CommandProcessor {
    private final Map<String, Command> commands;

    @Override
    public Command get(String name) {
        return commands.get(name);
    }

    @Override
    public Map<String, Command> gets() {
        return commands;
    }
}
