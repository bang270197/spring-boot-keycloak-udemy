package com.udemy.security.controller.LoginController;

import com.udemy.security.constant.Constants;
import com.udemy.security.entity.Customer;
import com.udemy.security.model.LoginRequestDTO;
import com.udemy.security.model.LoginResponseDTO;
import com.udemy.security.repository.CustomerRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class LoginController {

    private final CustomerRepository customerRepository;
    private final Environment env;

    @GetMapping("/user")
    public Customer getUserDetailAfterLogin(Authentication authentication){
        Optional<Customer> customers = customerRepository.findByEmail(authentication.getName());
        return customers.orElse(null);
    }

}
