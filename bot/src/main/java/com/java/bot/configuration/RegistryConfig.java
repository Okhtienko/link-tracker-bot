package com.java.bot.configuration;

import com.java.bot.bot.Command;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RegistryConfig {

    @Bean
    public Map<String, Command> commandMap(List<Command> commands) {
        return commands.stream().collect(Collectors.toMap(Command::command, command -> command));
    }

    @Bean
    public Map<Long, Set<String>> linkMap() {
        return new HashMap<>();
    }
}
