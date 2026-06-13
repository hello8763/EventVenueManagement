package View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class VenuePage extends JFrame {

    public JTextField txtVenueId;
    public JTextField txtName;
    public JTextField txtLocation;
    public JTextField txtCapacity;
    public JTextField txtHourlyRate;
    public JTextField txtStatus;

    public JButton btnAdd;
    public JButton btnUpdate;
    public JButton btnDelete;
    public JButton btnSearch;

    public JTable table;
    public DefaultTableModel model;

    public VenuePage() {
        super("Venue Management");
        setSize(750, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout());

        add(new JLabel("Venue ID"));
        txtVenueId = new JTextField(10);
        add(txtVenueId);

        add(new JLabel("Name"));
        txtName = new JTextField(15);
        add(txtName);

        add(new JLabel("Location"));
        txtLocation = new JTextField(15);
        add(txtLocation);

        add(new JLabel("Capacity"));
        txtCapacity = new JTextField(8);
        add(txtCapacity);

        add(new JLabel("Hourly Rate"));
        txtHourlyRate = new JTextField(8);
        add(txtHourlyRate);

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
        model.setColumnIdentifiers(new String[]{"ID", "Name", "Location", "Capacity", "Hourly Rate", "Status"});
        table = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(700, 250));
        add(scrollPane);
    }
}
