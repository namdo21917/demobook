package com.example.demobook.configuration;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;

public class AuditorAwareImpl implements AuditorAware<String> {
    private static final String USER_ID = "USER_ID"; // TODO: USER_ID

    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of(USER_ID);
    }
}
