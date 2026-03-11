import java.util.ArrayList;
import java.util.List;

/**
 * The Chat class represents a conversation between two users in the messaging system.
 * It contains the name of the participant and a list of messages exchanged in the chat.
 *
 * @author Matei Costinescu
 * @version 1.0
 */
public class Chat {
    private String participantName;
    private List<Message> messages;

    /**
     * Constructor for Chat class.
     * @param participantName a String representing the name of the participant in the chat.
     */
    public Chat(String participantName) {
        this.participantName = participantName;
        this.messages = new ArrayList<>();
    }

    // GETTERS
    /**
     * GET method for the latest message in the chat.
     * @return a Message object representing the latest message in the chat, or null if there are no messages.
     */
    public Message getLatestMessage() {
        if (messages.isEmpty()) {
            return null;
        }
        return messages.get(messages.size() - 1);
    }

    /**
     * GET method for participantName.
     * @return a String representing the name of the participant in the chat.
     */
    public String getParticipantName() {return participantName;}

    /**
     * GET method for messages.
     * @return a List of Message objects representing the messages exchanged in the chat.
     */
    public List<Message> getMessages() {return messages;}

    // METHODS
}
