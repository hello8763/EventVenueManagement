package Controller;

import DAO.PaymentDAO;
import DAO.PaymentDAOImp;
import DAO.BookingDAO;
import DAO.BookingDAOImp;
import Model.Payment;
import Model.Booking;
import Util.UserSession;
import View.PaymentPage;
import javax.swing.*;
import java.util.List;

public class PaymentController {

    PaymentPage view;
    PaymentDAO dao;
    BookingDAO bookingDAO; 

    public PaymentController(PaymentPage view) {
        this.view = view;
        dao = new PaymentDAOImp();
        bookingDAO = new BookingDAOImp();

        loadPayments();
        loadPendingBookings(); 

        view.cmbBooking.addActionListener(e -> updateAutoFields());
        updateAutoFields(); 

        view.btnAdd.addActionListener(e -> addPayment());
        view.btnUpdate.addActionListener(e -> updatePayment());
        view.btnDelete.addActionListener(e -> deletePayment());
        view.btnSearch.addActionListener(e -> searchPayment());
    }

    private void updateAutoFields() {
        Booking selectedBooking = (Booking) view.cmbBooking.getSelectedItem();
        if (selectedBooking != null) {
            view.txtAmount.setText(String.valueOf(selectedBooking.getTotalPrice()));
            view.txtPaymentStatus.setText("Paid");
        } else {
            view.txtAmount.setText("");
            view.txtPaymentStatus.setText("");
        }
    }

    public void loadPendingBookings() {
        view.cmbBooking.removeAllItems();
        String role = UserSession.getInstance().getRole();
        int userId = UserSession.getInstance().getUserId();

        List<Booking> bookings;
        if ("Customer".equals(role)) {
            bookings = bookingDAO.getBookingsByUserId(userId);
        } else {
            bookings = bookingDAO.getAllBookings();
        }

        for (Booking b : bookings) {
            if ("Confirmed".equalsIgnoreCase(b.getStatus())) {
                view.cmbBooking.addItem(b);
            }
        }
    }

    public void addPayment() {
        try {
            Booking selectedBooking = (Booking) view.cmbBooking.getSelectedItem();
            if (selectedBooking == null) {
                JOptionPane.showMessageDialog(view, "No pending bookings available to pay.");
                return;
            }

            Payment payment = new Payment();
            payment.setBookingId(selectedBooking.getBookingId()); 
            payment.setAmount(selectedBooking.getTotalPrice());
            payment.setPaymentMethod(view.txtPaymentMethod.getText());
            payment.setTransactionRef(view.txtTransactionRef.getText());
            payment.setPaymentStatus("Paid"); 
            
            if (dao.addPayment(payment)) {
                selectedBooking.setStatus("Paid");
                bookingDAO.updateBooking(selectedBooking);

                JOptionPane.showMessageDialog(view, "Payment Successful! Booking status updated to 'Paid'.", "Success", JOptionPane.INFORMATION_MESSAGE);
                loadPayments();
                loadPendingBookings(); 
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "An error occurred while processing the payment.");
        }
    }

    public void updatePayment() {
        try {
            Booking selectedBooking = (Booking) view.cmbBooking.getSelectedItem();
            if (selectedBooking == null) {
                JOptionPane.showMessageDialog(view, "Please select a booking.");
                return;
            }

            Payment payment = new Payment();
            payment.setPaymentId(Integer.parseInt(view.txtPaymentId.getText()));
            payment.setBookingId(selectedBooking.getBookingId());
            payment.setAmount(selectedBooking.getTotalPrice()); 
            payment.setPaymentMethod(view.txtPaymentMethod.getText());
            payment.setTransactionRef(view.txtTransactionRef.getText());
            payment.setPaymentStatus("Paid"); 
            
            if (dao.updatePayment(payment)) {
                JOptionPane.showMessageDialog(view, "Payment Updated", "Success", JOptionPane.INFORMATION_MESSAGE);
                loadPayments();
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(view, "Invalid format");
        }
    }

    public void deletePayment() {
        try {
            int id = Integer.parseInt(view.txtPaymentId.getText());
            if (dao.deletePayment(id)) {
                JOptionPane.showMessageDialog(view, "Payment Deleted", "Success", JOptionPane.INFORMATION_MESSAGE);
                loadPayments();
            }
        } catch (NumberFormatException ex) {
             JOptionPane.showMessageDialog(view, "Invalid ID format");
        }
    }

    public void searchPayment() {
        try {
            int id = Integer.parseInt(view.txtPaymentId.getText());
            Payment payment = dao.searchPaymentById(id);
            if (payment != null) {
                Booking b = bookingDAO.searchBookingById(payment.getBookingId());
                if (b != null) {
                    boolean found = false;
                    for (int i = 0; i < view.cmbBooking.getItemCount(); i++) {
                        if (view.cmbBooking.getItemAt(i).getBookingId() == b.getBookingId()) {
                            view.cmbBooking.setSelectedIndex(i);
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        view.cmbBooking.addItem(b);
                        view.cmbBooking.setSelectedItem(b);
                    }
                }

                view.txtAmount.setText(String.valueOf(payment.getAmount()));
                view.txtPaymentMethod.setText(payment.getPaymentMethod());
                view.txtTransactionRef.setText(payment.getTransactionRef());
                view.txtPaymentStatus.setText(payment.getPaymentStatus());
            } else {
                JOptionPane.showMessageDialog(view, "Payment Not Found");
            }
        } catch (NumberFormatException ex) {
             JOptionPane.showMessageDialog(view, "Invalid ID format");
        }
    }

    // UPDATED: Now checks role and loads only the allowed data into the table
    public void loadPayments() {
        view.model.setRowCount(0);
        String role = UserSession.getInstance().getRole();
        int userId = UserSession.getInstance().getUserId();
        
        List<Payment> payments;

        if ("Customer".equals(role)) {
            // Only fetch payments belonging to this specific user
            payments = dao.getPaymentsByUserId(userId);
        } else {
            // Admin sees everything
            payments = dao.getAllPayments();
        }

        for (Payment payment : payments) {
            Object[] row = {
                payment.getPaymentId(),
                payment.getBookingId(),
                payment.getAmount(),
                payment.getPaymentMethod(),
                payment.getTransactionRef(),
                payment.getPaymentStatus()
            };
            view.model.addRow(row);
        }
    }
}