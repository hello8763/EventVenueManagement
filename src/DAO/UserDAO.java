package DAO;

import Model.User;
import Model.Profile;

public interface UserDAO {
    String[] authenticateUser(String email, String password);
    boolean registerCustomer(User user, Profile profile);
}