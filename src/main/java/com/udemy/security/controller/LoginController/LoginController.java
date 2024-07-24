package com.udemy.security.controller.LoginController;

import com.udemy.security.entity.Customer;
import com.udemy.security.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class LoginController {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody Customer customer) {
        Customer savedCustomer = null;
        ResponseEntity response = null;
        try {
            String hashPwd = passwordEncoder.encode(customer.getPwd());
            customer.setPwd(hashPwd);
            savedCustomer = customerRepository.save(customer);
            if (savedCustomer.getCustomeId() > 0) {
                response = ResponseEntity.status(HttpStatus.CREATED).body("Give user detail are successfully registered");
            }
        } catch (Exception e) {
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An Exception occurred while registering the user");
        }

        return response;
    }

    @GetMapping("/user")
    public Customer getUserDetailAfterLogin(Authentication authentication){
        Optional<Customer> customers = customerRepository.findByEmail(authentication.getName());
        return customers.orElse(null);
    }
}
