package tools.vitruv.framework.remote.server.rest.endpoints.branch;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Request body DTO for updating the maturity level of a branch.
 *
 * <p>Expected JSON for {@code PATCH /vsum/branch}:
 * <pre>
 * {
 *   "name": "feature/my-feature",
 *   "maturity": "REVIEWED"
 * }
 * </pre>
 *
 * <p>Valid maturity values: {@code DRAFT}, {@code REVIEWED}, {@code FINAL}.
 */
public record SetBranchMaturityRequest(
    @JsonProperty("name") String name,
    @JsonProperty("maturity") String maturity) {}
