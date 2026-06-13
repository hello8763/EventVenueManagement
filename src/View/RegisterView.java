package View;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class RegisterView extends JFrame {
    public JTextField txtFullName, txtEmail, txtPhone, txtAddress;
    public JPasswordField txtPassword;
    public JButton btnRegister, btnBackToLogin;

    public RegisterView() {
        setTitle("Event Venue Management System - Registration");
        setSize(450, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel(new GridLayout(6, 1, 10, 10));
        mainPanel.setBorder(new EmptyBorder(20, 30, 20, 30));

        txtFullName = addField(mainPanel, "Full Name");
        txtEmail = addField(mainPanel, "Email Address");
        txtPhone = addField(mainPanel, "Phone Number");
        txtAddress = addField(mainPanel, "Physical Address");

        JPanel passPanel = new JPanel(new BorderLayout(5, 5));
        passPanel.add(new JLabel("Password"), BorderLayout.NORTH);
        txtPassword = new JPasswordField();
        passPanel.add(txtPassword, BorderLayout.CENTER);
        mainPanel.add(passPanel);

        JPanel actionPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        btnBackToLogin = new JButton("Back to Login");
        btnRegister = new JButton("Register Account");
        actionPanel.add(btnBackToLogin);
        actionPanel.add(btnRegister);
        mainPanel.add(actionPanel);

        add(mainPanel);
    }

    private JTextField addField(JPanel panel, String labelText) {
        JPanel fieldPanel = new JPanel(new BorderLayout(5, 5));
        fieldPanel.add(new JLabel(labelText), BorderLayout.NORTH);
        JTextField textField = new JTextField();
        fieldPanel.add(textField, BorderLayout.CENTER);
        panel.add(fieldPanel);
        return textField;
    }
}