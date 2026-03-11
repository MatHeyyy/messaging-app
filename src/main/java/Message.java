import java.time.LocalDateTime;

/**
 * The Message class represents a message sent from one user to another in the messaging system.
 * It contains information about the sender, content, timestamp, and read status of the message.
 *
 * @author Matei Costinescu
 * @version 1.0
 */
public class Message {
    private String senderName;
    private String content;
    private LocalDateTime timestamp;
    private boolean isRead;

    /**
     * Constructor for Message class.
     * @param senderName
     * @param content
     */
    public Message(String senderName, String content) {
        this.senderName = senderName;
        this.content = content;
        this.timestamp = LocalDateTime.now();
        this.isRead = false;
    }

    // Getters

    /**
     * GET method for senderName.
     * @return a String representing the name of the sender of the message.
     */
    public String getSenderName() {return senderName;}

    /**
     * GET method for content.
     * @return a String representing the content of the message.
     */
    public String getContent() {return content;}

    /**
     * GET method for timestamp.
     * @return a LocalDateTime object representing the time when the message was created.
     */
    public LocalDateTime getTimestamp() {return timestamp;}

    /**
     * GET method for isRead.
     * @return a boolean indicating whether the message has been read or not.
     */
    public boolean isRead() {return isRead;}

    //Setters

    /**
     * SET method to mark the message as read by setting the isRead field to true.
     */
    public void markAsRead() {this.isRead = true;}
}
