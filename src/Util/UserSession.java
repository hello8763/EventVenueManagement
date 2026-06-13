package Util;

public class UserSession {
    private static volatile UserSession instance;
    private int userId;
    private String email;
    private String role;

    private UserSession() {}

    public static UserSession getInstance() {
        if (instance == null) {
            synchronized (UserSession.class) {
                if (instance == null) {
                    instance = new UserSession();
                }
            }
        }
        return instance;
    }

    public void loginUser(int userId, String email, String role) {
        this.userId = userId;
        this.email = email;
        this.role = role;
    }

    public void logout() {
        this.userId = 0;
        this.email = null;
        this.role = null;
    }

    public int getUserId() { return userId; }
    public String getEmail() { return email; }
    public String getRole() { return role; }
}