package View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class BookingPage extends JFrame {

    public JTextField txtBookingId;
    public JTextField txtUserId;
    public JTextField txtVenueId;
    public JTextField txtTypeId;
    public JTextField txtEventDate;
    public JTextField txtStartTime;
    public JTextField txtEndTime;
    public JTextField txtTotalPrice;
    public JTextField txtStatus;

    public JButton btnAdd;
    public JButton btnUpdate;
    public JButton btnDelete;
    public JButton btnSearch;

    public JTable table;
    public DefaultTableModel model;

    public BookingPage() {
        super("Booking Management");
        setSize(800, 550);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout());

        add(new JLabel("Booking ID"));
        txtBookingId = new JTextField(8);
        add(txtBookingId);

        add(new JLabel("User ID"));
        txtUserId = new JTextField(8);
        add(txtUserId);

        add(new JLabel("Venue ID"));
        txtVenueId = new JTextField(8);
        add(txtVenueId);

        add(new JLabel("Type ID"));
        txtTypeId = new JTextField(8);
        add(txtTypeId);

        add(new JLabel("Event Date (YYYY-MM-DD)"));
        txtEventDate = new JTextField(12);
        add(txtEventDate);

        add(new JLabel("Start Time (HH:MM)"));
        txtStartTime = new JTextField(8);
        add(txtStartTime);

        add(new JLabel("End Time (HH:MM)"));
        txtEndTime = new JTextField(8);
        add(txtEndTime);

        add(new JLabel("Total Price"));
        txtTotalPrice = new JTextField(8);
        add(txtTotalPrice);

        add(new JLabel("Status"));
        txtStatus = new JTextField(10);
        add(txtStatus);

        btnAdd = new JButton("Add");
        btnUpdate = new JButton("Update");
        btnDelete = new JButton("Delete");
        btnSearch = new JButton("Search");

        add(btnAdd);
        add(btnUpdate);
        add(btnDelete);
        add(btnSearch);

        model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"ID", "User ID", "Venue ID", "Type ID", "Date", "Start", "End", "Total", "Status"});
        table = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(750, 250));
        add(scrollPane);
    }
}
