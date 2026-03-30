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
 * Vitruvius-specific metadata for a single Git branch, persisted as a JSON file under
 * {@code .vitruvius/branches/<branchName>.metadata}.
 *
 * <p>Tracks information Git does not store: lifecycle state, the parent branch this branch
 * was forked from, and creation/modification timestamps.
 *
 * <p>{@code name} and {@code parent} are immutable after construction. {@code state} may
 * change over the branch lifetime (e.g. ACTIVE → DELETED) via {@link #setState}, which
 * also refreshes {@code lastModified} automatically.
 */
@Getter
public class BranchMetadata {

  private static final Logger LOGGER = LogManager.getLogger(BranchMetadata.class);
  private static final DateTimeFormatter TIMESTAMP_FORMAT = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
  private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

  private final String name;
  private BranchState state;
  private final String parent;
  private final LocalDateTime createdAt;
  private LocalDateTime lastModified;

  /**
   * Creates a new {@link BranchMetadata} instance.
   *
   * @param name branch name.
   * @param state current lifecycle state.
   * @param parent name of the branch this branch was forked from.
   * @param createdAt creation timestamp.
   * @param lastModified last modification timestamp.
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
   * Updates the lifecycle state and refreshes {@code lastModified} to now.
   *
   * @param state the new lifecycle state, must not be null.
   */
  public void setState(BranchState state) {
    this.state = checkNotNull(state, "branch state must not be null");
    this.lastModified = LocalDateTime.now();
  }

  /**
   * Refreshes {@code lastModified} to now without changing any other field.
   * Called when a commit is made on this branch.
   */
  public void updateLastModified() {
    this.lastModified = LocalDateTime.now();
  }

  /**
   * Serializes this metadata to a pretty-printed JSON file at {@code path}.
   * Parent directories are created automatically if they do not exist.
   *
   * @param path target file path.
   * @throws IOException if the file cannot be written.
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
   * Deserializes a {@link BranchMetadata} instance from the JSON file at {@code path}.
   *
   * @param path source file path.
   * @return the reconstructed metadata.
   * @throws IOException if the file cannot be read.
   * @throws IllegalArgumentException if a required field is missing or malformed.
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
