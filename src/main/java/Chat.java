import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;
//import java.time.LocalDateTime;

/**
 * Represents a conversation thread with one participant and its message history.
 */
public class Chat implements Serializable {
	private static final long serialVersionUID = 1L;
    private String participantName;
    private List<Message> messages;

    /**
     * Creates a chat bound to a participant name.
     *
     * @param participantName participant display name for this chat
     */
    public Chat(String participantName) {
        this.participantName = participantName;
        this.messages = new ArrayList<>();
    }

    // GETTERS
    /**
     * Returns the latest message in the chat.
     *
     * @return most recent message, or {@code null} when the chat is empty
     */
    public Message getLatestMessage() {
        if (messages.isEmpty()) {
            return null;
        }
        return messages.get(messages.size() - 1);
    }

    /**
     * Returns the participant name associated with this chat.
     *
     * @return participant display name
     */
    public String getParticipantName() {return participantName;}

    /**
     * Returns the backing message list for this chat.
     *
     * @return mutable list of messages in chronological insertion order
     */
    public List<Message> getMessages() {return messages;}

    // METHODS

    /**
     * Appends a message to the chat history.
     *
     * @param message message to append
     */
    public void addMessage(Message message) {
        messages.add(message);
    }

    /**
     * Removes all messages from this chat.
     */
    public void clearChat() {
        messages.clear();
    }
}
