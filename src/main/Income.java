package main;

/**
 * This class represent one income and all its details :
 * Amount(positive) - title - month
 * almost Similar to Expense, extends abstract class ?
 */
public class Income extends Item {

    public Income(int itemAmount, String itemTitle, int itemMonth) {
        super(itemAmount, itemTitle, itemMonth);
    }
}
