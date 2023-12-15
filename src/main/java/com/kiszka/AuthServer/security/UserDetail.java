package com.kiszka.AuthServer.security;

import com.kiszka.AuthServer.dbconnection.User;
import com.kiszka.AuthServer.dbconnection.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;

@Service
public class UserDetail implements UserDetailsService {
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmailAddress(username);
        if(user.isEmpty()){
            throw new UsernameNotFoundException("This email address does not exists");
        }
        User existingUser = user.get();
        return new org.springframework.security.core.userdetails.User(
                username,
                existingUser.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority(existingUser.getRole())));
    }
}
