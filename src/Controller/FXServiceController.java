package Controller;

import DAO.ServiceDAO;
import DAO.ServiceDAOImp;
import Model.Service;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.List;

public class FXServiceController {

    @FXML private TextField txtServiceId, txtServiceName, txtPricePerUnit;
    @FXML private TextArea txtDescription;
    @FXML private CheckBox chkStatus;
    @FXML private TableView<Service> tblServices;
    @FXML private TableColumn<Service, Integer> colId;
    @FXML private TableColumn<Service, String> colName;
    @FXML private TableColumn<Service, String> colDescription;
    @FXML private TableColumn<Service, Double> colPrice;
    @FXML private TableColumn<Service, Boolean> colStatus;

    private ServiceDAO dao = new ServiceDAOImp();

    @FXML
    public void initialize() {
        // Map database entities onto standard JavaFX visual nodes explicitly
        colId.setCellValueFactory(new PropertyValueFactory<>("serviceId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("serviceName"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("pricePerUnit"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        loadServices();

        // Listen for row selections to load them into the form fields automatically
        tblServices.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                populateFields(newSel);
            }
        });
    }

    private void loadServices() {
        List<Service> services = dao.getAllServices();
        tblServices.setItems(FXCollections.observableArrayList(services));
    }

    private void populateFields(Service service) {
        txtServiceId.setText(String.valueOf(service.getServiceId()));
        txtServiceName.setText(service.getServiceName());
        txtDescription.setText(service.getDescription());
        txtPricePerUnit.setText(String.valueOf(service.getPricePerUnit()));
        chkStatus.setSelected(service.isStatus());
    }

    @FXML
    private void handleAdd() {
        try {
            Service service = new Service();
            service.setServiceName(txtServiceName.getText());
            service.setDescription(txtDescription.getText());
            service.setPricePerUnit(Double.parseDouble(txtPricePerUnit.getText()));
            service.setStatus(chkStatus.isSelected());

            if (dao.addService(service)) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Service saved into database catalog.");
                loadServices();
                clearForm();
            }
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Execution Error", "Verify currency input format constraints.");
        }
    }

    @FXML
    private void handleUpdate() {
        try {
            Service service = new Service();
            service.setServiceId(Integer.parseInt(txtServiceId.getText()));
            service.setServiceName(txtServiceName.getText());
            service.setDescription(txtDescription.getText());
            service.setPricePerUnit(Double.parseDouble(txtPricePerUnit.getText()));
            service.setStatus(chkStatus.isSelected());

            if (dao.updateService(service)) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Service configurations modified successfully.");
                loadServices();
            }
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Update Failed", "Verify indexing target parameters.");
        }
    }

    @FXML
    private void handleDelete() {
        try {
            int id = Integer.parseInt(txtServiceId.getText());
            if (dao.deleteService(id)) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Target catalog option removed cleanly.");
                loadServices();
                clearForm();
            }
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Deletion Failed", "Verify ID target keys mapping parameters.");
        }
    }

    @FXML
    private void handleSearch() {
        try {
            int id = Integer.parseInt(txtServiceId.getText());
            Service service = dao.searchServiceById(id);
            if (service != null) {
                populateFields(service);
                tblServices.getSelectionModel().select(service);
            } else {
                showAlert(Alert.AlertType.WARNING, "Not Found", "No registered match item matches ID value.");
            }
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Search Fault", "ID inputs must be numeric entries only.");
        }
    }

    private void clearForm() {
        txtServiceId.clear();
        txtServiceName.clear();
        txtDescription.clear();
        txtPricePerUnit.clear();
        chkStatus.setSelected(false);
    }

    private void showAlert(Alert.AlertType type, String title, String contents) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(contents);
        alert.showAndWait();
    }
}