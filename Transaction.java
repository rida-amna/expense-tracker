package LabProject;

public abstract class Transaction {
    protected double amount;
    public Transaction(double amount){
        this.amount=amount;
    }

    public double getAmount() {
        return amount;
    }
    public abstract String toString();
}
