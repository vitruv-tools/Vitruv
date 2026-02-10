package tools.vitruv.framework.vsum.branch.util;

import tools.vitruv.framework.vsum.branch.data.ValidationResult;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Getter;
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
 * <p>Creates two files:
 * <ul>
 *   <li><b>validation-result</b> - Human-readable text to show in terminal</li>
 *   <li><b>validation-result.json</b> - Machine-readable JSON for UI/API/future use</li>
 * </ul>
 *
 * <p>Workflow:
 * <ol>
 *   <li>VsumValidationWatcher validates VSUM</li>
 *   <li>Writes result files (text + JSON)</li>
 *   <li>Git pre-commit hook reads text file and displays to developer</li>
 *   <li>Hook allows/blocks commit based on validation status</li>
 *   <li>todo: UI reads JSON file for structured display</li>
 * </ol>
 */
public class ValidationResultFile {
    private static final Logger LOGGER = LogManager.getLogger(ValidationResultFile.class);
    private static final String TEXT_RESULT_FILENAME = "validation-result";
    private static final String JSON_RESULT_FILENAME = "validation-result.json";

    /**
     * -- GETTER --
     *
     */
    @Getter
    private final Path textResultPath;
    /**
     * -- GETTER --
     *
     */
    @Getter
    private final Path jsonResultPath;
    private final Gson gson;

    /**
     * Creates a validation result file manager for the given repository.
     *
     * @param repositoryRoot The root directory of the Git repository
     */
    public ValidationResultFile(Path repositoryRoot) {
        Path vitruviusDir = repositoryRoot.resolve(".vitruvius");
        this.textResultPath = vitruviusDir.resolve(TEXT_RESULT_FILENAME);
        this.jsonResultPath = vitruviusDir.resolve(JSON_RESULT_FILENAME);
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    /**
     * Writes validation result in both text and JSON formats.
     *
     * @param result the validation result to write
     * @throws IOException if files cannot be written
     */
    public void writeResult(ValidationResult result) throws IOException {
        Files.createDirectories(textResultPath.getParent());
        writeTextResult(result);
        writeJsonResult(result);
        LOGGER.debug("Wrote validation result: valid={}, errors={}, warnings={}", result.isValid(), result.getErrors().size(), result.getWarnings().size());
    }

    /**
     * Writes human-readable text result for developers.
     */
    private void writeTextResult(ValidationResult result) throws IOException {
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
     */
    private void writeJsonResult(ValidationResult result) throws IOException {
        Map<String, Object> data = new HashMap<>();
        data.put("valid", result.isValid());
        data.put("timestamp", LocalDateTime.now().toString());
        data.put("errors", result.getErrors());
        data.put("warnings", result.getWarnings());
        String json = gson.toJson(data);
        Files.writeString(jsonResultPath, json);
    }

    /**
     * Reads validation result from JSON file.
     *
     * <p>todo: Implement JSON parsing with proper error handling.
     * For now, this method is not needed as only the Git hook reads the text file.
     *
     * @return the validation result
     * @throws UnsupportedOperationException always (not yet implemented)
     */
    public ValidationResult readResult() {
        throw new UnsupportedOperationException("Reading validation results not yet implemented. " + "Will be added in Iteration 2 with proper JSON parsing. " + "Git hooks read the text file directly.");
    }

    /**
     * Deletes both validation result files.
     *
     * @throws IOException if files cannot be deleted
     */
    public void deleteResult() throws IOException {
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
            LOGGER.debug("Deleted validation result files");
        }
    }

    /**
     * Checks if validation result files exist.
     *
     * @return true if at least one result file exists
     */
    public boolean exists() {
        return Files.exists(textResultPath) || Files.exists(jsonResultPath);
    }

}