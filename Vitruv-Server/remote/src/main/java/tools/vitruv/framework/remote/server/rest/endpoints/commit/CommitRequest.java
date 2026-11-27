package tools.vitruv.framework.remote.server.rest.endpoints.commit;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * Request body DTO for creating a commit.
 *
 * <p>Expected JSON:
 * <pre>
 * {
 *   "message": "Add system element",
 *   "additionalFiles": ["relative/path/to/extra.json"],
 *   "excludeFiles": ["model/skip-this.xmi"]
 * }
 * </pre>
 *
 * <p>{@code additionalFiles} and {@code excludeFiles} are optional.
 * Paths are relative to the repository root.
 */
public record CommitRequest(
    @JsonProperty("message") String message,
    @JsonProperty("additionalFiles") List<String> additionalFiles,
    @JsonProperty("excludeFiles") List<String> excludeFiles) {}
