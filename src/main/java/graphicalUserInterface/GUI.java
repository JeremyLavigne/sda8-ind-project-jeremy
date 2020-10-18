package graphicalUserInterface;

import database.Database;
import main.Account;
import main.Item;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;


/**
 * This class is the entry point of the Graphical User Interface.
 *
 * @author Jeremy
 * @version 1.0
 */
public class GUI extends JFrame {
    private Account account;
    private Database database;      // Used to save in, not to read in.
    private JPanel balanceSummary;  // Need to be refresh at every change.
    private JPanel bottomComponent; // Can be either 'Show Items', 'Add Items' or 'Edit Items'.


    public GUI(Account userAccount, Database userDatabase) {
        this.account = userAccount;
        this.database = userDatabase;
        this.balanceSummary = new JPanel();
        this.bottomComponent = new JPanel();

        setBalanceSummary();
        setShowItemComponent((ArrayList<Item>) account.getItems()).run();

        this.setTitle("Money Tracker");
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);

        // Manage closing on our own : see method manageClosing()
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener( new WindowAdapter()
        {
            public void windowClosing(WindowEvent e) { manageClosing(); }
        });

        // Layout : First level -> two containers
        this.setLayout(new MigLayout());
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
        int result = JOptionPane.showConfirmDialog(this,
                "Click Yes to save your changes.", "See you soon",
                JOptionPane.YES_NO_OPTION);

