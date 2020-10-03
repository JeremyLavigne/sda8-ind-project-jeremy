package main;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public List<Item> getItems() {
        return this.items;
    }

    public int getItemsSize() {
        return this.items.size();
    }

    public void addItem(Item item) {
        this.items.add(item);
    }

    public void updateItem(int index, Item updatedItem) {
        this.items.set(index, updatedItem);
    }

    /**
     * Remove one item from the list
     * @param index -> index of item to remove
     */
    public void removeItem(int index) {
        // No need to check if empty list or index out of bounds, already done before.
        this.items.remove(index);
    }


    /**
     * Dispatch the correct displaying of user items, regarding his choices.
     * Quite long method : 3*2*2 = 12 different cases
     * @param what -> What are we displaying ? 'All', 'Only expenses' or 'Only incomes'
     * @param sortBy -> Sort by ? 'Month', 'Title' or 'Amount'
     * @param how -> 'Descending' or 'Ascending'
     */
    public void printItems(String what, String sortBy, String how) {

        // Empty list : will receive the an already ordered list regarding parameters
        List<Item> listToPrint = new ArrayList<>();

        switch (what) {
            case "All" :
                if (sortBy.equals("Month")) {
                    if (how.equals("Descending")) {
                        listToPrint = this.items.stream()
                                .sorted((i1,i2)-> Integer.compare(i1.getMonth(), i2.getMonth()))
                                .collect(Collectors.toList());
                    } else {
                        listToPrint = this.items.stream()
                                .sorted((i1,i2)-> Integer.compare(i2.getMonth(), i1.getMonth()))
                                .collect(Collectors.toList());
                    }
                } else if (sortBy.equals("Title")){
                    if (how.equals("Descending")) {
                        listToPrint = this.items.stream()
                                .sorted((i1,i2)-> CharSequence.compare(i1.getTitle(), i2.getTitle()))
                                .collect(Collectors.toList());
                    } else {
                        listToPrint = this.items.stream()
                                .sorted((i1,i2)-> CharSequence.compare(i2.getTitle(), i1.getTitle()))
                                .collect(Collectors.toList());
                    }
                } else {
                    if (how.equals("Descending")) {
                        listToPrint = this.items.stream()
                                .sorted((i1,i2)-> Integer.compare(i1.getAmount(), i2.getAmount()))
                                .collect(Collectors.toList());
                    } else {
                        listToPrint = this.items.stream()
                                .sorted((i1,i2)-> Integer.compare(i2.getAmount(), i1.getAmount()))
                                .collect(Collectors.toList());
                    }
                }
                break;
            case "Only Expenses" :
                if (sortBy.equals("Month")) {
                    if (how.equals("Descending")) {
                        listToPrint = this.items.stream()
                                .filter(item -> item.getAmount() < 0)
                                .sorted((i1,i2)-> Integer.compare(i1.getMonth(), i2.getMonth()))
                                .collect(Collectors.toList());
                    } else {
                        listToPrint = this.items.stream()
                                .filter(item -> item.getAmount() < 0)
                                .sorted((i1,i2)-> Integer.compare(i2.getMonth(), i1.getMonth()))
                                .collect(Collectors.toList());
                    }
                } else if (sortBy.equals("Title")){
                    if (how.equals("Descending")) {
                        listToPrint = this.items.stream()
                                .filter(item -> item.getAmount() < 0)
                                .sorted((i1,i2)-> CharSequence.compare(i1.getTitle(), i2.getTitle()))
                                .collect(Collectors.toList());
                    } else {
                        listToPrint = this.items.stream()
                                .filter(item -> item.getAmount() < 0)
                                .sorted((i1,i2)-> CharSequence.compare(i2.getTitle(), i1.getTitle()))
                                .collect(Collectors.toList());
                    }
                } else {
                    if (how.equals("Descending")) {
                        listToPrint = this.items.stream()
                                .filter(item -> item.getAmount() < 0)
                                .sorted((i1,i2)-> Integer.compare(i1.getAmount(), i2.getAmount()))
                                .collect(Collectors.toList());
                    } else {
                        listToPrint = this.items.stream()
                                .filter(item -> item.getAmount() < 0)
                                .sorted((i1,i2)-> Integer.compare(i2.getAmount(), i1.getAmount()))
                                .collect(Collectors.toList());
                    }
                }
                break;
            case "Only Incomes" :
                if (sortBy.equals("Month")) {
                    if (how.equals("Descending")) {
                        listToPrint = this.items.stream()
                                .filter(item -> item.getAmount() > 0)
                                .sorted((i1,i2)-> Integer.compare(i1.getMonth(), i2.getMonth()))
                                .collect(Collectors.toList());
                    } else {
                        listToPrint = this.items.stream()
                                .filter(item -> item.getAmount() > 0)
                                .sorted((i1,i2)-> Integer.compare(i2.getMonth(), i1.getMonth()))
                                .collect(Collectors.toList());
                    }
                } else if (sortBy.equals("Title")){
                    if (how.equals("Descending")) {
                        listToPrint = this.items.stream()
                                .filter(item -> item.getAmount() > 0)
                                .sorted((i1,i2)-> CharSequence.compare(i1.getTitle(), i2.getTitle()))
                                .collect(Collectors.toList());
                    } else {
                        listToPrint = this.items.stream()
                                .filter(item -> item.getAmount() > 0)
                                .sorted((i1,i2)-> CharSequence.compare(i2.getTitle(), i1.getTitle()))
                                .collect(Collectors.toList());
                    }
                } else {
                    if (how.equals("Descending")) {
                        listToPrint = this.items.stream()
                                .filter(item -> item.getAmount() > 0)
                                .sorted((i1,i2)-> Integer.compare(i1.getAmount(), i2.getAmount()))
                                .collect(Collectors.toList());
                    } else {
                        listToPrint = this.items.stream()
                                .filter(item -> item.getAmount() > 0)
                                .sorted((i1,i2)-> Integer.compare(i2.getAmount(), i1.getAmount()))
                                .collect(Collectors.toList());
                    }
                }
                break;
            default:
                break;
        }

        System.out.println("\n ------------------------------------\n ");
        printList(listToPrint);

    }

    /**
     * Display the list in parameter - and index as a line number
     * @param listToPrint -> Actual list to print
     */
    public void printList(List<Item> listToPrint) {
        for (int i = 0 ; i < listToPrint.size(); i ++) {
            System.out.println((i+1) + " - " + listToPrint.get(i));
        }
    }

}
