package userInterface;

import main.Account;
import java.util.Scanner;


/**
 * This class provides all user interface : menu(s), user choices via console input.
 * Dispatch to correct data/methods regarding user choice.
 */
public class UserInterface {
    private final Account account;
    private final Scanner scan;

    /**
     * Constructor - Need user Account to work with
     * @param userAccount -> user Account
     */
    public UserInterface(Account userAccount){
        this.account = userAccount;
        this.scan = new Scanner(System.in);
    }

    /**
     * Launch user interface.
     */
    public void start() {

        System.out.println("\nWelcome to TrackMoney.");
        System.out.println("You have currently " + this.account.getBalance() + " kr on your account.");

        this.offerOptions();

        this.scan.close();
    }


    // =============================================================================================
    // -------------------------------- UI display - Main methods ----------------------------------
    // =============================================================================================

    /**
     * First choice for user - Main menu.
     */
    private void offerOptions() {

        System.out.println("---------------------------------------");
        System.out.println("\nPick an option :\n" +
                "(1) Show items (All / Expense(s) / Income(s)), \n" +
                "(2) Add new expense / income, \n" +
                "(3) Edit item (edit, remove), \n" +
                "(4) Save and quit. \n");


        int input = getExpectedInteger(1,4);

        switch (input) {
            case 1 : this.showItemsPicked("All", "Month", "Descending"); break;
            case 2 : this.addItemPicked(); break;
            case 3 : this.editItemPicked(); break;
            case 4 : this.saveAndQuit(); break;
            default : break;
        }
    }

    /**
     * Choice n°1 - Show user items already in file
     */
    private void showItemsPicked(String what, String sortBy, String how) {

        displayAskedItems(what, sortBy, how);

        String[] options = defineOptions(what, sortBy, how);

        displayOptions(options);

        int input = getExpectedInteger(1,6);

        switch (input) {
            case 1 : this.showItemsPicked(options[0], sortBy, how); break;
            case 2 : this.showItemsPicked(options[1], sortBy, how); break;
            case 3 : this.showItemsPicked(what, options[2], how); break;
            case 4 : this.showItemsPicked(what, options[3], how); break;
            case 5 : this.showItemsPicked(what, sortBy, options[4]); break;
            case 6 : this.offerOptions(); break;
            default : break;
        }

    }
    /**
     * Choice n°2 - Add a new item
     */
    private void addItemPicked() {
        System.out.println("\n ------------------------------------ \n");
        System.out.println("Please enter new item details :");

        this.newItemForm(); // Should return object

        System.out.println("(1) Save - (2) Erase and restart");
        int inputSaveOrNot = getExpectedInteger(1,2);

        if (inputSaveOrNot == 1) {
            System.out.println("Saved!");
            this.offerOptions();
        } else {
            System.out.println("Current item deleted.");
            this.addItemPicked();
        }

    }
    /**
     * Choice n°3 - Edit an item which is already set
     */
    private void editItemPicked() {

        this.displayAskedItems("All", "Month", "Descending");

        System.out.println("\n ------------------------------------ \n");
        System.out.println("Please enter the line number that you want to edit/remove");

        int lineNumberToEdit = getExpectedInteger(1,12); // 12 should be replace by the length of array

        System.out.println("\nDo you want (1) Edit or (2) Remove : ");
        int inputChoice = getExpectedInteger(1, 2);

        if (inputChoice == 1) {
            this.editLine(lineNumberToEdit); // Will use the actual object here, not just the line number
            System.out.println("Line " + lineNumberToEdit + " was successfully edited.");
        } else {
            System.out.println("Line " + lineNumberToEdit + " was successfully removed.");
        }

        System.out.println("\nDo you want (1) Edit an other line (2) Back to menu : ");
        int inputBack = getExpectedInteger(1, 2);

        if (inputBack == 1) {
            this.editItemPicked();
        } else {
            this.offerOptions();
        }

    }

    /**
     * Choice n°4 - End program
     */
    private void saveAndQuit() {
        System.out.println("\n ------------------------------------ \n");
        System.out.println("Saved ! \n\nGood Bye :-)");
    }




    // =============================================================================================
    // -------------------------------- UI display - Display items (1) -----------------------------
    // =============================================================================================

    /**
     * Define options regarding the current user choice.
     * Avoid duplication : we do not offer the current displaying
     * @param what -> What are we displaying ? all, only expenses or only incomes
     * @param sortBy -> Sort by ? month, title or amount
     * @param how -> descending or ascending
     * @return the correct options as an array.
     */
    private String[] defineOptions(String what, String sortBy, String how) {

        String[] options = new String[6];

        if (what.equals("All")) {
            options[0] = "Only Expenses";
            options[1] = "Only Incomes";
        } else if (what.equals("Only Expenses")) {
            options[0] = "All";
            options[1] = "Only Incomes";
        } else {
            options[0] = "All";
            options[1] = "Only Expenses";
        }

        if (sortBy.equals("Month")) {
            options[2] = "Title";
            options[3] = "Amount";
        } else if (sortBy.equals("Title")) {
            options[2] = "Month";
            options[3] = "Amount";
        } else {
            options[2] = "Month";
            options[3] = "Title";
        }

        options[4] = how.equals("Descending") ? "Ascending" : "Descending";

        options[5] = "Back to menu";

        return options;
    }

