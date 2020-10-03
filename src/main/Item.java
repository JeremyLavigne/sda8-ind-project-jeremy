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
        switch (this.getMonth()) {
            case 1 : return "January";
            case 2 : return "February";
            case 3 : return "March";
            case 4 : return "April";
            case 5 : return "May";
            case 6 : return "June";
            case 7 : return "July";
            case 8: return "August";
            case 9 : return "September";
            case 10 : return "October";
            case 11 : return "November";
            case 12 : return "December";
            default: return "Unknown";
        }
    }

    @Override
    /**
     * Return only month here, the rest is managed by subclasses
     */
    public String toString() {
        return getStringMonth();
    }

}
