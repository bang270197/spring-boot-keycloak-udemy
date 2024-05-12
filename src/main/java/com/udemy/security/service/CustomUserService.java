//package com.udemy.security.service;
//
//import com.udemy.security.entity.Customer;
//import com.udemy.security.repository.CustomerRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//public class CustomUserService implements UserDetailsService {
//    //UserDetailsService interface chỉ có 1 method loadUserByName
//    private final CustomerRepository customerRepository;
//
//    /*
//    * UserDetailsService và AuthenticationProvider đều được sử dụng để xác thực người dùng trong Spring Security,
//    *  nhưng chúng có mục đích và cách tiếp cận khác nhau.
//    * Trong trường hợp của bạn, nếu bạn đã triển khai UserDetailsService và đang làm việc hiệu quả với nó,
//    * bạn có thể tiếp tục sử dụng nó. Tuy nhiên, nếu bạn muốn kiểm soát rộng hơn hoặc có yêu cầu xác thực phức tạp hơn,
//    * bạn có thể muốn chuyển sang sử dụng AuthenticationProvider
//    * */
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        String userName, passWord;
//        List<GrantedAuthority> grantedAuthorities = null;
//        List<Customer> customers = customerRepository.findByEmail(username);
//        if (customers.isEmpty()){
//            throw new UsernameNotFoundException("User not fount with username: " + username);
//        } else {
//            grantedAuthorities = new ArrayList<>();
//            grantedAuthorities.add(new SimpleGrantedAuthority(customers.get(0).getRole()));
//            userName = customers.get(0).getEmail();
//            passWord = customers.get(0).getPwd();
//        }
//        return new User(userName, passWord, grantedAuthorities);
//    }
//}
