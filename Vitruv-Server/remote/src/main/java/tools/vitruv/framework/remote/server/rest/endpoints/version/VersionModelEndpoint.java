package tools.vitruv.framework.remote.server.rest.endpoints.version;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.TreeWalk;
import tools.vitruv.framework.remote.common.json.JsonMapper;
import tools.vitruv.framework.remote.common.rest.constants.ContentType;
import tools.vitruv.framework.remote.server.exception.ServerHaltingException;
import tools.vitruv.framework.remote.server.http.HttpWrapper;
import tools.vitruv.framework.remote.server.rest.GetEndpoint;
import tools.vitruv.framework.remote.server.rest.endpoints.util.ModelFormatter;
import tools.vitruv.framework.remote.server.rest.endpoints.util.XmiModelParser;
import tools.vitruv.framework.remote.server.rest.endpoints.util.XmiModelParser.ParsedElement;
import tools.vitruv.framework.vsum.versioning.VersioningException;
import tools.vitruv.framework.vsum.versioning.VersioningService;

/**
 * {@code GET /vsum/version/model}
 *
 * <p>Returns the model state captured at a specific version, formatted for human inspection.
 *
 * <p>Required header: {@code Version-Id}
 * <p>Optional header: {@code Format}, one of {@code text} (default), {@code json},
 * or {@code mermaid}. The {@code text} format returns an ASCII containment tree;
 * {@code json} returns a nested JSON structure; {@code mermaid} returns a Mermaid
 * {@code graph TD} string for embedding in a browser.
 *
 * <p>Example request:
 * <pre>
 *   GET /vsum/version/model
 *   Version-Id: v1.0
 *   Format: text
 * </pre>
 *
 * <p>Example response (text):
 * <pre>
 * Version: v1.0  commit: abc1234
 *
 * System
 * ├── Component  { name="c1" }
 * └── Component  { name="c2" }
 * </pre>
 */
public class VersionModelEndpoint implements GetEndpoint {

  private static final String VSUM_BASE_DIR = ".vitruvius/vsum";

  private final VersioningService versioningService;
  private final JsonMapper mapper;
  private final Path repoRoot;

  public VersionModelEndpoint(VersioningService versioningService, JsonMapper mapper, Path repoRoot) {
    this.versioningService = versioningService;
    this.mapper = mapper;
    this.repoRoot = repoRoot;
  }

  @Override
  public String process(HttpWrapper wrapper) throws ServerHaltingException {
    String versionId = wrapper.getPathSegment(0);
    if (versionId == null || versionId.isBlank()) {
      throw badRequest("Missing version ID in path");
    }
    String format = wrapper.getQueryParameter("format");
    if (format == null || format.isBlank()) format = "text";

    try {
      var meta = versioningService.getVersion(versionId);
      String commitSha = meta.getCommitSha();
      String branch = meta.getBranch();
      List<ParsedElement> roots = readModelElements(commitSha, branch);

      return switch (format.toLowerCase()) {
        case "json" -> {
          wrapper.setContentType(ContentType.APPLICATION_JSON);
          var trees = roots.stream().map(ModelFormatter::toJsonTree).toList();
          yield mapper.serialize(Map.of("version", versionId,
              "commit", commitSha.substring(0, 7), "models", trees));
        }
        case "mermaid" -> {
          wrapper.setContentType(ContentType.TEXT_PLAIN);
          yield ModelFormatter.toMermaid(roots);
        }
        default -> {
          wrapper.setContentType(ContentType.TEXT_PLAIN);
          yield "Version: " + versionId + "  commit: " + commitSha.substring(0, 7) + "\n\n"
              + (roots.isEmpty() ? "(no model files found at this version)"
                  : ModelFormatter.toAsciiTree(roots));
        }
      };
    } catch (VersioningException e) {
      throw notFound(e.getMessage());
    } catch (IOException e) {
      throw internalServerError(e.getMessage());
    }
  }

  private List<ParsedElement> readModelElements(String commitSha, String branch) throws IOException {
    String encodedBranch = branch.replace("/", "__");
    String prefix = VSUM_BASE_DIR + "/" + encodedBranch + "/";
    var roots = new ArrayList<ParsedElement>();

    try (Git git = Git.open(repoRoot.toFile())) {
      var repo = git.getRepository();
      try (var revWalk = new RevWalk(repo)) {
        var commit = revWalk.parseCommit(repo.resolve(commitSha));
        try (var treeWalk = new TreeWalk(repo)) {
          treeWalk.addTree(commit.getTree());
          treeWalk.setRecursive(true);
          while (treeWalk.next()) {
            String path = treeWalk.getPathString();
            if (!path.startsWith(prefix)) continue;
            if (!path.endsWith(".model") && !path.endsWith(".xmi") && !path.endsWith(".ecore")) continue;
            byte[] bytes = repo.open(treeWalk.getObjectId(0)).getBytes();
            try {
              roots.addAll(XmiModelParser.parse(bytes));
            } catch (Exception ignored) {
              // not a parseable XMI file, skip
            }
          }
        }
      }
    }
    return roots;
  }
}
