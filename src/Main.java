// import Controller.LoginController;
// import View.LoginView;

// public class Main {
//     public static void main(String[] args) {
//         // Start the application at the Login View
//         LoginView loginWindow = new LoginView();
        
//         // Attach the logic to the view
//         new LoginController(loginWindow);
        
//         // Display it
//         loginWindow.setVisible(true);
//     }
// }

import Controller.LoginController;
import View.LoginView;
import javafx.application.Platform;

public class Main {
    public static void main(String[] args) {
        // Pre-start the JavaFX toolkit and prevent it from shutting down
        new Thread(() -> {
            Platform.startup(() -> {
                // THE FIX: Tell JavaFX to stay alive in the background
                Platform.setImplicitExit(false); 
            });
        }).start();

        // Start the Swing login as before
        LoginView loginWindow = new LoginView();
        new LoginController(loginWindow);
        loginWindow.setVisible(true);
    }
}