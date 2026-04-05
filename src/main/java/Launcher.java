/**
 * Alternate JVM entry point that delegates to {@link MainApp}.
 *
 * <p>This class is used to avoid JavaFX launch issues in some runtime setups.</p>
 */
public class Launcher {
    /**
     * Delegates startup to {@link MainApp#main(String[])}.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        // This bypasses the JVM's strict JavaFX check
        MainApp.main(args);
    }
}
