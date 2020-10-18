package graphicalUserInterface;

import database.Database;
import main.Account;
import main.Item;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;


/**
 * This class is the entry point of the Graphical User Interface.
 *
 * @author Jeremy
 * @version 1.0
 */
public class GUI extends JFrame {
    private Account account;
    private Database database;  // Used to save in, not to read in.
    private JLabel balance; // Need to be refresh at every change.
    private JPanel bottomComponent; // Can be either 'Show Items', 'Add Items' or 'Edit Items'.


    public GUI(Account userAccount, Database userDatabase) {
        this.account = userAccount;
        this.database = userDatabase;
        this.balance = new JLabel();
        this.bottomComponent = new JPanel();

        setBalance();
        setShowItemComponent((ArrayList<Item>) account.getItems());

        this.setTitle("Money Tracker");
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);

        // Manage closing on our own : see method manageClosing()
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener( new WindowAdapter()
        {
            public void windowClosing(WindowEvent e) { manageClosing(); }
        });


        // Layout
        this.setLayout(new MigLayout());

        // First level -> two containers
        this.add(setTopComponent(), "width 800, height 100, wrap");
        this.add(this.bottomComponent, "width 800, height 500");

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
        topComponent.setBorder(new MatteBorder(0, 0, 1, 0, Color.BLACK));

        // On left - Balance Summary
        JPanel balanceSummary = new JPanel();

        balanceSummary.setLayout(new MigLayout());
        balanceSummary.setBorder(new MatteBorder(0, 0, 0, 1, Color.BLACK));
        balanceSummary.add(balance, "align center");

        // On right - menu buttons
        JPanel menuButtons = new JPanel();
        JButton[] buttons = configureMenuButtons(); // Method will configure buttons - Stay focus on layout here

        menuButtons.setLayout(new MigLayout("align center", "", ""));
        menuButtons.add(buttons[0]);
        menuButtons.add(buttons[1]);
        menuButtons.add(buttons[2]);
        menuButtons.add(buttons[3]);

        // Insert Left and Right into Pane
        topComponent.setLayout(new MigLayout());
        topComponent.add(balanceSummary);
        topComponent.add(menuButtons);

