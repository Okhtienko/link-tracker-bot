package com.java.bot.processor;

import java.util.Set;

public interface LinkProcessor {

    boolean validate(String url);

    void remove(String url, Long id);

    void save(String url, Long id);

    Set<String> gets(Long id);
}
