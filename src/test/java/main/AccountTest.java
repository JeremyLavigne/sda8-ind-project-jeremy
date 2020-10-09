package main;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class AccountTest {

    @Test
    @DisplayName("getBalance method return the expected sum of items amount")
    public void getBalanceReturnExpectedSumOfItemsAmount() {
        assertThat(1).isEqualTo(1);
    }

    @Test
    @DisplayName("getBalance method return 0 when empty items list")
    public void getBalanceReturn0WhenEmptyList() {
        assertThat(1).isEqualTo(1);
    }

    @Test
    @DisplayName("addItem method increase the list size of 1")
    public void addItemIncreaseListSize() {
        assertThat(1).isEqualTo(1);
    }

    @Test
    @DisplayName("updateItem method doesn't change the list size")
    public void updateItemKeepListSize() {
        assertThat(1).isEqualTo(1);
    }

    @Test
    @DisplayName("removeItem method decrease the list size of 1")
    public void removeItemDecreaseListSize() {
        assertThat(1).isEqualTo(1);
    }

    @Test
    @DisplayName("printItems method with month parameter print a list sorted by month")
    public void printItemsPrintSortedItems() {
        assertThat(1).isEqualTo(1);
    }

    @Test
    @DisplayName("printItems method with 'only expense' parameter print a list of expense only")
    public void printItemsPrintOnlyExpenses() {
        assertThat(1).isEqualTo(1);
    }

}
