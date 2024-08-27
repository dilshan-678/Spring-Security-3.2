package com.SpringSecurity.Spring.Security.repository;

import com.SpringSecurity.Spring.Security.model.User;
import com.SpringSecurity.Spring.Security.util.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

    Optional<User> findByEmail(String  email);

    User findByRole(Role role);
}
