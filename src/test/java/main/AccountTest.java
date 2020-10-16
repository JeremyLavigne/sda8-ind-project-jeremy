package main;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;

public class AccountTest {

    Account account;
    ArrayList<Item> items;
    Item expense1;
    Item expense2;
    Item income1;
    Item income2;

    @BeforeEach
    public void createAccount() {
        expense1 = new Item("Expense", "Expense 1", 20, 1);
        expense2 = new Item("Expense", "Expense 2", 30, 2);
        income1 = new Item("Income", "Income 1", 20, 5);
        income2 = new Item("Income", "Income 2", 50, 7);

        items = new ArrayList<>(Arrays.asList(
                expense1, expense2, income1, income2
        ));

        account = new Account(items);
    }

    @Nested
    class GetBalanceTests {

        @Test
        @DisplayName("getBalance method return the expected sum of items amount")
        public void getBalanceReturnExpectedSumOfItemsAmount() {

            int balance = account.getBalance();

            assertThat(balance).isEqualTo(20);

        }

        @Test
        @DisplayName("getBalance method return 0 when empty items list")
        public void getBalanceReturn0WhenEmptyList() {

            Account accountTest = new Account(new ArrayList<>());

            int balance = accountTest.getBalance();

            assertThat(balance).isEqualTo(0);

        }
    }


    @Nested
    class AddItemTests {

        @Test
        @DisplayName("addItem method increase the list size of 1")
        public void addItemIncreaseListSize() {

            int initialListSize = account.getItemsSize();

            account.addItem(new Item("Expense", "TestItem", 0, 1));

            int newListSize = account.getItemsSize();

            assertThat(initialListSize).isEqualTo(newListSize - 1);

        }

        @Test
        @DisplayName("addItem method add the new item in the list")
        public void addItemAddItInList() {

            account.addItem(new Item("Expense", "TestItem", 0, 1));

            String expected = "Expense | TestItem | 0 | January";

            assertThat(expected).isEqualTo(account.getItems().get(4).toString());

        }
    }


    @Nested
    class UpdateItemTests {

        @Test
        @DisplayName("updateItem method doesn't change the list size")
        public void updateItemKeepListSize() {

            int initialListSize = account.getItemsSize();

            account.updateItem(0, new Item("Expense", "TestItem", 0, 1));

            int newListSize = account.getItemsSize();

            assertThat(initialListSize).isEqualTo(newListSize);

        }

        @Test
        @DisplayName("updateItem method reslly update the item")
        public void updateItemUpdateItemInList() {

            account.updateItem(0, new Item("Expense", "TestItem", 0, 1));

            String expected = "Expense | TestItem | 0 | January";

            assertThat(expected).isEqualTo(account.getItems().get(0).toString());

        }
    }

    @Nested
    class RemoveItemTests {

        @Test
        @DisplayName("removeItem method decrease the list size of 1")
        public void removeItemDecreaseListSize() {

            int initialListSize = account.getItemsSize();

            account.removeItem(0);

            int newListSize = account.getItemsSize();

            assertThat(initialListSize).isEqualTo(newListSize + 1);

        }

        @Test
        @DisplayName("removeItem method remove it from the list.")
        public void removeItemRemoveItFromList() {

            // Removing the last item
            account.removeItem(3);

            // Expected an Exception when trying to get it
            assertThatExceptionOfType(IndexOutOfBoundsException.class)
                    .isThrownBy(() -> {
                        account.getItems().get(3);
                    });
        }
    }


    @Nested
    class PrintItemTests {

        @Test
        @DisplayName("printItems method with month parameter print a list sorted by month.")
        public void printItemsPrintSortedItems() {

            // Catch all will be print in our 'outContent'
            ByteArrayOutputStream outContent = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outContent));

            // Print
            account.printItems("All", "Month", "Ascending");

            // Test the output
            String expected = "\n ------------------------------------\n\n" +
                                "1 - \u001B[31mExpense | Expense 1 | 20 | January\u001B[0m\n" +
                                "2 - \u001B[31mExpense | Expense 2 | 30 | February\u001B[0m\n" +
                                "3 - \u001B[32mIncome | Income 1 | 20 | May\u001B[0m\n" +
                                "4 - \u001B[32mIncome | Income 2 | 50 | July\u001B[0m\n";

            assertThat(items).isSortedAccordingTo(Comparator.comparingInt(Item::getMonth));
            assertThat(expected).isEqualTo(outContent.toString());

        }

        @Test
        @DisplayName("printItems method with 'only expense' parameter print a list of expense only")
        public void printItemsPrintOnlyExpenses() {

            // Catch all will be print in our 'outContent'
            ByteArrayOutputStream outContent = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outContent));

            // Print
            account.printItems("Only Expenses", "Month", "Ascending");

            // Test the output
            String expected = "\n ------------------------------------\n\n" +
                    "1 - \u001B[31mExpense | Expense 1 | 20 | January\u001B[0m\n" +
                    "2 - \u001B[31mExpense | Expense 2 | 30 | February\u001B[0m\n";

            assertThat(expected).isEqualTo(outContent.toString());

        }

        @Test
        @DisplayName("printItems method with 'only incomes' and 'descending' parameters print the right list")
        public void printItemsPrintOnlyIncomesDescending() {

            // Catch all will be print in our 'outContent'
            ByteArrayOutputStream outContent = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outContent));

            // Print
            account.printItems("Only Incomes", "Month", "Descending");

            // Test the output
            String expected = "\n ------------------------------------\n\n" +
                    "1 - \u001B[32mIncome | Income 2 | 50 | July\u001B[0m\n" +
                    "2 - \u001B[32mIncome | Income 1 | 20 | May\u001B[0m\n";

            assertThat(expected).isEqualTo(outContent.toString());

        }
    }
}
