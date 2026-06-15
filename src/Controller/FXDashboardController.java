// package Controller;

// import Util.UserSession;
// import View.*;
// import javafx.application.Platform;
// import javafx.fxml.FXML;
// import javafx.fxml.FXMLLoader;
// import javafx.scene.Parent;
// import javafx.scene.Scene;
// import javafx.scene.control.Button;
// import javafx.scene.control.Label;
// import javafx.scene.layout.GridPane;
// import javafx.stage.Stage;
// import javax.swing.SwingUtilities;
// import javax.swing.JOptionPane;

// public class FXDashboardController {

//     @FXML private Label lblUserEmail;
//     @FXML private Label lblUserRole;
//     @FXML private GridPane gridOperations;

//     private String role;

//     @FXML
//     public void initialize() {
//         // Pull down the active user credentials safely
//         this.role = UserSession.getInstance().getRole();
//         lblUserEmail.setText(UserSession.getInstance().getEmail());
//         lblUserRole.setText(role);

//         setupDashboardActions();
//     }

//     private void setupDashboardActions() {
//         gridOperations.getChildren().clear();

//         if ("Admin".equals(role)) {
//             // Row 0
//             gridOperations.add(createGridButton("Manage Venues", e -> openVenuePage()), 0, 0);
//             gridOperations.add(createGridButton("Manage Services", e -> openServicePage()), 1, 0);
//             // Row 1
//             gridOperations.add(createGridButton("Manage Bookings", e -> openBookingPage()), 0, 1);
//             gridOperations.add(createGridButton("Manage Payments", e -> openPaymentPage()), 1, 1);
//             // Row 2
//             gridOperations.add(createGridButton("Manage Event Types", e -> openEventTypePage()), 0, 2);
//             gridOperations.add(createGridButton("View System Users", e -> openUserListPage()), 1, 2);
            
//         } else if ("Customer".equals(role)) {
//             // Row 0
//             gridOperations.add(createGridButton("Browse Venues", e -> launchJavaFXVenueBrowser()), 0, 0);
//             gridOperations.add(createGridButton("My Bookings", e -> launchJavaFXBookingManagement()), 1, 0);
//             // Row 1
//             gridOperations.add(createGridButton("Make a Payment", e -> openPaymentPage()), 0, 1);
//         }
//     }

//     private Button createGridButton(String text, javafx.event.EventHandler<javafx.event.ActionEvent> action) {
//         Button btn = new Button(text);
//         btn.setMaxWidth(Double.MAX_VALUE);
//         btn.setMaxHeight(Double.MAX_VALUE);
//         btn.getStyleClass().add("dashboard-tile-button");
//         btn.setOnAction(action);
//         return btn;
//     }

//     // --- Legacy Swing Core Integration Windows ---
// private void openVenuePage() {
//         try {
//             FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/FXVenueManagement.fxml"));
//             Parent root = loader.load();
//             Stage stage = new Stage();
//             stage.setTitle("Admin Control Panel - Venue Management");
//             stage.setScene(new Scene(root, 950, 600));
//             stage.show();
//         } catch (Exception e) {
//             e.printStackTrace();
//             showErrorMessage("Critical UI failure while loading Venues interface:\n" + e.getMessage());
//         }
//     }

//     private void openServicePage() {
//         try {
//             FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/FXServiceManagement.fxml"));
//             Parent root = loader.load();
//             Stage stage = new Stage();
//             stage.setTitle("Admin Control Panel - Service Management");
//             stage.setScene(new Scene(root, 950, 600));
//             stage.show();
//         } catch (Exception e) {
//             e.printStackTrace();
//             showErrorMessage("Critical UI failure while loading Services interface:\n" + e.getMessage());
//         }
//     }

//     private void openBookingPage() {
//            try {
//             FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/FXBookingView.fxml"));
//             Parent root = loader.load();
//             Stage stage = new Stage();
//             stage.setTitle("Admin Control Panel - Booking Management");
//             stage.setScene(new Scene(root, 950, 600));
//             stage.show();
//         } catch (Exception e) {
//             e.printStackTrace();
//             showErrorMessage("Critical UI failure while loading Services interface:\n" + e.getMessage());
//         }
//     }

