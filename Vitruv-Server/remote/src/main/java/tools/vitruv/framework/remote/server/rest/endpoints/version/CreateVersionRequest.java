package tools.vitruv.framework.remote.server.rest.endpoints.version;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Request body DTO for creating a new version.
 *
 * <p>Expected JSON:
 * <pre>
 * {
 *   "versionId": "v1.0",
 *   "description": "Stable baseline after sprint 1"
 * }
 * </pre>
 *
 * <p>{@code description} is optional and may be null.
 */
public record CreateVersionRequest(
    @JsonProperty("versionId") String versionId,
    @JsonProperty("description") String description) {}
