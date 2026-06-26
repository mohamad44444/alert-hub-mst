package com.alerthub.securityservice.repository;

import com.alerthub.securityservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}