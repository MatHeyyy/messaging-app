import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditProfileController {

    @FXML private TextField usernameField;
    @FXML private TextField statusField;
    @FXML private Button saveBtn;

    @FXML
    public void initialize() {
        saveBtn.setOnAction(e -> saveProfile());
    }

    private void saveProfile() {
        String newName = usernameField.getText().trim();
        String newStatus = statusField.getText().trim();

        LandingPageController.updateProfile(newName, newStatus);

        Stage stage = (Stage) saveBtn.getScene().getWindow();
        stage.close();
    }
}