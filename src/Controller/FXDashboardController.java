package Controller;

import Util.UserSession;
import View.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javax.swing.SwingUtilities;
import javax.swing.JOptionPane;

public class FXDashboardController {

    @FXML private Label lblUserEmail;
    @FXML private Label lblUserRole;
    @FXML private GridPane gridOperations;

    private String role;

    @FXML
    public void initialize() {
        // Pull down the active user credentials safely
        this.role = UserSession.getInstance().getRole();
        lblUserEmail.setText(UserSession.getInstance().getEmail());
        lblUserRole.setText(role);

        setupDashboardActions();
    }

    private void setupDashboardActions() {
        gridOperations.getChildren().clear();

        if ("Admin".equals(role)) {
            // Row 0
            gridOperations.add(createGridButton("Manage Venues", e -> openVenuePage()), 0, 0);
            gridOperations.add(createGridButton("Manage Services", e -> openServicePage()), 1, 0);
            // Row 1
            gridOperations.add(createGridButton("Manage Bookings", e -> openBookingPage()), 0, 1);
            gridOperations.add(createGridButton("Manage Payments", e -> openPaymentPage()), 1, 1);
            // Row 2
            gridOperations.add(createGridButton("Manage Event Types", e -> openEventTypePage()), 0, 2);
            gridOperations.add(createGridButton("View System Users", e -> openUserListPage()), 1, 2);
            
        } else if ("Customer".equals(role)) {
            // Row 0
            gridOperations.add(createGridButton("Browse Venues", e -> launchJavaFXVenueBrowser()), 0, 0);
            gridOperations.add(createGridButton("My Bookings", e -> launchJavaFXBookingManagement()), 1, 0);
            // Row 1
            gridOperations.add(createGridButton("Make a Payment", e -> openPaymentPage()), 0, 1);
        }
    }

    private Button createGridButton(String text, javafx.event.EventHandler<javafx.event.ActionEvent> action) {
        Button btn = new Button(text);
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.setMaxHeight(Double.MAX_VALUE);
        btn.getStyleClass().add("dashboard-tile-button");
        btn.setOnAction(action);
        return btn;
    }

    // --- Legacy Swing Core Integration Windows ---
    private void openVenuePage() {
        SwingUtilities.invokeLater(() -> {
            VenuePage page = new VenuePage();
            new VenueController(page);
            page.setVisible(true);
        });
    }

    private void openServicePage() {
        SwingUtilities.invokeLater(() -> {
            ServicePage page = new ServicePage();
            new ServiceController(page);
            page.setVisible(true);
        });
    }

    private void openBookingPage() {
        SwingUtilities.invokeLater(() -> {
            BookingPage page = new BookingPage();
            new BookingController(page);
            page.setVisible(true);
        });
    }

    private void openPaymentPage() {
        SwingUtilities.invokeLater(() -> {
            PaymentPage page = new PaymentPage();
            new PaymentController(page);
            page.setVisible(true);
        });
    }

    private void openEventTypePage() {
        SwingUtilities.invokeLater(() -> {
            EventTypePage page = new EventTypePage();
            new EventTypeController(page);
            page.setVisible(true);
        });
    }

    private void openUserListPage() {
        SwingUtilities.invokeLater(() -> {
            UserListPage page = new UserListPage();
            new UserListController(page);
            page.setVisible(true);
        });
    }

    // --- Native JavaFX View Handlers ---
    private void launchJavaFXVenueBrowser() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/CustomerVenue.fxml"));
            Parent root = loader.load();
            
            // Link back context references if needed
            FXCustomerVenueController controller = loader.getController();

            Stage stage = new Stage();
            stage.setTitle("Browse Venues - JavaFX");
            stage.setScene(new Scene(root, 900, 600));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            showErrorMessage("Failed to load JavaFX Venue Browser UI:\n" + e.getMessage());
        }
    }

    private void launchJavaFXBookingManagement() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/BookingManagement.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Manage Bookings");
            stage.setScene(new Scene(root, 1000, 700));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            showErrorMessage("Failed to load Booking Management UI.\n" + e.getMessage());
        }
    }

    @FXML
    private void handleLogout() {
        UserSession.getInstance().logout();
        
        // Terminate the active JavaFX Dashboard Stage container
        Stage currentStage = (Stage) lblUserEmail.getScene().getWindow();
        currentStage.close();

        // Bounce back to safe login processing on the Main AWT thread loop
        SwingUtilities.invokeLater(() -> {
            LoginView loginView = new LoginView();
            new LoginController(loginView);
            loginView.setVisible(true);
        });
    }

    private void showErrorMessage(String msg) {
        SwingUtilities.invokeLater(() -> 
            JOptionPane.showMessageDialog(null, msg, "Execution Error", JOptionPane.ERROR_MESSAGE)
        );
    }
}