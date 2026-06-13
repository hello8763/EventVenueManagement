package Model;

import java.sql.Timestamp;

public class Customer extends User {
    
    public Customer() {
        super();
    }

    public Customer(int userId, String email, String passwordHash, boolean isActive, Timestamp createdAt) {
        super(userId, email, passwordHash, isActive, createdAt);
    }

    @Override
    public String getRole() {
        return "Customer";
    }
}