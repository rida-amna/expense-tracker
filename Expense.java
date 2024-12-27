package LabProject;
import java.time.LocalDate;

public class Expense extends Transaction{
    protected String category;
    protected String description;
    protected double amount;
    protected LocalDate date;

    public Expense( String category, String description , double amount){
        super(amount);// Pass amount to Transaction constructor
        this.category=category;
        this.description=description;
        this.date=LocalDate.now();
        this.amount=amount;//initialize amount here
    }

    public double getAmount() {
        return amount;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getDate() {
        return date;
    }


    public String getType(){
        return "Regular expenses => ";
    }

    public String toString(){
        return getType() +"[ Category : "+category+", Description : "+description+ ", Amount :  "+ amount + ", Date : "+ date +" ]";
    }

}
