package com.java.bot.processor;

import java.util.Map;

public interface CommandProcessor {

    Command get(String name);

    Map<String, Command> gets();
}
