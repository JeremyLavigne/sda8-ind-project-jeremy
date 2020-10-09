package main;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ItemTest {

    @Test
    @DisplayName("getStringMonth method return the expected month as a String")
    public void getStringMonthReturnStringMonth() {

        Item item = new Item("Expense", "Test", 0, 12);

        String month = item.getStringMonth();

        assertThat(month).isEqualTo("December");
    }
}
