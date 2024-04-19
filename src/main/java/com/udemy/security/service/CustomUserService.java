package com.udemy.security.service;

import com.udemy.security.entity.Customer;
import com.udemy.security.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserService implements UserDetailsService {

    private final CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String userName, passWord;
        List<GrantedAuthority> grantedAuthorities = null;
        List<Customer> customers = customerRepository.findByEmail(username);
        if (customers.isEmpty()){
            throw new UsernameNotFoundException("User not fount with username: " + username);
        } else {
            grantedAuthorities = new ArrayList<>();
            grantedAuthorities.add(new SimpleGrantedAuthority(customers.get(0).getRole()));
            userName = customers.get(0).getEmail();
            passWord = customers.get(0).getPwd();
        }
        return new User(userName, passWord, grantedAuthorities);
    }
}
