package com.java.bot.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LinkServiceTest {

    @InjectMocks
    private LinkService linkService;

    @Mock
    private Map<Long, Set<String>> links;

    @Test
    void testSaveUrl() {
        Long id = 123L;
        Set<String> urls = new HashSet<>();
        String url = "https://github.com/sanyarnd/tinkoff-java-course-2023/";

        when(links.get(id)).thenReturn(urls);
        when(links.computeIfAbsent(eq(id), any())).thenReturn(urls);

        linkService.save(url, id);

        assertEquals(urls, links.get(id));
        verify(links).computeIfAbsent(eq(id), any());
    }

    @Test
    void testSaveUrlWhenUrlAlreadyExists() {
        Long id = 123L;
        Set<String> urls = new HashSet<>();
        String url = "https://github.com/sanyarnd/tinkoff-java-course-2023/";

        urls.add(url);

        when(links.computeIfAbsent(eq(id), any())).thenReturn(urls);

        linkService.save(url, id);

        assertEquals(1, urls.size());
        verify(links).computeIfAbsent(eq(id), any());
    }

    @Test
    void testRemoveUrl() {
        Long id = 123L;
        Set<String> urls = new HashSet<>();
        String url = "https://github.com/sanyarnd/tinkoff-java-course-2023/";

        urls.add(url);

        when(links.computeIfPresent(eq(id), any())).thenAnswer(invocation -> {
            BiFunction<Long, Set<String>, Set<String>> function = invocation.getArgument(1);
            return function.apply(id, urls);
        });

        linkService.remove(url, id);

        assertFalse(urls.contains(url));
        verify(links).computeIfPresent(eq(id), any());
    }

    @Test
    void testRemoveUrlNotPresent() {
        Long id = 123L;
        String url = "https://github.com/sanyarnd/tinkoff-java-course-2023/";

        when(links.computeIfPresent(eq(id), any())).thenReturn(new HashSet<>());

        linkService.remove(url, id);

        assertNull(links.get(id));
        verify(links).computeIfPresent(eq(id), any());
    }

    @Test
    void testValidateUrl() {
        String url = "https://github.com/sanyarnd/tinkoff-java-course-2023/";
        assertTrue(linkService.validate(url));
    }

    @Test
    void testNotValidateUrl() {
        String url = "https://invalid.com/";
        assertFalse(linkService.validate(url));
    }

    @Test
    void testValidateUrlWithEmpty() {
        String url = "";
        assertFalse(linkService.validate(url));
    }

    @Test
    void testGetUrls() {
        Long id = 123L;
        Set<String> urls = buildUrls();

        when(links.getOrDefault(id, new HashSet<>())).thenReturn(urls);

        Set<String> response = linkService.gets(id);

        assertEquals(urls, response);
        verify(links).getOrDefault(id, new HashSet<>());
    }

    @Test
    void testGetUrlsWhenIdNotFound() {
        Long id = 321L;

        when(links.getOrDefault(id, new HashSet<>())).thenReturn(new HashSet<>());

        Set<String> response = linkService.gets(id);

        assertNotNull(response);
        assertTrue(response.isEmpty());
        verify(links).getOrDefault(id, new HashSet<>());
    }

    @Test
    void testGetUrlsWhenIdIsNull() {
        when(links.getOrDefault(null, new HashSet<>())).thenReturn(new HashSet<>());

        Set<String> response = linkService.gets(null);

        assertTrue(response.isEmpty());
        verify(links).getOrDefault(null, new HashSet<>());
    }

    private Set<String> buildUrls() {
        return Set.of(
            "https://github.com/sanyarnd/tinkoff-java-course-2023/",
            "https://stackoverflow.com/questions/1642028/what-is-the-operator-in-c"
        );
    }
}
