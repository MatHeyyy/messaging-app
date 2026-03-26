import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class ChatController {
    // Core data structures
    private HashMap<String, Contact> contacts;
    private HashMap<String, Chat> allChats;

    public ChatController() {
        this.contacts = new HashMap<>();
        this.allChats = new HashMap<>();
    }

    // --- REQUIREMENT: View and manage contacts ---
    
    public void addContact(Contact newContact)
    {
        contacts.put(newContact.getName(), newContact);
    }

    public List<Contact> getAllContacts() {
        return new ArrayList<>(contacts.values());
    }

    // --- REQUIREMENT: Select from their 3 most recent chats ---
    
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
    }
    
}
