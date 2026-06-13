package View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PaymentPage extends JFrame {

    public JTextField txtPaymentId;
    public JTextField txtBookingId;
    public JTextField txtAmount;
    public JTextField txtPaymentMethod;
    public JTextField txtTransactionRef;
    public JTextField txtPaymentStatus;

    public JButton btnAdd;
    public JButton btnUpdate;
    public JButton btnDelete;
    public JButton btnSearch;

    public JTable table;
    public DefaultTableModel model;

    public PaymentPage() {
        super("Payment Management");
        setSize(750, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout());

        add(new JLabel("Payment ID"));
        txtPaymentId = new JTextField(8);
        add(txtPaymentId);

        add(new JLabel("Booking ID"));
        txtBookingId = new JTextField(8);
        add(txtBookingId);

        add(new JLabel("Amount"));
        txtAmount = new JTextField(10);
        add(txtAmount);

        add(new JLabel("Payment Method"));
        txtPaymentMethod = new JTextField(12);
        add(txtPaymentMethod);

        add(new JLabel("Transaction Ref"));
        txtTransactionRef = new JTextField(15);
        add(txtTransactionRef);

        add(new JLabel("Payment Status"));
        txtPaymentStatus = new JTextField(10);
        add(txtPaymentStatus);

        btnAdd = new JButton("Add");
        btnUpdate = new JButton("Update");
        btnDelete = new JButton("Delete");
        btnSearch = new JButton("Search");

        add(btnAdd);
        add(btnUpdate);
        add(btnDelete);
        add(btnSearch);

        model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"ID", "Booking ID", "Amount", "Method", "Ref", "Status"});
        table = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(700, 250));
        add(scrollPane);
    }
}
