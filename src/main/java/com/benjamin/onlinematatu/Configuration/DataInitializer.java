package com.benjamin.onlinematatu.Configuration;

import com.benjamin.onlinematatu.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final AuthService authService;

    @Override
    public void run(String... args) throws Exception {
        // Create admin user on application startup
        authService.createAdminUser();
    }
} 