package com.example.childgarden.db.repository;

import com.example.childgarden.db.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    @Query("select r from Role r where r.roleName = :name")
    Role findByName(String name);

    @Query(value = "select * from role where id = (select role_id from roles_users where user_id = :userId)", nativeQuery = true)
    Role findRoleByUserId(Long userId);

}