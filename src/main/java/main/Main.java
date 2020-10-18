package main;

import database.Database;
import graphicalUserInterface.GUI;
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

        // Launch Interface -> Two options
        UserInterface ui = new UserInterface(userAccount);

        System.out.println("\nWelcome to TrackMoney.");

        System.out.println("---------------------------------------");
        System.out.println("\nWould you use :\n" +
                "(1) A console interface (regular project) \n" +
                "(2) A graphical interface (new feature) \n");

        int input = ui.getExpectedInteger(1,2);

        if (input == 1) { // Launch classic interface
            ui.start();

            // Save List of items in database when ui.stop()
            try {
                myDatabase.writeListIntoFile(savedList);

            } catch (IOException e) { e.printStackTrace(); }

        } else { // Launch graphical interface

            GUI gui = new GUI(userAccount, myDatabase); // Need to build it with database too
            gui.start();
        }
    }
}
