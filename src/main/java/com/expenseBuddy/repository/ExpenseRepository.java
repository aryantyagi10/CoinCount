package com.expenseBuddy.repository;

import com.expenseBuddy.model.ExpenseEntity;
import com.expenseBuddy.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<ExpenseEntity, Long> {

    //Custom query to fetch expenses for a specific user
    List<ExpenseEntity> findByUserId(Long userId);

    List<ExpenseEntity> findByUser(UserEntity user);

    List<ExpenseEntity> findAll();    //Fetch all expenses

    List<ExpenseEntity> findByUserUsername(String username);
}