        if (result == 0) { // If yes
            try { // Save list
                database.writeListIntoFile(account.getItems());
            } catch (IOException e) { e.printStackTrace(); }

            // Force quit window - 'Needed' when exit using in-app button.
            this.setVisible(false);
            this.dispose();
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

        // On right - menu buttons
        JPanel menuButtons = new JPanel();
        JButton[] buttons = configureMenuButtons(); // Method will configure buttons - Stay focus on layout here

        menuButtons.setLayout(new MigLayout("align center", "", ""));
        menuButtons.add(buttons[0]);menuButtons.add(buttons[1]);
        menuButtons.add(buttons[2]);menuButtons.add(buttons[3]);

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
     * @param items -> list To Display.
     * @return Runnable function - in order to be called as a parameter
     */
    private Runnable setShowItemComponent(ArrayList<Item> items) {

        return (new Runnable() {
            public void run() {

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

                for (Item item : items) {
                    JLabel lab = new JLabel(item.toString());
                    lab.setFont(new Font("Serif", Font.PLAIN, 18));

                    if (item.getType().equals("Expense")) {
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
        });
    }


    /**
     * Configure the 'Add item Component', the one supposed to add a new item
     *
     * @return Runnable function - in order to be called as a parameter
     */
    private Runnable setAddItemComponent() {

        return (new Runnable() {
            public void run() {

                // Top - Form
                JPanel form = new JPanel(new MigLayout());
                form.setBorder(new MatteBorder(0,0,1,0,Color.BLACK));

                // Type
                form.add(new JLabel("Type"));
                String[] elementsList1 = new String[]{"Expense", "Income"};
                JComboBox<String> typeList = new JComboBox<>(elementsList1);
                form.add(typeList, "wrap");

                // Title
                form.add(new JLabel("Title"));
                JTextField titleField = new JTextField(30);
                form.add(titleField, "wrap");

                // Amount
                form.add(new JLabel("Amount"));
                JTextField amountField = new JTextField(10);
                form.add(amountField, "wrap");

                // Month
                form.add(new JLabel("Month"));
                String[] elementsList2 = new String[]{"January","February","March","April",
                        "May","June","July","August","September",
                        "October","November","December"};
                JComboBox<String> monthList = new JComboBox<>(elementsList2);
                form.add(monthList, "wrap");


                // Bottom - Validation button (No confirmation message yet)
                JButton addButton = new JButton("Add");
                addButton.addActionListener(e -> {

                    // Check/Convert all data coming from form
                    String title = titleField.getText().length() > 40 ?
                                    titleField.getText().substring(0,40) : titleField.getText();
                    int amount = 0;
                    try {
                        amount = Integer.parseInt(amountField.getText());
                    } catch (Exception ex) {
                        System.out.println(amountField.getText() + " is not a number, 0 will be used instead.");
                    }
                    String type = (String) typeList.getSelectedItem();
                    int month = monthList.getSelectedIndex() + 1;

                    // Add new Item
                    account.addItem(new Item(type, title, amount, month));

                    // Refresh
                    bottomComponent.removeAll();balanceSummary.removeAll();
                    setAddItemComponent();setBalanceSummary();
                    bottomComponent.revalidate();balanceSummary.revalidate();
                    bottomComponent.repaint();balanceSummary.repaint();
                });

                // Wrap all
                bottomComponent.setLayout(new MigLayout());
                bottomComponent.add(form, "wrap");
                bottomComponent.add((addButton));

            }
        });
    }


    /**
     * Configure the 'Edit items Component', the one supposed to allow editing/removing
     *
     * @return Runnable function - in order to be called as a parameter
     */
    private Runnable setEditItemComponent() {
        return (new Runnable() {
            public void run() {
                bottomComponent.add(new JLabel("Not Ready Yet"));
            }
        });
    }


    /**
     * Balance will be updated here.
     * This method will be called every time balance changes.
     */
    private void setBalanceSummary() {

        balanceSummary.setLayout(new MigLayout());

        int bal = account.getBalance();
        if (bal < 0) {
            balanceSummary.setBackground(Color.RED);
        } else {
            balanceSummary.setBackground(Color.GREEN);
        }

        JLabel balance = new JLabel("Balance : " + bal + " kr");
        balanceSummary.add(balance, "align center");

    }



    // =============================================================================================
    // -------------------------------- Components - Buttons ---------------------------------------
    // =============================================================================================

    /**
     * Method called only once, in order to configure all (8) Sorting buttons and their functions.
     * Issue : Cannot sort by Title AND Only Expense for example. Was handle in regular interface.
     * Need some extra work to be handled here...
     * 8 times same operation, could be wrap in a method.
     *
     * @return an array of buttons
     */
    private JButton[] configureSortingButtons() {
        JButton[] buttons = new JButton[8];

        // First button - Display all items
        JButton allButton = new JButton("All Items");
        allButton.addActionListener(e ->
                changeBottomComponent(setShowItemComponent((ArrayList<Item>) account
                        .selectItems("All", "Month", "Ascending")))
        );

        // Second button - Display only Expense
        JButton expButton = new JButton("Only Expenses");
        expButton.addActionListener(e ->
                changeBottomComponent(setShowItemComponent((ArrayList<Item>) account
                        .selectItems("Only Expenses", "Month", "Ascending")))
        );

        // Third button - Display only Income
        JButton incButton = new JButton("Only Incomes");
        incButton.addActionListener(e ->
                changeBottomComponent(setShowItemComponent((ArrayList<Item>) account
                        .selectItems("Only Incomes", "Month", "Ascending")))
        );

        // Fourth button - Sort By Title
        JButton titleButton = new JButton("Sort by Title");
        titleButton.addActionListener(e ->
                changeBottomComponent(setShowItemComponent((ArrayList<Item>) account
                        .selectItems("All", "Title", "Ascending")))
        );

        // Fifth button - Sort By Month
        JButton monthButton = new JButton("Sort by Month");
        monthButton.addActionListener(e ->
                changeBottomComponent(setShowItemComponent((ArrayList<Item>) account
                        .selectItems("All", "Month", "Ascending")))
        );

        // Sixth button - Sort By Amount
        JButton amountButton = new JButton("Sort by Amount");
        amountButton.addActionListener(e ->
                changeBottomComponent(setShowItemComponent((ArrayList<Item>) account
                        .selectItems("All", "Amount", "Ascending")))
        );

        // Seventh button - Sort Descending
        JButton descButton = new JButton("Sort Descending");
        descButton.addActionListener(e ->
                changeBottomComponent(setShowItemComponent((ArrayList<Item>) account
                        .selectItems("All", "Month", "Descending")))
        );

        // Eight button - Sort Ascending
        JButton ascButton = new JButton("Sort Ascending");
        ascButton.addActionListener(e ->
                changeBottomComponent(setShowItemComponent((ArrayList<Item>) account
                        .selectItems("All", "Month", "Ascending")))
        );

        // Put in array and return
        buttons[0] = allButton;buttons[1] = expButton;
        buttons[2] = incButton;buttons[3] = titleButton;
        buttons[4] = monthButton;buttons[5] = amountButton;
        buttons[6] = descButton;buttons[7] = ascButton;

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
        showButton.addActionListener(e -> changeBottomComponent(setShowItemComponent((ArrayList<Item>) account.getItems())));

        // Second button - Add items
        JButton addButton = new JButton("Add Items");
        addButton.addActionListener(e -> changeBottomComponent(setAddItemComponent()));

        // Third button - Edit items
        JButton editButton = new JButton("Edit Items");
        editButton.addActionListener(e -> changeBottomComponent(setEditItemComponent()));

        // Fourth button - Save & quit
        JButton saveButton = new JButton("Save & Quit");
        saveButton.addActionListener(e -> manageClosing());

        // Put in array and return
        buttons[0] = showButton;buttons[1] = addButton;
        buttons[2] = editButton;buttons[3] = saveButton;

        return buttons;
    }


    /**
     * (Avoid code duplication) Set bottom component when user click on button, see methods before.
     * Manage also the displaying (remove & repaint)
     *
     * @param setComponentFunction -> method in charge of changing the component
     */
    private void changeBottomComponent(Runnable setComponentFunction) {
        bottomComponent.removeAll();
        setComponentFunction.run();
        bottomComponent.revalidate();
        bottomComponent.repaint();
    }

}