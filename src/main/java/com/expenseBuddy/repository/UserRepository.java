package com.expenseBuddy.repository;

import com.expenseBuddy.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {


  //Find user by username and role
  Optional<UserEntity> findByUsernameAndRole(String username, String role);

  //Find user by email (if needed for future functionality)
  Optional<UserEntity> findByEmail(String email);


  //check if a username already exists
  boolean existsByUsername(String username);

  Optional<UserEntity> findByUsername(String username);

  List<UserEntity> findByRole(String role);

  Optional<UserEntity> findByRoleAndId(String role, Long id);

}



