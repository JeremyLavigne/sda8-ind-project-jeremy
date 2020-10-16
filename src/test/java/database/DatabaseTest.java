package database;

import main.Item;
import org.junit.jupiter.api.*;

import java.io.*;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;


public class DatabaseTest {
    File fileForTest;
    Database databaseForTest;
    ArrayList<Item> itemsForTest;

    @BeforeEach
    public void createDatabaseForTest() throws IOException {
        fileForTest = new File("assets/fileForTest.txt");
        fileForTest.createNewFile();
        databaseForTest = new Database("assets/fileForTest.txt");
    }

    @BeforeEach
    public void createEmptyList() {
        itemsForTest = new ArrayList<>();
    }

    @AfterEach
    public void deleteFileForTest() {
        fileForTest.delete();
    }


    @Nested
    class ReadingFileTests {

        @Test
        @DisplayName("Reading a non existing file throw an exception.")
        public void readNonExistingFile() {
            databaseForTest = new Database("assets/NonExistingFile.txt");

            assertThatExceptionOfType(IOException.class)
                    .isThrownBy(() -> {
                        databaseForTest.getListFromFile();
                    });
        }

        @Test
        @DisplayName("Reading an empty file should return an empty list.")
        public void readEmptyFile() {

            // Read empty file
            try {
                itemsForTest = databaseForTest.getListFromFile();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }

            // Compare size
            assertThat(itemsForTest.size()).isEqualTo(0);

        }

        @Test
        @DisplayName("Reading an existing (non empty) file should return the content as a list.")
        public void ReadingExistingFile() throws IOException {

            // Prepare a file with two object inside
            ObjectOutputStream OOS = new ObjectOutputStream(new FileOutputStream(fileForTest));
            OOS.writeObject(new Item("Expense", "Test1", 0, 1));
            OOS.writeObject(new Item("Income", "Test2", 0, 2));

            // Read the file
            try {
                itemsForTest = databaseForTest.getListFromFile();
            } catch (IOException | ClassNotFoundException e) { e.printStackTrace(); }

            // Check content
            String expected = "[Expense | Test1 | 0 | January, Income | Test2 | 0 | February]";
            assertThat(expected).isEqualTo(itemsForTest.toString());
        }
    }

    @Nested
    class WritingFileTests {

        @Test
        @DisplayName("Writing into a non existing file should create it.")
        public void WriteNonExistingFile() {

            databaseForTest = new Database("assets/NonExistingFile.txt");

            try {
                databaseForTest.writeListIntoFile(itemsForTest);
            } catch (IOException e) { e.printStackTrace(); }

            // We know that reading a non existing file throw an exception (see test before)
            // So if there is no exception here, it means the file exist
            assertThatCode(() -> {
                Database newDatabaseForTest = new Database("assets/NonExistingFile.txt");
                newDatabaseForTest.getListFromFile();
            }).doesNotThrowAnyException();


            // Delete the created file
            File fileToRemove = new File("assets/NonExistingFile.txt");
            fileToRemove.delete();
        }


        @Test
        @DisplayName("Writing a list into a file should replace the previous list by the new one")
        public void WritingNewList() {

            // Read the file
            try {
                itemsForTest = databaseForTest.getListFromFile();
            } catch (IOException | ClassNotFoundException e) { e.printStackTrace(); }

            // Add two objects in list and write it into file
            itemsForTest.add(new Item("Expense", "Test1", 0, 1));
            itemsForTest.add(new Item("Income", "Test2", 0, 2));

            try {
                databaseForTest.writeListIntoFile(itemsForTest);
            } catch (IOException e) { e.printStackTrace(); }

            // Read the file again
            try {
                itemsForTest = databaseForTest.getListFromFile();
            } catch (IOException | ClassNotFoundException e) { e.printStackTrace(); }

            // Check content
            String expected = "[Expense | Test1 | 0 | January, Income | Test2 | 0 | February]";
            assertThat(expected).isEqualTo(itemsForTest.toString());
        }

    }
}