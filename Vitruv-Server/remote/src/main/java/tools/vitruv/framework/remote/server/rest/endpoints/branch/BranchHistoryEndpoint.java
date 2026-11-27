package tools.vitruv.framework.remote.server.rest.endpoints.branch;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import tools.vitruv.framework.remote.common.rest.constants.ContentType;
import tools.vitruv.framework.remote.server.exception.ServerHaltingException;
import tools.vitruv.framework.remote.server.http.HttpWrapper;
import tools.vitruv.framework.remote.server.rest.GetEndpoint;
import tools.vitruv.framework.vsum.branch.CommitManager;
import tools.vitruv.framework.vsum.branch.exception.BranchOperationException;
import tools.vitruv.framework.vsum.branch.storage.SemanticChangeEntry;
import tools.vitruv.framework.vsum.branch.storage.SemanticChangelogManager.ChangelogDocument;

/**
 * {@code GET /vsum/branch/{branchName}/history}
 *
 * <p>Returns the commit history of a branch with inline semantic change summaries.
 * Each commit entry includes the short SHA, message, timestamp, total semantic change count,
 * and a preview of up to three changes formatted as human-readable strings.
 *
 * <p>This bundles commit listing + per-commit changelog into a single call, avoiding N+1
 * round trips from CLI consumers. The branch name is path segment 0 after {@code /vsum/branch/}.
 *
 * <p>Example request:
 * <pre>
 *   GET /vsum/branch/main/history
 * </pre>
 *
 * <p>Example response:
 * <pre>
 * {
 *   "branch": "main",
 *   "commits": [
 *     {
 *       "sha": "abc1234...",
 *       "shortSha": "abc1234",
 *       "message": "add component c1",
 *       "authorDate": "2026-05-04T10:00:00",
 *       "hasChangelog": true,
 *       "totalSemanticChanges": 1,
 *       "changePreview": ["ELEMENT_CREATED model::Component"]
 *     }
 *   ]
 * }
 * </pre>
 */
public class BranchHistoryEndpoint implements GetEndpoint {

  private static final int MAX_PREVIEW_LINES = 3;
  private static final ObjectMapper RESPONSE_MAPPER =
      new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

  private final CommitManager commitManager;

  /**
   * Creates a new {@link BranchHistoryEndpoint}.
   *
   * @param commitManager the commit manager used to list commits and read changelogs.
   */
  public BranchHistoryEndpoint(CommitManager commitManager) {
    this.commitManager = commitManager;
  }

  @Override
  public String process(HttpWrapper wrapper) throws ServerHaltingException {
    String branch = wrapper.getPathSegment(0);
    if (branch == null || branch.isBlank()) {
      throw badRequest("Missing branch name in path");
    }
    try {
      var commits = commitManager.listCommits(branch);
      var entries = new ArrayList<CommitHistoryEntry>();

      for (var commit : commits) {
        var preview = new ArrayList<String>();
        if (commit.hasChangelog()) {
          ChangelogDocument doc = commitManager.readChangelog(branch, commit.shortSha());
          if (doc != null && doc.fileChanges != null) {
            doc.fileChanges.stream()
                .filter(fc -> fc.semanticChanges != null)
                .flatMap(fc -> fc.semanticChanges.stream())
                .limit(MAX_PREVIEW_LINES)
                .map(BranchHistoryEndpoint::formatChange)
                .forEach(preview::add);
          }
        }
        entries.add(new CommitHistoryEntry(
            commit.sha(),
            commit.shortSha(),
            commit.message(),
            commit.authorDate() != null ? commit.authorDate().toString() : null,
            commit.hasChangelog(),
            commit.totalSemanticChanges(),
            preview));
      }

      wrapper.setContentType(ContentType.APPLICATION_JSON);
      return RESPONSE_MAPPER.writeValueAsString(new BranchHistoryResponse(branch, entries));
    } catch (BranchOperationException e) {
      throw notFound(e.getMessage());
    } catch (IOException e) {
      throw internalServerError(e.getMessage());
    }
  }

  private static String formatChange(SemanticChangeEntry entry) {
    var sb = new StringBuilder();
    sb.append(entry.getChangeType() != null ? entry.getChangeType().name() : "CHANGE");
    if (entry.getEClass() != null) sb.append(" ").append(entry.getEClass());
    if (entry.getFeature() != null) {
      sb.append(".").append(entry.getFeature());
      if (entry.getFrom() != null && entry.getTo() != null) {
        sb.append(": ").append(entry.getFrom()).append(" -> ").append(entry.getTo());
      }
    }
    return sb.toString();
  }

  public record BranchHistoryResponse(String branch, List<CommitHistoryEntry> commits) {}

  public record CommitHistoryEntry(
      String sha,
      String shortSha,
      String message,
      String authorDate,
      boolean hasChangelog,
      int totalSemanticChanges,
      List<String> changePreview) {}
}
