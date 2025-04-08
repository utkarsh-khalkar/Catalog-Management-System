package org.perennial.gst_hero.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.perennial.gst_hero.Entity.User;
import org.perennial.gst_hero.repository.UserRepository;
import org.perennial.gst_hero.serviceImpl.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

/**
 * Author:  Utkarsh Khalkar
 * Title:   JWT Filter Authentication
 * Date:    02-04-2025
 * Time:    02:26 PM
 */
@Slf4j
@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ApplicationContext context;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        log.info("START :: CLASS :: JwtFilter :: METHOD :: doFilterInternal :: ");
        String authHeader = request.getHeader("Authorization");

        String token = null;
        String username = null;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {

            // extracting token from header
            token = authHeader.substring(7);
            // extracting username from token
            username=jwtService.extractUserName(token);
        }

        if (username !=null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // load user details from database
          //  UserDetails userDetails = context.getBean(MyUserDetailService.class).loadUserByUsername(username);
            Optional<User> user=userRepository.findByUsername(username);
            if (user.isPresent() && jwtService.validateToken(token, user.get())) {

                User user1 = user.get();
                // creates authentication token
                UsernamePasswordAuthenticationToken authenticationToken= new UsernamePasswordAuthenticationToken(user1,
                        null, user1.getAuthorities());
                // sets additional information like IP address,sessionID
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));


                // set authentication in security context
                log.info("User '{}' authenticated successfully. Setting security context.", username);
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                log.info("Security context authentication: {}", SecurityContextHolder.getContext().getAuthentication());


            }
        }

        filterChain.doFilter(request, response);
        log.info("END :: CLASS :: JwtFilter :: METHOD :: doFilterInternal :: ");

    }
}
