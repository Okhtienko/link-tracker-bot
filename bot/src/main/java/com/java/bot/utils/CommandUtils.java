package com.java.bot.utils;

import java.util.Set;

public final class CommandUtils {

    private static final Set<String> COMMANDS = Set.of("/start", "/help");

    private CommandUtils() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    public static boolean belongCommand(String command) {
        return COMMANDS.contains(command);
    }
}
