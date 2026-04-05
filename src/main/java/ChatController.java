import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

/**
 * ChatController manages all chat and contact operations.
 * Handles user profiles, contact management, chat operations.
 */
public class ChatController {
    private HashMap<String, Contact> contacts;
    private HashMap<String, Chat> allChats;
    private User currentUser;

     //empty contact and chat collections.
     
    public ChatController() {
        this.contacts = new HashMap<>();
        this.allChats = new HashMap<>();
    }

    
    /**
     * Loads previously saved contacts.
     * @param loadedContacts a HashMap of contacts to be loaded
     */
    public void setContacts(HashMap<String, Contact> loadedContacts) {
        this.contacts = loadedContacts;
    }

    /**
     * Loads previously saved chats.     
     * @param loadedChats a HashMap of chats to be loaded
     */
    public void setAllChats(HashMap<String, Chat> loadedChats) {
        this.allChats = loadedChats;
    }

    /**
     * Sets the current user.     
     * @param user the User object to set as current user
     */
    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    /**
     * Gets the current user.
     * @return the current User, or null if no user 
     */
    public User getCurrentUser() {
        return this.currentUser;
    }

    /**
     * Edits the current user's profile information.     
     * @param newUsername    the new username 
     * @param newPhoneNumber the new phone number 
     * @param newProfilePic  the new profile picture path 
     */
    public void editProfile(String newUsername, String newPhoneNumber, String newProfilePic) {
        if (this.currentUser != null) {
            if (newUsername != null && !newUsername.trim().isEmpty()) {
                this.currentUser.setUsername(newUsername);
            }
            if (newPhoneNumber != null && !newPhoneNumber.trim().isEmpty()) {
                this.currentUser.setPhoneNumber(newPhoneNumber);
            }
            if (newProfilePic != null && !newProfilePic.trim().isEmpty()) {
                this.currentUser.setProfilePicture(newProfilePic);
            }
        }
    }

    
    /**
     * Adds a new contact to the contacts list.     
     * @param newContact the Contact object to add
     */
    public void addContact(Contact newContact) {
        contacts.put(newContact.getName(), newContact);
    }

    /**
     * Gets all contacts in the system.     
     * @return a list of all Contact objects
     */
    public List<Contact> getAllContacts() {
        return new ArrayList<>(contacts.values());
    }

    /**
     * Gets all contacts sorted alphabetically.     
     * @return a list of contacts in alphabetical order
     */
    public List<Contact> getContactsAlphabetically() {
        List<Contact> sortedList = new ArrayList<>(contacts.values());
        sortedList.sort((c1, c2) -> c1.getName().compareToIgnoreCase(c2.getName()));
        return sortedList;
    }

    /**
     * Gets all contacts sorted by date added, with most recent first.     
     * @return a list of contacts ordered by date added 
     */
    public List<Contact> getContactsByRecentlyAdded() {
        List<Contact> sortedList = new ArrayList<>(contacts.values());
        sortedList.sort((c1, c2) -> c2.getDateAdded().compareTo(c1.getDateAdded()));
        return sortedList;
    }

    /**
     * Retrieves a specific contact by name.     
     * @param name the contact's name
     * @return the Contact object if found, null otherwise
     */
    public Contact getContact(String name) {
        return contacts.get(name);
    }

    /**
     * Removes a contact from the contacts list.     
     * @param contactName the name of the contact to remove
     */
    public void removeContact(String contactName) {
        contacts.remove(contactName);
    }

    
    /**
     * Opens a chat by its ID.
     * @param chatId the unique identifier of the chat
     * @return the Chat object if found, null otherwise
     */
    public Chat openChat(String chatId) {
        return allChats.get(chatId);
    }

    /**
     * Gets the three most recent chats with a specific contact.
     * Chats are sorted by the timestamp, newest first.     
     * @param targetContact the contact to find recent chats
     * @return a list of up to three recent chats
     */
    public List<Chat> getThreeRecentChats(Contact targetContact) {
        List<Chat> relevantChats = new ArrayList<>();

        for (Chat chat : allChats.values()) {
            if (chat.getParticipantName().equals(targetContact.getName())) {
                relevantChats.add(chat);
            }
        }

        relevantChats.sort((chat1, chat2) -> {
            if (chat1.getMessages().isEmpty()) return 1;
            if (chat2.getMessages().isEmpty()) return -1;

            Message lastMsg1 = chat1.getMessages().get(chat1.getMessages().size() - 1);
            Message lastMsg2 = chat2.getMessages().get(chat2.getMessages().size() - 1);

            return lastMsg2.getTimestamp().compareTo(lastMsg1.getTimestamp());
        });

        if (relevantChats.size() > 3) {
            return relevantChats.subList(0, 3);
        }

        return relevantChats;
    }

    /**
     * Deletes a chat from the system.     
     * @param chatId the unique identifier of the chat to delete
     */
    public void deleteChat(String chatId) {
        allChats.remove(chatId);
    }

    
    /**
     * Adds a new message to a specific chat.
     *
     * @param chatId the unique identifier of the chat
     * @param senderName the name of the message sender
     * @param text the content of the message
     */
    public void addMessageToChat(String chatId, String senderName, String text) {
        Chat chat = allChats.get(chatId);
        if (chat != null) {
            Message newMsg = new Message(senderName, text);
            chat.addMessage(newMsg);
        }
    }

    /**
     * Searches all messages across all chats for a keyword.
     * The search is case insensitive.
     * @param keyword the search term to look for
     * @return a list of messages containing the keyword
     */
    public List<Message> searchAllChats(String keyword) {
        List<Message> searchResults = new ArrayList<>();
        String searchTerm = keyword.toLowerCase();

        for (Chat chat : allChats.values()) {
            for (Message msg : chat.getMessages()) {
                if (msg.getContent().toLowerCase().contains(searchTerm)) {
                    searchResults.add(msg);
                }
            }
        }
        return searchResults;
    }
    
}
