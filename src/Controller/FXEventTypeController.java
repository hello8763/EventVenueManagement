package Controller;

import DAO.EventTypeDAO;
import DAO.EventTypeDAOImp;
import Model.EventType;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class FXEventTypeController {

    private EventTypeDAO dao;

    @FXML private TextField txtId, txtName, txtDesc, txtDeposit;
    @FXML private TableView<EventType> table;
    @FXML private TableColumn<EventType, Integer> colId;
    @FXML private TableColumn<EventType, String> colName, colDesc;
    @FXML private TableColumn<EventType, Double> colDeposit;

    @FXML
    public void initialize() {
        this.dao = new EventTypeDAOImp();

        // Bind columns directly to EventType structural properties
        colId.setCellValueFactory(new PropertyValueFactory<>("typeId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colDesc.setCellValueFactory(new PropertyValueFactory<>("description"));
        colDeposit.setCellValueFactory(new PropertyValueFactory<>("baseDeposit"));

        // Visual enhancement: row-click to auto-populate structural elements
        table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                populateFields(newSelection);
            }
        });

        loadTable();
    }

    private void populateFields(EventType et) {
        txtId.setText(String.valueOf(et.getTypeId()));
        txtName.setText(et.getName());
        txtDesc.setText(et.getDescription());
        txtDeposit.setText(String.valueOf(et.getBaseDeposit()));
    }

    private void loadTable() {
        try {
            table.setItems(FXCollections.observableArrayList(dao.getAllEventTypes()));
        } catch (Exception ex) {
            showAlert(Alert.AlertType.ERROR, "Database Error", "Could not synchronize data grid.");
        }
    }

    @FXML
    private void add() {
        try {
            if (txtName.getText().trim().isEmpty()) {
                showAlert(Alert.AlertType.WARNING, "Validation Error", "Event name cannot be left blank.");
                return;
            }

            EventType et = new EventType();
            et.setName(txtName.getText().trim());
            et.setDescription(txtDesc.getText().trim());
            et.setBaseDeposit(Double.parseDouble(txtDeposit.getText().trim()));

            if (dao.addEventType(et)) {
                showAlert(Alert.AlertType.INFORMATION, "Execution Complete", "Event Type registered successfully.");
                loadTable();
                clearInputFields();
            }
        } catch (NumberFormatException ex) {
            showAlert(Alert.AlertType.WARNING, "Type Validation", "Please supply a clean decimal value for structural pricing deposit elements.");
        }
    }

    @FXML
    private void update() {
        try {
            EventType et = new EventType();
            et.setTypeId(Integer.parseInt(txtId.getText().trim()));
            et.setName(txtName.getText().trim());
            et.setDescription(txtDesc.getText().trim());
            et.setBaseDeposit(Double.parseDouble(txtDeposit.getText().trim()));

            if (dao.updateEventType(et)) {
                showAlert(Alert.AlertType.INFORMATION, "Execution Complete", "Event Type layout adjustments processed successfully.");
                loadTable();
            } else {
                showAlert(Alert.AlertType.ERROR, "System Error", "Failed to update record mapping parameters.");
            }
        } catch (NumberFormatException ex) {
            showAlert(Alert.AlertType.WARNING, "Type Validation", "Verify index configuration fields follow technical variable boundaries.");
        }
    }

    @FXML
    private void delete() {
        try {
            int id = Integer.parseInt(txtId.getText().trim());
            if (dao.deleteEventType(id)) {
                showAlert(Alert.AlertType.INFORMATION, "Execution Complete", "Event Type structure successfully cleared.");
                loadTable();
                clearInputFields();
            } else {
                showAlert(Alert.AlertType.ERROR, "System Error", "Unable to purge target profile mapping registry link.");
            }
        } catch (NumberFormatException ex) {
            showAlert(Alert.AlertType.WARNING, "Type Validation", "Numeric operational reference index parsing failed.");
        }
    }

    @FXML
    private void search() {
        try {
            int id = Integer.parseInt(txtId.getText().trim());
            EventType et = dao.searchEventTypeById(id);
            if (et != null) {
                populateFields(et);
            } else {
                showAlert(Alert.AlertType.INFORMATION, "System Tracking", "Target identifier not located inside active indices.");
            }
        } catch (NumberFormatException ex) {
            showAlert(Alert.AlertType.WARNING, "Type Validation", "Invalid target identity trace argument format.");
        }
    }

    private void clearInputFields() {
        txtId.clear();
        txtName.clear();
        txtDesc.clear();
        txtDeposit.clear();
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}