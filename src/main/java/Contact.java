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

    // GETTERS

    /**
     * GET method for name.
     * @return a String representing the name of the contact.
     */
    public String getName() {return name;}

    /**
     * GET method for phoneNumber.
     * @return a String representing the phone number of the contact.
     */
    public String getPhoneNumber() {return phoneNumber;}

    /**
     * GET method for profilePicture.
     * @return a String representing the profile picture of the contact, currently an emoji.
     */
    public String getProfilePicture() {return profilePicture;}

    /**
     * GET method for dateAdded.
     * @return a LocalDateTime object representing the date and time when the contact was added.
     */
    public LocalDateTime getDateAdded() {return dateAdded;}

     // SETTERS

    /**
     * SET method to set the name of the contact.
     * @param name
     */
    public void setName(String name) {this.name = name;}

    /**
     * SET method to  set the phone number of the contact
     * @param phoneNumber
     */
    public void setPhoneNumber(String phoneNumber) {this.phoneNumber = phoneNumber;}

    /**
     * SET method to set the profile picture
     * @param profilePicture
     */
    public void setProfilePicture(String profilePicture) {this.profilePicture = profilePicture;}
}
