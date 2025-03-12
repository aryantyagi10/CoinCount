package com.expenseBuddy.controller;

import com.expenseBuddy.model.ExpenseEntity;
import com.expenseBuddy.model.UserEntity;
import com.expenseBuddy.service.ExpenseService;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/expenses")
public class AdminExpenseController {
    private final ExpenseService expenseService;

    public AdminExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @GetMapping
    public String viewAllExpenses(Model model){
        //Fetch all expenses and add to the model
        List<ExpenseEntity> expenses = expenseService.getAllExpenses();
        model.addAttribute("expenses", expenses);
        return "admin-view-expenses";
    }

    //For search functionality
    @GetMapping("/search")
    public String searchExpenses(@RequestParam("username") String username, Model model){
        List<ExpenseEntity> expenses = expenseService.getExpensesByUsername(username);
        model.addAttribute("expenses", expenses);
       //To show the search query in the search bar
        model.addAttribute("searchedUsername", username);
        return "admin-view-expenses";
    }

    @PostMapping("/delete/{id}")
    public String deleteExpense(@PathVariable Long id){
        expenseService.deleteExpenseById(id);
        return "redirect:/admin/expenses";
    }

}
