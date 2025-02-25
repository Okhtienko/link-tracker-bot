package com.java.bot.service;

import com.java.bot.processor.UserProcessor;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserProcessor {

    private final Set<Long> users;

    @Override
    public void save(Long id) {
        users.add(id);
    }

    @Override
    public boolean exists(Long id) {
        return users.contains(id);
    }
}
