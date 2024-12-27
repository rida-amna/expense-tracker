package LabProject;
import java.time.LocalDate;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        AVLTree avl = new AVLTree();

        System.out.println("\n\t\t\t''EXPENSE TRACKER''");
        System.out.println("\n=> First of all enter your budget (balance) so we can track all your expenses ");
        System.out.print("\n(*) Enter your initial balance: ");
        double balance = sc.nextDouble();
        sc.nextLine();
        double totalBalance = balance;
        ExpenseTracker et = new ExpenseTracker(avl, totalBalance);
        if (balance < 0) {
            System.out.println("Negative Balance, Invalid");
        } else {
            System.out.println("\nInitial balance = " + totalBalance + "$\n");
        }

        while (true) {
            System.out.println("=> Expense Tracker [Enter a numeric key]:");
            System.out.println("\n1. Add everyday regular expenses.");
            System.out.println("2. Add recurring expenses.");
            System.out.println("3. Add monthly income.");
            System.out.println("4. Delete any transaction from the expense tracker history.");
            System.out.println("5. View total balance.");
            System.out.println("6. Transaction History in sorted order by amount.");
            System.out.println("7. Undo last transaction.");
            System.out.println("8. Search for any transaction by amount.");
            System.out.println("9. Retrieve all the transaction history.");
            System.out.println("10. Exit.");
            System.out.print("\nEnter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    // Add regular expense
                    System.out.print("Enter category: ");
                    String category = sc.nextLine();
                    System.out.print("Enter description: ");
                    String description = sc.nextLine();
                    System.out.print("Enter amount: ");
                    double amount = sc.nextDouble();
                    sc.nextLine();

                    // Create and add expense
                    Expense expense = new Expense(category, description, amount);
                    et.addExpenses(category, description, amount); // Adding to the tracker
                    avl.insert(expense);  // Insert into AVL tree

                    // Update and show balance
                    totalBalance -= amount;
                    System.out.print("\nDo you want to view the total balance after your expense has been deducted? [yes/no]: ");
                    String ans = sc.nextLine();
                    if ("yes".equalsIgnoreCase(ans)) {
                        System.out.println("\nBalance left after deducting your " + category + " expense is: " + totalBalance + "$");
                    } else {
                        System.out.println("Expense added successfully to the expense tracker history.");
                    }
                    System.out.println();
                    break;

                case 2:

                    System.out.print("Enter category: ");
                    String rc = sc.nextLine();
                    System.out.print("Enter description: ");
                    String d = sc.nextLine();
                    System.out.print("Enter amount: ");
                    double a = sc.nextDouble();
                    sc.nextLine();  // To consume newline character
                    System.out.print("Enter frequency (annually/monthly/weekly): ");
                    String frequency = sc.nextLine();
                    System.out.print("Enter the date when you paid the recurring amount (yyyy-mm-dd): ");
                    String startDate = sc.nextLine();

                    LocalDate sd = LocalDate.parse(startDate);
                    // Create recurring expense
                    RecurringExpense re = new RecurringExpense(a, rc, d, sd, frequency);
                    et.addRecurring(a, rc, d, sd, frequency);  // Add to tracker
                    avl.insert(re);  // Insert into AVL tree

                    // Calculate next occurrence and display balance
                    re.nextOccurrence();  // Display next occurrence date
                    totalBalance -= a;
                    System.out.print("\nDo you want to view the balance after your expense will be deducted? [yes/no]: ");
                    String ans2 = sc.nextLine();
                    if ("yes".equalsIgnoreCase(ans2)) {
                        System.out.println("\nBalance after deducting your " + rc + " expense is: " + totalBalance);
                    } else {
                        System.out.println("\nRecurring expense added successfully.");
                    }
                    System.out.println();
                    break;

                case 3:

                    System.out.print("Enter category: ");
                    String c = sc.nextLine();
                    System.out.print("Enter description: ");
                    String d1 = sc.nextLine();
                    System.out.print("Enter amount: ");
                    double a1 = sc.nextDouble();
                    sc.nextLine();
                    System.out.print("Enter date (yyyy-mm-dd): ");
                    String date = sc.nextLine();

                    LocalDate date1 = LocalDate.parse(date);
                    // Create and add income
                    Income i = new Income(date1, c, d1, a1);
                    et.addIncome(date1, c, d1, a1);  // Add to tracker
                    avl.insert(i);  // Insert into AVL tree

                    // Update total balance
                    totalBalance += a1;
                    System.out.println("\nIncome added successfully in your total balance.");
                    System.out.println();
                    break;

                case 4:

                    if (avl.isEmpty()) {
                        System.out.println("There are no transactions to delete.");
                    } else {
                        avl.inOrder();
                        System.out.print("\nEnter the amount of the transaction you want to delete: ");
                        double amountDelete = sc.nextDouble();
                        sc.nextLine();

                        // Create a Transaction object that represents the amount to delete
                        Transaction deleteTransaction = new Transaction(amountDelete) {
                            @Override
                            public String toString() {
                                return "Transaction with amount: " + amount;
                            }
                        };

                        // Call the delete method of AVL tree to remove the transaction with the given amount
                        boolean deleted = avl.delete(deleteTransaction);

                        if (!avl.isEmpty()){
                            System.out.println("\nExpense tracker after deleting : "+amountDelete+"$  ");
                            avl.inOrder(); // Print all transactions from AVL tree
                            System.out.println();
                        }else {
                            System.out.println("After deleting " + amountDelete+ "$, expense tracker is empty");
                        }
                    }
                    break;

                case 5:

                    System.out.println("\n=> Total balance in your account is: " + totalBalance);
                    System.out.println();
                    break;

                case 6:
                    if (!avl.isEmpty()){
                        System.out.println("\n=> Transaction History in a sorted order : ");
                        avl.inOrder();  // Print all transactions from AVL tree
                        System.out.println();
                    }else {
                        System.out.println("\nTransaction History of your expense tracker is empty\n");
                    }
                    break;

                case 7:
                    System.out.println("=> Undo transaction : ");
                    // Undo last transaction
                    totalBalance = et.undoTransaction();  // Capture the updated balance
                    System.out.println("Balance after undo: " + totalBalance);
                    break;

                case 8:
                    System.out.println("=> Search any transaction by amount: ");
                    System.out.print("Enter the amount to search for: ");
                    double searchAmount = sc.nextDouble();
                    sc.nextLine();  // Consume newline character

                    // Create a Transaction object with the search amount
                    Transaction searchTransaction = new Transaction(searchAmount) {
                        @Override
                        public String toString() {
                            return "Transaction with amount: " + getAmount();
                        }
                    };

                    // Call the search method in AVL tree
                    double foundAmount = avl.search(avl.getRoot(), searchTransaction); // Pass root node to start search

                    if (foundAmount != 0) {
                        System.out.println("Transaction with amount " + foundAmount + " found in the tracker.");
                    } else {
                        System.out.println("Transaction with amount " + searchAmount + " not found.");
                    }
                    break;

                case 9:
                    System.out.println("\n=> Retrieved transaction History of the expenses tracker:");
                    et.display();
                    System.out.println();
                    break;

                case 10:
                    System.out.println("...Thank you <3...");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
    }
}




