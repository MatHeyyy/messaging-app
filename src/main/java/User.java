/**
 * Represents the primary user of the application
 */
public class User {
    private String userID;
    private String username;
    private String phoneNumber;
    private String profilePicture; //For now will display just an emoji, to be extended for when GUI is implemented

    /**
     * Constructor for User class.
     * @param userID
     * @param username
     * @param phoneNumber
     * @param profilePicture
     */
    public User(String userID, String username, String phoneNumber, String profilePicture) {
        this.userID = userID;
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.profilePicture = profilePicture;
    }

    // GETTERS

    /**
     * GET method for userID.
     * @return a String representing the unique identifier of the user.
     */
    public String getUserID() {return userID;}

    /**
     * GET method for username.
     * @return a String representing the username of the user.
     */
    public String getUsername() {return username;}

    /**
     * GET method for phpneNumber
     * @return a String representing the phone number of the user.
     */
    public String getPhoneNumber() {return phoneNumber;}

    /**
     * GET method for profilePicture
     * @return a String representing the profile picture of the user, currently an emoji.
     */
    public String getProfilePicture() {return profilePicture;}
}
