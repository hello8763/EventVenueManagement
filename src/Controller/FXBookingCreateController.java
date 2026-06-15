// package Controller;

// import DAO.*;
// import Model.*;
// import Util.UserSession;
// import javafx.collections.FXCollections;
// import javafx.fxml.FXML;
// import javafx.scene.control.*;
// import javafx.stage.Stage;
// import java.time.LocalDate;
// import java.time.LocalTime;
// import java.time.format.DateTimeFormatter;
// import java.util.List;

// public class FXBookingCreateController {

//     @FXML private Label lblVenueName, lblVenueLocation, lblVenueCapacity, lblVenueRate;
//     @FXML private ComboBox<EventType> cbEventType;
//     @FXML private DatePicker dpEventDate;
//     @FXML private TextField txtStartTime, txtEndTime;
//     @FXML private ListView<Service> listServices;
//     @FXML private TextField txtTotalPrice;
//     @FXML private Button btnCalculate, btnConfirm;

//     private Venue selectedVenue;
//     private BookingDAO bookingDAO;
//     private VenueDAO venueDAO;
//     private EventTypeDAO eventTypeDAO;
//     private ServiceDAO serviceDAO;
//     private BookingServicesDAO bookingServicesDAO;

//     @FXML
//     public void initialize() {
//         bookingDAO = new BookingDAOImp();
//         venueDAO = new VenueDAOImp();
//         eventTypeDAO = new EventTypeDAOImp();
//         serviceDAO = new ServiceDAOImp();
//         bookingServicesDAO = new BookingServicesDAOImp();

//         loadEventTypes();
//         loadServices();

//         // Allow multiple service selection
//         listServices.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

//         // Button actions
//         btnCalculate.setOnAction(e -> calculateTotal());
//         btnConfirm.setOnAction(e -> confirmBooking());
//     }

//     public void setVenueData(Venue venue) {
//         this.selectedVenue = venue;
//         lblVenueName.setText(venue.getName());
//         lblVenueLocation.setText(venue.getLocation());
//         lblVenueCapacity.setText(String.valueOf(venue.getCapacity()));
//         lblVenueRate.setText("$" + venue.getHourlyRate() + " / hr");
//     }

//     private void loadEventTypes() {
//         cbEventType.setItems(FXCollections.observableArrayList(eventTypeDAO.getAllEventTypes()));
//         cbEventType.setConverter(new javafx.util.StringConverter<EventType>() {
//             @Override
//             public String toString(EventType et) { return et == null ? "" : et.getName(); }
//             @Override
//             public EventType fromString(String s) { return null; }
//         });
//     }

//     private void loadServices() {
//         List<Service> services = serviceDAO.getAllServices();
//         listServices.setItems(FXCollections.observableArrayList(services));
//         // Display service name in the list
//         listServices.setCellFactory(lv -> new ListCell<Service>() {
//             @Override
//             protected void updateItem(Service s, boolean empty) {
//                 super.updateItem(s, empty);
//                 setText(empty || s == null ? "" : s.getServiceName() + " - $" + s.getPricePerUnit());
//             }
//         });
//     }

//     @FXML
//     private void calculateTotal() {
//         try {
//             // Validate inputs
//             if (selectedVenue == null) {
//                 showAlert("Error", "Venue data missing.", Alert.AlertType.ERROR);
//                 return;
//             }
//             if (cbEventType.getValue() == null) {
//                 showAlert("Missing Data", "Please select an event type.", Alert.AlertType.WARNING);
//                 return;
//             }
//             if (dpEventDate.getValue() == null) {
//                 showAlert("Missing Data", "Please select an event date.", Alert.AlertType.WARNING);
//                 return;
//             }
//             String start = txtStartTime.getText().trim();
//             String end = txtEndTime.getText().trim();
//             if (start.isEmpty() || end.isEmpty()) {
//                 showAlert("Missing Data", "Please enter start and end times (HH:MM).", Alert.AlertType.WARNING);
//                 return;
//             }

//             // Parse times
//             LocalTime startTime = LocalTime.parse(start, DateTimeFormatter.ofPattern("HH:mm"));
//             LocalTime endTime = LocalTime.parse(end, DateTimeFormatter.ofPattern("HH:mm"));

//             // Calculate duration (handle overnight: end <= start -> add 24h)
//             double startHours = startTime.getHour() + startTime.getMinute() / 60.0;
//             double endHours = endTime.getHour() + endTime.getMinute() / 60.0;
//             double duration = endHours - startHours;
//             if (duration <= 0) duration += 24;
//             if (duration <= 0) {
//                 showAlert("Invalid Time", "End time must be after start time.", Alert.AlertType.ERROR);
//                 return;
//             }

//             // Venue cost
//             double venueCost = selectedVenue.getHourlyRate() * duration;

//             // Services cost
//             double servicesCost = 0.0;
//             for (Service s : listServices.getSelectionModel().getSelectedItems()) {
//                 servicesCost += s.getPricePerUnit();
//             }

