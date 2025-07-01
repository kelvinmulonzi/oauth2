package com.example.Oath2.configuration;

import com.example.Oath2.Repo.UserRepository;
import com.example.Oath2.entities.Role;
import com.example.Oath2.entities.User;
import com.example.Oath2.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Configuration
public class UserConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CustomUserDetailsService customUserDetailsService(UserRepository userRepository) {
        // Create default users in database if they don't exist
        if (userRepository.findByUsername("user").isEmpty()) {
            User user = new User();
            user.setUsername("user");
            user.setPassword(passwordEncoder().encode("password"));
            user.setEnabled(true);
            Role userRole = new Role();
            userRole.setName("ROLE_USER");
            user.setRoles(Set.of(userRole));
            userRepository.save(user);
        }

        if (userRepository.findByUsername("admin").isEmpty()) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder().encode("password"));
            admin.setEnabled(true);
            Role adminRole = new Role();
            adminRole.setName("ROLE_ADMIN");
            admin.setRoles(Set.of(adminRole));
            userRepository.save(admin);
        }

        return new CustomUserDetailsService(userRepository);
    }
}