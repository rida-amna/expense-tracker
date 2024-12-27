package LabProject;

import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;
import java.util.Stack;

public class ExpenseTracker {
    private List<Transaction> transactions;  // Use Transaction as the base class
    private Stack<Transaction> undo;  // Stack to track undo operations
    private AVLTree avl;  // AVL tree to track transactions
    private double totalBalance;

    public ExpenseTracker(AVLTree avl, double initialBalance) {
        transactions = new ArrayList<>();
        this.avl = avl;
        this.totalBalance = initialBalance;
        undo = new Stack<>();
    }

    // Add regular expense
    public void addExpenses(String category, String description, double amount) {
        Expense expense = new Expense(category, description, amount);
        transactions.add(expense);
        undo.push(expense);  // Push the transaction to undo stack
        avl.insert(expense);  // Insert into AVL tree
        totalBalance -= amount;  // Deduct from balance
        System.out.println("Regular Expenses => [ Category : " + category + ", Description :" + description + ", Amount : " + amount + "$ ]");
    }

    // Add recurring expense
    public void addRecurring(double amount, String category, String description, LocalDate startDate, String frequency) {
        RecurringExpense re = new RecurringExpense(amount, category, description, startDate, frequency);
        transactions.add(re);
        undo.push(re);  // Push the recurring expense to undo stack
        avl.insert(re);  // Insert into AVL tree
        totalBalance -= amount;  // Deduct from balance
        System.out.println("Recurring Expenses => [ Category : " + category + ", Description :" + description + ", Amount : " + amount + "$ , Date : "+ startDate +" ]");
    }

    // Add income
    public void addIncome(LocalDate date, String category, String description, double amount) {
        Income income = new Income(date, category, description, amount);
        transactions.add(income);
        undo.push(income);  // Push the income to undo stack
        avl.insert(income);  // Insert into AVL tree
        totalBalance -= amount;  // Add to balance
        System.out.println("Income => [ Category : " + category + ", Description : "+ description + ", Amount :" + amount +"$ , Date : "+date+" ]" );
    }

    // Undo the last transaction
    public double undoTransaction() {
        if (!undo.isEmpty()) {
            Transaction lastTransaction = undo.pop();

            if (lastTransaction instanceof Expense) {
                totalBalance += lastTransaction.getAmount();  // Undo expense (add it back)
            } else if (lastTransaction instanceof Income) {
                totalBalance -= lastTransaction.getAmount();  // Undo income (subtract it)
            } else if (lastTransaction instanceof RecurringExpense) {
                totalBalance += lastTransaction.getAmount();  // Undo recurring expense (add it back)
            }

            // Remove the transaction from the AVL tree
            avl.delete(lastTransaction);
            System.out.println("Undo successful. Transaction undone.");
        } else {
            System.out.println("No transaction to undo.");
        }

        return totalBalance;  // Return the updated balance
    }

    // Display all transactions
    public void display() {
        if (transactions.isEmpty()) {
            System.out.println("Transaction list is empty");
            return;
        }
        System.out.println("Expenses History: ");
        for (Transaction transaction : transactions) {
            System.out.println(transaction);  // Will call toString() automatically
        }
    }
}
