package Controller;

import DAO.UserDAO;
import DAO.UserDAOImp;
import Model.Customer;
import Model.Profile;
import Model.User;
import View.RegisterView;
import View.LoginView;
import javax.swing.*;

public class RegisterController {
    private final RegisterView view;
    private final UserDAO dao;

    public RegisterController(RegisterView view) {
        this.view = view;
        this.dao = new UserDAOImp();

        this.view.btnRegister.addActionListener(e -> handleRegistration());
        this.view.btnBackToLogin.addActionListener(e -> navigateToLogin());
    }

    private void handleRegistration() {
        String fullName = view.txtFullName.getText().trim();
        String email = view.txtEmail.getText().trim();
        String phone = view.txtPhone.getText().trim();
        String address = view.txtAddress.getText().trim();
        String password = new String(view.txtPassword.getPassword()).trim();

        if (fullName.isEmpty() || email.isEmpty() || phone.isEmpty() || address.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(view, "All entry fields must be filled out.", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        User customerUser = new Customer();
        customerUser.setEmail(email);
        customerUser.setPasswordHash(password);

        Profile customerProfile = new Profile();
        customerProfile.setFullName(fullName);
        customerProfile.setPhoneNumber(phone);
        customerProfile.setAddress(address);

        if (dao.registerCustomer(customerUser, customerProfile)) {
            JOptionPane.showMessageDialog(view, "Account successfully provisioned.", "Success", JOptionPane.INFORMATION_MESSAGE);
            navigateToLogin();
        } else {
            JOptionPane.showMessageDialog(view, "Registration rejected. Email already active.", "System Conflict", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void navigateToLogin() {
        view.dispose();
        LoginView loginView = new LoginView();
        new LoginController(loginView);
        loginView.setVisible(true);
    }
}