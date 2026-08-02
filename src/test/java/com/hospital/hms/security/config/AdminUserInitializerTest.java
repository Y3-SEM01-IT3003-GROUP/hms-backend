package com.hospital.hms.security.config;

import com.hospital.hms.security.user.Role;
import com.hospital.hms.security.user.User;
import com.hospital.hms.security.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.ApplicationArguments;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AdminUserInitializerTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AdminUserInitializer initializer;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(initializer, "username", "admin");
        ReflectionTestUtils.setField(initializer, "email", "admin@hospital.com");
        ReflectionTestUtils.setField(initializer, "password", "Admin@123");
        ReflectionTestUtils.setField(initializer, "firstname", "System");
        ReflectionTestUtils.setField(initializer, "lastname", "Administrator");
    }

    @Test
    void shouldCreateAdminWhenNotExists() {
        when(userRepository.existsByUsernameOrEmail("admin", "admin@hospital.com")).thenReturn(false);
        when(passwordEncoder.encode("Admin@123")).thenReturn("encoded-password");

        initializer.run(new ApplicationArguments(new String[0]));

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(captor.capture());
        User createdUser = captor.getValue();

        assertEquals("admin", createdUser.getUsername());
        assertEquals("admin@hospital.com", createdUser.getEmail());
        assertEquals("encoded-password", createdUser.getPassword());
        assertEquals(Role.ADMIN, createdUser.getRole());
    }

    @Test
    void shouldSkipCreationWhenAdminAlreadyExists() {
        when(userRepository.existsByUsernameOrEmail("admin", "admin@hospital.com")).thenReturn(true);

        initializer.run(new ApplicationArguments(new String[0]));

        verify(userRepository, never()).save(any(User.class));
    }
}
