package LabProject;

import java.time.LocalDate;

public class RecurringExpense extends Expense {
    private LocalDate startDate;
    private String frequency;

    // Constructor for RecurringExpense
    public RecurringExpense(double amount, String category, String description, LocalDate startDate, String frequency) {
        super(category, description, amount);  // Call parent constructor
        this.startDate = startDate;
        this.frequency = frequency;
    }


    @Override
    public String toString() {
        return "Recurring Expense  => [ Category : " + category + ", Description : " + description + ", Amount : " + amount + ", Date : " + startDate + ", Frequency : " + frequency + ", Next date : " + getNextOccurrence() +" ]";
    }

    // Add a method to calculate the next occurrence
    public LocalDate getNextOccurrence() {
        // Calculate the next occurrence based on frequency (example for monthly frequency)
        if (frequency.equalsIgnoreCase("monthly")) {
            return startDate.plusMonths(1);
        }
        // Handle other frequencies...
        return startDate; // Default
    }

    // Method to get the date of next recurring transaction
    public void nextOccurrence() {
        System.out.println("\nNext recurring transaction date: " + getNextOccurrence());
    }
}

