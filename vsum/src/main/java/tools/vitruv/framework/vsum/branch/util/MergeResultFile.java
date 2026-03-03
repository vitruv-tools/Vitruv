package tools.vitruv.framework.vsum.branch.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import tools.vitruv.framework.vsum.branch.data.ValidationResult;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
     * @param repositoryRoot the root directory of the Git repository
     */
    public MergeResultFile(Path repositoryRoot) {
        this.vitruviusDir = repositoryRoot.resolve(".vitruvius");
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    public void writeResult(ValidationResult result, String requestId) throws IOException {
        Files.createDirectories(vitruviusDir);
        writeTextResult(result, getTextResultPath(requestId));
        writeJsonResult(result, requestId, getJsonResultPath(requestId));
        LOGGER.debug("Wrote merge result for requestId='{}': valid={}, errors={}, warnings={}", requestId, result.isValid(), result.getErrors().size(), result.getWarnings().size());
    }

    public void writeMetadata(String mergeCommitSha, String sourceBranch, String targetBranch, ValidationResult result, List<String> conflictingFiles) throws IOException {
        Path mergesDir = vitruviusDir.resolve(MERGE_DIR);
        Path metadataPath = mergesDir.resolve(mergeCommitSha + METADATA_SUFFIX);
        Files.createDirectories(mergesDir);

        Map<String, Object> metadata = new HashMap<>();
        metadata.put("mergeCommitSha", mergeCommitSha);
        metadata.put("sourceBranch", sourceBranch);
        metadata.put("targetBranch", targetBranch);
        metadata.put("timestamp", LocalDateTime.now().toString());
        metadata.put("valid", result.isValid());
        metadata.put("errors", result.getErrors());
        metadata.put("warnings", result.getWarnings());

        metadata.put("conflictingFiles", conflictingFiles);  // Git-level file conflicts
        metadata.put("semanticConflicts", List.of());

        Files.writeString(metadataPath, gson.toJson(metadata));
        LOGGER.debug("Wrote merge metadata: mergeCommit={}, source={}, target={}, valid={}", mergeCommitSha.substring(0, Math.min(7, mergeCommitSha.length())), sourceBranch, targetBranch, result.isValid());

    }

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
            return (warnings != null && !warnings.isEmpty()) ? ValidationResult.successWithWarnings(warnings) : ValidationResult.success();
        } else {
            List<String> safeErrors = errors != null ? errors : List.of();
            return (warnings != null && !warnings.isEmpty()) ? ValidationResult.failureWithWarnings(safeErrors, warnings) : ValidationResult.failure(safeErrors);
        }

    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> readMetadata(String mergeCommitSha) throws IOException {
        Path metadataPath = vitruviusDir.resolve(MERGE_DIR).resolve(mergeCommitSha + METADATA_SUFFIX);
        if (!Files.exists(metadataPath)) {
            return null;
        }
        return gson.fromJson(Files.readString(metadataPath), Map.class);
    }

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

    public boolean exists(String requestId) {
        return Files.exists(getTextResultPath(requestId)) || Files.exists(getJsonResultPath(requestId));
    }

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

    public Path getMetadataPath(String mergeCommitSha) {
        return vitruviusDir.resolve(MERGE_DIR).resolve(mergeCommitSha + METADATA_SUFFIX);
    }

    private void writeTextResult(ValidationResult result, Path textResultPath) throws IOException {
        var builder = new StringBuilder();
        builder.append("\n");

        if (result.isValid()) {
            // CHANGED: Added ✔ to match the hook's grep pattern
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
     * Includes the request identifier so the hook can verify it is reading the correct result file.
     */
    private void writeJsonResult(ValidationResult result, String requestId, Path jsonResultPath) throws IOException {
        Map<String, Object> data = new HashMap<>();
        data.put("requestId", requestId);
        data.put("valid", result.isValid());
        data.put("timestamp", LocalDateTime.now().toString());
        data.put("errors", result.getErrors());
        data.put("warnings", result.getWarnings());
        Files.writeString(jsonResultPath, gson.toJson(data));
    }
}
