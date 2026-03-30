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
 * Manages the validation result files that communicate virtual model consistency status
 * from the {@link tools.vitruv.framework.vsum.branch.handler.VsumValidationWatcher}
 * to the Git {@code pre-commit} hook.
 *
 * <p>Two files are written per validation request so that the hook can display a
 * human-readable message in the terminal and a future UI component can parse the
 * structured output:
 * <ul>
 *   <li>{@code .vitruvius/validation-result-{requestId}} - human-readable text.</li>
 *   <li>{@code .vitruvius/validation-result-{requestId}.json} - machine-readable JSON.</li>
 * </ul>
 *
 * <p>Each validation request receives its own pair of result files identified by the
 * request identifier so that concurrent hook invocations cannot read each other's results.
 *
 * <p>Workflow:
 * <ol>
 *   <li>{@link tools.vitruv.framework.vsum.branch.handler.VsumValidationWatcher} validates
 *       the model.</li>
 *   <li>Writes result files with the request identifier (text and JSON).</li>
 *   <li>The Git pre-commit hook reads its specific result file by request identifier.</li>
 *   <li>The hook allows or blocks the commit based on the validation status.</li>
 *   <li>The hook deletes the result files after reading.</li>
 * </ol>
 *
 * <p>A UI component reading the JSON file for structured display of validation history
 * is planned for a future iteration.
 */
public class ValidationResultFile {

  private static final Logger LOGGER = LogManager.getLogger(ValidationResultFile.class);
  private static final String TEXT_RESULT_PREFIX = "validation-result";
  private static final String JSON_RESULT_SUFFIX = ".json";

  private final Path vitruviusDir;
  private final Gson gson;

  /**
   * Creates a validation result file manager for the given repository.
   *
   * @param repositoryRoot the root directory of the Git repository.
   */
  public ValidationResultFile(Path repositoryRoot) {
    this.vitruviusDir = repositoryRoot.resolve(".vitruvius");
    this.gson = new GsonBuilder().setPrettyPrinting().create();
  }

  /**
   * Writes the validation result in both human-readable text and machine-readable JSON format.
   * Both files are named with the request identifier to prevent concurrent commits from
   * reading each other's results.
   *
   * <p>Files created:
   * <ul>
   *   <li>{@code .vitruvius/validation-result-{requestId}}</li>
   *   <li>{@code .vitruvius/validation-result-{requestId}.json}</li>
   * </ul>
   *
   * @param result    the validation result to write.
   * @param requestId the unique request identifier from {@link ValidationTriggerFile}.
   * @throws IOException if the files cannot be created or written.
   */
  public void writeResult(ValidationResult result, String requestId) throws IOException {
    Files.createDirectories(vitruviusDir);
    writeTextResult(result, getTextResultPath(requestId));
    writeJsonResult(result, getJsonResultPath(requestId));
    LOGGER.debug("Wrote validation result for requestId='{}': valid={}, errors={}, warnings={}",
        requestId, result.isValid(), result.getErrors().size(), result.getWarnings().size());
  }

  /**
   * Reads and reconstructs a {@link ValidationResult} from the JSON result file for the
   * given request identifier. Returns {@code null} if no result file exists yet, which
   * indicates the watcher has not finished processing the request.
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
    var errors = (List<String>) data.get("errors");

    @SuppressWarnings("unchecked")
    var warnings = (List<String>) data.get("warnings");

    // reconstruct the most specific factory method that matches the stored state
    // so that the round-tripped result is equivalent to the original.
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
   * Deletes both result files for the given request identifier.
   * Does nothing for each file that does not exist.
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
      LOGGER.debug("Deleted validation result files for requestId='{}'", requestId);
    }
  }

  /**
   * Returns true if at least one result file exists for the given request identifier.
   * The text file is written first, so this returns true as soon as the write begins.
   *
   * @param requestId the unique request identifier.
   * @return {@code true} if at least one result file is present.
   */
  public boolean exists(String requestId) {
    return Files.exists(getTextResultPath(requestId))
        || Files.exists(getJsonResultPath(requestId));
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
   * Deletes result files for the fixed identifier {@code "legacy"}.
   *
   * @throws IOException if the files cannot be deleted.
   * @deprecated Use {@link #deleteResult(String)} with a request identifier.
   */
  @Deprecated
  public void deleteResult() throws IOException {
    LOGGER.warn("Using deprecated deleteResult() without request ID"
        + " - use deleteResult(requestId) instead");
    deleteResult("legacy");
  }

  /**
   * Returns true if result files exist for the fixed identifier {@code "legacy"}.
   *
   * @return {@code true} if legacy result files are present.
   * @deprecated Use {@link #exists(String)} with a request identifier.
   */
  @Deprecated
  public boolean exists() {
    return exists("legacy");
  }

  /**
   * Writes a human-readable text summary of the validation result to the given path.
   * The output is intended to be displayed directly in the terminal by the Git hook.
   */
  private void writeTextResult(ValidationResult result, Path textResultPath) throws IOException {
    var sb = new StringBuilder();

    // status line shown at the top of the hook output.
    sb.append("\n");
    if (result.isValid()) {
      sb.append("✔ VALIDATION PASSED\n");
    } else {
      sb.append("✘ VALIDATION FAILED\n");
    }
    sb.append("\n\n");

    // list each error so the developer knows exactly what to fix.
    if (result.hasErrors()) {
      sb.append("Errors (").append(result.getErrors().size()).append("):\n");
      for (String error : result.getErrors()) {
        sb.append("  ✘ ").append(error).append("\n");
      }
      sb.append("\n");
    }

    // list warnings even on success so the developer is aware of non-blocking issues.
    if (result.hasWarnings()) {
      sb.append("Warnings (").append(result.getWarnings().size()).append("):\n");
      for (String warning : result.getWarnings()) {
        sb.append("  ⚠ ").append(warning).append("\n");
      }
      sb.append("\n");
    }

    // affirmative message for a completely clean result.
    if (result.isValid() && !result.hasWarnings()) {
      sb.append("No errors or warnings found.\n");
      sb.append("V-SUM is consistent and ready to commit.\n\n");
    }

    // action hint so the developer knows what to do when the commit is blocked.
    if (!result.isValid()) {
      sb.append("\nFix errors above before committing.\n\n");
    }

    Files.writeString(textResultPath, sb.toString());
  }

  /**
   * Writes a machine-readable JSON representation of the validation result to the given path.
   * The JSON includes the validity flag, timestamp, and the full error and warning lists so
   * that a future UI component can parse and display them.
   */
  private void writeJsonResult(ValidationResult result, Path jsonResultPath) throws IOException {
    Map<String, Object> data = new HashMap<>();
    data.put("valid", result.isValid());
    data.put("timestamp", LocalDateTime.now().toString());
    data.put("errors", result.getErrors());
    data.put("warnings", result.getWarnings());
    Files.writeString(jsonResultPath, gson.toJson(data));
  }
}
