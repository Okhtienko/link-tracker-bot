package com.java.bot.processor;

public interface UserProcessor {

    void save(Long id);

    boolean exists(Long id);
}
