package View;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class CustomerVenueBrowsePage extends JFrame {
    public JTable table;
    public DefaultTableModel model;
    public JButton btnSelect;

    public CustomerVenueBrowsePage() {
        super("Available Venues");
        setSize(700, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        model = new DefaultTableModel(new String[]{"ID","Name","Location","Capacity","Hourly Rate","Status"},0);
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);
        btnSelect = new JButton("Book This Venue");
        add(btnSelect, BorderLayout.SOUTH);
    }
}