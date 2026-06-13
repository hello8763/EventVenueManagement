package Controller;

import DAO.BookingDAO;
import DAO.BookingDAOImp;
import Model.Booking;
import View.BookingPage;
import Util.UserSession;
import javax.swing.*;
import java.util.List;

public class BookingController {

    BookingPage view;
    BookingDAO dao;

    public BookingController(BookingPage view) {
        this.view = view;
        dao = new BookingDAOImp();

        loadBookings();
        view.btnAdd.addActionListener(e -> addBooking());
        view.btnUpdate.addActionListener(e -> updateBooking());
        view.btnDelete.addActionListener(e -> deleteBooking());
        view.btnSearch.addActionListener(e -> searchBooking());
    }

public void addBooking() {
    try {
        Booking booking = new Booking();
        booking.setUserId(Integer.parseInt(view.txtUserId.getText()));
        booking.setVenueId(Integer.parseInt(view.txtVenueId.getText()));
        booking.setTypeId(Integer.parseInt(view.txtTypeId.getText()));
        booking.setEventDate(view.txtEventDate.getText());
        booking.setStartTime(view.txtStartTime.getText());
        booking.setEndTime(view.txtEndTime.getText());
        booking.setTotalPrice(Double.parseDouble(view.txtTotalPrice.getText()));
        booking.setStatus(view.txtStatus.getText());
        int id = dao.addBooking(booking);
        if (id > 0) {
            JOptionPane.showMessageDialog(view, "Booking Added (ID=" + id + ")");
            loadBookings();
        } else {
            JOptionPane.showMessageDialog(view, "Failed to add booking");
        }
    } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(view, "Invalid number format");
    }
}

public void loadBookings() {
    view.model.setRowCount(0);
    List<Booking> bookings;
    // If current user is Customer, show only their bookings
    if (UserSession.getInstance().getRole().equals("Customer")) {
        bookings = dao.getBookingsByUserId(UserSession.getInstance().getUserId());
    } else {
        bookings = dao.getAllBookings();
    }
    for (Booking booking : bookings) {
        Object[] row = {
            booking.getBookingId(), booking.getUserId(), booking.getVenueId(),
            booking.getTypeId(), booking.getEventDate(), booking.getStartTime(),
            booking.getEndTime(), booking.getTotalPrice(), booking.getStatus()
        };
        view.model.addRow(row);
    }
}

    public void updateBooking() {
        Booking booking = new Booking();
        booking.setBookingId(Integer.parseInt(view.txtBookingId.getText()));
        booking.setUserId(Integer.parseInt(view.txtUserId.getText()));
        booking.setVenueId(Integer.parseInt(view.txtVenueId.getText()));
        booking.setTypeId(Integer.parseInt(view.txtTypeId.getText()));
        booking.setEventDate(view.txtEventDate.getText());
        booking.setStartTime(view.txtStartTime.getText());
        booking.setEndTime(view.txtEndTime.getText());
        booking.setTotalPrice(Double.parseDouble(view.txtTotalPrice.getText()));
        booking.setStatus(view.txtStatus.getText());
        if (dao.updateBooking(booking)) {
            JOptionPane.showMessageDialog(view, "Booking Updated", "Success", JOptionPane.INFORMATION_MESSAGE);
            loadBookings();
        }
    }

    public void deleteBooking() {
        int id = Integer.parseInt(view.txtBookingId.getText());
        if (dao.deleteBooking(id)) {
            JOptionPane.showMessageDialog(view, "Booking Deleted", "Success", JOptionPane.INFORMATION_MESSAGE);
            loadBookings();
        }
    }

    public void searchBooking() {
        int id = Integer.parseInt(view.txtBookingId.getText());
        Booking booking = dao.searchBookingById(id);
        if (booking != null) {
            view.txtUserId.setText(String.valueOf(booking.getUserId()));
            view.txtVenueId.setText(String.valueOf(booking.getVenueId()));
            view.txtTypeId.setText(String.valueOf(booking.getTypeId()));
            view.txtEventDate.setText(booking.getEventDate());
            view.txtStartTime.setText(booking.getStartTime());
            view.txtEndTime.setText(booking.getEndTime());
            view.txtTotalPrice.setText(String.valueOf(booking.getTotalPrice()));
            view.txtStatus.setText(booking.getStatus());
        } else {
            JOptionPane.showMessageDialog(view, "Booking Not Found");
        }
    }

}
