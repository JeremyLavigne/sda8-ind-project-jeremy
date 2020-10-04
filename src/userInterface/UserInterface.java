package userInterface;

import main.Account;
import main.Expense;
import main.Income;
import main.Item;

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
        // Right here, Balance can only be seen once, at the beginning, bad ?
        System.out.println("You have currently " + this.account.getBalance() + " kr on your account.");

        this.offerOptions();
    }

    /**
     * End user interface
     */
    private void stop() {
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
     *   @param what -> What are we displaying ? 'All', 'Only expenses' or 'Only incomes'
     *   @param sortBy -> Sort by ? 'Month', 'Title' or 'Amount'
     *   @param how -> 'Descending' or 'Ascending'
     */
    private void showItemsPicked(String what, String sortBy, String how) {

        this.account.printItems(what, sortBy, how);

        // Menu to re-arrange display
        String[] options = defineOptions(what, sortBy, how);
        displayOptions(options);

        int inputMenu = getExpectedInteger(1,6); // User choice in the below menu

        switch (inputMenu) {
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

        Item newItem = this.newItemForm("", "", 0, "");

        System.out.println("(1) Save - (2) Erase and restart");
        int inputSaveOrNot = getExpectedInteger(1,2);

        if (inputSaveOrNot == 1) {
            System.out.println("Saved!");
            this.account.addItem(newItem);
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

        int numberOfLines = this.account.getItemsSize();
        if (numberOfLines == 0) {
            System.out.println("There is nothing to edit.");
            this.offerOptions();
            return;
        }

        // Print list in original order - need to match line number & index in list
        this.account.printList(this.account.getItems());

        System.out.println("\n ------------------------------------ \n");
        System.out.println("Please enter the line number that you want to edit/remove");

        int lineNumberToEdit = getExpectedInteger(1, numberOfLines);

        System.out.println("\nDo you want (1) Edit or (2) Remove : ");
        int inputChoice = getExpectedInteger(1, 2);

        if (inputChoice == 1) {
            this.editLine(lineNumberToEdit - 1); // parameter = index in items list
            System.out.println("Line " + lineNumberToEdit + " was successfully edited.");
        } else {
            this.account.removeItem(lineNumberToEdit -1);
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
        this.stop();
    }



    // =============================================================================================
    // -------------------------------- UI display - Display items (1) -----------------------------
    // =============================================================================================

    /**
     * Define options regarding the current user choice. Avoid duplication : we do not offer the current displaying
     * @param what -> What are we displaying ? 'All', 'Only expenses' or 'Only incomes'
     * @param sortBy -> Sort by ? 'Month', 'Title' or 'Amount'
     * @param how -> 'Descending' or 'Ascending'
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


    // =============================================================================================
    // -------------------------------- UI display - Edit  -----------------------------------------
    // =============================================================================================

    /**
     * Method to edit one line. Should we use the line number as an index of ArrayList ?
     * @param index -> index of item to edit
     */
    private void editLine(int index) {

        // Get the previous data from item to re-use them as default values in form
        String currentType = this.account.getItems().get(index).getType();
        String currentTitle = this.account.getItems().get(index).getTitle();
        int currentAmount = this.account.getItems().get(index).getAmount();
        String currentMonth = this.account.getItems().get(index).getStringMonth();

        Item updatedItem = this.newItemForm(currentType, currentTitle, currentAmount, currentMonth);

        this.account.updateItem(index, updatedItem);

    }

    /**
     * Form to create/update an item. Default values in parameters (for editing)
     * @param currentAmount -> amount default value
     * @param currentTitle -> title default value
     * @param currentMonth -> month default value (as a string)
     * @param currentType -> type default value
     */
    private Item newItemForm(String currentType, String currentTitle, int currentAmount, String currentMonth) {

        System.out.println("Type : (1) Expense - (2) Income :");
        System.out.print(currentType.equals("") ? "" : "(Currently : " + currentType + ")");
        int inputType = getExpectedInteger(1,2);

        System.out.println("Title :");
        System.out.print(currentTitle.equals("") ? "" : "(Currently : " +  currentTitle + ")");
        String inputTitle = getExpectedString(3, 20);

        System.out.println("Amount : ");
        System.out.print(currentAmount == 0 ? "" : "(Currently : " +
                (currentAmount > 0 ? currentAmount : -currentAmount) + ")");
        int inputAmount = getExpectedInteger(0, 10000000);

        System.out.println("Month : (0) Current month - (1) January - (2) February - ... -> ");
        System.out.print(currentMonth.equals("") ? "" : "(Currently : " +  currentMonth + ")");
        int inputMonth = getExpectedInteger(0, 12);

        System.out.println("This object will be " +
                (currentTitle.equals("") ? "created : " : "updated as : " )+
                (inputType == 1 ? "New expense : " : "New expense : " )
                + inputTitle + " - " + inputAmount + "Sek - Month(" + inputMonth + ").");

        if (inputType == 1) {
            return new Expense(inputAmount, inputTitle, inputMonth);
        } else {
            return new Income(inputAmount, inputTitle, inputMonth);
        }
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
