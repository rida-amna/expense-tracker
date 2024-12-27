package LabProject;

import java.time.LocalDate;

public class Income extends Expense {
    private LocalDate date;
    public Income(LocalDate date,String category, String description , double amount){
        super(category, description, amount);
        this.date=date;
    }

    @Override
    public LocalDate getDate() {
        return date;
    }

    public String getType(){
        return "Income => ";
    }
    public String toString(){
        return getType()+"[ Category : " + category+ ", Description : "+ description + ", Amount :" + amount +"$ , Date : "+date+" ]";
    }
}
