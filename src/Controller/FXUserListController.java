package Controller;

import DAO.UserDAO;
import DAO.UserDAOImp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class FXUserListController {

    private UserDAO dao;

    @FXML private TableView<UserRowModel> table;
    @FXML private TableColumn<UserRowModel, Integer> colUserId;
    @FXML private TableColumn<UserRowModel, String> colFullName, colEmail, colPhone, colRole, colAddress;

    @FXML
    public void initialize() {
        this.dao = new UserDAOImp();

        // Configure type-safe cell value factories utilizing structural mapping metadata
        colUserId.setCellValueFactory(new PropertyValueFactory<>("userId"));
        colFullName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        colRole.setCellValueFactory(new PropertyValueFactory<>("role"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));

        loadUsers();
    }

    @FXML
    private void loadUsers() {
        ObservableList<UserRowModel> rowDataList = FXCollections.observableArrayList();
        List<Object[]> rawUserPayload = dao.getAllUsersWithProfiles();

        for (Object[] obj : rawUserPayload) {
            if (obj != null && obj.length >= 6) {
                rowDataList.add(new UserRowModel(
                        (Integer) obj[0],
                        String.valueOf(obj[1]),
                        String.valueOf(obj[2]),
                        String.valueOf(obj[3]),
                        String.valueOf(obj[4]),
                        String.valueOf(obj[5])
                ));
            }
        }
        table.setItems(rowDataList);
    }

    /**
     * Immutable UI Projection Model mapping internal database tuple transformations cleanly 
     * without introducing overhead, boilerplate, or external dependencies.
     */
    public static class UserRowModel {
        private final int userId;
        private final String fullName;
        private final String email;
        private final String phone;
        private final String role;
        private final String address;

        public UserRowModel(int userId, String fullName, String email, String phone, String role, String address) {
            this.userId = userId;
            this.fullName = fullName;
            this.email = email;
            this.phone = phone;
            this.role = role;
            this.address = address;
        }

        public int getUserId() { return userId; }
        public String getFullName() { return fullName; }
        public String getEmail() { return email; }
        public String getPhone() { return phone; }
        public String getRole() { return role; }
        public String getAddress() { return address; }
    }
}