    /**
     * Offer options to re-arrange the list of items - display at the bottom of it.
     * @param options -> options to display
     */
    private void displayOptions(String[] options) {

        System.out.println("\n ------------------------------------ \nOptions : ");
        System.out.print("- Show ");

        for (int i = 0 ; i < options.length ; i++) {

            System.out.print("(" + (i+1) + ") " + options[i] + " ");

            if (i == 1) { System.out.print("\n- Sort by ");}
            if (i == 3) { System.out.print("\n- Change order to ");}
            if (i == 4) { System.out.println("\n");}

        }

        System.out.println("\n");
    }


    /**
     * Dispatch the correct displaying of user items, regarding his choices.
     * Ugly, can we remove/arrange those 3 almost similar case ?
     * @param what -> What are we displaying ? all, only expenses or only incomes
     * @param sortBy -> Sort by ? month, title or amount
     * @param how -> descending or ascending
     */
    private void displayAskedItems(String what, String sortBy, String how) {
        System.out.println("\n ------------------------------------\n ");
        switch (what) {
            case "All" :
                if (sortBy.equals("Month")) {
                    if (how.equals("Descending")) {
                        System.out.println(" - all month descending - ");
                    } else {
                        System.out.println(" - all month ascending - ");
                    }
                } else if (sortBy.equals("Title")){
                    if (how.equals("Descending")) {
                        System.out.println(" - all title descending - ");
                    } else {
                        System.out.println(" - all title ascending - ");
                    }
                } else {
                    if (how.equals("Descending")) {
                        System.out.println(" - all amount descending - ");
                    } else {
                        System.out.println(" - all amount ascending - ");
                    }
                }
                break;
            case "Only Expenses" :
                if (sortBy.equals("Month")) {
                    if (how.equals("Descending")) {
                        System.out.println(" - only expenses month descending - ");
                    } else {
                        System.out.println(" - only expenses month ascending - ");
                    }
                } else if (sortBy.equals("Title")){
                    if (how.equals("Descending")) {
                        System.out.println(" - only expenses title descending - ");
                    } else {
                        System.out.println(" - only expenses title ascending - ");
                    }
                } else {
                    if (how.equals("Descending")) {
                        System.out.println(" - only expenses amount descending - ");
                    } else {
                        System.out.println(" - only expenses amount ascending - ");
                    }
                }
                break;
            case "Only Incomes" :
                if (sortBy.equals("Month")) {
                    if (how.equals("Descending")) {
                        System.out.println(" - only incomes month descending - ");
                    } else {
                        System.out.println(" - only incomes month ascending - ");
                    }
                } else if (sortBy.equals("Title")){
                    if (how.equals("Descending")) {
                        System.out.println(" - only incomes title descending - ");
                    } else {
                        System.out.println(" - only incomes title ascending - ");
                    }
                } else {
                    if (how.equals("Descending")) {
                        System.out.println(" - only incomes amount descending - ");
                    } else {
                        System.out.println(" - only incomes amount ascending - ");
                    }
                }
                break;
            default:
                break;
        }
    }


    // =============================================================================================
    // -------------------------------- UI display - Edit  -----------------------------------------
    // =============================================================================================

    /**
     * Method to edit one line. Should we use the line number as an index of ArrayList ?
     * @param lineNumberToEdit -> Object to edit (Title - Amount - Month)
     */
    private void editLine(int lineNumberToEdit) {

        this.newItemForm(); // Should return object

        // Update : ..replace(lineNumber, new object)

    }

    /**
     * Could take default values in parameters,
     * and print it as a default value when editing: "Title (my old value)"
     */
    private void newItemForm() {
        System.out.println("Type : (1) Expense - (2) Income :");
        int inputType = getExpectedInteger(1,2);

        System.out.println("Title :");
        String inputTitle = getExpectedString(3, 20);

        System.out.println("Amount : ");
        int inputAmount = getExpectedInteger(0, 10000000);

        System.out.println("Month : (0) Current month - (1) January - (2) February - ... -> ");
        int inputMonth = getExpectedInteger(0, 12);

        // Need to add the line Number -> useful for editing
        System.out.println("This object will be created : " +
                inputType + " - " + inputTitle + " - " + inputAmount + " - " + inputMonth + ".");
    }


    // =============================================================================================
    // -------------------------------- UI display - Utils -----------------------------------------
    // =============================================================================================

    /**
     * Get an integer input from user. Ask again if not in range. Throw exception if not an Integer.
     * @param min  lowest possible int
     * @param max  highest possible int
     * @return User input Integer
     */
    private int getExpectedInteger(int min, int max) {
        int input;

        do {
            System.out.print("->");

            try {
                input = this.scan.nextInt();
            } catch (Exception e) {
                throw new IllegalArgumentException("We are expecting an integer here.");
            }

            if (input > max || input < min){
                System.out.println("Please enter a number between " + min + " and " + max + ".");
            }

        } while (input > max || input < min);

        return input;
    }

    /**
     * Get an String input from userAsk again if not in range. Do we need an exception here ??
     * @param minLength  lowest possible length
     * @param maxLength  highest possible length
     * @return User input string
     */
    private String getExpectedString(int minLength, int maxLength) {

        String input = this.scan.nextLine();
        // First scan here is not take in count
        // Java consider our "/n" or "" coming from previous one as input

        while (input.length() > maxLength || input.length() < minLength){

            System.out.print("->");
            // Need some try catch or not ??
            input = this.scan.nextLine();

            if (input.length() > maxLength || input.length() < minLength){
                System.out.println("Please enter a string between " + minLength + " and " + maxLength + " characters.");
            }

        }
        return input;
    }
}
