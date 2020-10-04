package main;

/**
 * This class represent one expense.
 * Is it really necessary ?
 */
public class Expense extends Item{

    public Expense(int itemAmount, String itemTitle, int itemMonth) {
        super(-itemAmount, itemTitle, itemMonth);
    }

    @Override
    public String toString() {
        return super.title + " | " + super.amount  + " | " + "--------- | " + super.toString();
    }
}
