package com.kiszka.AuthServer.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity(debug=true)
public class SecurityConfig{
    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize ->
                        authorize.anyRequest().authenticated())
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/")
                        .permitAll())
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .permitAll());
        return http.build();
    }
    @Bean
    public SecurityFilterChain orderFilterChain(HttpSecurity httpSecurity, AuthenticationProvider orderAuthenticationProvider) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .securityMatcher("/order/**")
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().authenticated()
                )
                .authenticationProvider(orderAuthenticationProvider)
                .httpBasic(Customizer.withDefaults());
        return httpSecurity.build();
    }

    @Bean
    public AuthenticationProvider orderAuthenticationProvider(UserDetailsService orderUserDetailsService) {
        DaoAuthenticationProvider orderDaoAuthenticationProvider = new DaoAuthenticationProvider();
        orderDaoAuthenticationProvider.setUserDetailsService(orderUserDetailsService);
        return orderDaoAuthenticationProvider;
    }

}
