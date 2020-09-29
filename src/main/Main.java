package main;

import userInterface.UserInterface;

/**
 *
 */
public class Main {

    public static void main(String[] args){

        // Account = get account from the saved file
        Account userAccount = new Account();
        UserInterface ui = new UserInterface(userAccount);

        ui.start();
    }
}
