package View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class UserListPage extends JFrame {
    public JTable table;
    public DefaultTableModel model;

    public UserListPage() {
        super("System Users Database");
        setSize(800, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Read-only table model
        model = new DefaultTableModel(new String[]{"User ID", "Full Name", "Email", "Phone", "Role", "Address"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Prevent accidental edits
            }
        };
        
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);
    }
}