        return topComponent;
    }

    /**
     * Configure the 'Show items Component', the one supposed to display list of items
     * Should be a scroll pane (in case of many items)
     *
     * @items -> listToDisplay.
     */
    private void setShowItemComponent(ArrayList<Item> items) {

        // Top - buttons for sorting (all, by title, descending, etc..)
        JPanel buttonsPane = new JPanel(new MigLayout());
        JButton[] sortingButtons = configureSortingButtons();

        for (int i = 0; i < sortingButtons.length; i++) {
            if (i == 2 || i == 5) {
                buttonsPane.add(sortingButtons[i], "wrap");
            } else {
                buttonsPane.add(sortingButtons[i]);
            }
        }

        // Bottom - displayed List of items
        JPanel listPane = new JPanel(new MigLayout());
        listPane.setPreferredSize(new Dimension(780, 400));
        listPane.setBackground(Color.white);

        for (int i = 0; i < items.size(); i++) {
            JLabel lab = new JLabel(items.get(i).toString());
            lab.setFont(new Font("Serif", Font.PLAIN, 14));

            if (items.get(i).getType().equals("Expense")) {
                lab.setForeground(Color.red);
            } else {
                lab.setForeground(Color.blue);
            }
            listPane.add(lab, "wrap");
        }

        bottomComponent.setLayout(new MigLayout());
        bottomComponent.add(buttonsPane, "wrap");
        bottomComponent.add((listPane));

    }


    /**
     * Configure the 'Show items Component', the one supposed to add a new item
     */
    private void setAddItemComponent() {

        bottomComponent.add(new JLabel("Please fill the form to add a new Item"));

        bottomComponent.add(new JTextField());

    }


    /**
     * Configure the 'Edit items Component', the one supposed to allow editing/removing
     *
     */
    private void setEditItemComponent() {

        bottomComponent.add(new JLabel("Edit your fucking Item"));

        bottomComponent.add(new JTextField());

    }



    // =============================================================================================
    // -------------------------------- Components - Second Level ----------------------------------
    // =============================================================================================

    /**
     * Balance will be updated here.
     * This method will be called every time balance changes.
     *
     * @return JLabel up to date
     */
    private void setBalance() {
        balance = new JLabel("Balance : " + Integer.toString(this.account.getBalance()) + " kr");
    }




    // =============================================================================================
    // -------------------------------- Components - Buttons ---------------------------------------
    // =============================================================================================

    /**
     * Method called only once, in order to configure all (8) Sorting buttons and their functions.
     * Issue : Cannot sort by Title AND Only Expense for example. Was handle in regular interface.
     * Need some extra work to be handled here...
     *
     * @return an array of buttons
     */
    private JButton[] configureSortingButtons() {
        JButton[] buttons = new JButton[8];

        // First button - Display all items
        JButton allButton = new JButton("All Items");
        allButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                bottomComponent.removeAll();
                setShowItemComponent((ArrayList<Item>) account.selectItems("All", "Month", "Ascending"));
                bottomComponent.revalidate();
                bottomComponent.repaint();
            }
        });

        // Second button - Display only Expense
        JButton expButton = new JButton("Only Expenses");
        expButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                bottomComponent.removeAll();
                setShowItemComponent((ArrayList<Item>) account.selectItems("Only Expenses", "Month", "Ascending"));
                bottomComponent.revalidate();
                bottomComponent.repaint();
            }
        });

        // Third button - Display only Income
        JButton incButton = new JButton("Only Incomes");
        incButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                bottomComponent.removeAll();
                setShowItemComponent((ArrayList<Item>) account.selectItems("Only Incomes", "Month", "Ascending"));
                bottomComponent.revalidate();
                bottomComponent.repaint();
            }
        });

        // Fourth button - Sort By Title
        JButton titleButton = new JButton("Sort by Title");
        titleButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                bottomComponent.removeAll();
                setShowItemComponent((ArrayList<Item>) account.selectItems("All", "Title", "Ascending"));
                bottomComponent.revalidate();
                bottomComponent.repaint();
            }
        });

        // Fifth button - Sort By Month
        JButton monthButton = new JButton("Sort by Month");
        monthButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                bottomComponent.removeAll();
                setShowItemComponent((ArrayList<Item>) account.selectItems("All", "Month", "Ascending"));
                bottomComponent.revalidate();
                bottomComponent.repaint();
            }
        });

        // Sixth button - Sort By Amount
        JButton amountButton = new JButton("Sort by Amout");
        amountButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                bottomComponent.removeAll();
                setShowItemComponent((ArrayList<Item>) account.selectItems("All", "Amount", "Ascending"));
                bottomComponent.revalidate();
                bottomComponent.repaint();
            }
        });

        // Seventh button - Sort Descending
        JButton descButton = new JButton("Sort Descending");
        descButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                bottomComponent.removeAll();
                setShowItemComponent((ArrayList<Item>) account.selectItems("All", "Month", "Descending"));
                bottomComponent.revalidate();
                bottomComponent.repaint();
            }
        });

        // Eight button - Sort Ascending
        JButton ascButton = new JButton("Sort Ascending");
        ascButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                bottomComponent.removeAll();
                setShowItemComponent((ArrayList<Item>) account.selectItems("All", "Month", "Ascending"));
                bottomComponent.revalidate();
                bottomComponent.repaint();
            }
        });


        // Put in array and return
        buttons[0] = allButton;
        buttons[1] = expButton;
        buttons[2] = incButton;
        buttons[3] = titleButton;
        buttons[4] = monthButton;
        buttons[5] = amountButton;
        buttons[6] = descButton;
        buttons[7] = ascButton;

        return buttons;
    }

    /**
     * Method called only once, in order to configure all (4) menu buttons and their functions.
     *
     * @return an array of buttons
     */
    private JButton[] configureMenuButtons() {
        JButton[] buttons = new JButton[4];

        // First button - Show items
        JButton showButton = new JButton("Show Items");
        showButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                bottomComponent.removeAll();
                setShowItemComponent((ArrayList<Item>) account.getItems());
                bottomComponent.revalidate();
                bottomComponent.repaint();
            }
        });

        // Second button - Add items
        JButton addButton = new JButton("Add Items");
        addButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                bottomComponent.removeAll();
                setAddItemComponent();
                bottomComponent.revalidate();
                bottomComponent.repaint();
            }
        });

        // Third button - Edit items
        JButton editButton = new JButton("Edit Items");
        editButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                bottomComponent.removeAll();
                setEditItemComponent();
                bottomComponent.revalidate();
                bottomComponent.repaint();
            }
        });

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