package Model;

import java.sql.Timestamp;

public class Admin extends User {

    public Admin() {
        super();
    }

    public Admin(int userId, String email, String passwordHash, boolean isActive, Timestamp createdAt) {
        super(userId, email, passwordHash, isActive, createdAt);
    }

    @Override
    public String getRole() {
        return "Admin";
    }
}