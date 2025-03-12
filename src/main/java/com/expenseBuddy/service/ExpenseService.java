package com.expenseBuddy.service;

import com.expenseBuddy.model.ExpenseEntity;
import com.expenseBuddy.model.UserEntity;
import com.expenseBuddy.repository.ExpenseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;

    //Constructor-based dependency injection
    public ExpenseService(ExpenseRepository expenseRepository){
        this.expenseRepository = expenseRepository;
    }

    //Add a new expense
    public ExpenseEntity addExpense(ExpenseEntity expense){
        return expenseRepository.save(expense);
    }

    //Get expenses by user ID
    public List<ExpenseEntity> getExpensesByUserId(Long userId){
        return expenseRepository.findByUserId(userId);
    }

    public List<ExpenseEntity> getExpensesByUser(UserEntity user){
        return expenseRepository.findByUser(user);
    }

    public ExpenseEntity findExpenseById(Long id){
        return expenseRepository.findById(id).orElse(null);
    }

    public void updateExpense(ExpenseEntity updatedExpense) {
        ExpenseEntity existingExpense = expenseRepository.findById(updatedExpense.getId())
                .orElseThrow(() -> new IllegalArgumentException("Expense not found"));
        existingExpense.setDescription(updatedExpense.getDescription());
        existingExpense.setAmount(updatedExpense.getAmount());
        existingExpense.setDate(updatedExpense.getDate());
        existingExpense.setCategory(updatedExpense.getCategory());
        existingExpense.setPaymentMethod(updatedExpense.getPaymentMethod());

        expenseRepository.save(existingExpense);
    }


    public void deleteExpenseById(Long id) {
        expenseRepository.deleteById(id);
    }


    //Fetch all expenses for admin
    public List<ExpenseEntity> getAllExpenses(){
        return expenseRepository.findAll();
    }

    public List<ExpenseEntity> getExpensesByUsername(String username) {
        return expenseRepository.findByUserUsername(username);  //Assumes 'UserEntity has a 'username' field
    }
}
