package Controller;

import DAO.VenueDAO;
import DAO.VenueDAOImp;
import Model.Venue;
import View.BookingCreatePage;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javax.swing.*;

public class FXCustomerVenueController {

    @FXML private TableView<Venue> venueTable;
    @FXML private TableColumn<Venue, Integer> colId;
    @FXML private TableColumn<Venue, String> colName;
    @FXML private TableColumn<Venue, String> colLocation;
    @FXML private TableColumn<Venue, Integer> colCapacity;
    @FXML private TableColumn<Venue, Double> colRate;
    @FXML private TableColumn<Venue, String> colStatus;
    @FXML private Button btnBook;

    private VenueDAO venueDAO;
    private JFrame parentDashboard;

    public void setDashboardParent(JFrame dashboard) {
        this.parentDashboard = dashboard;
    }

    @FXML
    public void initialize() {
        venueDAO = new VenueDAOImp();
        colId.setCellValueFactory(new PropertyValueFactory<>("venueId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        colCapacity.setCellValueFactory(new PropertyValueFactory<>("capacity"));
        colRate.setCellValueFactory(new PropertyValueFactory<>("hourlyRate"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        loadVenues();
    }

    private void loadVenues() {
        venueTable.setItems(FXCollections.observableArrayList(venueDAO.getAllVenues()));
    }

@FXML
    private void handleBookVenue() {
        Venue selectedVenue = venueTable.getSelectionModel().getSelectedItem();
        if (selectedVenue == null) {
            showAlert("Selection Required", "Please select a venue from the table to book.");
            return;
        }
        
        try {
            // 1. Explicitly check if NetBeans packaged the FXML file
            java.net.URL fxmlLocation = getClass().getResource("/View/BookingCreate.fxml");
            if (fxmlLocation == null) {
                showAlert("NetBeans Build Error", 
                          "NetBeans did not move the FXML file to the build folder!\n\n" +
                          "Please right-click your project and select 'Clean and Build'.");
                return;
            }

            // 2. Load the layout
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(fxmlLocation);
            javafx.scene.Parent root = loader.load();
            
            // 3. Hand over the venue data to the new screen
            FXBookingCreateController formController = loader.getController();
            formController.setVenueData(selectedVenue);
            
            // 4. Show the window
            javafx.stage.Stage stage = new javafx.stage.Stage();
            stage.setTitle("New Booking Form");
            stage.setScene(new javafx.scene.Scene(root, 800, 500));
            stage.show();
            
        } catch (Exception e) {
            e.printStackTrace();
            // Show the ACTUAL Java error on the screen so you don't have to dig through the console
            showAlert("Navigation Crash", "System Error: " + e.getMessage());
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}