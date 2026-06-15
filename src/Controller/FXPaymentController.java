package Controller;

import DAO.PaymentDAO;
import DAO.PaymentDAOImp;
import DAO.BookingDAO;
import DAO.BookingDAOImp;
import Model.Payment;
import Model.Booking;
import Util.UserSession;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;

import java.util.List;

public class FXPaymentController {

    private PaymentDAO dao;
    private BookingDAO bookingDAO;

    @FXML private Label lblPaymentId;
    @FXML private TextField txtPaymentId, txtAmount, txtPaymentMethod, txtTransactionRef, txtPaymentStatus;
    @FXML private ComboBox<Booking> cmbBooking;
    @FXML private Button btnAdd, btnUpdate, btnDelete, btnSearch;
    @FXML private TableView<Payment> table;
    
    @FXML private TableColumn<Payment, Integer> colId, colBookingId;
    @FXML private TableColumn<Payment, Double> colAmount;
    @FXML private TableColumn<Payment, String> colMethod, colRef, colStatus;

    @FXML
    public void initialize() {
        this.dao = new PaymentDAOImp();
        this.bookingDAO = new BookingDAOImp();

        // Bind Columns to model fields
        colId.setCellValueFactory(new PropertyValueFactory<>("paymentId"));
        colBookingId.setCellValueFactory(new PropertyValueFactory<>("bookingId"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colMethod.setCellValueFactory(new PropertyValueFactory<>("paymentMethod"));
        colRef.setCellValueFactory(new PropertyValueFactory<>("transactionRef"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("paymentStatus"));

        // Format dropdown list representation text cleanly
        cmbBooking.setConverter(new StringConverter<Booking>() {
            @Override
            public String toString(Booking b) {
                return b == null ? "" : "ID: " + b.getBookingId() + " (User: " + b.getUserId() + ") - $" + b.getTotalPrice();
            }
            @Override
            public Booking fromString(String string) { return null; }
        });

        // Add auto calculation selection tracking trigger listener
        cmbBooking.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> updateAutoFields());

        // Secure UI checks against profile settings
        String role = UserSession.getInstance().getRole();
        if ("Customer".equals(role)) {
            lblPaymentId.setVisible(false);
            txtPaymentId.setVisible(false);
            btnUpdate.setVisible(false);
            btnDelete.setVisible(false);
            btnSearch.setVisible(false);
        }

        loadPayments();
        loadPendingBookings();
    }

    private void updateAutoFields() {
        Booking selected = cmbBooking.getSelectionModel().getSelectedItem();
        if (selected != null) {
            txtAmount.setText(String.valueOf(selected.getTotalPrice()));
            txtPaymentStatus.setText("Paid");
        } else {
            txtAmount.setText("");
            txtPaymentStatus.setText("");
        }
    }

    public void loadPendingBookings() {
        cmbBooking.getItems().clear();
        String role = UserSession.getInstance().getRole();
        int userId = UserSession.getInstance().getUserId();

        List<Booking> bookings = "Customer".equals(role)
                ? bookingDAO.getBookingsByUserId(userId)
                : bookingDAO.getAllBookings();

        for (Booking b : bookings) {
            if ("Confirmed".equalsIgnoreCase(b.getStatus())) {
                cmbBooking.getItems().add(b);
            }
        }
    }

    public void loadPayments() {
        String role = UserSession.getInstance().getRole();
        int userId = UserSession.getInstance().getUserId();

        List<Payment> payments = "Customer".equals(role)
                ? dao.getPaymentsByUserId(userId)
                : dao.getAllPayments();

        table.setItems(FXCollections.observableArrayList(payments));
    }

    @FXML
    private void addPayment() {
        try {
            Booking selectedBooking = cmbBooking.getSelectionModel().getSelectedItem();
            if (selectedBooking == null) {
                showAlert(Alert.AlertType.WARNING, "Selection Error", "No pending bookings selected.");
                return;
            }

            Payment payment = new Payment();
            payment.setBookingId(selectedBooking.getBookingId());
            payment.setAmount(selectedBooking.getTotalPrice());
            payment.setPaymentMethod(txtPaymentMethod.getText().trim());
            payment.setTransactionRef(txtTransactionRef.getText().trim());
            payment.setPaymentStatus("Paid");

            if (dao.addPayment(payment)) {
                selectedBooking.setStatus("Paid");
                bookingDAO.updateBooking(selectedBooking);

                showAlert(Alert.AlertType.INFORMATION, "Success", "Payment Recorded successfully.");
                loadPayments();
                loadPendingBookings();
            }
        } catch (Exception ex) {
            showAlert(Alert.AlertType.ERROR, "Error", "An error occurred during payment mapping.");
        }
    }

    @FXML
    private void updatePayment() {
        try {
            Booking selectedBooking = cmbBooking.getSelectionModel().getSelectedItem();
            if (selectedBooking == null) {
                showAlert(Alert.AlertType.WARNING, "Selection Error", "Please verify active booking selection parameters.");
                return;
            }

            Payment payment = new Payment();
            payment.setPaymentId(Integer.parseInt(txtPaymentId.getText().trim()));
            payment.setBookingId(selectedBooking.getBookingId());
            payment.setAmount(selectedBooking.getTotalPrice());
            payment.setPaymentMethod(txtPaymentMethod.getText().trim());
            payment.setTransactionRef(txtTransactionRef.getText().trim());
            payment.setPaymentStatus("Paid");

            if (dao.updatePayment(payment)) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Payment Entry Updated");
                loadPayments();
            }
        } catch (NumberFormatException ex) {
            showAlert(Alert.AlertType.WARNING, "Format Error", "Invalid ID data string structure mapping.");
        }
    }

    @FXML
    private void deletePayment() {
        try {
            int id = Integer.parseInt(txtPaymentId.getText().trim());
            if (dao.deletePayment(id)) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Payment Log Dropped");
                loadPayments();
            }
        } catch (NumberFormatException ex) {
            showAlert(Alert.AlertType.WARNING, "Format Error", "Invalid reference parsing configuration.");
        }
    }

    @FXML
    private void searchPayment() {
        try {
            int id = Integer.parseInt(txtPaymentId.getText().trim());
            Payment payment = dao.searchPaymentById(id);
            if (payment != null) {
                Booking b = bookingDAO.searchBookingById(payment.getBookingId());
                if (b != null) {
                    if (!cmbBooking.getItems().contains(b)) {
                        cmbBooking.getItems().add(b);
                    }
                    cmbBooking.getSelectionModel().select(b);
                }
                txtAmount.setText(String.valueOf(payment.getAmount()));
                txtPaymentMethod.setText(payment.getPaymentMethod());
                txtTransactionRef.setText(payment.getTransactionRef());
                txtPaymentStatus.setText(payment.getPaymentStatus());
            } else {
                showAlert(Alert.AlertType.INFORMATION, "Not Found", "Payment History Index Trace Missing.");
            }
        } catch (NumberFormatException ex) {
            showAlert(Alert.AlertType.WARNING, "Format Error", "Check identifier string syntax validation.");
        }
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}