package main;

/**
 * One item.
 * Type -> Expense or Income
 * Title -> Label
 * Amount
 * Month -> taken as an integer for sorting, used as a String for displaying
 */
public class Item {
    private final String type; // Boolean ?
    private final int amount;
    private final String title;
    private final int month;

    public Item(String itemType, String itemTitle, int itemAmount, int itemMonth){
        this.type = itemType;
        this.amount = itemAmount;
        this.title = itemTitle;
        this.month = itemMonth;
    }

    public int getAmount() {
        return this.amount;
    }

    public int getMonth() {
        return this.month;
    }

    public String getTitle() {
        return this.title;
    }

    public String getType() {
        return this.type;
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
    public String toString() {
            return this.type + " | "
                    + this.title + " | "
                    + this.amount  + " | "
                    + this.getStringMonth();
    }

}