//             double total = venueCost + servicesCost;
//             txtTotalPrice.setText(String.format("%.2f", total));

//         } catch (Exception ex) {
//             ex.printStackTrace();
//             showAlert("Calculation Error", "Invalid time format. Use HH:MM (e.g., 14:30).", Alert.AlertType.ERROR);
//         }
//     }

//     @FXML
//     private void confirmBooking() {
//         try {
//             // Ensure total is calculated
//             if (txtTotalPrice.getText().isEmpty()) {
//                 calculateTotal();
//                 if (txtTotalPrice.getText().isEmpty()) {
//                     showAlert("Missing Total", "Please click 'Calculate Total' first.", Alert.AlertType.WARNING);
//                     return;
//                 }
//             }

//             // Format date and times for database
//             String eventDate = dpEventDate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
//             String startTime = txtStartTime.getText().trim() + ":00";   // add seconds
//             String endTime = txtEndTime.getText().trim() + ":00";

//             // Availability check
//             if (!bookingDAO.isVenueAvailable(selectedVenue.getVenueId(), eventDate, startTime, endTime)) {
//                 showAlert("Venue Unavailable",
//                         "This venue is already booked during the selected time frame.\nPlease choose a different time or date.",
//                         Alert.AlertType.ERROR);
//                 return;
//             }

//             // Create booking
//             Booking booking = new Booking();
//             booking.setUserId(UserSession.getInstance().getUserId());
//             booking.setVenueId(selectedVenue.getVenueId());
//             booking.setTypeId(cbEventType.getValue().getTypeId());
//             booking.setEventDate(eventDate);
//             booking.setStartTime(startTime);
//             booking.setEndTime(endTime);
//             booking.setTotalPrice(Double.parseDouble(txtTotalPrice.getText()));
//             booking.setStatus("Confirmed");

//             int bookingId = bookingDAO.addBooking(booking);
//             if (bookingId > 0) {
//                 // Save selected services
//                 for (Service s : listServices.getSelectionModel().getSelectedItems()) {
//                     BookingService bs = new BookingService(bookingId, s.getServiceId(), 1,
//                             s.getPricePerUnit(), s.getPricePerUnit());
//                     bookingServicesDAO.addBookingService(bs);
//                 }
//                 showAlert("Success", "Booking created successfully! ID: " + bookingId, Alert.AlertType.INFORMATION);
//                 ((Stage) txtStartTime.getScene().getWindow()).close();
//             } else {
//                 showAlert("Error", "Failed to save booking in database.", Alert.AlertType.ERROR);
//             }
//         } catch (Exception ex) {
//             ex.printStackTrace();
//             showAlert("Booking Error", "An error occurred: " + ex.getMessage(), Alert.AlertType.ERROR);
//         }
//     }

//     private void showAlert(String title, String content, Alert.AlertType type) {
//         Alert alert = new Alert(type);
//         alert.setTitle(title);
//         alert.setHeaderText(null);
//         alert.setContentText(content);
//         alert.showAndWait();
//     }
// }

package Controller;

