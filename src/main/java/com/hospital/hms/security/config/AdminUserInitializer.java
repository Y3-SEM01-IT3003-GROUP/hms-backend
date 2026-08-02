package com.hospital.hms.security.config;

import com.hospital.hms.security.user.Role;
import com.hospital.hms.security.user.User;
import com.hospital.hms.security.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class AdminUserInitializer implements ApplicationRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.admin.username:admin}")
    private String username;

    @Value("${app.admin.email:admin@hospital.com}")
    private String email;

    @Value("${app.admin.password:Admin@123}")
    private String password;

    @Value("${app.admin.firstname:System}")
    private String firstname;

    @Value("${app.admin.lastname:Administrator}")
    private String lastname;

    @Override
    public void run(ApplicationArguments args) {
        boolean exists = userRepository.existsByUsernameOrEmail(username, email);
        if (exists) {
            log.info("Admin user already exists: {}", username);
            return;
        }

        User adminUser = User.builder()
                .firstname(firstname)
                .lastname(lastname)
                .username(username)
                .email(email)
                .password(passwordEncoder.encode(password))
                .role(Role.ADMIN)
                .build();

        userRepository.save(adminUser);
        log.info("Created default admin user: {}", username);
    }
}
