package tools.vitruv.framework.vsum.branch;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;


public class BranchMetadata {
    private static final Logger LOGGER = LogManager.getLogger(BranchMetadata.class);
    private static final DateTimeFormatter TIMESTAMP_FORMAT = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    private final String name;
    private final String uid;
    private BranchState state;
    private final String parent;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    /**
     * Creates a new {@link BranchMetadata} instance.
     *
     * @param name      The name of the branch.
     * @param uid       A short unique identifier derived from the Git commit SHA.
     * @param state     The current lifecycle state of the branch.
     * @param parent    The name of the branch from which this branch was created.
     * @param createdAt The creation timestamp of the branch.
     * @param updatedAt The last updated timestamp of the branch.
     */
    public BranchMetadata(String name, String uid, BranchState state, String parent, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.name = checkNotNull(name, "Branch name must not be null");
        this.uid = checkNotNull(uid, "Branch uid must not be null");
        this.state = checkNotNull(state, "Branch state must not be null");
        this.parent = checkNotNull(parent, "Branch parent must not be null");
        this.createdAt = checkNotNull(createdAt, "Branch creation time must not be null");
        this.updatedAt = checkNotNull(updatedAt, "Branch update time must not be null");
    }

    /**
     * Returns the name of this branch
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the short unique identifier of this branch
     */
    public String getUid() {
        return uid;
    }

    /**
     * Returns the current lifecycle state of this branch
     */
    public BranchState getState() {
        return state;
    }

    /**
     * Set the new lifecycle state of this branch
     */
    public void setState(BranchState state) {
        this.state = checkNotNull(state, "Branch state must not be null");
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Returns the name of the parent branch from which the branch was created
     */
    public String getParent() {
        return parent;
    }

    /**
     * Returns the timestamp when this branch was created
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * Returns the timestamp of the last update to this ranch
     */
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    /**
     * Writes all branch metadata to a file at the given path.
     * Parent directories will be created if they do not exist.
     *
     * @param path the file path to write the metadata to
     * @throws IOException if file cannot be written
     */
    public void writeTo(Path path) throws IOException {
        Files.createDirectories(path.getParent());
        Map<String, String> entries = new LinkedHashMap<>();
        entries.put("name", name);
        entries.put("uid", uid);
        entries.put("state", state.name());
        entries.put("parent", parent);
        entries.put("createdAt", createdAt.format(TIMESTAMP_FORMAT));
        entries.put("updatedAt", updatedAt.format(TIMESTAMP_FORMAT));

        var lines = entries.entrySet().stream().map(e -> e.getKey() + "=" + e.getValue()).toList();
        Files.write(path, lines);
        LOGGER.debug("Wrote branch metadata to {}", path);
    }

    /**
     * Reads branch metadata from a file at the given path.
     * Any keys not recognized by this version of the metadata format will be ignored.
     *
     * @param path The file path to read from.
     * @return A new {@link BranchMetadata} instance retrieved from the file.
     * @throws IOException if the file cannot be read.
     * @throws IllegalArgumentException If required keys are missing or values are invalid.
     */
    public static BranchMetadata readFrom(Path path) throws IOException {
        var lines = Files.readAllLines(path);
        Map<String, String> entries = new LinkedHashMap<>();
        for (var line : lines) {
            var idx = line.indexOf('=');
            if (idx > 0) {
                entries.put(line.substring(0, idx), line.substring(idx + 1));
            }
        }

        String name = requireEntry(entries, "name", path);
        String uid = requireEntry(entries, "uid", path);
        BranchState state = BranchState.valueOf(requireEntry(entries, "state", path));
        String parent = requireEntry(entries, "parent", path);
        LocalDateTime createdAt = LocalDateTime.parse(requireEntry(entries, "createdAt", path), TIMESTAMP_FORMAT);
        LocalDateTime updatedAt = LocalDateTime.parse(requireEntry(entries, "updatedAt", path), TIMESTAMP_FORMAT);

        LOGGER.debug("Read branch metadata from {}", path);
        return new BranchMetadata(name, uid, state, parent, createdAt, updatedAt);
    }

    private static String requireEntry(Map<String, String> entries, String key, Path path) {
        var value = entries.get(key);
        if (value == null) {
            throw new IllegalArgumentException("Missing required key '" + key + "' in branch metadata file: " + path);
        }
        return value;
    }

}
