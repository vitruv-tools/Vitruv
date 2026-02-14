package tools.vitruv.framework.vsum.branch.data;

import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Holds the Vitruvius-specific metadata for a single Git branch. While Git itself only stores branch references as pointers to commits,
 * this class tracks additional information that the Vsum needs: the lifecycle state, the parent branch from which this
 * branch was forked, creation and modification timestamps, and a short unique identifier derived from the commit hash at branch creation time.
 *
 * <p>metadata is persisted in a simple key-value text file (one {@code key=value} entry per line) under {@code .vitruvius/branches/<branchName>.metadata}.
 * This format is intentionally human-readable so that supervisors and developers can inspect branch history without tooling.
 * The file is written by {@link #writeTo(Path)} and reconstructed by {@link #readFrom(Path)}.
 *
 * <p>The {@code name} and {@code parent} fields are immutable after construction. The {@code state} field may change over the lifetime of the branch
 * (for example from {@link BranchState#ACTIVE} to {@link BranchState#DELETED}) and is updated via {@link #setState(BranchState)}, which also updates the {@code lastModified} timestamp automatically.
 */
@Getter
public class BranchMetadata {
    private static final Logger LOGGER = LogManager.getLogger(BranchMetadata.class);

    /**
     * Formatter used for both writing and reading timestamp fields.
     */
    private static final DateTimeFormatter TIMESTAMP_FORMAT = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    /**
     * -- GETTER --
     *  returns the name of this branch.
     */
    private final String name;
    /**
     * -- GETTER --
     *  returns the short unique identifier of this branch, derived from the first seven characters of the commit hash at the time the branch was created.
     */
    private final String uid;
    /**
     * -- GETTER --
     *  returns the current lifecycle state of this branch.
     */
    private BranchState state;
    /**
     * -- GETTER --
     *  returns the name of the parent branch from which this branch was created.
     */
    private final String parent;
    /**
     * -- GETTER --
     *  returns the timestamp when this branch was created.
     */
    private final LocalDateTime createdAt;
    /**
     * -- GETTER --
     *  returns the timestamp of the most recent modification to this branch metadata.
     */
    private LocalDateTime lastModified;

    /**
     * creates a new {@link BranchMetadata} instance with all required fields.
     *
     * @param name         the name of the branch.
     * @param uid          a short unique identifier derived from the first seven characters of the Git commit hash at the time the branch was created.
     * @param state        the current lifecycle state of the branch.
     * @param parent       the name of the branch from which this branch was forked.
     * @param createdAt    the timestamp when the branch was created.
     * @param lastModified the timestamp of the most recent modification to this metadata.
     */
    public BranchMetadata(String name, String uid, BranchState state, String parent, LocalDateTime createdAt, LocalDateTime lastModified) {
        this.name = checkNotNull(name, "branch name must not be null");
        this.uid = checkNotNull(uid, "branch unique identifier must not be null");
        this.state = checkNotNull(state, "branch state must not be null");
        this.parent = checkNotNull(parent, "branch parent must not be null");
        this.createdAt = checkNotNull(createdAt, "branch creation time must not be null");
        this.lastModified = checkNotNull(lastModified, "branch last modified time must not be null");
    }

    /**
     * Sets the lifecycle state of this branch and updates the {@code lastModified} timestamp to the current time.
     *
     * @param state the new lifecycle state. must not be null.
     */
    public void setState(BranchState state) {
        this.state = checkNotNull(state, "branch state must not be null");
        this.lastModified = LocalDateTime.now();
    }

    /**
     * Writes all metadata fields to a file at the given path in {@code key=value} format, one entry per line.
     * Parent directories are created automatically if they do not exist, so callers do not need to create the {@code .vitruvius/branches/} directory in advance.
     *
     * <p>The key insertion order is preserved (name, uid, state, parent, createdAt, updatedAt) to make the file easy to read and diff in version control.
     *
     * @param path the file path to write the metadata to.
     * @throws IOException if the file or its parent directories cannot be created or written.
     */
    public void writeTo(Path path) throws IOException {
        Files.createDirectories(path.getParent());

        // use a linked map to preserve a stable, readable field order in the output file.
        Map<String, String> entries = new LinkedHashMap<>();
        entries.put("name", name);
        entries.put("uid", uid);
        entries.put("state", state.name());
        entries.put("parent", parent);
        entries.put("createdAt", createdAt.format(TIMESTAMP_FORMAT));
        entries.put("updatedAt", lastModified.format(TIMESTAMP_FORMAT));

        // each map entry becomes one line in the file, formatted as key=value.
        var lines = entries.entrySet().stream().map(e -> e.getKey() + "=" + e.getValue()).toList();
        Files.write(path, lines);
        LOGGER.debug("wrote branch metadata to {}", path);
    }

    /**
     * Reads branch metadata from a {@code key=value} file at the given path. File must have been written by {@link #writeTo(Path)} or conform to the same format.
     * Unrecognized keys are ignored to allow forward compatibility with future metadata versions.
     *
     * @param path the file path to read from.
     * @return a new {@link BranchMetadata} instance populated from the file contents.
     * @throws IOException if the file cannot be read.
     * @throws IllegalArgumentException if a required key is missing or a value cannot be parsed.
     */
    public static BranchMetadata readFrom(Path path) throws IOException {
        var lines = Files.readAllLines(path);
        Map<String, String> entries = new LinkedHashMap<>();
        for (var line : lines) {
            var separatorIndex = line.indexOf('=');
            // only accept lines where the separator is not the first character, so that lines with an empty key (for example "=value") are skipped
            // rather than producing a metadata entry with a blank key.
            if (separatorIndex > 0) {
                entries.put(line.substring(0, separatorIndex), line.substring(separatorIndex + 1));
            }
        }

        String name = requireEntry(entries, "name", path);
        String uid = requireEntry(entries, "uid", path);
        BranchState state = BranchState.valueOf(requireEntry(entries, "state", path));
        String parent = requireEntry(entries, "parent", path);
        LocalDateTime createdAt = LocalDateTime.parse(requireEntry(entries, "createdAt", path), TIMESTAMP_FORMAT);
        LocalDateTime updatedAt = LocalDateTime.parse(requireEntry(entries, "updatedAt", path), TIMESTAMP_FORMAT);

        LOGGER.debug("read branch metadata from {}", path);
        return new BranchMetadata(name, uid, state, parent, createdAt, updatedAt);
    }

    /**
     * Looks up a required key in the parsed entries map and returns its value. Throws an {@link IllegalArgumentException} if the key is absent,
     * so that the caller receives a clear message about which field is missing rather than a generic null pointer error.
     *
     * @param entries the parsed key-value pairs from the metadata file.
     * @param key  the required key to look up.
     * @param path the source file path, included in the error message for easier debugging.
     * @return the value associated with the key.
     * @throws IllegalArgumentException if the key is not present in the entries map.
     */
    private static String requireEntry(Map<String, String> entries, String key, Path path) {
        var value = entries.get(key);
        if (value == null) {
            throw new IllegalArgumentException("missing required key '" + key + "' in branch metadata file: " + path);
        }
        return value;
    }
}