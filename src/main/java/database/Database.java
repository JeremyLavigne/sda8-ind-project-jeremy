package database;

import main.Item;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * This class mocks a database: read and write the list of items
 * Only read once at first and write once at the end.
 */
public class Database {
    private final File file;

    public Database(String fileName) {
        this.file = new File(fileName);
    }

    /**
     * Get the list from the database and return it.
     * @return Initial user list of item
     * @throws FileNotFoundException -> If no file
     */
    public ArrayList<Item> getListFromFile() throws FileNotFoundException {
        Scanner scan = new Scanner(this.file);
        ArrayList<Item> list = new ArrayList<>();

        while (scan.hasNext()) {
            String[] splitLine = scan.nextLine().split(";");

            list.add(new Item(splitLine[0], // Type
                    splitLine[1], // Title
                    Integer.parseInt(splitLine[2]),  // Amount
                    Integer.parseInt(splitLine[3]))  // Month
            );
        }
        scan.close();
        return list;
    }


    /**
     * Save the list in the database.
     * @param list -> List modified/created in User Interface
     * @throws FileNotFoundException -> If no file
     */
    public void writeListIntoFile(List<Item> list) throws IOException {
        PrintWriter pw = new PrintWriter(this.file);

        for (Item item : list) {
            pw.println(item.getType() + ";" + item.getTitle() + ";" + item.getAmount() + ";" + item.getMonth());
        }

        pw.close();
    }
}
