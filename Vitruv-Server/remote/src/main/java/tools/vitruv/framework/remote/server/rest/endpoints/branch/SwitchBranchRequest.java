package tools.vitruv.framework.remote.server.rest.endpoints.branch;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Request body DTO for switching the active branch.
 *
 * <p>Expected JSON:
 * <pre>
 * {
 *   "name": "feature/my-feature"
 * }
 * </pre>
 */
public record SwitchBranchRequest(
    @JsonProperty("name") String name) {}
