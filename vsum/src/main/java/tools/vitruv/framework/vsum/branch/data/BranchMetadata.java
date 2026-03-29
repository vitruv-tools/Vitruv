package tools.vitruv.framework.vsum.branch.data;

import static com.google.common.base.Preconditions.checkNotNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Holds the Vitruvius-specific metadata for a single Git branch. While Git itself only stores
 * branch references as pointers to commits, this class tracks additional information that the
 * Vsum needs: the lifecycle state, the parent branch from which this branch was forked,
 * creation and modification timestamps, and a short unique identifier derived from the commit
 * hash at branch creation time.
 *
 * <p>Metadata is persisted as a JSON file under
 * {@code .vitruvius/branches/<branchName>.metadata}. This format is intentionally
 * human-readable so that supervisors and developers can inspect branch history without
 * tooling. The file is written by {@link #writeTo(Path)} and reconstructed by
 * {@link #readFrom(Path)}.
 *
 * <p>The {@code name} and {@code parent} fields are immutable after construction. The
 * {@code state} field may change over the lifetime of the branch (for example from
 * {@link BranchState#ACTIVE} to {@link BranchState#DELETED}) and is updated via
 * {@link #setState(BranchState)}, which also updates the {@code lastModified} timestamp
 * automatically.
 */
@Getter
public class BranchMetadata {
  private static final Logger LOGGER = LogManager.getLogger(BranchMetadata.class);

  /**
   * Formatter used for both writing and reading timestamp fields.
   */
  private static final DateTimeFormatter TIMESTAMP_FORMAT = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
  private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

  /**
   * -- GETTER --
   * returns the name of this branch.
   */
  private final String name;

  /**
   * -- GETTER --
   * returns the current lifecycle state of this branch.
   */
  private BranchState state;

  /**
   * -- GETTER --
   * returns the name of the parent branch from which this branch was created.
   */
  private final String parent;

  /**
   * -- GETTER --
   * returns the timestamp when this branch was created.
   */
  private final LocalDateTime createdAt;

  /**
   * -- GETTER --
   * returns the timestamp of the most recent modification to this branch metadata.
   */
  private LocalDateTime lastModified;

  /**
   * Creates a new {@link BranchMetadata} instance with all required fields.
   *
   * @param name         the name of the branch.
   * @param state        the current lifecycle state of the branch.
   * @param parent       the name of the branch from which this branch was forked.
   * @param createdAt    the timestamp when the branch was created.
   * @param lastModified the timestamp of the most recent modification to this metadata.
   */
  public BranchMetadata(String name, BranchState state, String parent,
      LocalDateTime createdAt, LocalDateTime lastModified) {
    this.name = checkNotNull(name, "branch name must not be null");
    this.state = checkNotNull(state, "branch state must not be null");
    this.parent = checkNotNull(parent, "branch parent must not be null");
    this.createdAt = checkNotNull(createdAt, "branch creation time must not be null");
    this.lastModified = checkNotNull(lastModified, "branch last modified time must not be null");
  }

  /**
   * Sets the lifecycle state of this branch and updates the {@code lastModified} timestamp
   * to the current time.
   *
   * @param state the new lifecycle state. must not be null.
   */
  public void setState(BranchState state) {
    this.state = checkNotNull(state, "branch state must not be null");
    this.lastModified = LocalDateTime.now();
  }

  /**
   * Updates lastModified to now without changing any other field.
   * Called when a commit is made on this branch.
   */
  public void updateLastModified() {
    this.lastModified = LocalDateTime.now();
  }

  /**
   * Writes all metadata fields to a JSON file at the given path.
   * Parent directories are created automatically if they do not exist, so callers do not
   * need to create the {@code .vitruvius/branches/} directory in advance.
   *
   * <p>The key insertion order is preserved (name, state, parent, createdAt, lastModified)
   * to make the file easy to read and diff in version control.
   *
   * @param path the file path to write the metadata to.
   * @throws IOException if the file or its parent directories cannot be created or written.
   */
  public void writeTo(Path path) throws IOException {
    Files.createDirectories(path.getParent());

    JsonObject json = new JsonObject();
    json.addProperty("branchName", name);
    json.addProperty("state", state.name());
    json.addProperty("parentBranch", parent);
    json.addProperty("createdAt", createdAt.format(TIMESTAMP_FORMAT));
    json.addProperty("lastModified", lastModified.format(TIMESTAMP_FORMAT));

    Files.writeString(path, GSON.toJson(json));
    LOGGER.debug("wrote branch metadata to {}", path);
  }

  /**
   * Reads branch metadata from a JSON file at the given path.
   *
   * @param path the file path to read from.
   * @return a new {@link BranchMetadata} instance populated from the file contents.
   * @throws IOException              if the file cannot be read.
   * @throws IllegalArgumentException if a required key is missing or a value cannot be parsed.
   */
  public static BranchMetadata readFrom(Path path) throws IOException {
    String content = Files.readString(path);
    JsonObject json = JsonParser.parseString(content).getAsJsonObject();

    String name = requireField(json, "branchName", path);
    BranchState state = BranchState.valueOf(requireField(json, "state", path));
    String parent = requireField(json, "parentBranch", path);
    LocalDateTime createdAt = LocalDateTime.parse(
        requireField(json, "createdAt", path), TIMESTAMP_FORMAT);
    LocalDateTime lastModified = LocalDateTime.parse(
        requireField(json, "lastModified", path), TIMESTAMP_FORMAT);

    LOGGER.debug("read branch metadata from {}", path);
    return new BranchMetadata(name, state, parent, createdAt, lastModified);
  }

  private static String requireField(JsonObject json, String key, Path path) {
    if (!json.has(key) || json.get(key).isJsonNull()) {
      throw new IllegalArgumentException(
          "missing required field '" + key + "' in branch metadata file: " + path);
    }
    return json.get(key).getAsString();
  }
}
