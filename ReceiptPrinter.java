import javax.swing.table.DefaultTableModel;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ReceiptPrinter {
    public static void printReceipt(
            DefaultTableModel comboModel, 
            double subtotal, 
            double totalVAT, 
            double totalAmount, 
            double totalDiscount, 
            double totalPayment, 
            double change) {

        // Get current date and time
        LocalDateTime timeNow = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String dateTime = timeNow.format(formatter);

        // Calculate total items
        int totalItems = 0;
        for (int i = 0; i < comboModel.getRowCount(); i++) {
            Object quantityObj = comboModel.getValueAt(i, 2);
            
            totalItems += Integer.parseInt(quantityObj.toString());
        }

        System.out.println("\n============== Jupiter Drugstore Receipt ================");
        System.out.println("Date/Time: " + dateTime + "\n");
        System.out.printf("%-15s %-8s %-7s %-10s %-10s\n",
                "Medicine", "Price", "Qty", "VAT", "Item Total");
        System.out.println("----------------------------------------------------------");

        for (int i = 0; i < comboModel.getRowCount(); i++) {
            System.out.printf("%-15s %-8s %-7s %-12s %-12s\n",
                    comboModel.getValueAt(i, 0),
                    comboModel.getValueAt(i, 1),
                    comboModel.getValueAt(i, 2),
                    comboModel.getValueAt(i, 3),
                    comboModel.getValueAt(i, 4));
        }

        System.out.println("----------------------------------------------------------");
        System.out.printf("Total Item(s): %d\n", totalItems);
        System.out.printf("Subtotal: %.2f\n", subtotal);
        System.out.printf("Total VAT: %.2f\n", totalVAT);
        System.out.printf("Total Discount: %.2f\n", totalDiscount);
        System.out.printf("Total Amount: %.2f\n", totalAmount);
        System.out.println("----------------------------------------------------------");
        System.out.printf("Total Payment: %.2f\n", totalPayment);
        System.out.printf("Change: %.2f\n", change);
        System.out.println("======================== Thank You! =======================\n");
    }
}
