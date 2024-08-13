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
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final Environment env;


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

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO requestDTO){
        Authentication authentication = UsernamePasswordAuthenticationToken.unauthenticated(requestDTO.userName(), requestDTO.passWord());
        Authentication authenticationResponse = authenticationManager.authenticate(authentication);
        String jwt = null;
        if (null != authentication) {

            if (null != env) {
                // get keyScret
                String secret = env.getProperty(Constants.JWT_SECRET_KEY, Constants.JWT_SECRET_DEFAULT_VALUE);
                //ma hoa
                SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
                jwt = Jwts.builder()
                        .issuer("security")
                        .subject("JWT Token")
                        .claim("username", authenticationResponse.getName())
                        .claim("authorities", authenticationResponse.getAuthorities().stream().map(
                                GrantedAuthority::getAuthority
                        ).collect(Collectors.joining(",")))
                        .issuedAt(new Date())
                        .expiration(new Date((new Date()).getTime() + 30000000))
                        .signWith(secretKey).compact();
            }
        }
        return ResponseEntity.status(HttpStatus.OK).header(Constants.JWT_HEADER, jwt).body(
                new LoginResponseDTO(HttpStatus.OK.getReasonPhrase(), jwt)
        );
    }
}
