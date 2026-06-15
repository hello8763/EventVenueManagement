package Controller;

// Your original project architecture imports
import DAO.UserDAO;
import DAO.UserDAOImp;
import Util.UserSession;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FXLoginController {

    @FXML private TextField txtEmail;
    @FXML private PasswordField txtPassword;
    @FXML private Button btnLogin;
    @FXML private Button btnGoToRegister;

    private final UserDAO dao;

    public FXLoginController() {
        this.dao = new UserDAOImp();
    }

    @FXML
    private void handleLogin() {
        String email = txtEmail.getText().trim();
        String password = txtPassword.getText().trim();

        if (email.isEmpty() || password.isEmpty()) {
            showNotification(Alert.AlertType.WARNING, "Validation Error", "All fields are required.");
            return;
        }

        String[] authData = dao.authenticateUser(email, password);

        if (authData != null) {
            int userId = Integer.parseInt(authData[0]);
            String role = authData[1];

            // Establish Global User Data Context Profile Session
            UserSession.getInstance().loginUser(userId, email, role);
            
            // Gracefully close login window safely
            closeCurrentStage();

            // Native load into Dashboard module scene framework
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Dashboard.fxml"));
                Parent root = loader.load();
                Stage stage = new Stage();
                stage.setTitle("Event Venue Management - Dashboard");
                stage.setScene(new Scene(root, 850, 550));
                stage.setResizable(false);
                stage.show();
            } catch (Exception ex) {
                ex.printStackTrace();
                showNotification(Alert.AlertType.ERROR, "System Failure", "Could not load Dashboard module framework layout view design structural components.");
            }
        } else {
            showNotification(Alert.AlertType.ERROR, "Authentication Failed", "Invalid email, password, or account is disabled.");
        }
    }

    @FXML
    private void handleGoToRegister() {
        closeCurrentStage();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/FXRegister.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Event Venue Management System - Registration");
            stage.setScene(new Scene(root, 460, 620));
            stage.setResizable(false);
            stage.show();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void closeCurrentStage() {
        Stage stage = (Stage) txtEmail.getScene().getWindow();
        stage.close();
    }

    private void showNotification(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        // Clean modern programmatic dialog styling matches dark aesthetic cleanly
        alert.getDialogPane().setStyle("-fx-background-color: #1e293b; -fx-text-fill: white;");
        alert.getDialogPane().lookup(".content.label").setStyle("-fx-text-fill: white;");
        alert.showAndWait();
    }
}