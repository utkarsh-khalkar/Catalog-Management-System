package org.perennial.gst_hero.config;

import lombok.extern.slf4j.Slf4j;
import org.perennial.gst_hero.filter.JwtFilter;
import org.perennial.gst_hero.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Author: Utkarsh Khalkar
 * Title:  Security Configuration
 * Date: 01:04:2025
 * Time: 05:50 PM
 */
@Slf4j
@Configuration
@EnableWebSecurity
public class SecurityConfig {


    @Autowired
    private JwtFilter jwtFilter;

    @Autowired
    private UserRepository userRepository;


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    /**
     * This method for authentication provider
     * @return daoAuthenticationProvider
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        return new AuthenticationProvider() {
            @Override
            public Authentication authenticate(Authentication authentication) {
                log.info("START :: CLASS :: SecurityConfig :: METHOD :: authenticationProvider");

                String username = authentication.getName();  // Get username
                String password = authentication.getCredentials().toString();  // Get password

                var user = userRepository.findByUsername(username)
                        .orElseThrow(() -> new RuntimeException("User not found"));

                if (!passwordEncoder().matches(password, user.getPassword())) {
                    throw new RuntimeException("Invalid credentials");
                }

                log.info("END :: CLASS :: SecurityConfig :: METHOD :: authenticationProvider");
                return new UsernamePasswordAuthenticationToken(user, password, user.getAuthorities());
            }

            @Override
            public boolean supports(Class<?> authentication) {
                return authentication.equals(UsernamePasswordAuthenticationToken.class);
            }
        };
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        log.info("START :: CLASS :: SecurityConfig :: METHOD :: securityFilterChain ::");
         http.csrf(AbstractHttpConfigurer::disable);
        http.authorizeHttpRequests(request -> request.requestMatchers("" +
                "/catalog/user/register","/catalog/user/login").permitAll().anyRequest().authenticated());
         http.httpBasic(Customizer.withDefaults());

         http.sessionManagement(session -> session.sessionCreationPolicy(
                 SessionCreationPolicy.STATELESS));
         http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
         log.info("END :: CLASS :: SecurityConfig :: METHOD :: securityFilterChain ::");
         return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        log.info("START :: CLASS :: SecurityConfig :: METHOD :: authenticationManager ::");
        AuthenticationManager authenticationManager = configuration.getAuthenticationManager();
        log.info("END :: CLASS :: SecurityConfig :: METHOD :: authenticationManager ::");
        return authenticationManager;
    }
}
