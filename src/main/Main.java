package main;

import userInterface.UserInterface;

/**
 * Entry point of the program
 */
public class Main {

    public static void main(String[] args){

        // getfromfile(myfile)
        // Account = get account from the saved file

        Account userAccount = new Account();
        UserInterface ui = new UserInterface(userAccount);

        ui.start();

        // Could save file here and not in the user interface
        // ui.stop() -> go back here and save
        // saveinfile(myfile)
    }
}
