package Controller;

import DAO.UserDAO;
import DAO.UserDAOImp;
import Util.UserSession;
import View.LoginView;
import View.RegisterView;
import View.Dashboard;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javax.swing.*;

public class LoginController {
    private final LoginView view;
    private final UserDAO dao;

    public LoginController(LoginView view) {
        this.view = view;
        this.dao = new UserDAOImp();

        this.view.btnLogin.addActionListener(e -> handleLogin());
        this.view.btnGoToRegister.addActionListener(e -> {
            view.dispose();
            RegisterView regView = new RegisterView();
            new RegisterController(regView);
            regView.setVisible(true);
        });
    }

    private void handleLogin() {
        String email = view.txtEmail.getText().trim();
        String password = new String(view.txtPassword.getPassword()).trim();

        if (email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(view, "All fields are required.", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String[] authData = dao.authenticateUser(email, password);

        if (authData != null) {
            int userId = Integer.parseInt(authData[0]);
            String role = authData[1];

            UserSession.getInstance().loginUser(userId, email, role);
            view.dispose();
            
            // Instantiates existing system dashboard now aware of the role
            // Dashboard mainApp = new Dashboard();
            Platform.runLater(() -> {
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
    }
});
// mainApp.setVisible(true);;
        } else {
            JOptionPane.showMessageDialog(view, "Invalid email, password, or account is disabled.", "Authentication Failed", JOptionPane.ERROR_MESSAGE);
        }
    }
}