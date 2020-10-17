package graphicalUserInterface;

import database.Database;
import main.Account;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


/**
 * This class is the entry point of the Graphical User Interface.
 *
 * @author Jeremy
 * @version 1.0
 */
public class GUI extends JFrame {
    private Account account;
    private Database database;

    public GUI(Account userAccount, Database userDatabase) {
        this.account = userAccount;
        this.database = userDatabase;

        this.setTitle("Money Tracker");
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        this.manageClosing();

        // --------------------------------------- Layout --------------------------------
        //this.setLayout(new MigLayout());

    }


    /**
     *Basic run method
     */
    public void run() {
        this.setVisible(true);
    }

    private void manageClosing() {

        this.addWindowListener( new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                JFrame frame = (JFrame)e.getSource();

                int result = JOptionPane.showConfirmDialog(
                        frame,
                        "Click Yes to save your changes.",
                        "See you soon",
                        JOptionPane.OK_OPTION);

                if (result == 0) {
                                    /*
                 Here we need to save.
                 */

                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                }
            }
        });
    }
}