package com.expenseBuddy.controller;

import com.expenseBuddy.model.ExpenseEntity;
import com.expenseBuddy.model.UserEntity;
import com.expenseBuddy.service.ExpenseService;
import com.expenseBuddy.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/user/expenses")
public class UserExpenseController {

    private final ExpenseService expenseService;
    private final UserService userService;

    //Constructor-based injection
    public UserExpenseController(ExpenseService expenseService, UserService userService){
        this.expenseService = expenseService;
        this.userService = userService;
    }

    //Display the Add Expense Form
    @GetMapping("/add-expense-view")
    public String showAddExpenseForm(Model model){
        model.addAttribute("expense",
                new ExpenseEntity());   //Create an empty ExpenseEntity object
        return "add-expense";   //Redirect to add-expense.html page
    }

    //Handle adding a new expense
    @PostMapping("/add-expense")
    public String addExpense(@ModelAttribute ExpenseEntity expense,
                             Authentication authentication){
            System.out.println("Expense Data: " + expense); //Print the data for debugging

            String username = authentication.getName(); //Get logged-in user's username
            UserEntity user = userService.findByUsername(username); //Fetch user from DB
            expense.setUser(user); //Set user Id in the expense
            expenseService.addExpense(expense); //save the expense
            return "redirect:/user/expenses/dashboard-view"; //Redirect to the user dashboard or expense list

    }

    //Display the edit form
    @GetMapping("/edit/{id}")
    public String showEditExpenseView(@PathVariable Long id, Model model){
        ExpenseEntity expense = expenseService.findExpenseById(id);
        if(expense == null){
            throw new IllegalArgumentException("Expense not found");
        }
        model.addAttribute("expense", expense);
        return "edit-expense";  //View for editing the expense
    }

    //Handle edit for an expense
    @PostMapping("/update")
    public String updateExpense(@ModelAttribute ExpenseEntity updatedExpense){
        expenseService.updateExpense(updatedExpense);
            return "redirect:/user/expenses/dashboard-view";

    }

    @PostMapping("/delete/{id}")
    public  String deleteExpense(@PathVariable Long id){
        expenseService.deleteExpenseById(id);
        return "redirect:/user/expenses/dashboard-view";
    }



    @GetMapping("/dashboard-view")
    public String showUserDashboard(Model model, Authentication authentication){

        //Get the logged-in user's username
        String username = authentication.getName();

        //Fetch the user entity
        UserEntity user = userService.findByUsername(username);

        //Fetch the user's expenses
        List<ExpenseEntity> expenses = expenseService.getExpensesByUser(user);


        //Calculate the total expense for the user
        double totalExpense = expenses.stream().mapToDouble(ExpenseEntity::getAmount).sum();

        //Add the username and expenses to the model
        model.addAttribute("username", username);  //Add the username
        model.addAttribute("expenses", expenses);  //Add the expenses to the model


        //Add the total expense to the model
        model.addAttribute("totalExpense", totalExpense);
        return "user-expenses-list";
    }

}
