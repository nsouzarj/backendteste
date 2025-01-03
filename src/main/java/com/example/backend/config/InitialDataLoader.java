package com.example.backend.config;

import com.example.backend.entity.Profile;
import com.example.backend.entity.User;
import com.example.backend.repository.ProfileRepository;
import com.example.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class InitialDataLoader implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(InitialDataLoader.class);
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        log.info("InitialDataLoader is running");
        if(userRepository.findByEmail("admin@example.com") == null){
            log.info("User admin@example.com not found. Creating...");
            Profile adminProfile = new Profile();
            adminProfile.setName("ADMIN");
            profileRepository.save(adminProfile);

            User adminUser = new User();
            adminUser.setName("admin");
            adminUser.setEmail("admin@example.com");
            adminUser.setPassword(passwordEncoder.encode("admin123"));
            adminUser.setProfile(adminProfile);
            userRepository.save(adminUser);

        }
    }
}