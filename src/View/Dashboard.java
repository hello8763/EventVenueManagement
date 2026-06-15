


package View;

import Controller.*;
import Util.UserSession;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javax.swing.*;
import java.awt.*;

public class Dashboard extends JFrame {
    private String role;

    public Dashboard() {
        super("Event Venue Management - Dashboard");
        this.role = UserSession.getInstance().getRole();
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(0, 1, 10, 10));

        JLabel welcome = new JLabel("Welcome, " + UserSession.getInstance().getEmail() + " (" + role + ")", SwingConstants.CENTER);
        add(welcome);

        if (role.equals("Admin")) {
            add(createButton("Manage Venues", e -> openVenuePage()));
            add(createButton("Manage Services", e -> openServicePage()));
            add(createButton("Manage Bookings", e -> openBookingPage()));
            add(createButton("Manage Payments", e -> openPaymentPage()));
            add(createButton("Manage Event Types", e -> {
                EventTypePage page = new EventTypePage();
                new EventTypeController(page);
                page.setVisible(true);
            }));
            add(createButton("View System Users", e -> {
                UserListPage page = new UserListPage();
                new UserListController(page);
                page.setVisible(true);
            }));
        } else if (role.equals("Customer")) {
            add(createButton("Browse Venues", e -> launchJavaFXVenueBrowser()));
             add(createButton("My Bookings", e -> launchJavaFXBookingManagement()));
            add(createButton("Make a Payment", e -> openPaymentPage()));
        }

        add(createButton("Logout", e -> logout()));
    }

    private JButton createButton(String text, java.awt.event.ActionListener action) {
        JButton btn = new JButton(text);
        btn.addActionListener(action);
        return btn;
    }
private void launchJavaFXVenueBrowser() {
    Platform.runLater(() -> {
        try {
            System.out.println("Loading JavaFX venue browser...");
            // Force a fresh FXMLLoader each time
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/View/CustomerVenue.fxml"));
            if (loader.getLocation() == null) {
                throw new Exception("FXML not found at /View/CustomerVenue.fxml");
            }
            Parent root = loader.load();
            FXCustomerVenueController controller = loader.getController();
            controller.setDashboardParent(this);

            Stage stage = new Stage();
            stage.setTitle("Browse Venues - JavaFX");
            stage.setScene(new Scene(root, 900, 600));
            stage.setOnHidden(e -> System.out.println("JavaFX window closed"));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            SwingUtilities.invokeLater(() -> 
                JOptionPane.showMessageDialog(this, "Failed to load JavaFX UI:\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE)
            );
        }
    });

    
}
private void launchJavaFXBookingManagement() {
    Platform.runLater(() -> {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/BookingManagement.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Manage Bookings");
            stage.setScene(new Scene(root, 1000, 700));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to load Booking Management UI.\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    });
}
    // ---------- Existing methods ----------
    private void openVenuePage() {
        VenuePage page = new VenuePage();
        new VenueController(page);
        page.setVisible(true);
    }

    private void openServicePage() {
        ServicePage page = new ServicePage();
        new ServiceController(page);
        page.setVisible(true);
    }

    private void openBookingPage() {
        BookingPage page = new BookingPage();
        new BookingController(page);
        page.setVisible(true);
    }

    private void openPaymentPage() {
        PaymentPage page = new PaymentPage();
        new PaymentController(page);
        page.setVisible(true);
    }

    private void logout() {
        UserSession.getInstance().logout();
        dispose();
        LoginView loginView = new LoginView();
        new LoginController(loginView);
        loginView.setVisible(true);
    }
}