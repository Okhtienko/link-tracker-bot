package com.java.bot.configuration;

import com.java.bot.processor.Command;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
public class CommandConfig {

    @Bean
    public Map<String, Command> commandMap(List<Command> commands) {
        return commands.stream().collect(Collectors.toMap(Command::command, command -> command));
    }
}
