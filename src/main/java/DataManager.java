import java.io.*;
import java.util.HashMap;

/**
 * This class saving and loading the application state to disk
 *
 * @author Matei Costinescu
 * @version 1.0
 */
public class DataManager {
    private static final String FILE_NAME = "chat_data.dat";

    /**
     * Method to save the current contacts and chats to a file
     * @param contacts
     * @param allChats
     */
    public void saveData(HashMap<String, Contact> contacts, HashMap<String, Chat> allChats) {
        try (FileOutputStream fileOut = new FileOutputStream(FILE_NAME);
            ObjectOutputStream out = new ObjectOutputStream(fileOut)){

            out.writeObject(contacts);
            out.writeObject(allChats);

            System.out.println("Data saved successfully to " + FILE_NAME);
        } catch (IOException e) {
            System.out.println("Error saving data to " + e.getMessage());
        }
    }

    /**
     * Method to load the contacts and chats from a file and set them in the ChatController
     * @param controller
     */
    @SuppressWarnings("unchecked")
    public void loadData(ChatController controller) {
        File file = new File(FILE_NAME);

        if (!file.exists()) {
            System.out.println("No previous data found. Starting fresh");
            return;
        }

        try (FileInputStream fileIn = new FileInputStream(FILE_NAME);
            ObjectInputStream in = new ObjectInputStream(fileIn)){

            HashMap<String, Contact> loadedContacts = (HashMap<String, Contact>) in.readObject();
            HashMap<String, Chat> loadedChats = (HashMap<String, Chat>) in.readObject();

            controller.setContacts(loadedContacts);
            controller.setAllChats(loadedChats);

            System.out.println("Data loaded successfully from " + FILE_NAME);
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading data from " + e.getMessage());
        }
    }
}
