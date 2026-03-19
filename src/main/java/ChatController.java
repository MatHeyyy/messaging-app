import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class ChatController {
    // Core data structures
    private HashMap<String, User> contacts;
    private HashMap<String, Chat> allChats;

    public ChatController() {
        this.contacts = new HashMap<>();
        this.allChats = new HashMap<>();
    }

    // --- REQUIREMENT: View and manage contacts ---
    
    public void addContact(User newContact) {
        contacts.put(newContact.getUsername(), newContact);
    }

    public List<User> getAllContacts() {
        return new ArrayList<>(contacts.values());
    }

    // --- REQUIREMENT: Select from their 3 most recent chats ---
    
    public List<Chat> getThreeRecentChats(User contact) {
        // Logic to find chats with this contact, sort by newest message, return top 3
        return new ArrayList<>(); 
    }

    // --- REQUIREMENT: Opening a chat to view and add to it ---
    
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

    // --- REQUIREMENT: Delete chats ---
    
    public void deleteChat(String chatId) {
        allChats.remove(chatId);
        // Note: You will eventually need to tell Person A to delete this from the file too!
    }
}
