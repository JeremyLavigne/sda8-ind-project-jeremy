package main;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class AccountTest {
    ArrayList<Item> items = new ArrayList<>();
    Item expense1 = new Item("Expense", "Expense 1", 20, 1);
    Item expense2 = new Item("Expense", "Expense 2", 30, 2);
    Item expense3 = new Item("Expense", "Expense 3", 10, 8);
    Item income1 = new Item("Income", "Income 1", 20, 5);
    Item income2 = new Item("Income", "Income 2", 50, 7);
    Item income3 = new Item("Income", "Income 3", 30, 9);

    @BeforeEach
    public void createList() {
        items = new ArrayList<>(Arrays.asList(
                expense1, expense2, expense3, income1, income2, income3
        ));
    }

    @Test
    @DisplayName("getBalance method return the expected sum of items amount")
    public void getBalanceReturnExpectedSumOfItemsAmount() {

        Account account = new Account(this.items);

        int balance = account.getBalance();

        assertThat(balance).isEqualTo(40);

    }

    @Test
    @DisplayName("getBalance method return 0 when empty items list")
    public void getBalanceReturn0WhenEmptyList() {

        Account account = new Account(new ArrayList<Item>());

        int balance = account.getBalance();

        assertThat(balance).isEqualTo(0);

    }

    @Test
    @DisplayName("addItem method increase the list size of 1")
    public void addItemIncreaseListSize() {

        Account account = new Account(this.items);

        int initialListSize = account.getItemsSize();

        account.addItem(new Item("Expense", "TestItem", 0, 1));

        int newListSize = account.getItemsSize();

        assertThat(initialListSize).isEqualTo(newListSize - 1);

    }

    @Test
    @DisplayName("updateItem method doesn't change the list size")
    public void updateItemKeepListSize() {

        Account account = new Account(this.items);

        int initialListSize = account.getItemsSize();

        account.updateItem(0, new Item("Expense", "TestItem", 0, 1));

        int newListSize = account.getItemsSize();

        assertThat(initialListSize).isEqualTo(newListSize);

    }

    @Test
    @DisplayName("removeItem method decrease the list size of 1")
    public void removeItemDecreaseListSize() {

        Account account = new Account(this.items);

        int initialListSize = account.getItemsSize();

        account.removeItem(0);

        int newListSize = account.getItemsSize();

        assertThat(initialListSize).isEqualTo(newListSize + 1);

    }

    @Test
    @DisplayName("printItems method with month parameter print a list sorted by month")
    public void printItemsPrintSortedItems() {

        Account account = new Account(this.items);

        account.printItems("All", "Month", "Descending");

        assertThat(this.items).isSortedAccordingTo(Comparator.comparingInt(Item::getMonth));

    }

    @Test
    @DisplayName("printItems method with 'only expense' parameter print a list of expense only")
    public void printItemsPrintOnlyExpenses() {

        assertThat(this.items).filteredOn("type", "Expense")
                .containsOnly(expense1, expense2, expense3);

    }

}
