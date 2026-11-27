package tools.vitruv.framework.remote.server.rest.endpoints.commit;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.IOException;
import java.nio.file.Path;
import tools.vitruv.framework.remote.common.json.JsonMapper;
import tools.vitruv.framework.remote.common.rest.constants.ContentType;
import tools.vitruv.framework.remote.server.exception.ServerHaltingException;
import tools.vitruv.framework.remote.server.http.HttpWrapper;
import tools.vitruv.framework.remote.server.rest.PostEndpoint;
import tools.vitruv.framework.vsum.branch.CommitManager;
import tools.vitruv.framework.vsum.branch.data.CommitOptions;
import tools.vitruv.framework.vsum.branch.data.CommitResult;
import tools.vitruv.framework.vsum.branch.exception.BranchOperationException;

/**
 * {@code POST /vsum/commit}
 *
 * <p>Commits all modified model files in the working directory using the given commit message.
 * Model files ({@code .xmi}, {@code .ecore}, {@code .genmodel},...)
 * are auto-staged. If semantic change tracking is attached, the JSON changelog and XMI delta
 * snapshots are written after the commit succeeds (the commit SHA is needed as a reference)
 * and staged as uncommitted changes, ready to be included in a subsequent commit.
 *
 * <p>Expected request body:
 * <pre>
 * {
 *   "message": "Add system element",
 *   "additionalFiles": ["relative/path/to/extra.json"],
 *   "excludeFiles": ["model/skip-this.xmi"]
 * }
 * </pre>
 *
 * <p>Returns the commit result as a JSON object:
 * <pre>
 * {
 *   "commitSha": "a1b2c3d...",
 *   "branch": "feature/my-feature",
 *   "authorName": "Linh Nguyen",
 *   "authorEmail": "linh@example.com",
 *   "authorDate": "2026-04-07T14:00:00",
 *   "stagedFiles": ["model/example.xmi"],
 *   "hasModelChanges": true
 * }
 * </pre>
 *
 * <p>Returns {@code 500} if there is nothing to commit or if the Git operation fails.
 */
public class CommitEndpoint implements PostEndpoint {

  private final CommitManager commitManager;
  private final JsonMapper mapper;
  private final Path repoRoot;

  /**
   * Creates a new {@link CommitEndpoint}.
   *
   * @param commitManager the commit manager used to perform the commit.
   * @param mapper the JSON mapper used to deserialize the request and serialize the response.
   * @param repoRoot the repository root, used to resolve relative paths in {@code additionalFiles}
   *     and {@code excludeFiles}.
   */
  public CommitEndpoint(CommitManager commitManager, JsonMapper mapper, Path repoRoot) {
    this.commitManager = commitManager;
    this.mapper = mapper;
    this.repoRoot = repoRoot;
  }

  @Override
  public String process(HttpWrapper wrapper) throws ServerHaltingException {
    try {
      String body = wrapper.getRequestBodyAsString();
      CommitRequest request = mapper.deserialize(body, CommitRequest.class);
      if (request.message() == null || request.message().isBlank()) {
        throw badRequest("Commit message must not be blank");
      }
      CommitResult result = hasOptions(request)
          ? commitManager.commit(request.message(), buildOptions(request))
          : commitManager.commit(request.message());
      wrapper.setContentType(ContentType.APPLICATION_JSON);
      return mapper.serialize(CommitResponse.from(result));
    } catch (BranchOperationException | IOException e) {
      throw internalServerError(e.getMessage());
    }
  }

  private boolean hasOptions(CommitRequest request) {
    return (request.additionalFiles() != null && !request.additionalFiles().isEmpty())
        || (request.excludeFiles() != null && !request.excludeFiles().isEmpty());
  }

  private CommitOptions buildOptions(CommitRequest request) {
    CommitOptions.Builder builder = CommitOptions.builder();
    if (request.additionalFiles() != null) {
      request.additionalFiles().stream()
          .map(repoRoot::resolve)
          .forEach(builder::addFile);
    }
    if (request.excludeFiles() != null) {
      request.excludeFiles().stream()
          .map(repoRoot::resolve)
          .forEach(builder::excludeFile);
    }
    return builder.build();
  }
}
