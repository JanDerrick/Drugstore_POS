import javax.swing.*;
import java.awt.*;
import java.util.*;
import javax.swing.event.*;
import javax.swing.table.*;

public class DrugstoreGUI extends JFrame {
    //For table in the GUI
    JTable table;
    DefaultTableModel comboModel;

    //JPanels
    JPanel panelInput;
    JPanel panelSummary;

    //JTextField for User Input
    JTextField textPrice;
    JTextField textQuantity;
    JTextField textTotalPayment;
    JTextField textVAT;

    //JLabels for Summary
    JLabel subTotalLabel;
    JLabel VATLabel;
    JLabel totalLabel;
    JLabel totalDiscountLabel;
    JLabel seniorPWDDiscountLabel;

    //JButtons
    JButton printButton;
    JButton addButton;

    //New components for search and remove
    JTextField searchField;
    JButton removeButton;

    //JCheckBox if the customer is Senior Citizen or PWD
    JCheckBox checkSeniorPWD;

    //For Changing the App Icon in the JFrame
    ImageIcon logoIcon = new ImageIcon("logo.png");

    ArrayList<Medicine> medicines = new ArrayList<>();

    //JComboBox for a Dropdown List of Medicines
    JComboBox<String> medicineComboBox;
    DefaultComboBoxModel<String> medicineNamesModel = new DefaultComboBoxModel<>();

    DrugstoreGUI() {
        setTitle("Jupiter Drugstore System");
        setSize(1200, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //setLocationRelativeTo(null); //set the JFrame to appear in the middle
        setLayout(new BorderLayout());

        setIconImage(logoIcon.getImage()); //Changes the logo

        //Method for adding the medicines
        initializeMedicines();

        comboModel = new DefaultTableModel(new String[]{
                "Medicine",
                "Price",
                "Qty", "VAT",
                "Item Total",
                "VAT Amount",
                "Discount"}, 0);

        table = new JTable(comboModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        //Input Panel
        panelInput = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));

        //Instantiating  the GUI Components
        medicineComboBox = new JComboBox<>(medicineNamesModel);
        medicineComboBox.setEditable(true);

        textPrice = new JTextField(8);
        textPrice.setEditable(false);
        textQuantity = new JTextField(3);
        textVAT = new JTextField(7);
        textVAT.setEditable(false);
        checkSeniorPWD = new JCheckBox("Senior Citizen/PWD");
        
        //add button
        addButton = new JButton("Add Item");
        addButton.setFont(new Font("Times New Roman", Font.BOLD, 15));
        addButton.setForeground(Color.BLUE);

        //search field 
        searchField = new JTextField(10);
        
        //remove button
        removeButton = new JButton("Remove Item");
        removeButton.setFont(new Font("Times New Roman", Font.BOLD, 15));
        removeButton.setForeground(Color.RED);

        // Add components to input panel
        panelInput.add(new JLabel("Search:"));
        panelInput.add(searchField);

        panelInput.add(new JLabel("Medicine:"));
        panelInput.add(medicineComboBox);

        panelInput.add(new JLabel("Price:"));
        panelInput.add(textPrice);

        panelInput.add(new JLabel("Quantity:"));
        panelInput.add(textQuantity);

        panelInput.add(new JLabel("VAT Type:"));
        panelInput.add(textVAT);

        panelInput.add(checkSeniorPWD);

        panelInput.add(addButton);
        panelInput.add(removeButton);

        add(panelInput, BorderLayout.NORTH);

        // Summary Panel
        panelSummary = new JPanel(new GridLayout(4, 4, 5, 5));

        subTotalLabel = new JLabel("0.00");
        VATLabel = new JLabel("0.00");
        totalDiscountLabel = new JLabel("0.00");
        seniorPWDDiscountLabel = new JLabel("0.00");
        totalLabel = new JLabel("0.00");
        
        //Textfield for the payment received
        textTotalPayment = new JTextField();
        textTotalPayment.setPreferredSize(new Dimension(100, 10));

        //Print Button for printing of receipt
        printButton = new JButton("Print Receipt");
        printButton.setFont(new Font("Times New Roman", Font.BOLD, 15));
        printButton.setForeground(Color.RED);

        // Row 1
        panelSummary.add(new JLabel("Subtotal:"));
        panelSummary.add(subTotalLabel);
        panelSummary.add(new JLabel("Total VAT:"));
        panelSummary.add(VATLabel);

        // Row 2
        panelSummary.add(new JLabel("Senior/PWD Discount:"));
        panelSummary.add(seniorPWDDiscountLabel);
        panelSummary.add(new JLabel("Total Amount:"));
        panelSummary.add(totalLabel);
        
        // Row 3
        panelSummary.add(new JLabel("Total Discount:"));
        panelSummary.add(totalDiscountLabel);
        

        panelSummary.add(new JLabel("Total Payment:"));

        panelSummary.add(textTotalPayment);

        panelSummary.add(new JLabel("")); //for spacing
        panelSummary.add(new JLabel(""));
        panelSummary.add(new JLabel(""));
        panelSummary.add(printButton);

        add(panelSummary, BorderLayout.SOUTH);

        // Action Listeners
        medicineComboBox.addActionListener(e -> updateMedicineDetails());
        addButton.addActionListener(e -> addItem());
        printButton.addActionListener(e -> printReceipt());
        removeButton.addActionListener(e -> removeSelectedItem());

        searchField.getDocument().addDocumentListener(createDocumentListener(() -> filterMedicines()));

        updateMedicineDetails();
    }

