package main;

/**
 * One item - Amount/Title/Month
 * Can be either Expense or Income
 */
public class Item {
    protected int amount;
    protected String title;
    protected int month; // Month is taken as an integer for sorting, used as a String for displaying

    public Item(int itemAmount, String itemTitle, int itemMonth){
        this.amount = itemAmount;
        this.title = itemTitle;
        this.month = itemMonth;
    }

    public int getAmount() {
        return amount;
    }

    public int getMonth() {
        return month;
    }

    public String getTitle() {
        return title;
    }

    public String getType() {
        if (this.getAmount() < 0) {
            return "Expense";
        }
        return "Income";
    }

    public String getStringMonth() {
        return switch (this.getMonth()) {
            case 1 -> "January";
            case 2 -> "February";
            case 3 -> "March";
            case 4 -> "April";
            case 5 -> "May";
            case 6 -> "June";
            case 7 -> "July";
            case 8 -> "August";
            case 9 -> "September";
            case 10 -> "October";
            case 11 -> "November";
            case 12 -> "December";
            default -> "Unknown";
        };
    }

    @Override
    /*
     * Return only month here, the rest is managed by subclasses
     */
    public String toString() {
        return getStringMonth();
    }

}
