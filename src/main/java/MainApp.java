import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

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

    public static void main(String[] args) {
        launch(args);
    }
}