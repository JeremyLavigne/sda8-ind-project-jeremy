package database;

import main.Item;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This class mocks a database: read and write the list of items.
 *
 * Only read once before launching user Interface
 * Only write once at the end, when user save.
 */
public class Database {

    private String fileName;

    public Database(String nameOfFile) {
        this.fileName = nameOfFile;
    }

    /**
     * Get the list from the database and return it. List is a stream object.
     *
     * @return saved user list of items
     */
    public ArrayList<Item> getListFromFile() throws IOException, ClassNotFoundException {

        ObjectInputStream OIS = new ObjectInputStream(new FileInputStream(fileName));
        ArrayList<Item> itemsFromDatabase = new ArrayList<>();

        try (OIS) {
            while (true) {
                Object read = OIS.readObject();
                if (read == null) { break; }

                itemsFromDatabase.add((Item) read);
            }

        } catch (EOFException e) {
            System.out.println("End of file");
        }

        OIS.close();

        return itemsFromDatabase;

    }


    /**
     * Save the list in the database. List is saved as a Stream object
     *
     * @param listToSave -> List modified/created in User Interface
     */
    public void writeListIntoFile(List<Item> listToSave) throws IOException {

        ObjectOutputStream OOS = new ObjectOutputStream(new FileOutputStream(fileName));

        for (Item item : listToSave) {
            OOS.writeObject(item);
        }

        OOS.close();
    }
}