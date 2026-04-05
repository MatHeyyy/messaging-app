import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.*;

/**
 * LandingPageController - Main landing page of the ChatApp.
 * UI/UX
 * Edit Profile, name and status.
 */
public class LandingPageController {

    @FXML private Label profileName;
    @FXML private Button editProfileBtn;
    @FXML private Button chatsBtn;
    @FXML private Button contactsBtn;
    @FXML private Button newChatBtn;
    @FXML private TextField searchField;
    @FXML private VBox recentChatsContainer;

    private ChatController chatController;

    // Edit Profile updates
    private static Label profileNameLabel;
    private static String currentUsername = "";
    private static String currentStatus = "";

    // Frequency counter for recent chats
    private final Map<String, Integer> openFrequency = new HashMap<>();

    @FXML
    public void initialize() {
        chatController = new ChatController();

        DataManager dm = new DataManager();
        dm.loadData(chatController);

        if (chatController.getCurrentUser() != null) {
            currentUsername = chatController.getCurrentUser().getUsername();
        }

        profileNameLabel = profileName;
        updateProfileDisplay();

        editProfileBtn.setOnAction(e -> openEditProfileWindow());
        chatsBtn.setOnAction(e -> openAllChatsWindow());
        contactsBtn.setOnAction(e -> openContactsWindow());
        newChatBtn.setOnAction(e -> openNewChatWindow());

        searchField.setOnAction(e -> {
            String keyword = searchField.getText().trim();
            if (!keyword.isEmpty()) {
                var results = chatController.searchAllChats(keyword);
                System.out.println("Found " + results.size() + " results for: " + keyword);
            }
        });

        loadRecentChatsByFrequency();
    }

    private void loadRecentChatsByFrequency() {
        recentChatsContainer.getChildren().clear();

        List<String> contacts = Arrays.asList("Alice Johnson", "Bob Smith", "Emma Davis");

        contacts.sort((a, b) -> openFrequency.getOrDefault(b, 0).compareTo(openFrequency.getOrDefault(a, 0)));

        for (String contactName : contacts) {
            Button btn = new Button("Recent chat with " + contactName);
            btn.setPrefWidth(680);
            btn.setStyle("-fx-padding: 15; -fx-background-color: #2A3942; -fx-text-fill: white; -fx-font-size: 15;");
            btn.setOnAction(e -> {
                openFrequency.put(contactName, openFrequency.getOrDefault(contactName, 0) + 1);
                openChatWindow(contactName);
                loadRecentChatsByFrequency();
            });
            recentChatsContainer.getChildren().add(btn);
        }
    }

    private void openChatWindow(String contactName) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ChatWindow.fxml"));
            Scene scene = new Scene(loader.load(), 700, 600);

            Stage stage = new Stage();
            stage.setTitle("Chat with " + contactName);
            stage.setScene(scene);
            stage.show();

            ChatWindowController controller = loader.getController();
            controller.setChatTitle(contactName);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void openEditProfileWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("EditProfile.fxml"));
            Scene scene = new Scene(loader.load(), 450, 320);
            Stage stage = new Stage();
            stage.setTitle("Edit Profile");
            stage.setScene(scene);
            stage.show();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void openAllChatsWindow() {
        try {
            VBox vbox = new VBox(15);
            vbox.setStyle("-fx-padding: 20; -fx-background-color: #111B21;");

            Label title = new Label("All Chats");
            title.setStyle("-fx-font-size: 20; -fx-text-fill: white; -fx-font-weight: bold;");

            Button chat1 = new Button("💬 Chat with Alice Johnson");
            Button chat2 = new Button("💬 Chat with Bob Smith");
            Button chat3 = new Button("💬 Chat with Emma Davis");

            String style = "-fx-background-color: #2A3942; -fx-text-fill: white; -fx-font-size: 16; -fx-padding: 15 30; -fx-background-radius: 10;";

            chat1.setStyle(style);
            chat2.setStyle(style);
            chat3.setStyle(style);

            chat1.setPrefWidth(400);
            chat2.setPrefWidth(400);
            chat3.setPrefWidth(400);

            chat1.setOnAction(e -> openChatWindow("Alice Johnson"));
            chat2.setOnAction(e -> openChatWindow("Bob Smith"));
            chat3.setOnAction(e -> openChatWindow("Emma Davis"));

            vbox.getChildren().addAll(title, chat1, chat2, chat3);

            Scene scene = new Scene(vbox, 450, 320);
            Stage stage = new Stage();
            stage.setTitle("All Chats");
            stage.setScene(scene);
            stage.show();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void openContactsWindow() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Contacts");
        alert.setHeaderText("Your Contacts");
        alert.setContentText("1. Alice Johnson\n2. Bob Smith\n3. Emma Davis");
        alert.showAndWait();
    }

    private void openNewChatWindow() {
        ChoiceDialog<String> dialog = new ChoiceDialog<>("Alice Johnson",
                "Alice Johnson", "Bob Smith", "Emma Davis");
        dialog.setTitle("New Chat");
        dialog.setHeaderText("Choose a contact to start a new chat with");
        dialog.setContentText("Select contact:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(this::openChatWindow);
    }

    /**
     * Updates username and status from Edit Profile window
     */
    public static void updateProfile(String newName, String newStatus) {
        if (!newName.isEmpty()) currentUsername = newName;
        currentStatus = newStatus;
        updateProfileDisplay();
    }

    private static void updateProfileDisplay() {
        if (profileNameLabel != null) {
            String name = currentUsername.isEmpty() ? "Guest" : currentUsername;
            if (currentStatus.isEmpty()) {
                profileNameLabel.setText("👤 " + name);
            } else {
                profileNameLabel.setText("👤 " + name + " (" + currentStatus + ")");
            }
        }
    }
}