    //Method for the Medicines
    private void initializeMedicines() {

        medicines.add(new Medicine("AMBIMOX", 4.00, false));
        medicines.add(new Medicine("Sumapen", 12.75, false));
        medicines.add(new Medicine("Sitag-100", 24.00, true));
        medicines.add(new Medicine("TWYNSTA", 27.00, true));
        medicines.add(new Medicine("CLONISAPH", 13.00, true));
        medicines.add(new Medicine("Neozep Forte", 6.25, false));
        medicines.add(new Medicine("Biogesic", 4.50, false));
        medicines.add(new Medicine("Montelukast", 15.00, false));
        medicines.add(new Medicine("RANVAST", 8.00, true));
        medicines.add(new Medicine("Fern-C", 9.50, false));
        medicines.add(new Medicine("POTEN-CEE", 6.50, false));
        medicines.add(new Medicine("Bisolab", 15.00, true));
        medicines.add(new Medicine("GLUMET", 14.60, true));
        medicines.add(new Medicine("PONSTAN", 41.00, false));

        Collections.sort(medicines, new Comparator<Medicine>() {
            @Override
            public int compare(Medicine m1, Medicine m2) {
                return m1.getName().compareTo(m2.getName());
            }
        });

        for (Medicine m : medicines) {
            medicineNamesModel.addElement(m.getName());
        }
    }

    //Method for updating the details when the user picked a medicine
    private void updateMedicineDetails() {
        String selectedName = (String) medicineComboBox.getSelectedItem();
        if (selectedName != null) {
            Medicine med = getMedicineByName(selectedName);
            if (med != null) {
                textPrice.setText(String.format("%.2f", med.getPrice()));
                textVAT.setText(med.isVatExempt() ? "VAT Exempt" : "VAT 12%");
            } else {
                textPrice.setText("");
                textVAT.setText("");
            }
        }
    }

    // Filter medicines in the combo box by search text
    private void filterMedicines() {
        String filter = searchField.getText().toLowerCase();
        medicineNamesModel.removeAllElements();
        for (Medicine med : medicines) {
            if (med.getName().toLowerCase().contains(filter)) {
                medicineNamesModel.addElement(med.getName());
            }
        }
    }

    //Method that gets the name of the medicine
    private Medicine getMedicineByName(String name) {
        for (Medicine med : medicines) {
            if (med.getName().equalsIgnoreCase(name)) {
                return med;
            }
        }
        return null;
    }

