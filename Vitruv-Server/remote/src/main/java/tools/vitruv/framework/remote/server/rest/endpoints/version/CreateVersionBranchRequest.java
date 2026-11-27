package tools.vitruv.framework.remote.server.rest.endpoints.version;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Request body DTO for creating a branch from a version.
 *
 * <p>Expected JSON:
 * <pre>
 * {
 *   "branchName": "feature/from-v1.0"
 * }
 * </pre>
 *
 * <p>The version to branch from is passed via the {@code Version-Id} header.
 */
public record CreateVersionBranchRequest(
    @JsonProperty("branchName") String branchName) {}
