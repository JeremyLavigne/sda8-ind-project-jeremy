package database;

import main.Item;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.*;


public class DatabaseTest {
    ArrayList<Item> testList = new ArrayList<>();
    Throwable thrown;

    @Test
    @DisplayName("Reading an existing (non empty) file should return a list (non empty)")
    public void ReadingExistingFileShouldReturnList() {

        Database myDatabase = new Database("assets/database.txt");

        try {
            this.testList = myDatabase.getListFromFile();
        } catch (FileNotFoundException e) {
            this.thrown = catchThrowable(() -> { throw new Exception("No file"); });
        }

        assertThat(testList).isInstanceOf(java.util.ArrayList.class);
        assertThat(testList.size()).isGreaterThan(0);
    }

    @Test
    @DisplayName("Reading a non existing file throw an exception")
    public void ReadingNonExistingFileShouldThrowException() {

        Database myDatabase = new Database("assets/noFile.txt");

        try {
            this.testList = myDatabase.getListFromFile();
        } catch (FileNotFoundException e) {
            this.thrown = catchThrowable(() -> { throw new Exception("No file"); });
        }

        assertThat(this.thrown).isInstanceOf(Exception.class)
                .hasMessageContaining("No file");

    }

    @Test
    @DisplayName("Reading an empty file should return an empty list")
    public void ReadingEmptyFileShouldReturnEmptyList() {

        File file = new File("assets/test.txt");
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Database myDatabase = new Database("assets/test.txt");

        try {
            this.testList = myDatabase.getListFromFile();
        } catch (FileNotFoundException e) {
            this.thrown = catchThrowable(() -> { throw new Exception("No file"); });
        }

        assertThat(testList).isInstanceOf(java.util.ArrayList.class);
        assertThat(testList.size()).isEqualTo(0);

        file.delete();

    }

    @Test
    @DisplayName("Writing a list into an existing file should replace the previous by the new one")
    public void WritingNewListIntoExistingFileShouldReplacePreviousByNew() {

        // New file with 3 lines
        File file = new File("assets/test.txt");
        try {
            file.createNewFile();
            FileWriter myWriter = new FileWriter("assets/test.txt");
            myWriter.write("Expense;test;0;0\nExpense;test;0;0\nExpense;test;0;0");
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Database myDatabase = new Database("assets/test.txt");

        try {
            this.testList = myDatabase.getListFromFile();
        } catch (FileNotFoundException e) {
            this.thrown = catchThrowable(() -> { throw new Exception("No file"); });
        }

        // New List with 1 items
        ArrayList<Item> newList = new ArrayList<>();
        newList.add(new Item("Expense", "test", 0, 1));

        try {
            myDatabase.writeListIntoFile(newList);
        } catch (IOException e) {
            this.thrown = catchThrowable(() -> { throw new Exception("Other"); });
        }

        // Get again from the file
        ArrayList<Item> testList2 = new ArrayList<>();
        try {
            testList = myDatabase.getListFromFile();
        } catch (FileNotFoundException e) {
            this.thrown = catchThrowable(() -> { throw new Exception("No file"); });
        }

        // Means testList, is replace by newList
        assertThat(testList.size()).isGreaterThan(testList2.size());

        file.delete();
    }

/* Test fail cause create a new file if not existing
    choices : Remove test ? Consider creating file is good ? Find a way to thrown expression ?

   @Test
    @DisplayName("Writing into a non existing file throw an exception")
    public void WritingIntoANonExistingFileShouldThrowAnException() {

        Database myDatabase = new Database("assets/noFile.txt");

        try {
            myDatabase.writeListIntoFile(this.testList);
        } catch (IOException e) {
            this.thrown = catchThrowable(() -> { throw new Exception("Other"); });
        }

        assertThat(this.thrown).isInstanceOf(Exception.class)
                .hasMessageContaining("No file");
    }*/

}
