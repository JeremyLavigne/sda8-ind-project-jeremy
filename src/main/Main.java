package main;

import userInterface.UserInterface;

/**
 * Entry point of the program
 */
public class Main {

    public static void main(String[] args){

        // getfromfile(myfile)
        // Class + package 'Database' -> field File file -> read/write
        // Account = get account from the saved file

        Account userAccount = new Account();
        userAccount.addItem(new Expense(2330, "test1", 12));
        userAccount.addItem(new Expense(1230, "mytest", 2));
        userAccount.addItem(new Income(76399, "test with spaces  ", 1));
        userAccount.addItem(new Expense(30, "aaaaaa", 11));
        userAccount.addItem(new Income(-10, "test with negative amount", 6));
        userAccount.addItem(new Income(9999999, "test6", 5));
        userAccount.addItem(new Expense(2330, "7333", 8));
        userAccount.addItem(new Expense(1230, "blah", 2));
        userAccount.addItem(new Income(76399, "testwithalongline", 12));

        userAccount.printItems("Only Expenses", "Month", "Ascending");


        //UserInterface ui = new UserInterface(userAccount);

        //ui.start();

        // Could save file here and not in the user interface
        // ui.stop() -> go back here and save
        // saveinfile(myfile)
    }
}
