import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Represents a single chat message with sender metadata and read state.
 */
public class Message implements Serializable {
	private static final long serialVersionUID = 1L;
    private String senderName;
    private String content;
    private LocalDateTime timestamp;
    private boolean isRead;

    /**
     * Creates a new unread message stamped with the current time.
     *
     * @param senderName sender display name
     * @param content message body text
     */
    public Message(String senderName, String content) {
        this.senderName = senderName;
        this.content = content;
        this.timestamp = LocalDateTime.now();
        this.isRead = false;
    }

    // GETTERS

    /**
     * Returns the sender display name.
     *
     * @return sender name
     */
    public String getSenderName() {return senderName;}

    /**
     * Returns the message content.
     *
     * @return message body text
     */
    public String getContent() {return content;}

    /**
     * Returns when this message was created.
     *
     * @return creation timestamp
     */
    public LocalDateTime getTimestamp() {return timestamp;}

    /**
     * Indicates whether the recipient has read this message.
     *
     * @return {@code true} when read, otherwise {@code false}
     */
    public boolean isRead() {return isRead;}

    // SETTERS
    /**
     * Marks this message as read.
     */
    public void markAsRead() {this.isRead = true;}

    /**
     * Returns a log-like representation of this message.
     *
     * @return formatted message string with timestamp and read marker
     */
    @Override
    public String toString() {
        return "[" + timestamp + "] " + senderName + ": " + content + (isRead ? " (read)" : " (unread)");
    }
}
