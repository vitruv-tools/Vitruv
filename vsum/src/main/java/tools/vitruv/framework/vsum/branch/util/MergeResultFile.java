package tools.vitruv.framework.vsum.branch.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import tools.vitruv.framework.vsum.branch.data.ValidationResult;

/**
 * Manages the merge result files that communicate post-merge validation status from the
 * {@link tools.vitruv.framework.vsum.branch.handler.VsumMergeWatcher} to the Git
 * {@code post-merge} hook.
 *
 * <p>Two files are written per merge validation request so that the hook can display a
 * human-readable message in the terminal and a future UI component can parse structured output:
 * <ul>
 *   <li>{@code .vitruvius/merge-result-{requestId}} - human-readable text.</li>
 *   <li>{@code .vitruvius/merge-result-{requestId}.json} - machine-readable JSON.</li>
 * </ul>
 *
 * <p>A permanent merge metadata record is also written to
 * {@code .vitruvius/merges/{mergeCommitSha}.metadata} so that the full merge context
 * (source branch, target branch, validation result, conflicting files) is preserved for
 * later inspection.
 */
public class MergeResultFile {

  private static final Logger LOGGER = LogManager.getLogger(MergeResultFile.class);
  private static final String TEXT_RESULT_PREFIX = "merge-result";
  private static final String JSON_RESULT_SUFFIX = ".json";
  private static final String MERGE_DIR = "merges";
  private static final String METADATA_SUFFIX = ".metadata";

  private final Path vitruviusDir;
  private final Gson gson;

  /**
   * Creates a merge result file manager for the given repository root.
   *
   * @param repositoryRoot the root directory of the Git repository.
   */
  public MergeResultFile(Path repositoryRoot) {
    this.vitruviusDir = repositoryRoot.resolve(".vitruvius");
    this.gson = new GsonBuilder().setPrettyPrinting().create();
  }

  /**
   * Writes the merge validation result in both human-readable text and machine-readable JSON
   * format. Both files are named with the request identifier to prevent concurrent hooks from
   * reading each other's results.
   *
   * @param result    the validation result to write.
   * @param requestId the unique request identifier from {@link MergeTriggerFile}.
   * @throws IOException if the files cannot be created or written.
   */
  public void writeResult(ValidationResult result, String requestId) throws IOException {
    Files.createDirectories(vitruviusDir);
    writeTextResult(result, getTextResultPath(requestId));
    writeJsonResult(result, requestId, getJsonResultPath(requestId));
    LOGGER.debug("Wrote merge result for requestId='{}': valid={}, errors={}, warnings={}",
        requestId, result.isValid(), result.getErrors().size(), result.getWarnings().size());
  }

  /**
   * Writes a permanent merge metadata record to
   * {@code .vitruvius/merges/{mergeCommitSha}.metadata}.
   * The record captures the full merge context for later inspection.
   *
   * @param mergeCommitSha   the SHA of the merge commit created by Git.
   * @param sourceBranch     the branch that was merged in.
   * @param targetBranch     the branch that received the merge.
   * @param result           the post-merge validation result.
   * @param conflictingFiles the list of files with Git-level merge conflicts.
   * @throws IOException if the metadata file cannot be created or written.
   */
  public void writeMetadata(
      String mergeCommitSha, String sourceBranch, String targetBranch,
      ValidationResult result, List<String> conflictingFiles) throws IOException {
    Path mergesDir = vitruviusDir.resolve(MERGE_DIR);
    Files.createDirectories(mergesDir);

    Map<String, Object> metadata = new HashMap<>();
    metadata.put("mergeCommitSha", mergeCommitSha);
    metadata.put("sourceBranch", sourceBranch);
    metadata.put("targetBranch", targetBranch);
    metadata.put("timestamp", LocalDateTime.now().toString());
    metadata.put("valid", result.isValid());
    metadata.put("errors", result.getErrors());
    metadata.put("warnings", result.getWarnings());
    metadata.put("conflictingFiles", conflictingFiles);
    metadata.put("semanticConflicts", List.of());

    Path metadataPath = mergesDir.resolve(mergeCommitSha + METADATA_SUFFIX);
    Files.writeString(metadataPath, gson.toJson(metadata));
    LOGGER.debug("Wrote merge metadata: mergeCommit={}, source={}, target={}, valid={}",
        mergeCommitSha.substring(0, Math.min(7, mergeCommitSha.length())),
        sourceBranch, targetBranch, result.isValid());
  }

  /**
   * Reads and reconstructs a {@link ValidationResult} from the JSON result file for the
   * given request identifier. Returns {@code null} if no result file exists yet, indicating
   * the watcher has not finished processing.
   *
   * @param requestId the unique request identifier.
   * @return the reconstructed validation result, or {@code null} if the file does not exist.
   * @throws IOException if the file exists but cannot be read or parsed.
   */
  public ValidationResult readResult(String requestId) throws IOException {
    Path jsonResultPath = getJsonResultPath(requestId);

    if (!Files.exists(jsonResultPath)) {
      return null;
    }
    @SuppressWarnings("unchecked")
    Map<String, Object> data = gson.fromJson(Files.readString(jsonResultPath), Map.class);

    boolean valid = (Boolean) data.get("valid");

    @SuppressWarnings("unchecked")
    List<String> errors = (List<String>) data.get("errors");

    @SuppressWarnings("unchecked")
    List<String> warnings = (List<String>) data.get("warnings");

    if (valid) {
      return (warnings != null && !warnings.isEmpty())
          ? ValidationResult.successWithWarnings(warnings)
          : ValidationResult.success();
    } else {
      List<String> safeErrors = errors != null ? errors : List.of();
      return (warnings != null && !warnings.isEmpty())
          ? ValidationResult.failureWithWarnings(safeErrors, warnings)
          : ValidationResult.failure(safeErrors);
    }
  }

