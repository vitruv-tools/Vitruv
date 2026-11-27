package tools.vitruv.framework.remote.server.rest.endpoints.branch;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.nio.file.Path;
import java.util.List;
import tools.vitruv.framework.remote.common.json.JsonMapper;
import tools.vitruv.framework.remote.common.rest.constants.ContentType;
import tools.vitruv.framework.remote.server.exception.ServerHaltingException;
import tools.vitruv.framework.remote.server.http.HttpWrapper;
import tools.vitruv.framework.remote.server.rest.GetEndpoint;
import tools.vitruv.framework.vsum.branch.SemanticConflictDetector;
import tools.vitruv.framework.vsum.branch.data.ReplayResult;
import tools.vitruv.framework.vsum.branch.data.SemanticConflict;
import tools.vitruv.framework.vsum.branch.exception.BranchOperationException;

/**
 * {@code GET /vsum/branch/{branchName}/conflicts}
 *
 * <p>Returns a preview of semantic conflicts that would arise from merging this branch into
 * a base branch. Runs steps 1-3 of the replay pipeline (direct conflict detection via changelog
 * analysis) without applying any model changes. The branch name is path segment 0 after
 * {@code /vsum/branch/}. The optional query parameter {@code base} selects the reference branch
 * (defaults to {@code main}).
 *
 * <p>Example request:
 * <pre>
 *   GET /vsum/branch/feature%2Fpump/conflicts?base=main
 * </pre>
 *
 * <p>Returns {@code 404} if the branch or the base branch does not exist.
 */
public class BranchConflictsEndpoint implements GetEndpoint {

  private final Path repoRoot;
  private final JsonMapper mapper;

  public BranchConflictsEndpoint(Path repoRoot, JsonMapper mapper) {
    this.repoRoot = repoRoot;
    this.mapper = mapper;
  }

  @Override
  public String process(HttpWrapper wrapper) throws ServerHaltingException {
    String branchName = wrapper.getPathSegment(0);
    if (branchName == null || branchName.isBlank()) {
      throw badRequest("Missing branch name in path");
    }
    String baseBranch = wrapper.getQueryParameter("base");
    if (baseBranch == null || baseBranch.isBlank()) baseBranch = "main";

    try {
      SemanticConflictDetector detector = new SemanticConflictDetector(repoRoot);
      ReplayResult result = detector.analyzeBranches(branchName, baseBranch);
      List<SemanticConflict> conflicts = result.getConflicts();
      ConflictsResponse response = new ConflictsResponse(
          branchName, baseBranch, conflicts.size(), conflicts);
      wrapper.setContentType(ContentType.APPLICATION_JSON);
      return mapper.serialize(response);
    } catch (BranchOperationException e) {
      throw notFound(e.getMessage());
    } catch (JsonProcessingException e) {
      throw internalServerError(e.getMessage());
    }
  }

  public record ConflictsResponse(
      String branch,
      String baseBranch,
      int conflictCount,
      List<SemanticConflict> conflicts) {}
}
