package graphicalUserInterface;

import database.Database;
import main.Account;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    private Database database;  // Used to save in, not to read in.

    public GUI(Account userAccount, Database userDatabase) {
        this.account = userAccount;
        this.database = userDatabase;

        this.setTitle("Money Tracker");
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);

        // Manage closing on our own : see method manageClosing()
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener( new WindowAdapter()
        {
            public void windowClosing(WindowEvent e) { manageClosing(); }
        });


        // --------------------------------------- Layout --------------------------------
        this.setLayout(new MigLayout());

        // First level -> two containers
        this.add(setTopComponent(), "width 800, height 100, wrap");
        this.add(setBottomComponent(), "width 800, height 500");
    }


    /**
     * Launch/Show user interface.
     */
    public void start() {
        this.setVisible(true);
    }

    /**
     * User can close from red top cross or in-app special button.
     * Both leads the program here, ask confirmation and save all changes into database.
     */
    protected void manageClosing() {
        int result = JOptionPane.showConfirmDialog(
                this,
                "Click Yes to save your changes.",
                "See you soon",
                JOptionPane.YES_NO_OPTION);

        if (result == 0) { // If yes
            /*
                 Here we need to save.
             */

            //this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.setVisible(false);
            this.dispose(); // Force quit window - Useful when exit using in-app button.
        }
    }



    // =============================================================================================
    // -------------------------------- Components - First Level -----------------------------------
    // =============================================================================================

    /**
     * Configure the top component. Structure will never change.
     * Contains account balance and menu buttons.
     *
     * @return the component, ready to be displayed.
     */
    private JPanel setTopComponent() {

        // Container
        JPanel topComponent = new JPanel();

        // On left - Balance Summary
        JPanel balanceSummary = new JPanel();
        JLabel balance = refreshBalance();

        balanceSummary.setLayout(new MigLayout());
        balanceSummary.add(balance, "align center");

        // On right - menu buttons
        JPanel menuButtons = new JPanel();
        JButton[] buttons = configureButtons(); // Method will configure buttons - Stay focus on layout here
        JButton showButton = buttons[0];
        JButton addButton = buttons[1];
        JButton editButton = buttons[2];
        JButton saveButton = buttons[3];

        menuButtons.setLayout(new MigLayout());
        menuButtons.add(showButton);
        menuButtons.add(addButton);
        menuButtons.add(editButton);
        menuButtons.add(saveButton);

        // Insert Left and Right into Pane
        this.setLayout(new MigLayout());
        this.add(balanceSummary);
        this.add(menuButtons);

        return topComponent;
    }

    /**
     * Configure the bottom component. Structure will change regarding user choices.
     * Can be either 'Show Items', 'Add Items' or 'Edit Items'.
     *
     * @return the component, ready to be displayed.
     */
    private JPanel setBottomComponent() {
        return new JPanel();
    }

    // =============================================================================================
    // -------------------------------- Components - Second Level -----------------------------------
    // =============================================================================================

    /**
     * Balance will be updated here.
     * This method will be called every time balance changes.
     *
     * @return JLabel up to date
     */
    private JLabel refreshBalance() {
        return new JLabel(Integer.toString(this.account.getBalance()));
    }

    /**
     * Method called only once, in order to configure all buttons and their functions.
     *
     * @return an array of buttons
     */
    private JButton[] configureButtons() {
        JButton[] buttons = new JButton[4];

        // First button - Show items
        JButton showButton = new JButton("Show Items");

        // Second button - Add items
        JButton addButton = new JButton("Add Items");

        // Third button - Edit items
        JButton editButton = new JButton("Edit Items");

        // Fourth button - Save & quit
        JButton saveButton = new JButton("Save & Quit");
        saveButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                manageClosing();
            }
        });


        // Put in array and return
        buttons[0] = showButton;
        buttons[1] = addButton;
        buttons[2] = editButton;
        buttons[3] = saveButton;

        return buttons;
    }
}