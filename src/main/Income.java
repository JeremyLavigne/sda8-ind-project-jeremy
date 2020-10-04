package main;

/**
 * This class represent one income.
 * Is it really necessary ?
 */
public class Income extends Item {

    public Income(int itemAmount, String itemTitle, int itemMonth) {
        super(itemAmount, itemTitle, itemMonth);
    }

    @Override
    public String toString() {
        return super.title + " | "  + "--------- | " + super.amount  + " | " + super.toString();
    }
}

