import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Represents a saved contact that can be messaged by the user.
 */
public class Contact implements Serializable {
	private static final long serialVersionUID = 1L;
    private String name;
    private String phoneNumber;
    private String profilePicture;
    private LocalDateTime dateAdded;

    /**
     * Creates a contact with explicit profile picture information.
     *
     * @param name contact display name
     * @param phoneNumber contact phone number
     * @param profilePicture profile picture marker or path
     */
    public Contact(String name, String phoneNumber, String profilePicture) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.profilePicture = profilePicture;
        this.dateAdded = LocalDateTime.now();
    }

    /**
     * Creates a contact with a default profile picture marker.
     *
     * @param name contact display name
     * @param phoneNumber contact phone number
     */
    public Contact(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.profilePicture = "👤";
        this.dateAdded = LocalDateTime.now();
    }

    // GETTERS

    /**
     * Returns the contact name.
     *
     * @return contact display name
     */
    public String getName() {return name;}

    /**
     * Returns the contact phone number.
     *
     * @return phone number string
     */
    public String getPhoneNumber() {return phoneNumber;}

    /**
     * Returns the profile picture marker.
     *
     * @return profile picture marker or path
     */
    public String getProfilePicture() {return profilePicture;}

    /**
     * Returns the timestamp when this contact was created.
     *
     * @return contact creation timestamp
     */
    public LocalDateTime getDateAdded() {return dateAdded;}

     // SETTERS

    /**
     * Updates the contact name.
     *
     * @param name new contact name
     */
    public void setName(String name) {this.name = name;}

    /**
     * Updates the contact phone number.
     *
     * @param phoneNumber new phone number
     */
    public void setPhoneNumber(String phoneNumber) {this.phoneNumber = phoneNumber;}

    /**
     * Updates the contact profile picture marker.
     *
     * @param profilePicture new profile picture marker or path
     */
    public void setProfilePicture(String profilePicture) {this.profilePicture = profilePicture;}

    /**
     * Returns a compact contact summary string.
     *
     * @return formatted contact string
     */
    @Override
    public String toString() {
        return profilePicture + " " + name + " (" + phoneNumber + ")";
    }
}
