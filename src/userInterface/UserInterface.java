package userInterface;

import main.Account;

import java.util.Scanner;


/**
 *
 */
public class UserInterface {
    private Account account;
    private Scanner scan;

    public UserInterface(Account userAccount){
        this.account = userAccount;
        this.scan = new Scanner(System.in);
    }

    public void start() {

        System.out.println("\nWelcome to TrackMoney.");
        System.out.println("You have currently " + this.account.getBalance() + " kr on your account.");

        this.offerOptions();

        this.scan.close();
    }


    // -------------------------------- UI display ---------------------------------

    private void offerOptions() {

        System.out.println("---------------------------------------");
        System.out.println("\nPick an option :\n" +
                "(1) Show items (All / Expense(s) / Income(s)), \n" +
                "(2) Add new expense / income, \n" +
                "(3) Edit item (edit, remove), \n" +
                "(4) Save and quit. \n");


        int input = getExpectedInput(1,4);

        switch (input) {
            case 1 : this.showItemsPicked("All", "Month", "Descending"); break;
            case 2 : this.addItemPicked(); break;
            case 3 : this.editItemPicked(); break;
            case 4 : this.saveAndQuit(); break;
            default : break;
        }
    }


    private void showItemsPicked(String what, String sortBy, String how) {

        displayAskedItems(what, sortBy, how);

        String[] options = defineOptions(what, sortBy, how);

        displayOptions(options);

        int input = getExpectedInput(1,6);

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

    private void addItemPicked() {
        System.out.println("Deal with new item");
    }

    private void editItemPicked() {
        System.out.println("Deal with editing options");
    }

    private void saveAndQuit() {
        System.out.println("Save and quit");
    }


    // ---------------------------- Display items (Show) -------------------------------

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
     * Ugly, can we remove those 3 almost similar case ?
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

    // -------------------------------- Utils ---------------------------------

    /**
     * Return an integer in the range asked. Ask again if not in range.
     * Throw exception if not an Integer.
     */
    private int getExpectedInput(int min, int max) {
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
}
