package main;

import database.Database;
import userInterface.UserInterface;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Entry point of the program
 */
public class Main {

    public static void main(String[] args){

        // Get List of Item from database
        Database myDatabase = new Database("assets/database.txt");

        ArrayList<Item> savedList = new ArrayList<>();
        try {
            savedList = myDatabase.getListFromFile();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // Create account
        Account userAccount = new Account(savedList);

        // Launch Interface
        UserInterface ui = new UserInterface(userAccount);
        ui.start();

        // Save List of item
        try {
            myDatabase.writeListIntoFile(userAccount.getItems());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
