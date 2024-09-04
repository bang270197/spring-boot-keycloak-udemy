package com.udemy.security.config;

import com.udemy.security.exceptionhandling.CustomAccessDeniedHander;
import com.udemy.security.exceptionhandling.CustomBasicAuthenticationEntryPoint;
import com.udemy.security.filter.CsrfCookieFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;

@Configuration
public class SecurityConfig {
    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        CsrfTokenRequestAttributeHandler requestHandler = new CsrfTokenRequestAttributeHandler();

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new KeyCloakRoleConverter());

        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // inactive JSESSION
                .csrf(csrf -> csrf.csrfTokenRequestHandler(requestHandler).ignoringRequestMatchers(
                        "/api/v1/myContact", "/api/v1/myNotice", "/api/v1/register", "/api/v1/user")
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
                .addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class)
                .authorizeHttpRequests(requests ->
                        requests.requestMatchers("/api/v1/myNotice").hasRole("ADMIN")
                                .requestMatchers("/api/v1/myAccount").hasRole("USER")
                                .requestMatchers("/api/v1/myBalance").hasAnyAuthority("ADMIN", "USER")
                                .requestMatchers("/api/v1/myContact").hasRole("ADMIN")
                                .requestMatchers("/api/v1/myCard").hasRole("USER")
                                .requestMatchers("/api/v1/myLoans").authenticated()

                                .requestMatchers("/api/v1/user").authenticated()
                                .requestMatchers("/api/v1/register").permitAll());
        http.oauth2ResourceServer(rsc -> rsc.jwt(jwtConfigurer -> jwtConfigurer.jwtAuthenticationConverter(jwtAuthenticationConverter)));


        http.httpBasic(e -> e.authenticationEntryPoint(new CustomBasicAuthenticationEntryPoint()));
        http.exceptionHandling(e -> e.accessDeniedHandler(new CustomAccessDeniedHander()));

        return http.build();
    }
}



