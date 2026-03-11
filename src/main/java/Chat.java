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
}
