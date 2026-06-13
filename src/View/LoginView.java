package View;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class LoginView extends JFrame {
    public JTextField txtEmail;
    public JPasswordField txtPassword;
    public JButton btnLogin, btnGoToRegister;

    public LoginView() {
        setTitle("Event Venue Management System - Sign In");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        mainPanel.setBorder(new EmptyBorder(20, 30, 20, 30));

        JPanel emailPanel = new JPanel(new BorderLayout(5, 5));
        emailPanel.add(new JLabel("Email Address"), BorderLayout.NORTH);
        txtEmail = new JTextField();
        emailPanel.add(txtEmail, BorderLayout.CENTER);

        JPanel passPanel = new JPanel(new BorderLayout(5, 5));
        passPanel.add(new JLabel("Password"), BorderLayout.NORTH);
        txtPassword = new JPasswordField();
        passPanel.add(txtPassword, BorderLayout.CENTER);

        JPanel actionPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        btnLogin = new JButton("Login");
        btnGoToRegister = new JButton("Create Account");
        actionPanel.add(btnLogin);
        actionPanel.add(btnGoToRegister);

        mainPanel.add(emailPanel);
        mainPanel.add(passPanel);
        mainPanel.add(new JLabel("")); // Spacer
        mainPanel.add(actionPanel);

        add(mainPanel);
    }
}