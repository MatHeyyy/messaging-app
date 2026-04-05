import java.io.Serializable;
//import java.time.LocalDateTime;


/**
 * Represents an application user profile.
 */
public class User implements Serializable {
	private static final long serialVersionUID = 1L;
    private String userID;
    private String username;
    private String phoneNumber;
    private String profilePicture; //For now will display just an emoji, to be extended for when GUI is implemented

    /**
     * Creates a user profile with all persisted fields.
     *
     * @param userID unique user identifier
     * @param username display name
     * @param phoneNumber phone number
     * @param profilePicture profile picture marker or path
     */
    public User(String userID, String username, String phoneNumber, String profilePicture) {
        this.userID = userID;
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.profilePicture = profilePicture;
    }

    // GETTERS

    /**
     * Returns the unique user identifier.
     *
     * @return user ID
     */
    public String getUserID() {return userID;}

    /**
     * Returns the username.
     *
     * @return display name
     */
    public String getUsername() {return username;}

    /**
     * Returns the phone number.
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

    // SETTERS

    /**
     * Updates the user ID.
     *
     * @param userID new user ID
     */
    public void setUserID(String userID) {this.userID = userID;}

    /**
     * Updates the username.
     *
     * @param username new display name
     */
    public void setUsername(String username) {this.username = username;}

    /**
     * Updates the phone number.
     *
     * @param phoneNumber new phone number
     */
    public void setPhoneNumber(String phoneNumber) {this.phoneNumber = phoneNumber;}

    /**
     * Updates the profile picture marker.
     *
     * @param profilePicture new profile picture marker or path
     */
    public void setProfilePicture(String profilePicture) {this.profilePicture = profilePicture;}


    /**
     * Returns a compact profile summary string.
     *
     * @return formatted user string
     */
    @Override
    public String toString() {
        return profilePicture + " " + username + " (ID: " + userID + "Phone: " + phoneNumber + ")";
    }
}
