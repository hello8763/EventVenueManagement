package Controller;

// Your structural architecture entity model dependencies
import DAO.UserDAO;
import DAO.UserDAOImp;
import Model.Customer;
import Model.Profile;
import Model.User;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FXRegisterController {

    @FXML private TextField txtFullName;
    @FXML private TextField txtEmail;
    @FXML private TextField txtPhone;
    @FXML private TextField txtAddress;
    @FXML private PasswordField txtPassword;
    @FXML private Button btnRegister;
    @FXML private Button btnBackToLogin;

    private final UserDAO dao;

    public FXRegisterController() {
        this.dao = new UserDAOImp();
    }

    @FXML
    private void handleRegistration() {
        String fullName = txtFullName.getText().trim();
        String email = txtEmail.getText().trim();
        String phone = txtPhone.getText().trim();
        String address = txtAddress.getText().trim();
        String password = txtPassword.getText().trim();

        if (fullName.isEmpty() || email.isEmpty() || phone.isEmpty() || address.isEmpty() || password.isEmpty()) {
            showNotification(Alert.AlertType.WARNING, "Validation Error", "All entry fields must be filled out.");
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
            showNotification(Alert.AlertType.INFORMATION, "Success", "Account successfully provisioned.");
            handleBackToLogin();
        } else {
            showNotification(Alert.AlertType.ERROR, "System Conflict", "Registration rejected. Email already active.");
        }
    }

    @FXML
    private void handleBackToLogin() {
        Stage stage = (Stage) txtEmail.getScene().getWindow();
        stage.close();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/FXLogin.fxml"));
            Parent root = loader.load();
            Stage loginStage = new Stage();
            loginStage.setTitle("Event Venue Management System - Sign In");
            loginStage.setScene(new Scene(root, 420, 480));
            loginStage.setResizable(false);
            loginStage.show();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void showNotification(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.getDialogPane().setStyle("-fx-background-color: #1e293b; -fx-text-fill: white;");
        alert.getDialogPane().lookup(".content.label").setStyle("-fx-text-fill: white;");
        alert.showAndWait();
    }
}