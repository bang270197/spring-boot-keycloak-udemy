package com.udemy.security.config;

import com.udemy.security.entity.Customer;
import com.udemy.security.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Component
public class UserPwdAuthenticationProvider implements AuthenticationProvider {
    /*
    * Trong Spring Security, AuthenticationProvider là một interface quan trọng để xác thực người dùng.
    * Nhiệm vụ của nó là kiểm tra thông tin đăng nhập được cung cấp bởi người dùng (như tên đăng nhập và mật khẩu)
    * và xác định xem liệu họ có được phép truy cập vào ứng dụng hay không.
    * */

    private final CustomerRepository customerRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String userName = authentication.getName();
        String pwd = authentication.getCredentials().toString();
       List<Customer> customers = customerRepository.findByEmail(userName);
       if (!customers.isEmpty()){
           if (passwordEncoder.matches(pwd, customers.get(0).getPwd())){
                List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
                grantedAuthorities.add(new SimpleGrantedAuthority(customers.get(0).getRole()));

               return new UsernamePasswordAuthenticationToken(userName, pwd, grantedAuthorities);
           }
           throw  new BadCredentialsException("Invalid pasword");
       } else {
           throw new BadCredentialsException("No user registered with the detail");
       }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
