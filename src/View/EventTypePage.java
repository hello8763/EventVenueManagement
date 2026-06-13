package View;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class EventTypePage extends JFrame {
    public JTextField txtId, txtName, txtDesc, txtDeposit;
    public JButton btnAdd, btnUpdate, btnDelete, btnSearch;
    public JTable table;
    public DefaultTableModel model;

    public EventTypePage() {
        super("Manage Event Types");
        setSize(650, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new FlowLayout());

        add(new JLabel("ID:")); txtId = new JTextField(5); add(txtId);
        add(new JLabel("Name:")); txtName = new JTextField(15); add(txtName);
        add(new JLabel("Description:")); txtDesc = new JTextField(20); add(txtDesc);
        add(new JLabel("Deposit:")); txtDeposit = new JTextField(8); add(txtDeposit);

        btnAdd = new JButton("Add"); btnUpdate = new JButton("Update");
        btnDelete = new JButton("Delete"); btnSearch = new JButton("Search");
        add(btnAdd); add(btnUpdate); add(btnDelete); add(btnSearch);

        model = new DefaultTableModel(new String[]{"ID","Name","Description","Deposit"},0);
        table = new JTable(model);
        add(new JScrollPane(table));
    }
}