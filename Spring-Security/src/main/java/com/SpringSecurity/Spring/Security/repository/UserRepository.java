package com.SpringSecurity.Spring.Security.repository;

import com.SpringSecurity.Spring.Security.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
}
