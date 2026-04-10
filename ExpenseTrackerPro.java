import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;

class Expense {
    String category;
    double amount;

    Expense(String category, double amount) {
        this.category = category;
        this.amount = amount;
    }
}

public class ExpenseTrackerPro extends JFrame implements ActionListener {

    ArrayList<Expense> expenses = new ArrayList<>();
    double budget;

    JComboBox<String> categoryBox;
    JTextField amountField;
    JTextArea displayArea;

    JButton addBtn, viewBtn, totalBtn, saveBtn, loadBtn, clearBtn;

    ExpenseTrackerPro() {

        setTitle("Smart Expense Tracker PRO");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Budget Input
        budget = Double.parseDouble(JOptionPane.showInputDialog("Enter Monthly Budget:"));

        // TOP PANEL
        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(200, 230, 255));

        categoryBox = new JComboBox<>(new String[]{"Food", "Travel", "Shopping", "Bills", "Other"});
        amountField = new JTextField(8);

        topPanel.add(new JLabel("Category:"));
        topPanel.add(categoryBox);
        topPanel.add(new JLabel("Amount:"));
        topPanel.add(amountField);

        add(topPanel, BorderLayout.NORTH);

        // CENTER AREA
        displayArea = new JTextArea();
        displayArea.setEditable(false);
        displayArea.setFont(new Font("Arial", Font.PLAIN, 14));
        add(new JScrollPane(displayArea), BorderLayout.CENTER);

        // BUTTON PANEL
        JPanel btnPanel = new JPanel();
        btnPanel.setBackground(new Color(220, 255, 220));

        addBtn = new JButton("Add");
        viewBtn = new JButton("View");
        totalBtn = new JButton("Total");
        saveBtn = new JButton("Save");
        loadBtn = new JButton("Load");
        clearBtn = new JButton("Clear");

        btnPanel.add(addBtn);
        btnPanel.add(viewBtn);
        btnPanel.add(totalBtn);
        btnPanel.add(saveBtn);
        btnPanel.add(loadBtn);
        btnPanel.add(clearBtn);

        add(btnPanel, BorderLayout.SOUTH);

        // EVENTS
        addBtn.addActionListener(this);
        viewBtn.addActionListener(this);
        totalBtn.addActionListener(this);
        saveBtn.addActionListener(this);
        loadBtn.addActionListener(this);
        clearBtn.addActionListener(this);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == addBtn) addExpense();
        else if (e.getSource() == viewBtn) viewExpenses();
        else if (e.getSource() == totalBtn) showTotal();
        else if (e.getSource() == saveBtn) saveToFile();
        else if (e.getSource() == loadBtn) loadFromFile();
        else if (e.getSource() == clearBtn) clearAll();
    }

    void addExpense() {
        try {
            String category = categoryBox.getSelectedItem().toString();
            double amount = Double.parseDouble(amountField.getText());

            expenses.add(new Expense(category, amount));
            amountField.setText("");

            JOptionPane.showMessageDialog(this, "Expense Added!");
            checkBudget();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Invalid Input!");
        }
    }

    void viewExpenses() {
        displayArea.setText("---- Expense List ----\n");
        for (Expense e : expenses) {
            displayArea.append(e.category + " : ₹" + e.amount + "\n");
        }
    }

    void showTotal() {
        double total = 0;
        for (Expense e : expenses) total += e.amount;

        if (total > budget) {
            JOptionPane.showMessageDialog(this, "⚠ Budget Exceeded!\nTotal: ₹" + total);
        } else {
            JOptionPane.showMessageDialog(this,
                    "Total: ₹" + total + "\nRemaining: ₹" + (budget - total));
        }
    }

    void checkBudget() {
        double total = 0;
        for (Expense e : expenses) total += e.amount;

        if (total > budget) {
            JOptionPane.showMessageDialog(this, "⚠ ALERT: Budget Crossed!");
        }
    }

    void saveToFile() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("expenses.txt"));

            for (Expense e : expenses) {
                writer.write(e.category + "," + e.amount);
                writer.newLine();
            }

            writer.close();
            JOptionPane.showMessageDialog(this, "Saved to file!");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error saving file!");
        }
    }

    void loadFromFile() {
        try {
            expenses.clear();

            BufferedReader reader = new BufferedReader(new FileReader("expenses.txt"));
            String line;

            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                expenses.add(new Expense(data[0], Double.parseDouble(data[1])));
            }

            reader.close();
            JOptionPane.showMessageDialog(this, "Loaded from file!");
            viewExpenses();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error loading file!");
        }
    }

    void clearAll() {
        expenses.clear();
        displayArea.setText("");
        JOptionPane.showMessageDialog(this, "All data cleared!");
    }

    public static void main(String[] args) {
        new ExpenseTrackerPro();
    }
}
    

