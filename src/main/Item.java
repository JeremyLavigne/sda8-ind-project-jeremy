package main;

/**
 * Can be either Expense or Income
 */
public class Item {
    private int amount;
    private String title;
    private int month;

    public Item(int itemAmount, String itemTitle, int itemMonth){
        this.amount = itemAmount;
        this.title = itemTitle;
        this.month = itemMonth;
    }

    @Override
    public String toString() {
        return "Item{" +
                "amount=" + amount +
                ", title='" + title + '\'' +
                ", month=" + month +
                '}';
    }
}
