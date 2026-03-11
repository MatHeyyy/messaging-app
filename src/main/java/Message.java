import java.time.LocalDateTime;


public class Message {
    private String senderName;
    private String content;
    private LocalDateTime timestamp;
    private boolean isRead;

    public Message(String senderName, String content) {
        this.senderName = senderName;
        this.content = content;
        this.timestamp = LocalDateTime.now();
        this.isRead = false;
    }
}
