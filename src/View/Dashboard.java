package View;

import Controller.*;
import Util.UserSession;
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

        // Role-based buttons
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
       }  else if (role.equals("Customer")) {
    add(createButton("Browse Venues", e -> {
        CustomerVenueBrowsePage page = new CustomerVenueBrowsePage();
        new CustomerVenueController(page);
        page.setVisible(true);
    }));
    add(createButton("My Bookings", e -> openBookingPage()));
    add(createButton("Make a Payment", e -> openPaymentPage()));
}

        add(createButton("Logout", e -> logout()));
    }

    private JButton createButton(String text, java.awt.event.ActionListener action) {
        JButton btn = new JButton(text);
        btn.addActionListener(action);
        return btn;
    }

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
    private void openBrowseVenues() {
    CustomerVenueBrowsePage page = new CustomerVenueBrowsePage();
    new CustomerVenueController(page);
    page.setVisible(true);
}

private void openCreateBooking() {
     JOptionPane.showMessageDialog(this, "Please go to 'Browse Venues' and select a venue to book.");
}

    private void logout() {
        UserSession.getInstance().logout();
        dispose();
        LoginView loginView = new LoginView();
        new LoginController(loginView);
        loginView.setVisible(true);
    }
}