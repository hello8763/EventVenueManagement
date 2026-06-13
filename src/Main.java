import Controller.LoginController;
import View.LoginView;

public class Main {
    public static void main(String[] args) {
        // Start the application at the Login View
        LoginView loginWindow = new LoginView();
        
        // Attach the logic to the view
        new LoginController(loginWindow);
        
        // Display it
        loginWindow.setVisible(true);
    }
}