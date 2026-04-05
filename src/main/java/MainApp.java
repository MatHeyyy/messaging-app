import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * JavaFX application entry point that loads the landing page UI.
 */
public class MainApp extends Application {

    /**
     * Creates and shows the primary stage containing the landing page scene.
     *
     * @param primaryStage primary JavaFX window provided by the runtime
     * @throws Exception when the FXML resource cannot be loaded
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        // landing page
        FXMLLoader loader = new FXMLLoader(getClass().getResource("LandingPage.fxml"));
        Scene scene = new Scene(loader.load(), 950, 650);

        primaryStage.setTitle("ChatApp");
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(950);
        primaryStage.setMinHeight(650);
        primaryStage.show();
    }

    /**
     * Launches the JavaFX application.
     *
     * @param args command-line arguments passed to the JavaFX launcher
     */
    public static void main(String[] args) {
        launch(args);
    }
}