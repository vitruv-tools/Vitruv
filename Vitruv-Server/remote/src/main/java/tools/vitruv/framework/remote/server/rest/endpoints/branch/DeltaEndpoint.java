package tools.vitruv.framework.remote.server.rest.endpoints.branch;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.revwalk.filter.RevFilter;
import tools.vitruv.framework.remote.common.rest.constants.ContentType;
import tools.vitruv.framework.remote.server.exception.ServerHaltingException;
import tools.vitruv.framework.remote.server.http.HttpWrapper;
import tools.vitruv.framework.remote.server.rest.GetEndpoint;
import tools.vitruv.framework.vsum.branch.CommitManager;
import tools.vitruv.framework.vsum.branch.exception.BranchOperationException;
import tools.vitruv.framework.vsum.branch.storage.SemanticChangeEntry;
import tools.vitruv.framework.vsum.branch.storage.SemanticChangelogManager.ChangelogDocument;

/**
 * {@code GET /vsum/delta/{branchName}}
 *
 * <p>Returns the aggregated semantic delta between a branch and a base branch.
 * The delta lists all semantic changes introduced on the target branch since it diverged
 * from the base, grouped by commit (short SHA).
 *
 * <p>The branch name is path segment 0 after {@code /vsum/delta/}.
 * <p>Optional query parameter {@code base}: the reference branch (defaults to {@code main}).
 *
 * <p>The merge-base commit is found via JGit's {@link RevFilter#MERGE_BASE}. Only commits
 * on the branch after the merge-base that have a semantic changelog are included.
 *
 * <p>Example request:
 * <pre>
 *   GET /vsum/delta/feature%2Fpump?base=main
 * </pre>
 *
 * <p>Example response:
 * <pre>
 * {
 *   "branch": "feature/pump",
 *   "baseBranch": "main",
 *   "commits": [
 *     {
 *       "shortSha": "abc1234",
 *       "changes": ["CREATE_OBJECT Component", "SET_EATTRIBUTE Component.name: null -> pump"]
 *     }
 *   ]
 * }
 * </pre>
 */
public class DeltaEndpoint implements GetEndpoint {

  private static final ObjectMapper RESPONSE_MAPPER =
      new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

  private final CommitManager commitManager;
  private final Path repoRoot;

  public DeltaEndpoint(CommitManager commitManager, Path repoRoot) {
    this.commitManager = commitManager;
    this.repoRoot = repoRoot;
  }

  @Override
  public String process(HttpWrapper wrapper) throws ServerHaltingException {
    String branch = wrapper.getPathSegment(0);
    if (branch == null || branch.isBlank()) {
      throw badRequest("Missing branch name in path");
    }
    String baseBranch = wrapper.getQueryParameter("base");
    if (baseBranch == null || baseBranch.isBlank()) baseBranch = "main";

    try {
      List<String> shasOnBranch = findCommitsSinceDivergence(branch, baseBranch);

      var entries = new ArrayList<DeltaCommitEntry>();
      for (String sha : shasOnBranch) {
        String shortSha = sha.substring(0, Math.min(7, sha.length()));
        ChangelogDocument doc = commitManager.readChangelog(branch, shortSha);
        if (doc == null || doc.fileChanges == null || doc.fileChanges.isEmpty()) continue;
        var changes = new ArrayList<String>();
        doc.fileChanges.stream()
            .filter(fc -> fc.semanticChanges != null)
            .flatMap(fc -> fc.semanticChanges.stream())
            .map(DeltaEndpoint::formatChange)
            .forEach(changes::add);
        if (changes.isEmpty()) continue;
        entries.add(new DeltaCommitEntry(shortSha, changes));
      }

      wrapper.setContentType(ContentType.APPLICATION_JSON);
      return RESPONSE_MAPPER.writeValueAsString(new DeltaResponse(branch, baseBranch, entries));
    } catch (BranchOperationException e) {
      throw notFound(e.getMessage());
    } catch (IOException | GitAPIException e) {
      throw internalServerError(e.getMessage());
    }
  }

  private List<String> findCommitsSinceDivergence(String branch, String baseBranch)
      throws BranchOperationException, IOException, GitAPIException {
    try (Git git = Git.open(repoRoot.toFile())) {
      var repo = git.getRepository();
      var branchRef = repo.resolve(branch);
      var baseRef = repo.resolve(baseBranch);

      if (branchRef == null) {
        throw new BranchOperationException("Branch not found: " + branch);
      }
      if (baseRef == null) {
        throw new BranchOperationException("Base branch not found: " + baseBranch);
      }

      RevCommit mergeBase = null;
      try (var revWalk = new RevWalk(repo)) {
        revWalk.setRevFilter(RevFilter.MERGE_BASE);
        revWalk.markStart(revWalk.parseCommit(branchRef));
        revWalk.markStart(revWalk.parseCommit(baseRef));
        mergeBase = revWalk.next();
      }

      var shas = new ArrayList<String>();
      if (mergeBase == null) {
        // No common ancestor; include all commits on branch
        git.log().add(branchRef).call().forEach(c -> shas.add(c.getName()));
      } else {
        git.log().addRange(mergeBase.getId(), branchRef).call()
            .forEach(c -> shas.add(c.getName()));
      }
      return shas;
    }
  }

  private static String formatChange(SemanticChangeEntry entry) {
    var sb = new StringBuilder();
    sb.append(entry.getChangeType() != null ? entry.getChangeType().name() : "CHANGE");
    if (entry.getEClass() != null) sb.append(" ").append(entry.getEClass());
    if (entry.getFeature() != null) {
      sb.append(".").append(entry.getFeature());
      if (entry.getTo() != null) {
        sb.append(": ");
        if (entry.getFrom() != null) sb.append(entry.getFrom()).append(" -> ");
        sb.append(entry.getTo());
      }
    }
    return sb.toString();
  }

  public record DeltaResponse(String branch, String baseBranch, List<DeltaCommitEntry> commits) {}

  public record DeltaCommitEntry(String shortSha, List<String> changes) {}
}
