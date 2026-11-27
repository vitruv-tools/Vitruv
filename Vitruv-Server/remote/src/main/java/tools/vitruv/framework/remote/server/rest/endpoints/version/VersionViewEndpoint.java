package tools.vitruv.framework.remote.server.rest.endpoints.version;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.TreeWalk;
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
 * {@code GET /vsum/version/view}
 *
 * <p>Returns an HTML page containing a browser-renderable Mermaid containment graph
 * of the model state captured at a specific version.
 *
 * <p>Paste the response body into a browser (or serve it) to get an interactive graph.
 * Mermaid.js is loaded from the jsDelivr CDN; an internet connection is required.
 *
 * <p>Required header: {@code Version-Id}
 *
 * <p>Example request:
 * <pre>
 *   GET /vsum/version/view
 *   Version-Id: v1.0
 * </pre>
 *
 * <p>Returns {@code Content-Type: text/html; charset=utf-8}.
 */
public class VersionViewEndpoint implements GetEndpoint {

  private static final String VSUM_BASE_DIR = ".vitruvius/vsum";

  private final VersioningService versioningService;
  private final Path repoRoot;

  public VersionViewEndpoint(VersioningService versioningService, Path repoRoot) {
    this.versioningService = versioningService;
    this.repoRoot = repoRoot;
  }

  @Override
  public String process(HttpWrapper wrapper) throws ServerHaltingException {
    String versionId = wrapper.getPathSegment(0);
    if (versionId == null || versionId.isBlank()) {
      throw badRequest("Missing version ID in path");
    }
    try {
      var meta = versioningService.getVersion(versionId);
      String commitSha = meta.getCommitSha();
      List<ParsedElement> roots = readModelElements(commitSha, meta.getBranch());
      String mermaid = roots.isEmpty()
          ? "graph TD\n  n0[\"(no models found)\"]"
          : ModelFormatter.toMermaid(roots);
      wrapper.setContentType(ContentType.TEXT_HTML);
      return buildHtmlPage(versionId, commitSha, meta.getBranch(), mermaid);
    } catch (VersioningException e) {
      throw notFound(e.getMessage());
    } catch (IOException e) {
      throw internalServerError(e.getMessage());
    }
  }

  private static String buildHtmlPage(String versionId, String commitSha,
      String branch, String mermaidDiagram) {
    String shortSha = commitSha.length() >= 7 ? commitSha.substring(0, 7) : commitSha;
    return "<!DOCTYPE html><html lang=\"en\"><head>"
        + "<meta charset=\"utf-8\">"
        + "<title>Model: " + escHtml(versionId) + "</title>"
        + "<script src=\"https://cdn.jsdelivr.net/npm/mermaid@10/dist/mermaid.min.js\"></script>"
        + "<style>body{font-family:sans-serif;margin:2em;background:#fafafa}"
        + "h2{color:#333}code{background:#eee;padding:2px 5px;border-radius:3px}"
        + ".mermaid{background:#fff;border:1px solid #ddd;padding:1em;border-radius:6px}"
        + "</style></head><body>"
        + "<h2>Model snapshot &mdash; " + escHtml(versionId) + "</h2>"
        + "<p>Branch: <code>" + escHtml(branch) + "</code> &nbsp; "
        + "Commit: <code>" + escHtml(shortSha) + "</code></p>"
        + "<div class=\"mermaid\">\n" + mermaidDiagram + "\n</div>"
        + "<script>mermaid.initialize({startOnLoad:true,theme:'default'});</script>"
        + "</body></html>";
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
            byte[] bytes = repo.open(treeWalk.getObjectId(0)).getBytes();
            try {
              roots.addAll(XmiModelParser.parse(bytes));
            } catch (Exception ignored) {
              // not parseable as XMI, skip
            }
          }
        }
      }
    }
    return roots;
  }

  private static String escHtml(String s) {
    if (s == null) return "";
    return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
  }
}