    //method for adding medicine
    private void addItem() {
        try {
            String name = (String) medicineComboBox.getSelectedItem();
            Medicine med = getMedicineByName(name);

            if (med == null) {
                JOptionPane.showMessageDialog(this,
                        "Selected medicine is not valid.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            double price = med.getPrice();
            int quantity = Integer.parseInt(textQuantity.getText());
            String vatType = med.isVatExempt() ? "VAT Exempt" : "VAT 12%";

            double itemTotal = price * quantity;
            double vatAmount = med.isVatExempt() ? 0 : itemTotal * 0.12;
            double discountAmount = checkSeniorPWD.isSelected() ? itemTotal * 0.20 : 0;

            int response = JOptionPane.showConfirmDialog(this,
                    "Do you want to add the item?",
                    "Confirm Add Item",
                    JOptionPane.YES_NO_OPTION);

            if (response == JOptionPane.YES_OPTION) {
                comboModel.addRow(new Object[]{
                        name, String.format("%.2f", price),
                        quantity, vatType,
                        String.format("%.2f", itemTotal),
                        String.format("%.2f", vatAmount),
                        String.format("%.2f", discountAmount)});
                calculateTotals();
                textQuantity.setText("");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Invalid quantity.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    //Remove selected item from table
    private void removeSelectedItem() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            int response = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to remove this item?",
                    "Confirm Removal",
                    JOptionPane.YES_NO_OPTION);
            if (response == JOptionPane.YES_OPTION) {
                comboModel.removeRow(selectedRow);
                calculateTotals();
            }
        } else {
            JOptionPane.showMessageDialog(this,
                    "Please select a row to remove.",
                    "No Selection",
                    JOptionPane.WARNING_MESSAGE);
        }
    }
    
    // Helper method to create a DocumentListener that calls a Runnable on any change
    private DocumentListener createDocumentListener(Runnable r) {
        return new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { r.run(); }
            public void removeUpdate(DocumentEvent e) { r.run(); }
            public void changedUpdate(DocumentEvent e) { r.run(); }
        };
    }

    //Calculating the totals
    private void calculateTotals() {
        double subtotal = 0;
        double totalVAT = 0;
        double totalDiscount = 0;
        double seniorPWDDiscount = 0;

        for (int i = 0; i < comboModel.getRowCount(); i++) {
            subtotal += Double.parseDouble(comboModel.getValueAt(i, 4).toString());
            totalVAT += Double.parseDouble(comboModel.getValueAt(i, 5).toString());
            totalDiscount += Double.parseDouble(comboModel.getValueAt(i, 6).toString());
        }

        seniorPWDDiscount = totalDiscount;
        double total = subtotal + totalVAT - seniorPWDDiscount;

        subTotalLabel.setText(String.format("%.2f", subtotal));
        VATLabel.setText(String.format("%.2f", totalVAT));
        totalDiscountLabel.setText(String.format("%.2f", totalDiscount));
        seniorPWDDiscountLabel.setText(String.format("%.2f", seniorPWDDiscount));
        totalLabel.setText(String.format("%.2f", total));
    }

    // Method that Prints the receipt
    private void printReceipt() {

        int response = JOptionPane.showConfirmDialog(this,
                "Would you like to print the receipt?",
                "Confirm Print Receipt",
                JOptionPane.YES_NO_OPTION);

        if (response == JOptionPane.YES_OPTION) {
            try {
                double subtotal = Double.parseDouble(subTotalLabel.getText());
                double totalVAT = Double.parseDouble(VATLabel.getText());
                double totalAmount = Double.parseDouble(totalLabel.getText());
                double totalDiscount = Double.parseDouble(totalDiscountLabel.getText());
                double totalPayment = Double.parseDouble(textTotalPayment.getText());

                if (totalPayment < totalAmount) {
                    JOptionPane.showMessageDialog(this,
                            "Total Payment cannot be less than Total Amount!",
                            "Insufficient Payment",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }

                double change = totalPayment - totalAmount;

                //Our ReceiptPrinter Class
                ReceiptPrinter.printReceipt(comboModel,
                        subtotal,
                        totalVAT,
                        totalAmount,
                        totalDiscount,
                        totalPayment,
                        change);

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this,
                        "Please enter a valid numeric value for Total Payment.",
                        "Invalid Input",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