import DAO.*;
import Model.*;
import Util.UserSession;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class FXBookingCreateController {

    @FXML private Label lblVenueName, lblVenueLocation, lblVenueCapacity, lblVenueRate;
    @FXML private ComboBox<EventType> cbEventType;
    @FXML private DatePicker dpEventDate;
    @FXML private TextField txtStartTime, txtEndTime;
    @FXML private ListView<Service> listServices;
    @FXML private TextField txtTotalPrice;
    @FXML private Button btnCalculate, btnConfirm;

    private Venue selectedVenue;
    private BookingDAO bookingDAO;
    private BookingServicesDAO bookingServicesDAO;

    @FXML
    public void initialize() {
        bookingDAO = new BookingDAOImp();
        bookingServicesDAO = new BookingServicesDAOImp();

        loadEventTypes();
        loadServices();

        // Enable multiple selection
        listServices.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        // Custom cell display for services
        listServices.setCellFactory(lv -> new ListCell<Service>() {
            @Override
            protected void updateItem(Service s, boolean empty) {
                super.updateItem(s, empty);
                setText(empty || s == null ? "" : s.getServiceName() + "  -  $" + s.getPricePerUnit());
            }
        });

        // Auto‑recalculate when inputs change
        cbEventType.valueProperty().addListener((obs, old, val) -> calculateTotal());
        dpEventDate.valueProperty().addListener((obs, old, val) -> calculateTotal());
        txtStartTime.textProperty().addListener((obs, old, val) -> calculateTotal());
        txtEndTime.textProperty().addListener((obs, old, val) -> calculateTotal());
        listServices.getSelectionModel().selectedItemProperty().addListener((obs, old, val) -> calculateTotal());

        // Manual calculation button (redundant but kept for user comfort)
        btnCalculate.setOnAction(e -> calculateTotal());
        btnConfirm.setOnAction(e -> confirmBooking());
    }

    public void setVenueData(Venue venue) {
        this.selectedVenue = venue;
        lblVenueName.setText(venue.getName());
        lblVenueLocation.setText(venue.getLocation());
        lblVenueCapacity.setText(String.valueOf(venue.getCapacity()));
        lblVenueRate.setText(String.format("$%.2f / hr", venue.getHourlyRate()));
        calculateTotal(); // initial calculation
    }

    private void loadEventTypes() {
        EventTypeDAO eventTypeDAO = new EventTypeDAOImp();
        cbEventType.setItems(FXCollections.observableArrayList(eventTypeDAO.getAllEventTypes()));
        cbEventType.setConverter(new javafx.util.StringConverter<EventType>() {
            @Override public String toString(EventType et) { return et == null ? "" : et.getName(); }
            @Override public EventType fromString(String s) { return null; }
        });
    }

    private void loadServices() {
        ServiceDAO serviceDAO = new ServiceDAOImp();
        List<Service> services = serviceDAO.getAllServices();
        listServices.setItems(FXCollections.observableArrayList(services));
    }

    @FXML
    private void calculateTotal() {
        if (selectedVenue == null || cbEventType.getValue() == null || dpEventDate.getValue() == null) {
            txtTotalPrice.clear();
            return;
        }

        try {
            String start = txtStartTime.getText().trim();
            String end = txtEndTime.getText().trim();
            if (start.isEmpty() || end.isEmpty()) {
                txtTotalPrice.clear();
                return;
            }

            LocalTime startTime = LocalTime.parse(start, DateTimeFormatter.ofPattern("HH:mm"));
            LocalTime endTime = LocalTime.parse(end, DateTimeFormatter.ofPattern("HH:mm"));

            // Duration calculation (overnight allowed)
            double startHours = startTime.getHour() + startTime.getMinute() / 60.0;
            double endHours = endTime.getHour() + endTime.getMinute() / 60.0;
            double duration = endHours - startHours;
            if (duration <= 0) duration += 24;
            if (duration <= 0) {
                txtTotalPrice.setText("Invalid time");
                return;
            }

            double venueCost = selectedVenue.getHourlyRate() * duration;
            double servicesCost = listServices.getSelectionModel().getSelectedItems().stream()
                    .mapToDouble(Service::getPricePerUnit).sum();
            double total = venueCost + servicesCost;

            txtTotalPrice.setText(String.format("%.2f", total));
        } catch (Exception e) {
            txtTotalPrice.setText("Format error");
        }
    }

    @FXML
    private void confirmBooking() {
        // Validate total
        if (txtTotalPrice.getText().isEmpty() || txtTotalPrice.getText().equals("Format error") || txtTotalPrice.getText().equals("Invalid time")) {
            calculateTotal();
            if (txtTotalPrice.getText().isEmpty() || txtTotalPrice.getText().equals("Format error") || txtTotalPrice.getText().equals("Invalid time")) {
                showAlert("Missing Information", "Please enter valid start/end times (HH:MM) and select an event type.", Alert.AlertType.WARNING);
                return;
            }
        }

        try {
            String eventDate = dpEventDate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String startTime = txtStartTime.getText().trim() + ":00";
            String endTime = txtEndTime.getText().trim() + ":00";

            // Availability check
            if (!bookingDAO.isVenueAvailable(selectedVenue.getVenueId(), eventDate, startTime, endTime)) {
                showAlert("Venue Unavailable",
                        "This venue is already booked during the selected time.\nPlease choose a different time or date.",
                        Alert.AlertType.ERROR);
                return;
            }

            // Create booking
            Booking booking = new Booking();
            booking.setUserId(UserSession.getInstance().getUserId());
            booking.setVenueId(selectedVenue.getVenueId());
            booking.setTypeId(cbEventType.getValue().getTypeId());
            booking.setEventDate(eventDate);
            booking.setStartTime(startTime);
            booking.setEndTime(endTime);
            booking.setTotalPrice(Double.parseDouble(txtTotalPrice.getText()));
            booking.setStatus("Confirmed");

            int bookingId = bookingDAO.addBooking(booking);
            if (bookingId > 0) {
                for (Service s : listServices.getSelectionModel().getSelectedItems()) {
                    BookingService bs = new BookingService(bookingId, s.getServiceId(), 1,
                            s.getPricePerUnit(), s.getPricePerUnit());
                    bookingServicesDAO.addBookingService(bs);
                }
                showAlert("Success", "Booking created successfully!\nBooking ID: " + bookingId, Alert.AlertType.INFORMATION);
                ((Stage) txtStartTime.getScene().getWindow()).close();
            } else {
                showAlert("Database Error", "Failed to save booking. Please try again.", Alert.AlertType.ERROR);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            showAlert("Error", "An unexpected error occurred: " + ex.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void showAlert(String title, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}