  /**
   * Reads the merge metadata record for the given merge commit SHA.
   * Returns {@code null} if no metadata file exists for that commit.
   *
   * @param mergeCommitSha the SHA of the merge commit.
   * @return the metadata map, or {@code null} if no metadata file is present.
   * @throws IOException if the file exists but cannot be read or parsed.
   */
  @SuppressWarnings("unchecked")
  public Map<String, Object> readMetadata(String mergeCommitSha) throws IOException {
    Path metadataPath = vitruviusDir.resolve(MERGE_DIR).resolve(mergeCommitSha + METADATA_SUFFIX);
    if (!Files.exists(metadataPath)) {
      return null;
    }
    return gson.fromJson(Files.readString(metadataPath), Map.class);
  }

  /**
   * Deletes both result files for the given request identifier. Does nothing for each file
   * that does not exist.
   *
   * @param requestId the unique request identifier.
   * @throws IOException if a file exists but cannot be deleted.
   */
  public void deleteResult(String requestId) throws IOException {
    boolean deleted = false;
    Path textResultPath = getTextResultPath(requestId);
    Path jsonResultPath = getJsonResultPath(requestId);
    if (Files.exists(textResultPath)) {
      Files.delete(textResultPath);
      deleted = true;
    }
    if (Files.exists(jsonResultPath)) {
      Files.delete(jsonResultPath);
      deleted = true;
    }
    if (deleted) {
      LOGGER.debug("Deleted merge result files for requestId='{}'", requestId);
    }
  }

  /**
   * Returns true if at least one result file exists for the given request identifier.
   *
   * @param requestId the unique request identifier.
   * @return {@code true} if at least one result file is present.
   */
  public boolean exists(String requestId) {
    return Files.exists(getTextResultPath(requestId))
        || Files.exists(getJsonResultPath(requestId));
  }

  /**
   * Returns true if a metadata file exists for the given merge commit SHA.
   *
   * @param mergeCommitSha the SHA of the merge commit.
   * @return {@code true} if the metadata file is present.
   */
  public boolean metadataExists(String mergeCommitSha) {
    return Files.exists(getMetadataPath(mergeCommitSha));
  }

  /**
   * Returns the path of the human-readable text result file for the given request.
   *
   * @param requestId the unique request identifier.
   * @return the path to the text result file.
   */
  public Path getTextResultPath(String requestId) {
    return vitruviusDir.resolve(TEXT_RESULT_PREFIX + "-" + requestId);
  }

  /**
   * Returns the path of the machine-readable JSON result file for the given request.
   *
   * @param requestId the unique request identifier.
   * @return the path to the JSON result file.
   */
  public Path getJsonResultPath(String requestId) {
    return vitruviusDir.resolve(TEXT_RESULT_PREFIX + "-" + requestId + JSON_RESULT_SUFFIX);
  }

  /**
   * Returns the path of the metadata file for the given merge commit SHA.
   *
   * @param mergeCommitSha the SHA of the merge commit.
   * @return the path to the metadata file.
   */
  public Path getMetadataPath(String mergeCommitSha) {
    return vitruviusDir.resolve(MERGE_DIR).resolve(mergeCommitSha + METADATA_SUFFIX);
  }

  private void writeTextResult(ValidationResult result, Path textResultPath) throws IOException {
    var builder = new StringBuilder();
    builder.append("\n");

    if (result.isValid()) {
      builder.append("POST-MERGE VALIDATION PASSED\n");
      builder.append("\n");
      builder.append("Merged VSUM state is consistent\n");
    } else {
      builder.append("POST-MERGE VALIDATION FAILED\n");
      builder.append("\n");
      builder.append("The merged VSUM state contains inconsistencies.\n");
      builder.append("The merge has completed but the model may require attention.\n");
    }
    builder.append("\n");
    if (result.hasErrors()) {
      builder.append("Inconsistencies detected (").append(result.getErrors().size()).append("):\n");
      for (String error : result.getErrors()) {
        builder.append(error).append("\n");
      }
      builder.append("\n");
    }
    if (result.hasWarnings()) {
      builder.append("Warnings (").append(result.getWarnings().size()).append("):\n");
      for (String warning : result.getWarnings()) {
        builder.append(warning).append("\n");
      }
      builder.append("\n");
    }
    if (result.isValid() && !result.hasWarnings()) {
      builder.append("No inconsistencies or warnings found.\n");
    }
    builder.append("\n");
    Files.writeString(textResultPath, builder.toString());
  }

  /**
   * Writes a machine-readable JSON representation of the merge validation result.
   * Includes the request identifier so the hook can verify it is reading the correct result.
   */
  private void writeJsonResult(
      ValidationResult result, String requestId, Path jsonResultPath) throws IOException {
    Map<String, Object> data = new HashMap<>();
    data.put("requestId", requestId);
    data.put("valid", result.isValid());
    data.put("timestamp", LocalDateTime.now().toString());
    data.put("errors", result.getErrors());
    data.put("warnings", result.getWarnings());
    Files.writeString(jsonResultPath, gson.toJson(data));
  }
}
