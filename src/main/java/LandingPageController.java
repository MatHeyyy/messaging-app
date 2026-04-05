import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.*;

/**
 * Controls the landing page view and top-level navigation actions.
 *
 * <p>This controller wires button actions, profile display state, chat search,
 * and a simple recent-chat list ordered by how often each chat is opened.</p>
 */
public class LandingPageController {

    @FXML private Label profileName;
    @FXML private Button editProfileBtn;
    @FXML private Button chatsBtn;
    @FXML private Button contactsBtn;
    @FXML private Button addContactBtn;
    @FXML private Button newChatBtn;
    @FXML private TextField searchField;
    @FXML private Label recentChatsTitleLabel;
    @FXML private VBox recentChatsContainer;

    private ChatController chatController;

    // Edit Profile updates
    private static Label profileNameLabel;
    private static String currentUsername = "";
    private static String currentStatus = "";

    // Frequency counter for recent chats
    private final Map<String, Integer> openFrequency = new HashMap<>();
    private String activeSearchQuery = "";

    /**
     * Initializes UI bindings, restores persisted data, and populates recent chats.
     */
    @FXML
    public void initialize() {
        chatController = new ChatController();

        DataManager dm = new DataManager();
        dm.loadData(chatController);
        ensureDefaultContacts();

        if (chatController.getCurrentUser() != null) {
            currentUsername = chatController.getCurrentUser().getUsername();
        }

        profileNameLabel = profileName;
        updateProfileDisplay();

        editProfileBtn.setOnAction(e -> openEditProfileWindow());
        chatsBtn.setOnAction(e -> openAllChatsWindow());
        contactsBtn.setOnAction(e -> openContactsWindow());
        addContactBtn.setOnAction(e -> openAddContactDialog());
        newChatBtn.setOnAction(e -> openNewChatWindow());

        searchField.textProperty().addListener((obs, oldValue, newValue) -> {
            activeSearchQuery = newValue == null ? "" : newValue.trim();
            refreshSearchResults();
        });
        searchField.setOnAction(e -> refreshSearchResults());

        loadRecentChatsByFrequency();
    }

    /**
     * Rebuilds the recent chat buttons sorted by open frequency.
     */
    private void loadRecentChatsByFrequency() {
        refreshSearchResults();
    }

    /**
     * Refreshes the center list using either recent chats or filtered search results.
     */
    private void refreshSearchResults() {
        List<Contact> contacts = getContactsSortedByFrequency();
        String query = activeSearchQuery.toLowerCase(Locale.ROOT);

        if (query.isEmpty()) {
            recentChatsTitleLabel.setText("Your 3 most recent chats");
            renderRecentChatButtons(contacts);
            return;
        }

        List<Contact> filteredContacts = new ArrayList<>();
        for (Contact contact : contacts) {
            String name = contact.getName().toLowerCase(Locale.ROOT);
            String phone = contact.getPhoneNumber().toLowerCase(Locale.ROOT);
            if (name.contains(query) || phone.contains(query)) {
                filteredContacts.add(contact);
            }
        }

        recentChatsTitleLabel.setText("Search results");
        renderRecentChatButtons(filteredContacts);

        int messageMatches = chatController.searchAllChats(activeSearchQuery).size();
        Label messageResult = new Label("Matched messages: " + messageMatches);
        messageResult.setStyle("-fx-text-fill: #6A6A6A; -fx-font-size: 12;");
        recentChatsContainer.getChildren().add(messageResult);
    }

    /**
     * Returns all contacts sorted by open frequency, then by name.
     */
    private List<Contact> getContactsSortedByFrequency() {
        List<Contact> contacts = chatController.getAllContacts();
        contacts.sort((c1, c2) -> {
            Integer firstCount = openFrequency.getOrDefault(c1.getName(), 0);
            Integer secondCount = openFrequency.getOrDefault(c2.getName(), 0);
            int frequencyCompare = secondCount.compareTo(firstCount);
            if (frequencyCompare != 0) {
                return frequencyCompare;
            }
            return c1.getName().compareToIgnoreCase(c2.getName());
        });
        return contacts;
    }

