package com.example.Spring_Security_Latest.repository;

import com.example.Spring_Security_Latest.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserInfoRepository extends JpaRepository<UserInfo,Integer> {

    Optional<UserInfo> findByUsername(String username);
}
