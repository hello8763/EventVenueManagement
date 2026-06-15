package Controller;

import DAO.BookingDAO;
import DAO.BookingDAOImp;
import Model.Booking;
import Util.UserSession;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class FXBookingController {

    private BookingDAO dao;

    @FXML private TextField txtBookingId, txtUserId, txtVenueId, txtTypeId, txtEventDate, txtStartTime, txtEndTime, txtTotalPrice, txtStatus;
    @FXML private Button btnAdd, btnUpdate, btnDelete, btnSearch;
    @FXML private TableView<Booking> table;
    
    @FXML private TableColumn<Booking, Integer> colId, colUser, colVenue, colType;
    @FXML private TableColumn<Booking, String> colDate, colStart, colEnd, colStatus;
    @FXML private TableColumn<Booking, Double> colPrice;

    @FXML
    public void initialize() {
        this.dao = new BookingDAOImp();
        
        // Map Table Columns to Model fields
        colId.setCellValueFactory(new PropertyValueFactory<>("bookingId"));
        colUser.setCellValueFactory(new PropertyValueFactory<>("userId"));
        colVenue.setCellValueFactory(new PropertyValueFactory<>("venueId"));
        colType.setCellValueFactory(new PropertyValueFactory<>("typeId"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("eventDate"));
        colStart.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        colEnd.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        // Click row to fill fields logic
        table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                populateFields(newSelection);
            }
        });

        loadBookings();
    }

    private void populateFields(Booking booking) {
        txtBookingId.setText(String.valueOf(booking.getBookingId()));
        txtUserId.setText(String.valueOf(booking.getUserId()));
        txtVenueId.setText(String.valueOf(booking.getVenueId()));
        txtTypeId.setText(String.valueOf(booking.getTypeId()));
        txtEventDate.setText(booking.getEventDate());
        txtStartTime.setText(booking.getStartTime());
        txtEndTime.setText(booking.getEndTime());
        txtTotalPrice.setText(String.valueOf(booking.getTotalPrice()));
        txtStatus.setText(booking.getStatus());
    }

    public void loadBookings() {
        List<Booking> bookings;
        if ("Customer".equals(UserSession.getInstance().getRole())) {
            bookings = dao.getBookingsByUserId(UserSession.getInstance().getUserId());
        } else {
            bookings = dao.getAllBookings();
        }
        table.setItems(FXCollections.observableArrayList(bookings));
    }

    @FXML
    private void addBooking() {
        try {
            Booking booking = new Booking();
            booking.setUserId(Integer.parseInt(txtUserId.getText().trim()));
            booking.setVenueId(Integer.parseInt(txtVenueId.getText().trim()));
            booking.setTypeId(Integer.parseInt(txtTypeId.getText().trim()));
            booking.setEventDate(txtEventDate.getText().trim());
            booking.setStartTime(txtStartTime.getText().trim());
            booking.setEndTime(txtEndTime.getText().trim());
            booking.setTotalPrice(Double.parseDouble(txtTotalPrice.getText().trim()));
            booking.setStatus(txtStatus.getText().trim());

            int id = dao.addBooking(booking);
            if (id > 0) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Booking Added (ID=" + id + ")");
                loadBookings();
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to add booking");
            }
        } catch (NumberFormatException ex) {
            showAlert(Alert.AlertType.WARNING, "Format Error", "Invalid number format inserted.");
        }
    }

    @FXML
    private void updateBooking() {
        try {
            Booking booking = new Booking();
            booking.setBookingId(Integer.parseInt(txtBookingId.getText().trim()));
            booking.setUserId(Integer.parseInt(txtUserId.getText().trim()));
            booking.setVenueId(Integer.parseInt(txtVenueId.getText().trim()));
            booking.setTypeId(Integer.parseInt(txtTypeId.getText().trim()));
            booking.setEventDate(txtEventDate.getText().trim());
            booking.setStartTime(txtStartTime.getText().trim());
            booking.setEndTime(txtEndTime.getText().trim());
            booking.setTotalPrice(Double.parseDouble(txtTotalPrice.getText().trim()));
            booking.setStatus(txtStatus.getText().trim());

            if (dao.updateBooking(booking)) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Booking Updated");
                loadBookings();
            }
        } catch (NumberFormatException ex) {
            showAlert(Alert.AlertType.WARNING, "Format Error", "Invalid numbers formatted.");
        }
    }

    @FXML
    private void deleteBooking() {
        try {
            int id = Integer.parseInt(txtBookingId.getText().trim());
            if (dao.deleteBooking(id)) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Booking Deleted");
                loadBookings();
            }
        } catch (NumberFormatException ex) {
            showAlert(Alert.AlertType.WARNING, "Format Error", "Invalid ID format.");
        }
    }

    @FXML
    private void searchBooking() {
        try {
            int id = Integer.parseInt(txtBookingId.getText().trim());
            Booking booking = dao.searchBookingById(id);
            if (booking != null) {
                populateFields(booking);
            } else {
                showAlert(Alert.AlertType.INFORMATION, "Not Found", "Booking Not Found");
            }
        } catch (NumberFormatException ex) {
            showAlert(Alert.AlertType.WARNING, "Format Error", "Provide a numeric ID.");
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