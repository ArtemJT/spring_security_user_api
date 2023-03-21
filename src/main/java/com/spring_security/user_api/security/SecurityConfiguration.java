package com.spring_security.user_api.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.SecurityContextRepository;

/**
 * @author Artem Kovalov on 17.03.2023
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final UserDetailsService userDetailsService;
    private final SecurityContextRepository securityContextRepository;

    @Value("${url.login}")
    private String loginUrl;

    @Value("${url.registration}")
    private String registrationUrl;

    @Value("${url.success}")
    private String successUrl;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
                .csrf().disable()
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(registrationUrl).permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(login -> login
                        .loginPage(loginUrl)
                        .defaultSuccessUrl(successUrl)
                        .permitAll()
                )
                .logout(logout -> logout
                        .invalidateHttpSession(false)
                        .logoutSuccessUrl(loginUrl)
                        .permitAll()
                )
                .securityContext(context -> context.securityContextRepository(securityContextRepository));

        return http.build();
    }
}
