package DAO;

import Model.User;
import Model.Profile;
import java.util.List;

public interface UserDAO {
    String[] authenticateUser(String email, String password);
    boolean registerCustomer(User user, Profile profile);
    // Add this inside the interface:
List<Object[]> getAllUsersWithProfiles();
}