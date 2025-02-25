package com.java.bot.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private Set<Long> users;

    @InjectMocks
    private UserService userService;

    @Test
    void testSaveUser() {
        Long id = 123L;

        userService.save(id);
        verify(users).add(id);
    }

    @Test
    void testExistsWhenUserIsPresent() {
        Long id = 123L;

        when(users.contains(id)).thenReturn(true);

        assertTrue(userService.exists(id));
        verify(users).contains(id);
    }

    @Test
    void testExistsWhenUserIsNotPresent() {
        Long id = 321L;

        when(users.contains(id)).thenReturn(false);

        assertFalse(userService.exists(id));
        verify(users).contains(id);
    }
}
