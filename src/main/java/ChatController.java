import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

/**
 * Coordinates in-memory chat, contact, and current-user operations.
 *
 * <p>This controller acts as the application's central facade for profile updates,
 * contact list management, chat retrieval, and message search.</p>
 */
public class ChatController {
    private HashMap<String, Contact> contacts;
    private HashMap<String, Chat> allChats;
    private User currentUser;

    /**
     * Creates a controller with empty contact and chat collections.
     */
    public ChatController() {
        this.contacts = new HashMap<>();
        this.allChats = new HashMap<>();
    }

    
    /**
     * Replaces the current contact map with previously loaded contacts.
     *
     * @param loadedContacts contacts keyed by contact name
     */
    public void setContacts(HashMap<String, Contact> loadedContacts) {
        this.contacts = loadedContacts;
    }

    /**
     * Replaces the current chat map with previously loaded chats.
     *
     * @param loadedChats chats keyed by chat identifier
     */
    public void setAllChats(HashMap<String, Chat> loadedChats) {
        this.allChats = loadedChats;
    }

    /**
     * Sets the active user for profile operations.
     *
     * @param user user to mark as current; may be {@code null}
     */
    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    /**
     * Returns the active user.
     *
     * @return current user, or {@code null} when none is set
     */
    public User getCurrentUser() {
        return this.currentUser;
    }

    /**
     * Updates fields on the current user profile.
     *
     * <p>Each value is applied only when it is non-null and not blank.</p>
     *
     * @param newUsername new username value
     * @param newPhoneNumber new phone number value
     * @param newProfilePic new profile picture value
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
     * Adds or replaces a contact keyed by contact name.
     *
     * @param newContact contact to store
     */
    public void addContact(Contact newContact) {
        contacts.put(newContact.getName(), newContact);
    }

    /**
     * Returns all known contacts.
     *
     * @return copy of contact values in unspecified order
     */
    public List<Contact> getAllContacts() {
        return new ArrayList<>(contacts.values());
    }

    /**
     * Returns contacts sorted by name, case-insensitively.
     *
     * @return new list sorted alphabetically by contact name
     */
    public List<Contact> getContactsAlphabetically() {
        List<Contact> sortedList = new ArrayList<>(contacts.values());
        sortedList.sort((c1, c2) -> c1.getName().compareToIgnoreCase(c2.getName()));
        return sortedList;
    }

    /**
     * Returns contacts sorted by creation time, newest first.
     *
     * @return new list ordered by descending {@link Contact#getDateAdded()}
     */
    public List<Contact> getContactsByRecentlyAdded() {
        List<Contact> sortedList = new ArrayList<>(contacts.values());
        sortedList.sort((c1, c2) -> c2.getDateAdded().compareTo(c1.getDateAdded()));
        return sortedList;
    }

    /**
     * Looks up a contact by exact name key.
     *
     * @param name contact name key
     * @return matching contact, or {@code null} when absent
     */
    public Contact getContact(String name) {
        return contacts.get(name);
    }

    /**
     * Removes a contact by name.
     *
     * @param contactName name key to remove
     */
    public void removeContact(String contactName) {
        contacts.remove(contactName);
    }

    
    /**
     * Retrieves a chat by identifier.
     *
     * @param chatId chat identifier key
     * @return matching chat, or {@code null} when absent
     */
    public Chat openChat(String chatId) {
        return allChats.get(chatId);
    }

    /**
     * Returns up to three most recent chats for a specific contact.
     *
     * <p>Chats without messages are treated as older than chats with messages.</p>
     *
     * @param targetContact contact whose chats should be considered
     * @return list containing at most three chats ordered by latest message time
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
     * Removes a chat by identifier.
     *
     * @param chatId chat identifier key
     */
    public void deleteChat(String chatId) {
        allChats.remove(chatId);
    }

    
    /**
     * Appends a new message to an existing chat.
     *
     * <p>If the chat does not exist, this method does nothing.</p>
     *
     * @param chatId chat identifier key
     * @param senderName display name of the message sender
     * @param text message body text
     */
    public void addMessageToChat(String chatId, String senderName, String text) {
        Chat chat = allChats.get(chatId);
        if (chat != null) {
            Message newMsg = new Message(senderName, text);
            chat.addMessage(newMsg);
        }
    }

    /**
     * Searches message content across all chats using case-insensitive matching.
     *
     * @param keyword search term to find in message content
     * @return list of matching messages; empty when no matches exist
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
