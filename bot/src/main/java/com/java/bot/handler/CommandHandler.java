package com.java.bot.handler;

import com.java.bot.bot.Command;
import com.java.bot.processor.CommandProcessor;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

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
