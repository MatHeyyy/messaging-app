import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Handles profile edits made in the edit-profile dialog.
 */
public class EditProfileController {

    @FXML private TextField usernameField;
    @FXML private TextField statusField;
    @FXML private Button saveBtn;

    /**
     * Binds the Save button action to persist profile changes.
     */
    @FXML
    public void initialize() {
        saveBtn.setOnAction(e -> saveProfile());
    }

    /**
     * Pushes profile changes to the landing page controller and closes the dialog.
     */
    private void saveProfile() {
        String newName = usernameField.getText().trim();
        String newStatus = statusField.getText().trim();

        LandingPageController.updateProfile(newName, newStatus);

        Stage stage = (Stage) saveBtn.getScene().getWindow();
        stage.close();
    }
}