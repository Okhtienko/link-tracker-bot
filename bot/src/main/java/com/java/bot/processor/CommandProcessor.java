package com.java.bot.processor;

import com.java.bot.bot.Command;
import java.util.Map;

public interface CommandProcessor {

    Command get(String name);

    Map<String, Command> gets();
}
