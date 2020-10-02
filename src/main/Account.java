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

    // Update item

    // remove item

    // Display items (what, sortby, how)
    // Create a local list we can map/reduce/sort, ... and display
}
