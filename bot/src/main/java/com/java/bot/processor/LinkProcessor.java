package com.java.bot.processor;

import java.util.Set;

public interface LinkProcessor {

    boolean validateUrl(String url);

    boolean existsUrl(String url, Long id);

    void remove(String url, Long id);

    void save(String url, Long id);

    Set<String> gets(Long id);
}
