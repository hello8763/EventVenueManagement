package Controller;

import DAO.UserDAO;
import DAO.UserDAOImp;
import View.UserListPage;
import java.util.List;

public class UserListController {
    private UserListPage view;
    private UserDAO dao;

    public UserListController(UserListPage view) {
        this.view = view;
        this.dao = new UserDAOImp();
        loadUsers();
    }

    private void loadUsers() {
        view.model.setRowCount(0);
        List<Object[]> users = dao.getAllUsersWithProfiles();
        for (Object[] user : users) {
            view.model.addRow(user);
        }
    }
}