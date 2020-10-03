package main;

import java.util.ArrayList;

/**
 * This class represents one user account.
 * Should we use Arraylist ? easier to deal with, sort, etc..
 * Should we use HashMap ? Can be interesting when editing.
 * two separate lists expenses - income ? arrayList<Item> items ?
 */
public class Account {
    private int balance;
    private ArrayList<Item> items;

    public Account() {
        this.balance = 0;
        this.items = new ArrayList<>();
    }

    public int getBalance() {
        return this.balance;
    }

    // Add item
    public void addItem(Item item) {
        this.items.add(item);
    }

    // Should be inside ShowItems (what, hoz, ..)
    // Should use a list as a parameter
    // Never actually need to print the original list
    public void printList() {
        for (int i = 0 ; i < this.items.size(); i ++) {
            System.out.println((i+1) + " - " + items.get(i));
        }
    }
    // Update item
    // Check if empty list

    // remove item
    // Check if empty list

    // Display items (what, sortby, how)
    // Create a local list we can map/reduce/sort, ... and display
}
