package com.udemy.security.config;

import com.udemy.security.exceptionhandling.CustomAccessDeniedHander;
import com.udemy.security.exceptionhandling.CustomBasicAuthenticationEntryPoint;
import com.udemy.security.filter.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@Configuration
public class SecurityConfig {
    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        CsrfTokenRequestAttributeHandler requestHandler = new CsrfTokenRequestAttributeHandler();
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // inactive JSESSION
                .csrf(csrf -> csrf.csrfTokenRequestHandler(requestHandler).ignoringRequestMatchers("/api/v1/myContact", "/api/v1/myNotice", "/api/v1/register", "/api/v1/user", "/api/v1/login").csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())).addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class)

                .authorizeHttpRequests(requests -> requests.requestMatchers("/api/v1/myNotice").hasRole("ADMIN").requestMatchers("/api/v1/myContact").hasRole("USER").requestMatchers("/api/v1/user").authenticated().requestMatchers("/api/v1/register", "/api/v1/login", "/notices").permitAll());
        http.formLogin(Customizer.withDefaults());


        http.httpBasic(e -> e.authenticationEntryPoint(new CustomBasicAuthenticationEntryPoint()));
        http.exceptionHandling(e -> e.accessDeniedHandler(new CustomAccessDeniedHander()));

        return http.build();
    }
}