    /**
     * Renders recent chat buttons for the provided contact list.
     *
     * @param contacts contacts to display
     */
    private void renderRecentChatButtons(List<Contact> contacts) {
        recentChatsContainer.getChildren().clear();

        if (contacts.isEmpty()) {
            String message = activeSearchQuery.isEmpty()
                    ? "No contacts yet. Add contacts to start chatting."
                    : "No contacts match your search.";
            Label empty = new Label(message);
            empty.setStyle("-fx-text-fill: #A0A0A0; -fx-font-size: 14;");
            recentChatsContainer.getChildren().add(empty);
            return;
        }

        for (Contact contact : contacts) {
            String contactName = contact.getName();
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

    /**
     * Opens a new chat window and sets its title for the chosen contact.
     *
     * @param contactName contact display name shown in the chat header
     */
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

    /**
     * Opens the edit-profile dialog window.
     */
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

    /**
     * Opens a window listing all hard-coded chat entries.
     */
    private void openAllChatsWindow() {
        Optional<String> sortChoice = chooseSortMode("Sort Chats");
        if (sortChoice.isEmpty()) {
            return;
        }

        try {
            VBox vbox = new VBox(15);
            vbox.setStyle("-fx-padding: 20; -fx-background-color: #111B21;");

            Label title = new Label("All Chats");
            title.setStyle("-fx-font-size: 20; -fx-text-fill: white; -fx-font-weight: bold;");
            String style = "-fx-background-color: #2A3942; -fx-text-fill: white; -fx-font-size: 16; -fx-padding: 15 30; -fx-background-radius: 10;";
            List<Contact> contacts = getContactsBySelection(sortChoice.get());

            vbox.getChildren().add(title);
            if (contacts.isEmpty()) {
                Label empty = new Label("No chats available. Add a contact to start.");
                empty.setStyle("-fx-text-fill: #A0A0A0; -fx-font-size: 14;");
                vbox.getChildren().add(empty);
            } else {
                for (Contact contact : contacts) {
                    String contactName = contact.getName();
                    Button chatButton = new Button("💬 Chat with " + contactName);
                    chatButton.setStyle(style);
                    chatButton.setPrefWidth(400);
                    chatButton.setOnAction(e -> openChatWindow(contactName));
                    vbox.getChildren().add(chatButton);
                }
            }

            Scene scene = new Scene(vbox, 450, 320);
            Stage stage = new Stage();
            stage.setTitle("All Chats");
            stage.setScene(scene);
            stage.show();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Shows the contacts information dialog.
     */
    private void openContactsWindow() {
        if (chatController.getAllContacts().isEmpty()) {
            Alert emptyAlert = new Alert(Alert.AlertType.INFORMATION);
            emptyAlert.setTitle("Contacts");
            emptyAlert.setHeaderText("Your Contacts");
            emptyAlert.setContentText("No contacts available yet.");
            emptyAlert.showAndWait();
            return;
        }

        Optional<String> sortChoice = chooseSortMode("Sort Contacts");
        if (sortChoice.isEmpty()) {
            return;
        }

        List<Contact> sortedContacts = getContactsBySelection(sortChoice.get());
        StringBuilder contactListText = new StringBuilder();
        for (int i = 0; i < sortedContacts.size(); i++) {
            Contact contact = sortedContacts.get(i);
            contactListText
                    .append(i + 1)
                    .append(". ")
                    .append(contact.getName())
                    .append(" - ")
                    .append(contact.getPhoneNumber())
                    .append("\n");
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Contacts");
        alert.setHeaderText("Your Contacts (" + sortChoice.get() + ")");
        alert.setContentText(contactListText.toString().trim());
        alert.showAndWait();
    }

    /**
     * Shows a contact picker dialog and opens the selected chat.
     */
    private void openNewChatWindow() {
        Optional<String> sortChoice = chooseSortMode("Sort New Chat Contacts");
        if (sortChoice.isEmpty()) {
            return;
        }

        List<Contact> sortedContacts = getContactsBySelection(sortChoice.get());
        if (sortedContacts.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("New Chat");
            alert.setHeaderText("No contacts available");
            alert.setContentText("Add contacts before starting a new chat.");
            alert.showAndWait();
            return;
        }

        List<String> contactNames = new ArrayList<>();
        for (Contact contact : sortedContacts) {
            contactNames.add(contact.getName());
        }

        ChoiceDialog<String> dialog = new ChoiceDialog<>(contactNames.getFirst(), contactNames);
        dialog.setTitle("New Chat");
        dialog.setHeaderText("Choose a contact to start a new chat with (" + sortChoice.get() + ")");
        dialog.setContentText("Select contact:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(this::openChatWindow);
    }

    /**
     * Updates the displayed profile information from the edit-profile dialog.
     *
     * @param newName new username value; ignored when empty
     * @param newStatus new status text; may be empty
     */
    public static void updateProfile(String newName, String newStatus) {
        if (!newName.isEmpty()) currentUsername = newName;
        currentStatus = newStatus;
        updateProfileDisplay();
    }

    /**
     * Refreshes the profile label text based on current static profile fields.
     */
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

    /**
     * Seeds the UI with demo contacts when no saved contacts are available.
     */
    private void ensureDefaultContacts() {
        if (!chatController.getAllContacts().isEmpty()) {
            return;
        }

        chatController.addContact(new Contact("Alice Johnson", "0700-111-222", "👩"));
        chatController.addContact(new Contact("Bob Smith", "0700-333-444", "👨"));
        chatController.addContact(new Contact("Emma Davis", "0700-555-666", "👩"));
    }

    /**
     * Resolves a contact ordering strategy selected by the user.
     *
     * @param sortSelection selected sort option from the UI
     * @return contacts ordered according to the selected option
     */
    private List<Contact> getContactsBySelection(String sortSelection) {
        if ("Recently Added".equals(sortSelection)) {
            return chatController.getContactsByRecentlyAdded();
        }
        return chatController.getContactsAlphabetically();
    }

    /**
     * Opens a simple dialog flow that collects and adds a new contact.
     */
    private void openAddContactDialog() {
        TextInputDialog nameDialog = new TextInputDialog();
        nameDialog.setTitle("Add Contact");
        nameDialog.setHeaderText("Create a new contact");
        nameDialog.setContentText("Contact name:");

        Optional<String> nameResult = nameDialog.showAndWait();
        if (nameResult.isEmpty()) {
            return;
        }

        String contactName = nameResult.get().trim();
        if (contactName.isEmpty()) {
            showInfo("Invalid input", "Contact name cannot be empty.");
            return;
        }

        TextInputDialog phoneDialog = new TextInputDialog();
        phoneDialog.setTitle("Add Contact");
        phoneDialog.setHeaderText("Create a new contact");
        phoneDialog.setContentText("Phone number:");

        Optional<String> phoneResult = phoneDialog.showAndWait();
        if (phoneResult.isEmpty()) {
            return;
        }

        String phoneNumber = phoneResult.get().trim();
        if (phoneNumber.isEmpty()) {
            showInfo("Invalid input", "Phone number cannot be empty.");
            return;
        }

        if (contactExists(contactName)) {
            showInfo("Duplicate contact", "A contact with this name already exists.");
            return;
        }

        TextInputDialog pictureDialog = new TextInputDialog("👤");
        pictureDialog.setTitle("Add Contact");
        pictureDialog.setHeaderText("Optional profile marker");
        pictureDialog.setContentText("Emoji or picture marker:");

        Optional<String> pictureResult = pictureDialog.showAndWait();
        String picture = pictureResult.map(String::trim).filter(value -> !value.isEmpty()).orElse("👤");

        chatController.addContact(new Contact(contactName, phoneNumber, picture));
        loadRecentChatsByFrequency();
        showInfo("Contact added", "Added contact: " + contactName);
    }

    /**
     * Checks whether a contact name already exists (case-insensitive).
     *
     * @param name candidate contact name
     * @return {@code true} if a matching contact exists
     */
    private boolean contactExists(String name) {
        String normalizedInput = normalizeName(name);
        for (Contact contact : chatController.getAllContacts()) {
            if (normalizeName(contact.getName()).equals(normalizedInput)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Normalizes a contact name for case-insensitive comparisons.
     *
     * @param name raw contact name
     * @return lowercase trimmed form
     */
    private String normalizeName(String name) {
        return name.trim().toLowerCase(Locale.ROOT);
    }

    /**
     * Shows a simple information alert.
     *
     * @param header alert header
     * @param content alert content
     */
    private void showInfo(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Add Contact");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    /**
     * Prompts the user to choose a contact sort mode.
     *
     * @param title dialog window title
     * @return selected sort mode, or empty when cancelled
     */
    private Optional<String> chooseSortMode(String title) {
        ChoiceDialog<String> sortDialog = new ChoiceDialog<>("Alphabetical", "Alphabetical", "Recently Added");
        sortDialog.setTitle(title);
        sortDialog.setHeaderText("Choose how to sort contacts");
        sortDialog.setContentText("Sort by:");
        return sortDialog.showAndWait();
    }
}