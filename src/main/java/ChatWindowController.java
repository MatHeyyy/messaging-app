import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

/**
 * ChatWindowController - Controls the individual chat window.
 * UI/UX
 * Displays messages as bubbles, allows sending new messages.
 * Like chat button.
 */
public class ChatWindowController {

    @FXML private Label chatTitle;
    @FXML private VBox messageContainer;
    @FXML private TextField messageField;
    @FXML private Button sendBtn;
    @FXML private Button likeBtn;
    @FXML private ScrollPane scrollPane;

    @FXML
    public void initialize() {
    	likeBtn.setFocusTraversable(false);
        chatTitle.setText("Chat with Friend");

        // Set button actions
        sendBtn.setOnAction(e -> sendMessage());
        messageField.setOnAction(e -> sendMessage());

        // Like button action
        likeBtn.setOnAction(e -> {
            likeBtn.setText("❤️ Liked!");
            System.out.println("User liked this chat!");
        });

        // Load sample messages for demonstration
        loadSampleMessages();
    }

    /**
     * Loads sample messages when the chat window opens.
     */
    private void loadSampleMessages() {
        messageContainer.getChildren().clear();

        addMessageBubble("Hello! How are you today?", false);
        addMessageBubble("I'm doing great, thanks! And you?", true);
        addMessageBubble("Everything is perfect! 😊", false);

        // Scroll to the bottom
        scrollPane.setVvalue(1.0);
    }

    /**
     * Adds a message bubble to the chat (blue for user, dark for friend).
     */
    private void addMessageBubble(String text, boolean isMine) {
        Label bubble = new Label(text);
        bubble.setWrapText(true);
        bubble.setMaxWidth(480);

        if (isMine) {
            // User's message - blue bubble on the right
            bubble.setStyle("-fx-background-color: #00BFFF; -fx-text-fill: white; " +
                           "-fx-padding: 12; -fx-background-radius: 18 18 5 18; " +
                           "-fx-alignment: CENTER_RIGHT;");
        } else {
            // Friend's message - dark bubble on the left
            bubble.setStyle("-fx-background-color: #2A3942; -fx-text-fill: white; " +
                           "-fx-padding: 12; -fx-background-radius: 18 18 18 5;");
        }

        messageContainer.getChildren().add(bubble);
    }

    /**
     * Sends a new message from the current user.
     */
    private void sendMessage() {
        String text = messageField.getText().trim();
        if (text.isEmpty()) return;

        // Show user's message as a blue bubble
        addMessageBubble(text, true);
        messageField.clear();
        scrollPane.setVvalue(1.0);

        // Simulate a reply from the other person 
        new Thread(() -> {
            try { Thread.sleep(800); } catch (InterruptedException ignored) {}
            javafx.application.Platform.runLater(() -> {
                addMessageBubble("Message received! 👍", false);
                scrollPane.setVvalue(1.0);
            });
        }).start();
    }
    /**
     * Sets the chat title when opening from New Chat
     */
    public void setChatTitle(String contactName) {
        chatTitle.setText("💬 " + contactName);
    }
}