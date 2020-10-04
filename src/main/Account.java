package main;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class represents one user account.
 * One single field -> List of item (expense or income)
 */
public class Account {
    private final ArrayList<Item> items;

    public Account(ArrayList<Item> initialList) {
        this.items = initialList;
    }

    // Calculate balance using all item amount inside current list of items
    public int getBalance() {
        int incomeSum = this.items.stream()
                .filter(item -> item.getType().equals("Income"))
                .map(Item::getAmount)
                .reduce(0, (sum, previous) -> sum += previous);
        int expenseSum = this.items.stream()
                .filter(item -> item.getType().equals("Expense"))
                .map(Item::getAmount)
                .reduce(0, (sum, previous) -> sum += previous);

        return incomeSum - expenseSum;
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
    public void removeItem(int index) {
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
                                .sorted(Comparator.comparingInt(Item::getMonth))
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
                                .sorted(Comparator.comparingInt(Item::getAmount))
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
                                .filter(item -> item.getType().equals("Expense"))
                                .sorted(Comparator.comparingInt(Item::getMonth))
                                .collect(Collectors.toList());
                    } else {
                        listToPrint = this.items.stream()
                                .filter(item -> item.getType().equals("Expense"))
                                .sorted((i1,i2)-> Integer.compare(i2.getMonth(), i1.getMonth()))
                                .collect(Collectors.toList());
                    }
                } else if (sortBy.equals("Title")){
                    if (how.equals("Descending")) {
                        listToPrint = this.items.stream()
                                .filter(item -> item.getType().equals("Expense"))
                                .sorted((i1,i2)-> CharSequence.compare(i1.getTitle(), i2.getTitle()))
                                .collect(Collectors.toList());
                    } else {
                        listToPrint = this.items.stream()
                                .filter(item -> item.getType().equals("Expense"))
                                .sorted((i1,i2)-> CharSequence.compare(i2.getTitle(), i1.getTitle()))
                                .collect(Collectors.toList());
                    }
                } else {
                    if (how.equals("Descending")) {
                        listToPrint = this.items.stream()
                                .filter(item -> item.getType().equals("Expense"))
                                .sorted(Comparator.comparingInt(Item::getAmount))
                                .collect(Collectors.toList());
                    } else {
                        listToPrint = this.items.stream()
                                .filter(item -> item.getType().equals("Expense"))
                                .sorted((i1,i2)-> Integer.compare(i2.getAmount(), i1.getAmount()))
                                .collect(Collectors.toList());
                    }
                }
                break;
            case "Only Incomes" :
                if (sortBy.equals("Month")) {
                    if (how.equals("Descending")) {
                        listToPrint = this.items.stream()
                                .filter(item -> item.getType().equals("Income"))
                                .sorted(Comparator.comparingInt(Item::getMonth))
                                .collect(Collectors.toList());
                    } else {
                        listToPrint = this.items.stream()
                                .filter(item -> item.getType().equals("Income"))
                                .sorted((i1,i2)-> Integer.compare(i2.getMonth(), i1.getMonth()))
                                .collect(Collectors.toList());
                    }
                } else if (sortBy.equals("Title")){
                    if (how.equals("Descending")) {
                        listToPrint = this.items.stream()
                                .filter(item -> item.getType().equals("Income"))
                                .sorted((i1,i2)-> CharSequence.compare(i1.getTitle(), i2.getTitle()))
                                .collect(Collectors.toList());
                    } else {
                        listToPrint = this.items.stream()
                                .filter(item -> item.getType().equals("Income"))
                                .sorted((i1,i2)-> CharSequence.compare(i2.getTitle(), i1.getTitle()))
                                .collect(Collectors.toList());
                    }
                } else {
                    if (how.equals("Descending")) {
                        listToPrint = this.items.stream()
                                .filter(item -> item.getType().equals("Income"))
                                .sorted(Comparator.comparingInt(Item::getAmount))
                                .collect(Collectors.toList());
                    } else {
                        listToPrint = this.items.stream()
                                .filter(item -> item.getType().equals("Income"))
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
