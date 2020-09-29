package main;

import java.util.ArrayList;

public class Account {
    private int balance;
    private ArrayList<Expense> expenses;
    private ArrayList<Income> incomes;

    public Account() {
        this.balance = 0;
        this.expenses = new ArrayList<>();
        this.incomes = new ArrayList<>();
    }


    public int getBalance() {
        return this.balance;
    }
}
