import java.io.*;
import java.util.HashMap;

/**
 * Persists and restores application chat data from local disk.
 */
public class DataManager {
    private static final String FILE_NAME = "chat_data.dat";

    /**
     * Serializable container that stores all persisted application state.
     */
    private static class PersistedData implements Serializable {
        private static final long serialVersionUID = 1L;

        private final HashMap<String, Contact> contacts;
        private final HashMap<String, Chat> allChats;
        private final User currentUser;
        private final String currentStatus;

        private PersistedData(HashMap<String, Contact> contacts,
                              HashMap<String, Chat> allChats,
                              User currentUser,
                              String currentStatus) {
            this.contacts = contacts;
            this.allChats = allChats;
            this.currentUser = currentUser;
            this.currentStatus = currentStatus;
        }
    }

    /**
     * Saves contact and chat maps to the data file.
     *
     * @param contacts contacts keyed by contact name
     * @param allChats chats keyed by chat identifier
     */
    public void saveData(HashMap<String, Contact> contacts, HashMap<String, Chat> allChats) {
        saveData(contacts, allChats, null, "");
    }

    /**
     * Saves contacts, chats, profile user, and profile status to the data file.
     *
     * @param contacts contacts keyed by contact name
     * @param allChats chats keyed by chat identifier
     * @param currentUser current signed-in user profile
     * @param currentStatus user status text shown on landing page
     */
    public void saveData(HashMap<String, Contact> contacts,
                         HashMap<String, Chat> allChats,
                         User currentUser,
                         String currentStatus) {
        try (FileOutputStream fileOut = new FileOutputStream(FILE_NAME);
            ObjectOutputStream out = new ObjectOutputStream(fileOut)){

            PersistedData persistedData = new PersistedData(
                    contacts,
                    allChats,
                    currentUser,
                    currentStatus == null ? "" : currentStatus
            );
            out.writeObject(persistedData);

            System.out.println("Data saved successfully to " + FILE_NAME);
        } catch (IOException e) {
            System.out.println("Error saving data to " + e.getMessage());
        }
    }

    /**
     * Saves all state currently held by a chat controller plus profile status text.
     *
     * @param controller source of contacts, chats, and current user
     * @param currentStatus profile status string
     */
    public void saveData(ChatController controller, String currentStatus) {
        saveData(
                controller.getContactsMap(),
                controller.getAllChatsMap(),
                controller.getCurrentUser(),
                currentStatus
        );
    }

    /**
     * Loads contacts and chats from disk into a controller instance.
     *
     * <p>If no data file exists, the method returns without modifying the controller.</p>
     *
     * @param controller destination controller that receives loaded maps
     */
    @SuppressWarnings("unchecked")
    public String loadData(ChatController controller) {
        File file = new File(FILE_NAME);

        if (!file.exists()) {
            System.out.println("No previous data found. Starting fresh");
            return "";
        }

        try (FileInputStream fileIn = new FileInputStream(FILE_NAME);
            ObjectInputStream in = new ObjectInputStream(fileIn)){

            Object firstObject = in.readObject();

            if (firstObject instanceof PersistedData persistedData) {
                controller.setContacts(persistedData.contacts);
                controller.setAllChats(persistedData.allChats);
                controller.setCurrentUser(persistedData.currentUser);
                System.out.println("Data loaded successfully from " + FILE_NAME);
                return persistedData.currentStatus == null ? "" : persistedData.currentStatus;
            }

            // Backward compatibility with older files that wrote contacts/chats as two objects.
            HashMap<String, Contact> loadedContacts = (HashMap<String, Contact>) firstObject;
            HashMap<String, Chat> loadedChats = (HashMap<String, Chat>) in.readObject();

            controller.setContacts(loadedContacts);
            controller.setAllChats(loadedChats);

            System.out.println("Data loaded successfully from " + FILE_NAME);
            return "";
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading data from " + e.getMessage());
            return "";
        }
    }
}
