package tools.vitruv.framework.remote.server.rest.endpoints.merge;

import java.io.IOException;
import tools.vitruv.framework.remote.common.json.JsonMapper;
import tools.vitruv.framework.remote.common.rest.constants.ContentType;
import tools.vitruv.framework.remote.server.exception.ServerHaltingException;
import tools.vitruv.framework.remote.server.http.HttpWrapper;
import tools.vitruv.framework.remote.server.rest.PostEndpoint;
import tools.vitruv.framework.vsum.branch.MergeManager;
import tools.vitruv.framework.vsum.branch.data.ModelMergeResult;
import tools.vitruv.framework.vsum.branch.exception.BranchOperationException;
import tools.vitruv.framework.vsum.branch.merge.ConflictResolutionProvider;

/**
 * {@code POST /vsum/merge}
 *
 * <p>Merges a source branch into the currently active branch using a three-way merge.
 * Optionally deletes the source branch after a successful merge.
 *
 * <p>Expected request body:
 * <pre>
 * {
 *   "sourceBranch": "feature/my-feature",
 *   "deleteAfterMerge": false,
 *   "resolutionStrategy": "THEIRS"
 * }
 * </pre>
 *
 * <p>{@code resolutionStrategy} is optional. Omit it (or set to null) to let conflicts
 * block the merge. Set to {@code "THEIRS"} or {@code "OURS"} for auto-resolution.
 *
 * <p>Returns the merge result as a JSON object:
 * <pre>
 * {
 *   "status": "SUCCESS",
 *   "sourceBranch": "feature/my-feature",
 *   "targetBranch": "master",
 *   "mergeCommitSha": "a1b2c3d...",
 *   "fastForward": false,
 *   "conflictingFiles": [],
 *   "message": "Merged 'feature/my-feature' into 'master' successfully",
 *   "successful": true
 * }
 * </pre>
 *
 * <p>Possible status values: {@code SUCCESS}, {@code FAST_FORWARD}, {@code CONFLICTING},
 * {@code FAILED}.
 * When {@code CONFLICTING}, {@code conflictingFiles} lists semantic conflict descriptors.
 */
public class MergeEndpoint implements PostEndpoint {

  private final MergeManager mergeManager;
  private final JsonMapper mapper;

  /**
   * Creates a new {@link MergeEndpoint}.
   *
   * @param mergeManager the merge manager used to perform the merge.
   * @param mapper the JSON mapper used to deserialize the request and serialize the response.
   */
  public MergeEndpoint(MergeManager mergeManager, JsonMapper mapper) {
    this.mergeManager = mergeManager;
    this.mapper = mapper;
  }

  @Override
  public String process(HttpWrapper wrapper) throws ServerHaltingException {
    try {
      String body = wrapper.getRequestBodyAsString();
      MergeRequest request = mapper.deserialize(body, MergeRequest.class);
      if (request.sourceBranch() == null || request.sourceBranch().isBlank()) {
        throw badRequest("sourceBranch must not be blank");
      }

      String strategy = request.resolutionStrategy();
      boolean hasStrategy = strategy != null && !strategy.isBlank();
      if (hasStrategy) {
        ConflictResolutionProvider provider;
        if ("THEIRS".equalsIgnoreCase(strategy)) {
          provider = ConflictResolutionProvider.chooseAllTheirs();
        } else if ("OURS".equalsIgnoreCase(strategy)) {
          provider = ConflictResolutionProvider.chooseAllOurs();
        } else {
          throw badRequest("Unknown resolutionStrategy: " + strategy);
        }
        mergeManager.setConflictResolutionProvider(provider);
      }

      try {
        ModelMergeResult result = mergeManager.merge(
            request.sourceBranch(), request.deleteAfterMerge());
        wrapper.setContentType(ContentType.APPLICATION_JSON);
        return mapper.serialize(result);
      } finally {
        if (hasStrategy) {
          mergeManager.clearConflictResolutionProvider();
        }
      }
    } catch (BranchOperationException | IOException e) {
      throw internalServerError(e.getMessage());
    }
  }
}
