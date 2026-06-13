package View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ServicePage extends JFrame {

    public JTextField txtServiceId;
    public JTextField txtServiceName;
    public JTextField txtDescription;
    public JTextField txtPricePerUnit;
    public JCheckBox chkStatus;

    public JButton btnAdd;
    public JButton btnUpdate;
    public JButton btnDelete;
    public JButton btnSearch;

    public JTable table;
    public DefaultTableModel model;

    public ServicePage() {
        super("Service Management");
        setSize(750, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout());

        add(new JLabel("Service ID"));
        txtServiceId = new JTextField(8);
        add(txtServiceId);

        add(new JLabel("Service Name"));
        txtServiceName = new JTextField(15);
        add(txtServiceName);

        add(new JLabel("Description"));
        txtDescription = new JTextField(20);
        add(txtDescription);

        add(new JLabel("Price Per Unit"));
        txtPricePerUnit = new JTextField(10);
        add(txtPricePerUnit);

        add(new JLabel("Active"));
        chkStatus = new JCheckBox();
        add(chkStatus);

        btnAdd = new JButton("Add");
        btnUpdate = new JButton("Update");
        btnDelete = new JButton("Delete");
        btnSearch = new JButton("Search");

        add(btnAdd);
        add(btnUpdate);
        add(btnDelete);
        add(btnSearch);

        model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"ID", "Name", "Description", "Price/Unit", "Active"});
        table = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(700, 250));
        add(scrollPane);
    }
}
