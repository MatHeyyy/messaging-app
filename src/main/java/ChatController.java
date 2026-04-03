import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class ChatController {
    private HashMap<String, Contact> contacts;
    private HashMap<String, Chat> allChats;
    private User currentUser;

    public ChatController() {
        this.contacts = new HashMap<>();
        this.allChats = new HashMap<>();
    }


    // SETTERS

    /**
     * SET method to load saved contacts
     * @param loadedContacts
     */
    public void setContacts(HashMap<String, Contact> loadedContacts) {
        this.contacts = loadedContacts;
    }

    /**
     * SET method to load saved chats
     * @param loadedChats
     */
    public void setAllChats(HashMap<String, Chat> loadedChats) {
        this.allChats = loadedChats;
    }

    // ==========================================
    // EDITING PROFILE
    // ==========================================
    
    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    public User getCurrentUser() {
        return this.currentUser;
    }

    public void editProfile(String newUsername, String newPhoneNumber, String newProfilePic) {
        if (this.currentUser != null) {
            if (newUsername != null && !newUsername.trim().isEmpty()) {
                this.currentUser.setUsername(newUsername); // Assumes User has setUsername()
            }
            if (newPhoneNumber != null && !newPhoneNumber.trim().isEmpty()) {
                this.currentUser.setPhoneNumber(newPhoneNumber); // Assumes User has setPhoneNumber()
            }
            if (newProfilePic != null && !newProfilePic.trim().isEmpty()) {
                this.currentUser.setProfilePicture(newProfilePic); // Assumes User has setProfilePicture()
            }
        }
    }

    // ==========================================
    // REQUIREMENT: View and manage contacts
    // ==========================================

    public void addContact(Contact newContact) {
        contacts.put(newContact.getName(), newContact);
    }

    public List<Contact> getAllContacts() {
        return new ArrayList<>(contacts.values());
    }

    /**
     * Method to sort the list of contacts in alphabetical order
     * @return A list of contacts in alphabetical order
     */
    public List<Contact> getContactsAlphabetically() {
        List<Contact> sortedList = new ArrayList<>(contacts.values());
        sortedList.sort((c1, c2) -> c1.getName().compareToIgnoreCase(c2.getName()));
        return sortedList;
    }

    /**
     * Method to sort the list of contacts by the date they were added,
     * with the most recently added contacts appearing first
     * @return A list of contacts in order of newest to oldest based on the date they were added
     */
    public List<Contact> getContactsByRecentlyAdded(){
        List<Contact> sortedList = new ArrayList<>(contacts.values());
        sortedList.sort((c1, c2) -> c2.getDateAdded().compareTo(c1.getDateAdded()));
        return sortedList;
    }

    // Find a specific contact by name
    public Contact getContact(String name) {
        return contacts.get(name);
    }

    // ==========================================
    // REQUIREMENT: Select from their 3 most recent chats
    // ==========================================
    
    public List<Chat> getThreeRecentChats(Contact targetContact) {
        List<Chat> relevantChats = new ArrayList<>();

        // Find all chats that involve this specific contact
        for (Chat chat : allChats.values()) {
            if (chat.getParticipantName().equals(targetContact.getName())) {
                relevantChats.add(chat);
            }
        }

        // Sort these chats by the timestamp of their LAST message
        relevantChats.sort((chat1, chat2) -> {
            // Safety check: if a chat has no messages, push it to the bottom
            if (chat1.getMessages().isEmpty()) return 1;
            if (chat2.getMessages().isEmpty()) return -1;

            // Get the last message in both chats
            Message lastMsg1 = chat1.getMessages().get(chat1.getMessages().size() - 1);
            Message lastMsg2 = chat2.getMessages().get(chat2.getMessages().size() - 1);

            // Compare the timestamps. 
            // Use time2.compareTo(time1) to sort in DESCENDING order (newest first)
            return lastMsg2.getTimestamp().compareTo(lastMsg1.getTimestamp());
        });

        // Step 3: Return only the top 3 (or fewer if they only have 1 or 2 chats)
        if (relevantChats.size() > 3) {
            return relevantChats.subList(0, 3); // Returns index 0, 1, and 2
        }
        
        return relevantChats;
    }

    // ==========================================
    // REQUIREMENT: Opening a chat to view and add to it
    // ==========================================
    
    public Chat openChat(String chatId) {
        return allChats.get(chatId);
    }

    public void addMessageToChat(String chatId, String senderName, String text) {
        Chat chat = allChats.get(chatId);
        if (chat != null) {
            Message newMsg = new Message(senderName, text);
            chat.addMessage(newMsg);
        }
    }

    // ==========================================
    // SEARCHING FOR CHATS
    // ==========================================

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

    // ==========================================
    // REQUIREMENT: Delete chats
    // ==========================================

    /**
     * Method to remove a chat from the allChats HashMap
     * @param chatId
     */
    public void deleteChat(String chatId) {
        allChats.remove(chatId);
    }

    /**
     * Method to remove a contact from the contacts HashMap
     * @param contactName
     */
    public void removeContact(String contactName){
        contacts.remove(contactName);
    }
    
}
