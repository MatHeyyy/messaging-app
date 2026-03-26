import java.time.LocalDateTime;

/**
 * The Contact class represents a contact that is added by the user to message.
 *
 * @author Matei Costinescu
 * @version 1.0
 */
public class Contact {
    private String name;
    private String phoneNumber;
    private String profilePicture;
    private LocalDateTime dateAdded;

    /**
     * Primary Constructor for the Contact class
     * @param name
     * @param phoneNumber
     * @param profilePicture
     */
    public Contact(String name, String phoneNumber, String profilePicture) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.profilePicture = profilePicture;
        this.dateAdded = LocalDateTime.now();
    }

    /**
     * Secondary Constructor for the Contact class for when the user provides no profile picture
     */
    public Contact(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.profilePicture = "👤";
        this.dateAdded = LocalDateTime.now();
    }
}
