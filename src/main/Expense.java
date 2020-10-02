package main;

/**
 * This class represent one expense and all its details :
 * Amount(negative) - title - month
 * almost Similar to Income, extends abstract class ?
 */
public class Expense extends Item{
    public Expense(int itemAmount, String itemTitle, int itemMonth) {
        super(-itemAmount, itemTitle, itemMonth);
    }

    //?? Really useful ?? Can we deal with only items??
}
