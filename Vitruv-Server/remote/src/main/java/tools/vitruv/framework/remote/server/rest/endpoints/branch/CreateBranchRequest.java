package tools.vitruv.framework.remote.server.rest.endpoints.branch;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Request body DTO for creating a new branch.
 *
 * <p>Expected JSON:
 * <pre>
 * {
 *   "name": "feature/my-feature",
 *   "fromBranch": "main"
 * }
 * </pre>
 */
public record CreateBranchRequest(
    @JsonProperty("name") String name,
    @JsonProperty("fromBranch") String fromBranch) {}
