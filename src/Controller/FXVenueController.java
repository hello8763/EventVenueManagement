package Controller;

import DAO.VenueDAO;
import DAO.VenueDAOImp;
import Model.Venue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.List;

public class FXVenueController {

    @FXML private TextField txtVenueId, txtName, txtLocation, txtCapacity, txtHourlyRate, txtStatus;
    @FXML private TableView<Venue> tblVenues;
    @FXML private TableColumn<Venue, Integer> colId;
    @FXML private TableColumn<Venue, String> colName;
    @FXML private TableColumn<Venue, String> colLocation;
    @FXML private TableColumn<Venue, Integer> colCapacity;
    @FXML private TableColumn<Venue, Double> colHourlyRate;
    @FXML private TableColumn<Venue, String> colStatus;

    private VenueDAO dao = new VenueDAOImp();

    @FXML
    public void initialize() {
        // Map the model fields to table view columns
        colId.setCellValueFactory(new PropertyValueFactory<>("venueId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        colCapacity.setCellValueFactory(new PropertyValueFactory<>("capacity"));
        colHourlyRate.setCellValueFactory(new PropertyValueFactory<>("hourlyRate"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        loadVenues();
        
        // Add listener for row selection to populate fields automatically
        tblVenues.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                populateFields(newSelection);
            }
        });
    }

    private void loadVenues() {
        List<Venue> venues = dao.getAllVenues();
        tblVenues.setItems(FXCollections.observableArrayList(venues));
    }

    private void populateFields(Venue venue) {
        txtVenueId.setText(String.valueOf(venue.getVenueId()));
        txtName.setText(venue.getName());
        txtLocation.setText(venue.getLocation());
        txtCapacity.setText(String.valueOf(venue.getCapacity()));
        txtHourlyRate.setText(String.valueOf(venue.getHourlyRate()));
        txtStatus.setText(venue.getStatus());
    }

    @FXML
    private void handleAdd() {
        try {
            Venue venue = new Venue();
            venue.setName(txtName.getText());
            venue.setLocation(txtLocation.getText());
            venue.setCapacity(Integer.parseInt(txtCapacity.getText()));
            venue.setHourlyRate(Double.parseDouble(txtHourlyRate.getText()));
            venue.setStatus(txtStatus.getText());

            if (dao.addVenue(venue)) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Venue saved successfully!");
                loadVenues();
                clearForm();
            }
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Data Entry Error", "Please verify your numeric inputs.");
        }
    }

    @FXML
    private void handleUpdate() {
        try {
            Venue venue = new Venue();
            venue.setVenueId(Integer.parseInt(txtVenueId.getText()));
            venue.setName(txtName.getText());
            venue.setLocation(txtLocation.getText());
            venue.setCapacity(Integer.parseInt(txtCapacity.getText()));
            venue.setHourlyRate(Double.parseDouble(txtHourlyRate.getText()));
            venue.setStatus(txtStatus.getText());

            if (dao.updateVenue(venue)) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Venue updated successfully!");
                loadVenues();
            }
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Update Failed", "Ensure valid structural inputs and a correct ID mapping.");
        }
    }

    @FXML
    private void handleDelete() {
        try {
            int id = Integer.parseInt(txtVenueId.getText());
            if (dao.deleteVenue(id)) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Venue deleted successfully.");
                loadVenues();
                clearForm();
            }
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Deletion Error", "Invalid target identification key.");
        }
    }

    @FXML
    private void handleSearch() {
        try {
            int id = Integer.parseInt(txtVenueId.getText());
            Venue venue = dao.searchVenueById(id);
            if (venue != null) {
                populateFields(venue);
                tblVenues.getSelectionModel().select(venue);
            } else {
                showAlert(Alert.AlertType.WARNING, "Not Found", "No venue matches the provided ID.");
            }
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Search Error", "Provide a valid numeric query ID.");
        }
    }

    private void clearForm() {
        txtVenueId.clear();
        txtName.clear();
        txtLocation.clear();
        txtCapacity.clear();
        txtHourlyRate.clear();
        txtStatus.clear();
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}