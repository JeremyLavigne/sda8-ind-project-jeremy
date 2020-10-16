package main;

import database.Database;
import userInterface.UserInterface;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Entry point of the program
 */
public class Main {

    public static void main(String[] args){

        // Create database
        Database myDatabase = new Database("assets/database.txt");

        // Get List of Items from database
        ArrayList<Item> savedList = new ArrayList<>();
        try {
            savedList = myDatabase.getListFromFile();

        } catch (IOException | ClassNotFoundException e) { e.printStackTrace(); }

        // Create account
        Account userAccount = new Account(savedList);

        // Launch Interface
        UserInterface ui = new UserInterface(userAccount);
        ui.start();

        userAccount.printItems("All", "Month", "Ascending");

        // Save List of items in database
        try {
            myDatabase.writeListIntoFile(savedList);

        } catch (IOException e) { e.printStackTrace(); }

    }
}
