package main;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


/**
 * This class represents one user account.
 * One single field -> List of item (expense or income)
 *
 * @author Jeremy
 * @version 1.0
 */
public class Account {

    private ArrayList<Item> items;

    public Account(ArrayList<Item> initialList) {
        this.items = initialList;
    }

    /**
     * Calculate balance using all item amount inside current list of items
      */
    public int getBalance() {

        int incomeSum = this.items.stream()                     // All incomes
                .filter(item -> item.getType().equals("Income"))
                .map(Item::getAmount)
                .reduce(0, (sum, previous) -> sum += previous);

        int expenseSum = this.items.stream()                    // All expenses
                .filter(item -> item.getType().equals("Expense"))
                .map(Item::getAmount)
                .reduce(0, (sum, previous) -> sum += previous);

        return incomeSum - expenseSum;                          // Balance
    }

    public List<Item> getItems() {
        return this.items;
    }

    public int getItemsSize() {
        return this.items.size();
    }

    public void addItem(Item item) {
        this.items.add(item);
    }

    // No need to check if empty list or index out of bounds, already done before.
    public void updateItem(int index, Item updatedItem) {
        this.items.set(index, updatedItem);
    }
    // Same here
    public void removeItem(int index) {
        this.items.remove(index);
    }


    /**
     * Define the correct list to be display, regarding user choices. Then, print the list.
     *
     * @param what -> 'All', 'Only expenses' or 'Only incomes'
     * @param sortBy -> 'Month', 'Title' or 'Amount'
     * @param how -> 'Descending' or 'Ascending'
     */
    public void printItems(String what, String sortBy, String how) {

        List<Item> listToPrint = this.items;

        if (!what.equals("All")) {
            listToPrint = listToPrint.stream()
                    .filter(item ->
                            item.getType().equals( what.equals("Only Expenses") ? "Expense" : "Income"))
                    .collect(Collectors.toList());
        }
        // If "All", keep the original list

        switch (sortBy) {
            case "Month":
                Collections.sort(listToPrint, Comparator.comparingInt(Item::getMonth));
                break;
            case "Title":
                Collections.sort(listToPrint, (i1, i2) -> CharSequence.compare(i1.getTitle(), i2.getTitle()));
                break;
            case "Amount":
                Collections.sort(listToPrint, Comparator.comparingInt(Item::getAmount));
                break;
            default: break;
        }

        if (how.equals("Descending")) {
            Collections.reverse(listToPrint);
        }
        // if "Ascending", keep original order

        System.out.println("\n ------------------------------------\n");

        printList(listToPrint);
    }

    /**
     * Display the list in parameter - add a line number
     * Expense in Red, Income in Green.
     *
     * @param listToPrint - List to print.
     */
    public void printList(List<Item> listToPrint) {
        String ANSI_RESET = "\u001B[0m";    // Back to black
        String ANSI_RED = "\u001B[31m";     // Put before a String to have it in Red
        String ANSI_GREEN = "\u001B[32m";   // Put before a String to have it in Green

        for (int i = 0 ; i < listToPrint.size(); i ++) {
            System.out.print((i+1) + " - ");

            if (listToPrint.get(i).getType().equals("Expense")) {
                System.out.println(ANSI_RED + listToPrint.get(i) + ANSI_RESET);
            } else {
                System.out.println(ANSI_GREEN + listToPrint.get(i) + ANSI_RESET);
            }

        }
    }

}
