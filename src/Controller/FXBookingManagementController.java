package Controller;

import DAO.BookingDAO;
import DAO.BookingDAOImp;
import Model.Booking;
import Util.UserSession;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class FXBookingManagementController {

    @FXML private TextField txtBookingId, txtUserId, txtVenueId, txtTypeId;
    @FXML private TextField txtEventDate, txtStartTime, txtEndTime, txtTotalPrice, txtStatus;
    @FXML private Button btnUpdate, btnSearch, btnRefresh;
    @FXML private TableView<Booking> tvBookings;
    @FXML private TableColumn<Booking, Integer> colId, colUserId, colVenueId, colTypeId;
    @FXML private TableColumn<Booking, String> colEventDate, colStartTime, colEndTime, colStatus;
    @FXML private TableColumn<Booking, Double> colTotalPrice;

    private BookingDAO bookingDAO;
    private ObservableList<Booking> bookingList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        bookingDAO = new BookingDAOImp();

        colId.setCellValueFactory(new PropertyValueFactory<>("bookingId"));
        colUserId.setCellValueFactory(new PropertyValueFactory<>("userId"));
        colVenueId.setCellValueFactory(new PropertyValueFactory<>("venueId"));
        colTypeId.setCellValueFactory(new PropertyValueFactory<>("typeId"));
        colEventDate.setCellValueFactory(new PropertyValueFactory<>("eventDate"));
        colStartTime.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        colEndTime.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        colTotalPrice.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        tvBookings.setItems(bookingList);
        loadBookings();

        tvBookings.getSelectionModel().selectedItemProperty().addListener((obs, old, selected) -> {
            if (selected != null) populateForm(selected);
        });

        btnUpdate.setOnAction(e -> updateBooking());
        btnSearch.setOnAction(e -> searchBooking());
        btnRefresh.setOnAction(e -> loadBookings());
    }

    private void loadBookings() {
        bookingList.clear();
        String role = UserSession.getInstance().getRole();
        int userId = UserSession.getInstance().getUserId();

        if ("Customer".equals(role)) {
            bookingList.addAll(bookingDAO.getBookingsByUserId(userId));
        } else {
            bookingList.addAll(bookingDAO.getAllBookings());
        }
    }

    private void populateForm(Booking b) {
        txtBookingId.setText(String.valueOf(b.getBookingId()));
        txtUserId.setText(String.valueOf(b.getUserId()));
        txtVenueId.setText(String.valueOf(b.getVenueId()));
        txtTypeId.setText(String.valueOf(b.getTypeId()));
        txtEventDate.setText(b.getEventDate());
        txtStartTime.setText(b.getStartTime());
        txtEndTime.setText(b.getEndTime());
        txtTotalPrice.setText(String.valueOf(b.getTotalPrice()));
        txtStatus.setText(b.getStatus());
    }

    private void updateBooking() {
        if (txtBookingId.getText().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Select a booking from the table first.");
            return;
        }
        try {
            Booking booking = new Booking();
            booking.setBookingId(Integer.parseInt(txtBookingId.getText()));
            booking.setUserId(Integer.parseInt(txtUserId.getText()));
            booking.setVenueId(Integer.parseInt(txtVenueId.getText()));
            booking.setTypeId(Integer.parseInt(txtTypeId.getText()));
            booking.setEventDate(txtEventDate.getText());
            booking.setStartTime(txtStartTime.getText());
            booking.setEndTime(txtEndTime.getText());
            booking.setTotalPrice(Double.parseDouble(txtTotalPrice.getText()));
            booking.setStatus(txtStatus.getText());

            if (bookingDAO.updateBooking(booking)) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Booking updated.");
                loadBookings();
                clearForm();
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Update failed.");
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Invalid Input", "Check numeric fields.");
        }
    }

    private void searchBooking() {
        try {
            int id = Integer.parseInt(txtBookingId.getText());
            Booking booking = bookingDAO.searchBookingById(id);
            if (booking != null) {
                populateForm(booking);
                tvBookings.getSelectionModel().select(booking);
            } else {
                showAlert(Alert.AlertType.WARNING, "Not Found", "No booking with ID " + id);
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Invalid ID", "Enter a numeric Booking ID to search.");
        }
    }

    private void clearForm() {
        txtBookingId.clear();
        txtUserId.clear();
        txtVenueId.clear();
        txtTypeId.clear();
        txtEventDate.clear();
        txtStartTime.clear();
        txtEndTime.clear();
        txtTotalPrice.clear();
        txtStatus.clear();
        tvBookings.getSelectionModel().clearSelection();
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}