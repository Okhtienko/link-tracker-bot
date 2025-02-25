package com.java.bot;


import com.java.bot.configuration.ApplicationConfig;
import com.java.bot.handler.LinkTrackerBot;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@RequiredArgsConstructor
@EnableConfigurationProperties(ApplicationConfig.class)
public class BotApplication implements CommandLineRunner {

    private final LinkTrackerBot linkTrackerBot;

    public static void main(String[] args) {
        SpringApplication.run(BotApplication.class, args);
    }

    @Override
    public void run(String... args) {
        linkTrackerBot.start();
    }
}
