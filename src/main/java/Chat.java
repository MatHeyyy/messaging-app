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
    private String chatName;
    private boolean groupChat;
    private List<String> participants;
    private List<Message> messages;

    /**
     * Creates a chat bound to a participant name.
     *
     * @param participantName participant display name for this chat
     */
    public Chat(String participantName) {
        this.participantName = participantName;
        this.chatName = participantName;
        this.groupChat = false;
        this.participants = new ArrayList<>();
        this.participants.add(participantName);
        this.messages = new ArrayList<>();
    }

    /**
     * Creates a group chat.
     *
     * @param chatName group display name
     * @param participants group members by display name
     */
    public Chat(String chatName, List<String> participants) {
        this.participantName = chatName;
        this.chatName = chatName;
        this.groupChat = true;
        this.participants = new ArrayList<>(participants);
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
     * Returns the display name of the chat.
     *
     * @return display name used in chat lists and header
     */
    public String getChatName() {
        if (chatName == null || chatName.isBlank()) {
            return participantName;
        }
        return chatName;
    }

    /**
     * Indicates whether this chat is a group chat.
     *
     * @return {@code true} for group chats
     */
    public boolean isGroupChat() {
        return groupChat;
    }

    /**
     * Returns the participant display names.
     *
     * @return mutable participant list
     */
    public List<String> getParticipants() {
        if (participants == null) {
            participants = new ArrayList<>();
            if (participantName != null && !participantName.isBlank()) {
                participants.add(participantName);
            }
        }
        return participants;
    }

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
