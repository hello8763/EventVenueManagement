
// import Controller.LoginController;
// import View.LoginView;
// import javafx.application.Platform;

// public class Main {
//     public static void main(String[] args) {
//         // Pre-start the JavaFX toolkit and prevent it from shutting down
//         new Thread(() -> {
//             Platform.startup(() -> {
//                 // THE FIX: Tell JavaFX to stay alive in the background
//                 Platform.setImplicitExit(false); 
//             });
//         }).start();

//         // Start the Swing login as before
//         LoginView loginWindow = new LoginView();
//         new LoginController(loginWindow);
//         loginWindow.setVisible(true);
//     }
// }


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // Load your brand new beautiful modern login UI view frame layout immediately 
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/FXLogin.fxml"));
            Parent root = loader.load();
            
            primaryStage.setTitle("Event Venue Management System - Sign In");
            primaryStage.setScene(new Scene(root, 420, 480));
            primaryStage.setResizable(false);
            primaryStage.show();
            
        } catch (Exception e) {
            System.err.println("CRITICAL: Failed to launch modern JavaFX application application UI environment matrix context.");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // Safe programmatic native runtime bootstrap execution launchpad
        launch(args);
    }
}