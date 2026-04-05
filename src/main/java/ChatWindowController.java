import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

/**
 * Manages interactions inside a single chat window.
 *
 * <p>The controller renders simple message bubbles, handles send actions,
 * and updates the window title based on the selected contact.</p>
 */
public class ChatWindowController {

    @FXML private Label chatTitle;
    @FXML private VBox messageContainer;
    @FXML private TextField messageField;
    @FXML private Button sendBtn;
    @FXML private Button likeBtn;
    @FXML private ScrollPane scrollPane;

    private Chat chat;
    private String currentUsername = "You";
    private Runnable onDataChanged;

    /**
     * Initializes event handlers and loads demo content for the chat view.
     */
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

        renderMessages();
    }

    /**
     * Binds this window to a chat model and persistence callback.
     *
     * @param chat chat model to render and mutate
     * @param currentUsername sender label for current user messages
     * @param onDataChanged callback invoked after message mutations
     */
    public void setChatData(Chat chat, String currentUsername, Runnable onDataChanged) {
        this.chat = chat;
        if (currentUsername != null && !currentUsername.trim().isEmpty()) {
            this.currentUsername = currentUsername.trim();
        }
        this.onDataChanged = onDataChanged;
        renderMessages();
    }

    /**
     * Renders chat messages from the bound chat model.
     */
    private void renderMessages() {
        messageContainer.getChildren().clear();

        if (chat != null) {
            for (Message message : chat.getMessages()) {
                boolean isMine = message.getSenderName().equalsIgnoreCase(currentUsername);
                addMessageBubble(message.getContent(), isMine);
            }
        }

        scrollPane.setVvalue(1.0);
    }

    /**
     * Adds a formatted message bubble to the chat transcript.
     *
     * @param text message text to render
     * @param isMine {@code true} for current-user style, {@code false} for contact style
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

        if (chat == null) {
            return;
        }

        chat.addMessage(new Message(currentUsername, text));
        notifyDataChanged();

        addMessageBubble(text, true);
        messageField.clear();
        scrollPane.setVvalue(1.0);

        // Simulate a reply from the other person 
        new Thread(() -> {
            try { Thread.sleep(800); } catch (InterruptedException ignored) {}
            javafx.application.Platform.runLater(() -> {
                String sender = getSimulatedIncomingSender();
                String incomingText = chat.isGroupChat()
                        ? sender + ": Message received! 👍"
                        : "Message received! 👍";
                chat.addMessage(new Message(sender, incomingText));
                notifyDataChanged();
                addMessageBubble(incomingText, false);
                scrollPane.setVvalue(1.0);
            });
        }).start();
    }

    /**
     * Picks a sender name used for simulated incoming messages.
     *
     * @return selected sender display name
     */
    private String getSimulatedIncomingSender() {
        if (chat == null) {
            return "Friend";
        }

        if (chat.isGroupChat()) {
            for (String participant : chat.getParticipants()) {
                if (!participant.equalsIgnoreCase(currentUsername)) {
                    return participant;
                }
            }
            return "Group member";
        }

        return chat.getParticipantName();
    }

    /**
     * Executes persistence callback after data changes.
     */
    private void notifyDataChanged() {
        if (onDataChanged != null) {
            onDataChanged.run();
        }
    }
    /**
     * Sets the chat header title for the selected contact.
     *
     * @param contactName contact display name shown in the title
     */
    public void setChatTitle(String contactName) {
        chatTitle.setText("💬 " + contactName);
    }
}