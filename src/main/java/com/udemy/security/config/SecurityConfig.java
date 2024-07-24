package com.udemy.security.config;

import com.udemy.security.exceptionhandling.CustomAccessDeniedHander;
import com.udemy.security.exceptionhandling.CustomBasicAuthenticationEntryPoint;
import com.udemy.security.filter.AuthoritiesLoggingAtFilter;
import com.udemy.security.filter.AuthoritieslLoggingAfterFilter;
import com.udemy.security.filter.CsrfCookieFilter;
import com.udemy.security.filter.RequestValidateBeforeFillter;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
        requestHandler.setCsrfRequestAttributeName("_csrf");

        http.securityContext(securityContext -> securityContext.requireExplicitSave(false))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
//                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .cors(cors -> cors.configurationSource(new CorsConfigurationSource() {
                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                        CorsConfiguration config = new CorsConfiguration();
                        config.setAllowedOrigins(Collections.singletonList("*"));
                        config.setAllowedHeaders(Collections.singletonList("*"));
                        config.setAllowCredentials(true);
                        config.setAllowedMethods(Collections.singletonList("*"));
                        config.setMaxAge(3600L);  // Use uppercase L for long literal
                        return config;
                    }
                }))
                .csrf(csrf -> csrf.csrfTokenRequestHandler(requestHandler).ignoringRequestMatchers("/register")
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                ).addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class)
                .addFilterAfter(new AuthoritieslLoggingAfterFilter(), BasicAuthenticationFilter.class)
                .addFilterAt(new AuthoritiesLoggingAtFilter(), BasicAuthenticationFilter.class)
                //Test fillterBefore
//                .addFilterBefore(new RequestValidateBeforeFillter(), BasicAuthenticationFilter.class)
                .authorizeHttpRequests(requests ->
                        requests
                                .requestMatchers("/api/v1/myAccount").hasAuthority("VIEWACCOUNT")
                                .requestMatchers("/api/v1/myBalance").hasAnyAuthority("VIEWBALANCE","VIEWACCOUNT")
                                .requestMatchers("/api/v1/myLoans").hasAuthority("VIEWLOANS")
                                .requestMatchers("/api/v1/myCard").hasAuthority("VIEWCARDS")

                                .requestMatchers( "/api/v1/user").authenticated()
                                .requestMatchers("/myNotice", "/register").permitAll()
                );
                http.formLogin(Customizer.withDefaults());
                http.httpBasic(e -> e.authenticationEntryPoint(new CustomBasicAuthenticationEntryPoint()));
                 http.exceptionHandling(e -> e.accessDeniedHandler(new CustomAccessDeniedHander()));

        return http.build();
    }


//    @Bean
//    CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowedOrigins(Arrays.asList("https://example.com"));
//        configuration.setAllowedMethods(Arrays.asList("GET","POST"));
//        configuration.setAllowedHeaders(Arrays.asList("*"));
//        configuration.setMaxAge(3600l);
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return NoOpPasswordEncoder.getInstance();
//    }


//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
//    }
//
//    /**
//     * From Spring Security 6.3 version
//     *
//     * @return
//     */
//    @Bean
//    public CompromisedPasswordChecker compromisedPasswordChecker() {
//        return new HaveIBeenPwnedRestApiPasswordChecker();
//    }
}
