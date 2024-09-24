package com.example.Spring_Security_Latest.service;

import com.example.Spring_Security_Latest.config.UserInfoUserDetails;
import com.example.Spring_Security_Latest.entity.UserInfo;
import com.example.Spring_Security_Latest.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserInfoUserDetailsService implements UserDetailsService {

    @Autowired
    private UserInfoRepository repo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       Optional<UserInfo> info = repo.findByUsername(username);
       return info.map(UserInfoUserDetails::new)
               .orElseThrow(()->new UsernameNotFoundException("User not found"));
    }
}
