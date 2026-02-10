package tools.vitruv.framework.vsum.branch.util;

import tools.vitruv.framework.vsum.branch.data.ValidationResult;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Manages validation result files that communicate V-SUM validation status.
 *
 * <p>Creates two files per validation request:
 * <ul>
 *   <li><b>validation-result-{requestId}</b> - Human-readable text to show in terminal</li>
 *   <li><b>validation-result-{requestId}.json</b> - Machine-readable JSON for UI/API/future use</li>
 * </ul>
 *
 * <p>Each validation request gets its own result files identified by request ID.
 * This prevents concurrent commits from reading each other's validation results.
 *
 * <p>Workflow:
 * <ol>
 *   <li>VsumValidationWatcher validates VSUM</li>
 *   <li>Writes result files with request ID (text + JSON)</li>
 *   <li>Git pre-commit hook reads its specific result file by request ID</li>
 *   <li>Hook allows/blocks commit based on validation status</li>
 *   <li>Result files are cleaned up after reading</li>
 *   <li>todo: UI reads JSON file for structured display</li>
 * </ol>
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
     * @param repositoryRoot The root directory of the Git repository
     */
    public ValidationResultFile(Path repositoryRoot) {
        this.vitruviusDir = repositoryRoot.resolve(".vitruvius");
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    /**
     * Writes validation result in both text and JSON formats with request-specific filenames.
     *
     * <p>Result files are named with the request ID to prevent concurrent commits
     * from reading each other's results.
     *
     * <p>Files created:
     * <ul>
     *   <li>{@code .vitruvius/validation-result-{requestId}}</li>
     *   <li>{@code .vitruvius/validation-result-{requestId}.json}</li>
     * </ul>
     *
     * @param result the validation result to write
     * @param requestId the unique request identifier (from ValidationTriggerFile)
     * @throws IOException if files cannot be written
     */
    public void writeResult(ValidationResult result, String requestId) throws IOException {
        Files.createDirectories(vitruviusDir);

        Path textResultPath = getTextResultPath(requestId);
        Path jsonResultPath = getJsonResultPath(requestId);

        writeTextResult(result, textResultPath);
        writeJsonResult(result, jsonResultPath);

        LOGGER.debug("Wrote validation result for requestId={}: valid={}, errors={}, warnings={}", requestId, result.isValid(), result.getErrors().size(), result.getWarnings().size());
    }

    /**
     * Writes human-readable text result for developers.
     *
     * <p> Now accepts explicit path parameter for request-specific files.
     */
    private void writeTextResult(ValidationResult result, Path textResultPath) throws IOException {
        StringBuilder sb = new StringBuilder();
        // Header
        sb.append("\n");
        if (result.isValid()) {
            sb.append("✔ VALIDATION PASSED\n");
        } else {
            sb.append("✘ VALIDATION FAILED\n");
        }
        sb.append("\n");
        sb.append("\n");

        // Errors section
        if (result.hasErrors()) {
            sb.append("Errors (").append(result.getErrors().size()).append("):\n");
            for (String error : result.getErrors()) {
                sb.append("  ✘ ").append(error).append("\n");
            }
            sb.append("\n");
        }

        // Warnings section
        if (result.hasWarnings()) {
            sb.append("Warnings (").append(result.getWarnings().size()).append("):\n");
            for (String warning : result.getWarnings()) {
                sb.append("  ⚠ ").append(warning).append("\n");
            }
            sb.append("\n");
        }

        // Success message
        if (result.isValid() && !result.hasWarnings()) {
            sb.append("No errors or warnings found.\n");
            sb.append("V-SUM is consistent and ready to commit.\n");
            sb.append("\n");
        }

        // Footer with action
        if (!result.isValid()) {
            sb.append("\n");
            sb.append("Fix errors above before committing.\n");
            sb.append("\n");
        }

        Files.writeString(textResultPath, sb.toString());
    }

    /**
     * Writes machine-readable JSON result for UI/API.
     *
     * <p> Now accepts explicit path parameter for request-specific files.
     */
    private void writeJsonResult(ValidationResult result, Path jsonResultPath) throws IOException {
        Map<String, Object> data = new HashMap<>();
        data.put("valid", result.isValid());
        data.put("timestamp", LocalDateTime.now().toString());
        data.put("errors", result.getErrors());
        data.put("warnings", result.getWarnings());

        String json = gson.toJson(data);
        Files.writeString(jsonResultPath, json);
    }

    /**
     * Reads validation result from request-specific JSON file.
     *
     * <p> Reads result for a specific request ID.
     *
     * @param requestId the unique request identifier
     * @return the validation result, or null if file doesn't exist
     * @throws IOException if file exists but cannot be read
     */
    public ValidationResult readResult(String requestId) throws IOException {
        Path jsonResultPath = getJsonResultPath(requestId);

        if (!Files.exists(jsonResultPath)) {
            return null;
        }

        String json = Files.readString(jsonResultPath);

        @SuppressWarnings("unchecked")
        Map<String, Object> data = gson.fromJson(json, Map.class);

        boolean valid = (Boolean) data.get("valid");

        @SuppressWarnings("unchecked")
        var errors = (java.util.List<String>) data.get("errors");

        @SuppressWarnings("unchecked")
        var warnings = (java.util.List<String>) data.get("warnings");

        if (valid) {
            if (warnings != null && !warnings.isEmpty()) {
                return ValidationResult.successWithWarnings(warnings);
            }
            return ValidationResult.success();
        } else {
            return ValidationResult.failure(errors != null ? errors : java.util.List.of());
        }
    }

    /**
     * Deletes both validation result files for a specific request.
     *
     * <p> Deletes request-specific files.
     *
     * @param requestId the unique request identifier
     * @throws IOException if files exist but cannot be deleted
     */
    public void deleteResult(String requestId) throws IOException {
        Path textResultPath = getTextResultPath(requestId);
        Path jsonResultPath = getJsonResultPath(requestId);

        boolean textDeleted = false;
        boolean jsonDeleted = false;

        if (Files.exists(textResultPath)) {
            Files.delete(textResultPath);
            textDeleted = true;
        }

        if (Files.exists(jsonResultPath)) {
            Files.delete(jsonResultPath);
            jsonDeleted = true;
        }

        if (textDeleted || jsonDeleted) {
            LOGGER.debug("Deleted validation result files for requestId={}", requestId);
        }
    }

    /**
     * Checks if validation result files exist for a specific request.
     *
     * <p> Checks for request-specific files.
     *
     * @param requestId the unique request identifier
     * @return true if at least one result file exists for this request
     */
    public boolean exists(String requestId) {
        return Files.exists(getTextResultPath(requestId)) || Files.exists(getJsonResultPath(requestId));
    }

    /**
     * Gets the path for the text result file for a specific request.
     *
     * @param requestId the unique request identifier
     * @return path to the text result file
     */
    public Path getTextResultPath(String requestId) {
        return vitruviusDir.resolve(TEXT_RESULT_PREFIX + "-" + requestId);
    }

    /**
     * Gets the path for the JSON result file for a specific request.
     *
     * @param requestId the unique request identifier
     * @return path to the JSON result file
     */
    public Path getJsonResultPath(String requestId) {
        return vitruviusDir.resolve(TEXT_RESULT_PREFIX + "-" + requestId + JSON_RESULT_SUFFIX);
    }

    /**
     * DEPRECATED: Use {@link #writeResult(ValidationResult, String)} instead.
     *
     * <p>This method is kept for backward compatibility but will write to a file
     * named "validation-result-legacy" which won't be read by the updated pre-commit hook.
     *
     * @param result the validation result to write
     * @throws IOException if files cannot be written
     * @deprecated Use writeResult(ValidationResult, String) with a request ID
     */
    @Deprecated
    public void writeResult(ValidationResult result) throws IOException {
        LOGGER.warn("Using deprecated writeResult() without request ID. Use writeResult(result, requestId) instead.");
        writeResult(result, "legacy");
    }

    /**
     * DEPRECATED: Use {@link #readResult(String)} instead.
     *
     * @return the validation result
     * @throws UnsupportedOperationException always
     * @deprecated Use readResult(String requestId) instead
     */
    @Deprecated
    public ValidationResult readResult() {
        throw new UnsupportedOperationException("Reading validation results without request ID is not supported. " + "Use readResult(String requestId) instead.");
    }

    /**
     * DEPRECATED: Use {@link #deleteResult(String)} instead.
     *
     * @throws IOException if files cannot be deleted
     * @deprecated Use deleteResult(String requestId) instead
     */
    @Deprecated
    public void deleteResult() throws IOException {
        LOGGER.warn("Using deprecated deleteResult() without request ID. Use deleteResult(requestId) instead.");
        deleteResult("legacy");
    }

    /**
     * DEPRECATED: Use {@link #exists(String)} instead.
     *
     * @return true if legacy result files exist
     * @deprecated Use exists(String requestId) instead
     */
    @Deprecated
    public boolean exists() {
        return exists("legacy");
    }
}