//     private void openPaymentPage() {
//            try {
//             FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/FXPaymentView.fxml"));
//             Parent root = loader.load();
//             Stage stage = new Stage();
//             stage.setTitle("Admin Control Panel - Payment Management");
//             stage.setScene(new Scene(root, 950, 600));
//             stage.show();
//         } catch (Exception e) {
//             e.printStackTrace();
//             showErrorMessage("Critical UI failure while loading Services interface:\n" + e.getMessage());
//         }
//     }

//     private void openEventTypePage() {
//               try {
//             FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/FXEventTypeView.fxml"));
//             Parent root = loader.load();
//             Stage stage = new Stage();
//             stage.setTitle("Admin Control Panel - Event Type Management");
//             stage.setScene(new Scene(root, 950, 600));
//             stage.show();
//         } catch (Exception e) {
//             e.printStackTrace();
//             showErrorMessage("Critical UI failure while loading Services interface:\n" + e.getMessage());
//         }
//     }

//     private void openUserListPage() {
//        try {
//             FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/FXUserListView.fxml"));
//             Parent root = loader.load();
//             Stage stage = new Stage();
//             stage.setTitle("Admin Control Panel - User List Management");
//             stage.setScene(new Scene(root, 950, 600));
//             stage.show();
//         } catch (Exception e) {
//             e.printStackTrace();
//             showErrorMessage("Critical UI failure while loading Services interface:\n" + e.getMessage());
//         }
//     }

//     // --- Native JavaFX View Handlers ---
//     private void launchJavaFXVenueBrowser() {
//         try {
//             FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/CustomerVenue.fxml"));
//             Parent root = loader.load();
            
//             // Link back context references if needed
//             FXCustomerVenueController controller = loader.getController();

//             Stage stage = new Stage();
//             stage.setTitle("Browse Venues - JavaFX");
//             stage.setScene(new Scene(root, 900, 600));
//             stage.show();
//         } catch (Exception e) {
//             e.printStackTrace();
//             showErrorMessage("Failed to load JavaFX Venue Browser UI:\n" + e.getMessage());
//         }
//     }

//     private void launchJavaFXBookingManagement() {
//         try {
//             FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/BookingManagement.fxml"));
//             Parent root = loader.load();
//             Stage stage = new Stage();
//             stage.setTitle("Manage Bookings");
//             stage.setScene(new Scene(root, 1000, 700));
//             stage.show();
//         } catch (Exception e) {
//             e.printStackTrace();
//             showErrorMessage("Failed to load Booking Management UI.\n" + e.getMessage());
//         }
//     }

//     @FXML
//     private void handleLogout() {
//         UserSession.getInstance().logout();
        
//         // Terminate the active JavaFX Dashboard Stage container
//         Stage currentStage = (Stage) lblUserEmail.getScene().getWindow();
//         currentStage.close();

//         // Bounce back to safe login processing on the Main AWT thread loop
//         SwingUtilities.invokeLater(() -> {
//             LoginView loginView = new LoginView();
//             new LoginController(loginView);
//             loginView.setVisible(true);
//         });
//     }

//     private void showErrorMessage(String msg) {
//         SwingUtilities.invokeLater(() -> 
//             JOptionPane.showMessageDialog(null, msg, "Execution Error", JOptionPane.ERROR_MESSAGE)
//         );
//     }
// }

















package Controller;

import Util.UserSession;
import View.*;

