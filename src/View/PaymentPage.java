package View;

import Model.Booking;
import Util.UserSession;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PaymentPage extends JFrame {

    public JTextField txtPaymentId;
    public JComboBox<Booking> cmbBooking; 
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
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout());

        String role = UserSession.getInstance().getRole();

        JLabel lblPaymentId = new JLabel("Payment ID");
        add(lblPaymentId);
        txtPaymentId = new JTextField(8);
        add(txtPaymentId);

        add(new JLabel("Select Booking"));
        cmbBooking = new JComboBox<>(); 
        add(cmbBooking);

        add(new JLabel("Amount (Auto)"));
        txtAmount = new JTextField(10);
        txtAmount.setEditable(false); 
        txtAmount.setBackground(Color.LIGHT_GRAY); 
        add(txtAmount);

        add(new JLabel("Payment Method"));
        txtPaymentMethod = new JTextField(12);
        add(txtPaymentMethod);

        add(new JLabel("Transaction Ref"));
        txtTransactionRef = new JTextField(15);
        add(txtTransactionRef);

        add(new JLabel("Payment Status (Auto)"));
        txtPaymentStatus = new JTextField(10);
        txtPaymentStatus.setEditable(false); 
        txtPaymentStatus.setBackground(Color.LIGHT_GRAY); 
        add(txtPaymentStatus);

        btnAdd = new JButton("Pay Now");
        btnUpdate = new JButton("Update");
        btnDelete = new JButton("Delete");
        btnSearch = new JButton("Search");

        add(btnAdd);
        add(btnUpdate);
        add(btnDelete);
        add(btnSearch);

        // SECURE UI: Hide admin-only controls from customers
        if ("Customer".equals(role)) {
            lblPaymentId.setVisible(false);
            txtPaymentId.setVisible(false);
            btnUpdate.setVisible(false);
            btnDelete.setVisible(false);
            btnSearch.setVisible(false);
        }

        model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"ID", "Booking ID", "Amount", "Method", "Ref", "Status"});
        table = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(750, 250));
        add(scrollPane);
    }
}