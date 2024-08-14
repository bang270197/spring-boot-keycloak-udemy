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
//        requestHandler.setCsrfRequestAttributeName("_csrf"); //CREATE CSRF

        http
                /*
                * Spring Security quản lý SecurityContext, thông tin về người dùng đã được xác thực và các quyền của họ
                * =>requireExplicitSave(false) Spring Security sẽ tự động lưu SecurityContextHolder.getContext().save()
                * không cần can thiệp thêm từ phía lập trình viên
                * */
//                .securityContext(securityContext -> securityContext.requireExplicitSave(false))
//                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS)) //active JSESSION
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // inactive JSESSION
//                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .cors(cors -> cors.configurationSource(new CorsConfigurationSource() {
                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                        CorsConfiguration config = new CorsConfiguration();
                        config.setAllowedOrigins(Collections.singletonList("*"));
                        config.setAllowedHeaders(Collections.singletonList("*"));
                        config.setAllowCredentials(true);
                        config.setAllowedMethods(Collections.singletonList("*"));
                        config.setExposedHeaders(Arrays.asList("Authorization"));
                        config.setMaxAge(3600L);  // Use uppercase L for long literal
                        return config;
                    }
                }))
                //CSRF bảo vệ thường chỉ được áp dụng cho các HTTP methods thay đổi trạng thái của server (như POST, PUT, DELETE)
                .csrf(csrf -> csrf.csrfTokenRequestHandler(requestHandler)
                        .ignoringRequestMatchers("/api/v1/myContact","/api/v1/register","/api/v1/user","/api/v1/login")
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
                .addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class)
                /*
                * Khi một yêu cầu HTTP tới, BasicAuthenticationFilter sẽ kiểm tra xem tiêu đề Authorization
                * có tồn tại và có phải là loại Basic không.
                  Nếu có, nó sẽ giải mã thông tin Base64 để lấy ra tên người dùng và mật khẩu.
                  Sau đó, nó tạo ra một đối tượng UsernamePasswordAuthenticationToken với các thông tin này
                  và gửi nó đến AuthenticationManager để xác thực.
                * */
//                .addFilterAfter(new AuthoritieslLoggingAfterFilter(), BasicAuthenticationFilter.class)
//                .addFilterAt(new AuthoritiesLoggingAtFilter(), BasicAuthenticationFilter.class)


                .addFilterAfter(new JWTTokenGeneratorFillter(), BasicAuthenticationFilter.class) //generator token sau khi đăng nhập
                .addFilterBefore(new JWTTokenValidatorFilter(), BasicAuthenticationFilter.class) // validate token trước khi authentication

                //Test fillterBefore
//                .addFilterBefore(new RequestValidateBeforeFillter(), BasicAuthenticationFilter.class)
                .authorizeHttpRequests(requests ->
                        requests
                                //kiểm tra một quyền cụ thể mà người dùng phải có
                                //chỉ định các quyền rất chi tiết cho từng API
//                                .requestMatchers("/myNotice").hasAuthority("USER")
                                .requestMatchers("/api/v1/myNotice").hasRole("USER")
                                .requestMatchers("/api/v1/myContact").hasRole("USER")
                                //hasRole kiểm tra vai trò của người dùng
                                .requestMatchers("/api/v1/myCards").hasAnyAuthority("VIEWCARDS")

//                                .requestMatchers("/api/v1/myBalance").hasAnyAuthority("VIEWBALANCE","VIEWACCOUNT")
//                                .requestMatchers("/api/v1/myLoans").hasAuthority("VIEWLOANS")
//                                .requestMatchers("/api/v1/myCard").hasAuthority("VIEWCARDS")

                                .requestMatchers( "/api/v1/user").authenticated()
                                .requestMatchers( "/api/v1/register", "/api/v1/login","/notices").permitAll()
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

//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManage(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder){
        UserPwdAuthenticationProvider userPwdAuthenticationProvider =
                new UserPwdAuthenticationProvider(userDetailsService, passwordEncoder);
        ProviderManager providerManager = new ProviderManager(
                userPwdAuthenticationProvider
        );
        // Thiết lập này ngăn không cho ProviderManager xóa thông tin xác thực sau khi quá trình xác thực hoàn tất
        providerManager.setEraseCredentialsAfterAuthentication(false);
        return providerManager;
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