// TODO: Replace this import with your actual database utility class if it is named differently
import Databases.mysqlDBConnection; 

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javax.swing.SwingUtilities;
import javax.swing.JOptionPane;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class FXDashboardController {

    @FXML private Label lblUserEmail;
    @FXML private Label lblUserRole;
    @FXML private GridPane gridOperations;
    
    // Live Telemetry Components
    @FXML private Label lblStatVenues;
    @FXML private Label lblStatBookings;
    @FXML private Label lblStatRevenue;
    @FXML private Label lblStatUsers;
    @FXML private VBox analyticsContainer;

    private String role;

    @FXML
    public void initialize() {
        this.role = UserSession.getInstance().getRole();
        lblUserEmail.setText(UserSession.getInstance().getEmail());
        lblUserRole.setText(role);

        setupDashboardActions();
        
        if ("Admin".equals(role)) {
            loadSystemTelemetry();
        } else {
            if (analyticsContainer != null) {
                analyticsContainer.setVisible(false);
                analyticsContainer.setManaged(false);
            }
        }
    }

    /**
     * Fetches real-time status data directly from your database
     */
    private void loadSystemTelemetry() {
        new Thread(() -> {
            int totalVenues = 0;
            int totalBookings = 0;
            double totalRevenue = 0.0;
            int totalUsers = 0;

            String queryVenues = "SELECT COUNT(*) FROM venues";
            String queryBookings = "SELECT COUNT(*) FROM bookings";
            String queryRevenue = "SELECT SUM(total_price) FROM bookings"; // Adjusted to match standard booking columns
            String queryUsers = "SELECT COUNT(*) FROM users";

            // ATTENTION: If your connection class is named differently (e.g. DBConnection.getConnection()),
            // change 'mysqlDBConnection.getConnection()' right below to match your project's setup.
            try (Connection con = mysqlDBConnection.getConnection()) {
                
                // 1. Get total venues
                try (PreparedStatement ps = con.prepareStatement(queryVenues); ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) totalVenues = rs.getInt(1);
                } catch (Exception e) { System.err.println("Venues query error: " + e.getMessage()); }

                // 2. Get total bookings
                try (PreparedStatement ps = con.prepareStatement(queryBookings); ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) totalBookings = rs.getInt(1);
                } catch (Exception e) { System.err.println("Bookings query error: " + e.getMessage()); }

                // 3. Get total revenue
                try (PreparedStatement ps = con.prepareStatement(queryRevenue); ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) totalRevenue = rs.getDouble(1);
                } catch (Exception e) { System.err.println("Revenue query error: " + e.getMessage()); }

                // 4. Get total users
                try (PreparedStatement ps = con.prepareStatement(queryUsers); ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) totalUsers = rs.getInt(1);
                } catch (Exception e) { System.err.println("Users query error: " + e.getMessage()); }

                // Pack values for thread-safe UI rendering
                final int finalVenues = totalVenues;
                final int finalBookings = totalBookings;
                final double finalRevenue = totalRevenue;
                final int finalUsers = totalUsers;

                Platform.runLater(() -> {
                    if (lblStatVenues != null) lblStatVenues.setText(String.valueOf(finalVenues));
                    if (lblStatBookings != null) lblStatBookings.setText(String.valueOf(finalBookings));
                    if (lblStatRevenue != null) lblStatRevenue.setText(String.format("$%,.2f", finalRevenue));
                    if (lblStatUsers != null) lblStatUsers.setText(String.valueOf(finalUsers));
                });

            } catch (Exception e) {
                // If a connection level issue happens, print it directly to the console so you can see it
                System.err.println("CRITICAL: Dashboard telemetry failed to open database connection.");
                e.printStackTrace();
                
                // Set fallbacks to 0 instead of leaving them stuck at "..."
                Platform.runLater(() -> {
                    if (lblStatVenues != null) lblStatVenues.setText("0");
                    if (lblStatBookings != null) lblStatBookings.setText("0");
                    if (lblStatRevenue != null) lblStatRevenue.setText("$0.00");
                    if (lblStatUsers != null) lblStatUsers.setText("0");
                });
            }
        }).start();
    }

    private void setupDashboardActions() {
        gridOperations.getChildren().clear();

        if ("Admin".equals(role)) {
            gridOperations.add(createGridButton("Venue Controller", "Manage physical properties & capacities", e -> openVenuePage()), 0, 0);
            gridOperations.add(createGridButton("Service Catalog", "Manage premium items & packages", e -> openServicePage()), 1, 0);
            gridOperations.add(createGridButton("Booking Ledger", "Audit scheduling & date conflicts", e -> openBookingPage()), 0, 1);
            gridOperations.add(createGridButton("Financial Invoices", "Track transactional transaction flows", e -> openPaymentPage()), 1, 1);
            gridOperations.add(createGridButton("Event Classifications", "Configure category defaults & base deposits", e -> openEventTypePage()), 0, 2);
            gridOperations.add(createGridButton("User Credentials Matrix", "Audit account security profiles", e -> openUserListPage()), 1, 2);
            
        } else if ("Customer".equals(role)) {
            gridOperations.add(createGridButton("Browse Venues", "Explore availability and pricing structures", e -> launchJavaFXVenueBrowser()), 0, 0);
            gridOperations.add(createGridButton("My Bookings", "Track reservations and active status details", e -> launchJavaFXBookingManagement()), 1, 0);
            gridOperations.add(createGridButton("Settle Balances", "Make instant payments securely via invoice", e -> openPaymentPage()), 0, 1);
        }
    }

    private VBox createGridButton(String titleText, String subtitleText, javafx.event.EventHandler<javafx.event.ActionEvent> action) {
        VBox tileCard = new VBox(6);
        tileCard.getStyleClass().add("dashboard-operation-card");

        Label lblTitle = new Label(titleText);
        lblTitle.getStyleClass().add("card-action-title");

        Label lblSubtitle = new Label(subtitleText);
        lblSubtitle.getStyleClass().add("card-action-subtitle");
        lblSubtitle.setWrapText(true);

        Button actionBtn = new Button("Launch Module →");
        actionBtn.setMaxWidth(Double.MAX_VALUE);
        actionBtn.getStyleClass().add("card-action-button");
        actionBtn.setOnAction(action);

        tileCard.getChildren().addAll(lblTitle, lblSubtitle, actionBtn);
        return tileCard;
    }

    // --- Window Controller Navigation Methods ---
    private void openVenuePage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/FXVenueManagement.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Admin Control Panel - Venue Management");
            stage.setScene(new Scene(root, 950, 600));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            showErrorMessage("Critical UI failure while loading Venues interface:\n" + e.getMessage());
        }
    }

    private void openServicePage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/FXServiceManagement.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Admin Control Panel - Service Management");
            stage.setScene(new Scene(root, 950, 600));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            showErrorMessage("Critical UI failure while loading Services interface:\n" + e.getMessage());
        }
    }

    private void openBookingPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/FXBookingView.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Admin Control Panel - Booking Management");
            stage.setScene(new Scene(root, 950, 600));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            showErrorMessage("Critical UI failure while loading Bookings interface:\n" + e.getMessage());
        }
    }

    private void openPaymentPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/FXPaymentView.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Admin Control Panel - Payment Management");
            stage.setScene(new Scene(root, 950, 600));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            showErrorMessage("Critical UI failure while loading Payments interface:\n" + e.getMessage());
        }
    }

    private void openEventTypePage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/FXEventTypeView.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Admin Control Panel - Event Type Management");
            stage.setScene(new Scene(root, 950, 600));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            showErrorMessage("Critical UI failure while loading Event Types interface:\n" + e.getMessage());
        }
    }

    private void openUserListPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/FXUserListView.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Admin Control Panel - User List Management");
            stage.setScene(new Scene(root, 950, 600));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            showErrorMessage("Critical UI failure while loading Users interface:\n" + e.getMessage());
        }
    }

    private void launchJavaFXVenueBrowser() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/CustomerVenue.fxml"));
            Parent root = loader.load();
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
        Stage currentStage = (Stage) lblUserEmail.getScene().getWindow();
        currentStage.close();

          try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/FXLogin.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Event Venue Management System - Sign In");
            stage.setScene(new Scene(root, 420, 480));
            stage.setResizable(false);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            showErrorMessage("Failed to load Booking Management UI.\n" + e.getMessage());
        }
    }

    private void showErrorMessage(String msg) {
        SwingUtilities.invokeLater(() -> 
            JOptionPane.showMessageDialog(null, msg, "Execution Error", JOptionPane.ERROR_MESSAGE)
        );
    }
}