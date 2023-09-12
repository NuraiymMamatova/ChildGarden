package com.example.childgarden.db.repository;

import com.example.childgarden.db.entities.Role;
import com.example.childgarden.db.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    @Query("select u.roles from User u where u.email = :email")
    Role findRoleByUserEmail(String email);

}