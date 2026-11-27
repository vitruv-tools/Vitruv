package tools.vitruv.framework.vsum.versioning.data;

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
import tools.vitruv.framework.vsum.branch.data.MaturityLevel;

/**
 * Holds Vitruvius-specific metadata for a single model version (Git annotated tag).
 *
 * <p>Persisted at {@code .vitruvius/versions/<versionId>.metadata} in JSON format,
 * mirroring what Git stores in the annotated tag object but making it queryable
 * without JGit calls.
 *
 * <p>All fields except {@code maturity} are immutable after construction.
 * Maturity can be updated via {@link #setMaturity(MaturityLevel)} to reflect
 * the user's assessment of a version's readiness over time.
 *
 * <p>Covers DE-6: version ID, timestamp, author, optional description.
 */

@Getter
public class VersionMetadata {

  private static final Logger LOGGER = LogManager.getLogger(VersionMetadata.class);
  private static final DateTimeFormatter TIMESTAMP_FORMAT = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
  private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

  /** The version identifier - matches the Git tag name (e.g. "v1.0"). */
  private final String versionId;

  /** The full SHA of the commit this version points to. */
  private final String commitSha;

  /** The branch on which this version was created. */
  private final String branch;

  /** Name of the person who created the version tag. */
  private final String taggerName;

  /** Email of the person who created the version tag. */
  private final String taggerEmail;

  /** Timestamp when the version tag was created. */
  private final LocalDateTime createdAt;

  /** Optional human-readable description of what this version represents. */
  private final String description;

  /** Maturity level of this version. Defaults to {@link MaturityLevel#DRAFT} on creation. */
  private MaturityLevel maturity;

  public VersionMetadata(String versionId, String commitSha, String branch,
                       String taggerName, String taggerEmail,
                       LocalDateTime createdAt, String description,
                       MaturityLevel maturity) {
    this.versionId = checkNotNull(versionId, "version ID must not be null");
    this.commitSha = checkNotNull(commitSha, "commit SHA must not be null");
    this.branch = checkNotNull(branch, "branch must not be null");
    this.taggerName = checkNotNull(taggerName, "tagger name must not be null");
    this.taggerEmail = checkNotNull(taggerEmail, "tagger email must not be null");
    this.createdAt = checkNotNull(createdAt, "createdAt must not be null");
    this.description = description != null ? description : "";
    this.maturity = checkNotNull(maturity, "maturity must not be null");
  }

  /**
   * Updates the maturity level of this version.
   *
   * @param maturity the new maturity level, must not be null.
   */
  public void setMaturity(MaturityLevel maturity) {
    this.maturity = checkNotNull(maturity, "maturity must not be null");
  }

  /**
   * Writes this version metadata to a JSON file at the given path.
   * Parent directories are created automatically.
   */
  public void writeTo(Path path) throws IOException {
    Files.createDirectories(path.getParent());

    JsonObject json = new JsonObject();
    json.addProperty("versionId", versionId);
    json.addProperty("commitSha", commitSha);
    json.addProperty("branch", branch);
    json.addProperty("taggerName", taggerName);
    json.addProperty("taggerEmail", taggerEmail);
    json.addProperty("createdAt", createdAt.format(TIMESTAMP_FORMAT));
    json.addProperty("description", description);
    json.addProperty("maturity", maturity.name());

    Files.writeString(path, GSON.toJson(json));
    LOGGER.debug("Wrote version metadata to {}", path);
  }

  /**
   * Reads version metadata from a JSON file at the given path.
   */
  public static VersionMetadata readFrom(Path path) throws IOException {
    String content = Files.readString(path);
    JsonObject json = JsonParser.parseString(content).getAsJsonObject();

    String versionId = requireField(json, "versionId", path);
    String commitSha = requireField(json, "commitSha", path);
    String branch = requireField(json, "branch", path);
    String taggerName = requireField(json, "taggerName", path);
    String taggerEmail = requireField(json, "taggerEmail", path);
    LocalDateTime createdAt = LocalDateTime.parse(
            requireField(json, "createdAt", path), TIMESTAMP_FORMAT);
    String description = json.has("description")
            ? json.get("description").getAsString() : "";
    MaturityLevel maturity = MaturityLevel.DRAFT;
    if (json.has("maturity") && !json.get("maturity").isJsonNull()) {
      try {
        maturity = MaturityLevel.valueOf(json.get("maturity").getAsString());
      } catch (IllegalArgumentException e) {
        LOGGER.warn("Unknown maturity '{}' in {}, defaulting to DRAFT",
                json.get("maturity").getAsString(), path);
      }
    }

    LOGGER.debug("Read version metadata from {}", path);
    return new VersionMetadata(versionId, commitSha, branch,
            taggerName, taggerEmail, createdAt, description, maturity);
  }

  private static String requireField(JsonObject json, String key, Path path) {
    if (!json.has(key) || json.get(key).isJsonNull()) {
      throw new IllegalArgumentException(
                "missing required field '" + key + "' in version metadata: " + path);
    }
    return json.get(key).getAsString();
  }

  @Override
  public String toString() {
    return "VersionMetadata{versionId='" + versionId + "'"
              + ", commitSha='" + commitSha.substring(0, Math.min(7, commitSha.length())) + "'"
              + ", branch='" + branch + "'"
              + ", tagger='" + taggerName + "'"
              + ", createdAt=" + createdAt
              + ", description='" + description + "'"
              + ", maturity=" + maturity
              + "}";
  }
}