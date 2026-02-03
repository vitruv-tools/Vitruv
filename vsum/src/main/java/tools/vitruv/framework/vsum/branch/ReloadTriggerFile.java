package tools.vitruv.framework.vsum.branch;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


/**
 * Manages a small trigger file that tells the VSUM to reload models.
 * How it works:
 * - Git hook creates this file after a branch switch.
 * - VSUM background watcher periodically checks for the file.
 * - If it exists, VSUM reloads models and deletes the file.
 * This ensures VSUM is updated even when developers use command-line Git.
 */
public class ReloadTriggerFile {
    private static final Logger LOGGER = LogManager.getLogger(ReloadTriggerFile.class);
    private static final String TRIGGER_FILENAME = "reload-trigger";

    private final Path triggerFilePath;

    /**
     * Creates a new trigger file manager for the given repository.
     *
     * @param repositoryRoot The root directory of the Git repository
     */
    public ReloadTriggerFile(Path repositoryRoot) {
        // store reload-trigger file in .vitruvius directory
        this.triggerFilePath = repositoryRoot.resolve(".vitruvius").resolve(TRIGGER_FILENAME);
    }

    /**
     * Creates the trigger file to signal that a reload is needed.
     * The file contains the timestamp when it was created.
     *
     * @throws IOException if the file cannot be created
     */
    public void createTrigger() throws IOException {
        // create parent directory if needed
        Files.createDirectories(triggerFilePath.getParent());
        // write timestamp to file for debugging
        String timestamp = java.time.LocalDateTime.now().toString();
        Files.writeString(triggerFilePath, "Reload requested at: " + timestamp);
        LOGGER.debug("Created reload trigger file at: {}", triggerFilePath);
    }

    /**
     * Checks if the trigger file exists. If it does, deletes it and returns true.
     * If it doesn't exist, returns false
     *
     * @return true if the trigger file existed and false otherwise
     */
    public boolean checkAndClearTrigger() {
        if (Files.exists(triggerFilePath)) {
            try {
                Files.delete(triggerFilePath);
                LOGGER.debug("Reload trigger detected and cleared from: {}", triggerFilePath);
                return true;
            } catch (IOException e) {
                LOGGER.warn("Failed to delete reload trigger file: {}", triggerFilePath, e);
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the path where the trigger file is located.
     * Useful for Git hooks that need to create the file.
     *
     * @return The full path to the trigger file
     */
    public Path getTriggerPath() {
        return triggerFilePath;
    }

    /**
     * Checks if the trigger file currently exists, without deleting it.
     *
     * @return true if the trigger file exists, false otherwise
     */
    public boolean exists() {
        return Files.exists(triggerFilePath);
    }
}