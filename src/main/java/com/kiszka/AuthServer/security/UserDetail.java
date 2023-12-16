package com.kiszka.AuthServer.security;

import com.kiszka.AuthServer.dbconnection.User;
import com.kiszka.AuthServer.dbconnection.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
@Slf4j
public class UserDetail implements UserDetailsService {
    private final UserRepository userRepository;
    @Autowired
    public UserDetail(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmailAddress(username);
        if(user.isEmpty()){
            throw new UsernameNotFoundException("This email address does not exists");
        }
        User existingUser = user.get();
        log.info("Email Address: {}, Password: {}", existingUser.getEmailAddress(), existingUser.getPassword());
        return new org.springframework.security.core.userdetails.User(
                username,
                existingUser.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority(existingUser.getRole())));
    }
}
