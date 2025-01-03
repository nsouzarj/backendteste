package com.example.backend.config;

import com.example.backend.entity.User;
import com.example.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Collections;

@Service
public class JpaUserDetailsService implements UserDetailsService {

    private static final Logger log = LoggerFactory.getLogger(JpaUserDetailsService.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("loadUserByUsername called with username: {}", username);
        User user = userRepository.findByName(username);


        if (user == null) {
            log.error("User not found for username: {}", username);
            throw new UsernameNotFoundException("User not found");
        }
        log.info("User found: {}", user.getEmail());
        log.info("Roles found: {}", user.getProfile().getName());

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getProfile().getName())));
    }
}