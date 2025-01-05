package com.java.bot.service;

import com.java.bot.processor.LinkProcessor;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LinkService implements LinkProcessor {

    private final Map<Long, Set<String>> links;

    @Override
    public boolean validate(String url) {
        return Stream.of(
            url.matches("^https?://github.com(/.*)?$"),
            url.matches("^https?://stackoverflow.com(/.*)?$")
        ).anyMatch(Boolean::valueOf);
    }

    @Override
    public void remove(String url, Long id) {
        links.computeIfPresent(id, (key, urls) -> {
            urls.remove(url);
            return urls;
        });
    }

    @Override
    public void save(String url, Long id) {
        links.computeIfAbsent(id, urls -> new HashSet<>()).add(url);
    }

    @Override
    public Set<String> gets(Long id) {
        return links.getOrDefault(id, new HashSet<>());
    }
}
