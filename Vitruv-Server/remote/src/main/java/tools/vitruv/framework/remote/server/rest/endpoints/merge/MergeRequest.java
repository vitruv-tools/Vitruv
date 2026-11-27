package tools.vitruv.framework.remote.server.rest.endpoints.merge;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Request body DTO for merging a branch into the current branch.
 *
 * <p>Expected JSON:
 * <pre>
 * {
 *   "sourceBranch": "feature/my-feature",
 *   "deleteAfterMerge": false,
 *   "resolutionStrategy": "THEIRS"
 * }
 * </pre>
 *
 * <p>{@code deleteAfterMerge} is optional and defaults to {@code false}.
 *
 * <p>{@code resolutionStrategy} is optional. When omitted (or null), semantic conflicts
 * block the merge and {@code CONFLICTING} is returned. Accepted values:
 * <ul>
 *   <li>{@code "THEIRS"}: accept all changes from the source branch on conflict</li>
 *   <li>{@code "OURS"}: keep all target branch values on conflict</li>
 * </ul>
 */
public record MergeRequest(
    @JsonProperty("sourceBranch") String sourceBranch,
    @JsonProperty("deleteAfterMerge") boolean deleteAfterMerge,
    @JsonProperty("resolutionStrategy") String resolutionStrategy) {}
