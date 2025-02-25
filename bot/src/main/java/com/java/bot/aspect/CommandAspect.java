package com.java.bot.aspect;

import com.java.bot.service.UserService;
import com.java.bot.utils.CommandUtils;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class CommandAspect {

    private final UserService userService;

    private static final String MESSAGE = "Please register using the /start command.";

    @Around("@annotation(BotCommand)")
    public Object checkPermission(ProceedingJoinPoint joinPoint) throws Throwable {
        Update update = (Update) joinPoint.getArgs()[0];
        Long id = update.message().chat().id();

        if (!hasPermission(update)) {
            return new SendMessage(id, MESSAGE);
        }

        return joinPoint.proceed();
    }

    private boolean hasPermission(Update update) {
        Long userId = update.message().from().id();
        String command = update.message().text();

        return userService.exists(userId) || CommandUtils.commandAllowedWithoutAuth(command);
    }
}
