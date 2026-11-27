package tools.vitruv.framework.vsum.branch.merge;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;

import tools.vitruv.change.interaction.InteractionResultProvider;
import tools.vitruv.change.propagation.ChangePropagationSpecification;
import tools.vitruv.framework.vsum.VirtualModelBuilder;
import tools.vitruv.framework.vsum.internal.InternalVirtualModel;

/**
 * Utility to load VSUM state from a specific Git commit.
 * Used by the semantic merge engine to instantiate the target branch state.
 */
public class GitStateLoader {

    private static final Logger LOGGER = LogManager.getLogger(GitStateLoader.class);

    private final Path repoRoot;

    public GitStateLoader(Path repoRoot) {
        this.repoRoot = repoRoot;
    }

    /**
     * Checks out the VSUM files at a specific commit into a temporary directory.
     * Creates a fresh clone/worktree of the repo at the given commit.
     *
     * @param commitSha the commit to load
     * @param tempDir   the temporary directory to check out into
     * @throws IOException     if file operations fail
     * @throws GitAPIException if Git operations fail
     */
    public void checkoutStateAtCommit(String commitSha, Path tempDir) throws IOException, GitAPIException {
        LOGGER.info("Checking out state at commit {} into {}", commitSha.substring(0, 7), tempDir);

        // Use JGit to read the commit's tree and extract files to tempDir.
        // This avoids clone conflicts when the source repo has open file handles.
        try (Git git = Git.open(repoRoot.toFile())) {
            var repo = git.getRepository();
            var revCommit = new org.eclipse.jgit.revwalk.RevWalk(repo)
                    .parseCommit(repo.resolve(commitSha));
            var tree = revCommit.getTree();

            try (var treeWalk = new org.eclipse.jgit.treewalk.TreeWalk(repo)) {
                treeWalk.addTree(tree);
                treeWalk.setRecursive(true);
                while (treeWalk.next()) {
                    String path = treeWalk.getPathString();
                    Path outFile = tempDir.resolve(path);
                    Files.createDirectories(outFile.getParent());

                    var objectId = treeWalk.getObjectId(0);
                    var loader = repo.open(objectId);
                    Files.write(outFile, loader.getBytes());
                }
            }
        }
    }

    private long countFiles(Path dir) throws IOException {
        try (var stream = Files.walk(dir)) {
            return stream.filter(Files::isRegularFile).count();
        }
    }

    /**
     * Creates a fresh VirtualModel from a VSUM stored on disk.
     *
     * @param vsumFolder the folder containing the VSUM data (models, uuid, correspondences)
     * @param specs      the change propagation specifications to register
     * @return an initialized InternalVirtualModel
     */
    public static InternalVirtualModel loadVsumFromDir(
            Path vsumFolder,
            Collection<ChangePropagationSpecification> specs,
            InteractionResultProvider interactionProvider) throws IOException {
        // Fix models.models and uuid.uuid in the BranchAwareVirtualModel layout
        // (.vitruvius/vsum/<branch>/) so the VSUM can find its model resources.
        // Returns the branch name that was fixed (used to init the scratch git repo).
        String activeBranch = fixBranchAwareVsumData(vsumFolder);

        // VsumFileSystemLayout calls Git.open(vsumFolder) during construction to resolve the
        // current branch name. The temp dir extracted from a git commit is a plain directory
        // with no .git. We initialise a minimal git repo with one commit so the open succeeds.
        // Use the same branch name that was found in the VSUM data so VsumFileSystemLayout
        // resolves the correct .vitruvius/vsum/<branch>/models.models.
        if (!Files.isDirectory(vsumFolder.resolve(".git"))) {
            String initBranch = activeBranch != null ? activeBranch : "main";
            try (Git tempGit = Git.init()
                    .setDirectory(vsumFolder.toFile())
                    .setInitialBranch(initBranch)
                    .call()) {
                tempGit.add().addFilepattern(".").call();
                tempGit.commit()
                        .setMessage("merge-engine-init")
                        .setAllowEmpty(true)
                        .setAuthor("vitruvius-merge", "merge@vitruvius")
                        .call();
            } catch (org.eclipse.jgit.api.errors.GitAPIException e) {
                throw new IOException(
                        "failed to init scratch git repo in " + vsumFolder + ": " + e.getMessage(), e);
            }
        }

        VirtualModelBuilder builder = new VirtualModelBuilder()
                .withStorageFolder(vsumFolder)
                .withUserInteractorForResultProvider(interactionProvider);
        for (ChangePropagationSpecification spec : specs) {
            builder.withChangePropagationSpecifications(spec);
        }
        return builder.buildAndInitialize();
    }

    /**
     * Fixes the per-branch VSUM data files in {@code .vitruvius/vsum/BRANCH/} so that
     * absolute URIs from the original temp directory are rewritten to reference the
     * actual {@code vsumFolder}.
     *
     * <p>VsumFileSystemLayout stores VSUM data in {@code .vitruvius/vsum/BRANCH/} where
     * BRANCH is the current git branch name. This method fixes the URIs in all found
     * branch directories and returns the name of the preferred branch ("main" if present,
     * otherwise the first found) so the caller can initialise the scratch git repo on
     * the matching branch.
     *
     * @return the branch name whose data was fixed, or {@code null} if no branch dirs found
     */
    private static String fixBranchAwareVsumData(Path vsumFolder) throws IOException {
        Path vitruviusVsumDir = vsumFolder.resolve(".vitruvius/vsum");
        if (!Files.isDirectory(vitruviusVsumDir)) return null;

        String folderUri = org.eclipse.emf.common.util.URI.createFileURI(
                vsumFolder.toAbsolutePath().toString()).toString();

        String preferredBranch = null;
        List<Path> branchDirList = new java.util.ArrayList<>();
        try (var stream = Files.list(vitruviusVsumDir)) {
            stream.filter(Files::isDirectory).forEach(branchDirList::add);
        }

        for (Path branchDir : branchDirList) {
            String branchName = branchDir.getFileName().toString();
            fixModelsFile(branchDir.resolve("models.models"), vsumFolder, folderUri);
            fixUuidFile(branchDir.resolve("uuid.uuid"), folderUri);
            fixCorrespondenceFile(branchDir.resolve("correspondences.correspondence"), folderUri);
            if (preferredBranch == null || "main".equals(branchName)) {
                preferredBranch = branchName;
            }
        }
        if (preferredBranch != null) {
            LOGGER.info("Fixed VSUM data in {} branch dir(s), preferred branch: {}", branchDirList.size(), preferredBranch);
        }
        return preferredBranch;
    }

    /**
     * Rewrites model resource URIs in a models.models file so they point to
     * files in {@code vsumFolder} instead of the original (stale) directory.
     */
    private static void fixModelsFile(Path modelsFile, Path vsumFolder, String folderUri) throws IOException {
        if (!Files.exists(modelsFile)) return;
        List<String> oldLines = Files.readAllLines(modelsFile);
        List<String> modelUris = new java.util.ArrayList<>();
        for (String oldLine : oldLines) {
            if (oldLine.isBlank()) continue;
            String fileName = oldLine.substring(oldLine.lastIndexOf('/') + 1);
            Path modelFile = vsumFolder.resolve(fileName);
            if (Files.exists(modelFile)) {
                modelUris.add(org.eclipse.emf.common.util.URI.createFileURI(
                        modelFile.toAbsolutePath().toString()).toString());
            }
        }
        Files.writeString(modelsFile, String.join(java.lang.System.lineSeparator(), modelUris)
                + java.lang.System.lineSeparator());
    }

    /** Rewrites uuid.uuid so HierarchicalId file URIs reference the new vsumFolder. */
    private static void fixUuidFile(Path uuidFile, String folderUri) throws IOException {
        if (!Files.exists(uuidFile)) return;
        List<String> lines = Files.readAllLines(uuidFile);
        List<String> fixedLines = new java.util.ArrayList<>();
        boolean changed = false;
        for (String line : lines) {
            if (line.isBlank()) continue;
            String fixed = line;
            if (line.contains("|") && line.contains("file:")) {
                String[] parts = line.split("\\|", 2);
                if (parts.length == 2) {
                    String idPart = parts[1];
                    int hashIdx = idPart.indexOf('#');
                    String resourceUri = hashIdx >= 0 ? idPart.substring(0, hashIdx) : idPart;
                    String fragment = hashIdx >= 0 ? idPart.substring(hashIdx) : "";
                    int lastSlash = resourceUri.lastIndexOf('/');
                    if (lastSlash >= 0) {
                        String fileName = resourceUri.substring(lastSlash + 1);
                        fixed = parts[0] + "|" + folderUri + "/" + fileName + fragment;
                        changed = true;
                    }
                }
            }
            fixedLines.add(fixed);
        }
        if (changed) {
            Files.writeString(uuidFile, String.join(java.lang.System.lineSeparator(), fixedLines)
                    + java.lang.System.lineSeparator());
        }
    }

    /** Rewrites href URIs in correspondences.correspondence to reference the new vsumFolder. */
    private static void fixCorrespondenceFile(Path corrFile, String folderUri) throws IOException {
        if (!Files.exists(corrFile)) return;
        String content = Files.readString(corrFile);
        String fixed = content.replaceAll(
                "file:/+[^\"]*?/([^/\"]+)(#[^\"]*)?\"",
                folderUri + "/$1$2\"");
        if (!fixed.equals(content)) {
            Files.writeString(corrFile, fixed);
        }
    }

    /**
     * Finds the merge base (common ancestor) of two commits using JGit.
     *
     * @param commit1 first commit SHA
     * @param commit2 second commit SHA
     * @return the merge base commit SHA, or null if none found
     */
    public String findMergeBase(String commit1, String commit2) throws IOException {
        try (Git git = Git.open(repoRoot.toFile());
             RevWalk walk = new RevWalk(git.getRepository())) {

            ObjectId id1 = git.getRepository().resolve(commit1);
            ObjectId id2 = git.getRepository().resolve(commit2);

            if (id1 == null || id2 == null) {
                throw new IOException("Cannot resolve commits: " + commit1 + " or " + commit2);
            }

            RevCommit rev1 = walk.parseCommit(id1);
            RevCommit rev2 = walk.parseCommit(id2);

            walk.setRevFilter(org.eclipse.jgit.revwalk.filter.RevFilter.MERGE_BASE);
            walk.markStart(rev1);
            walk.markStart(rev2);

            RevCommit mergeBase = walk.next();
            if (mergeBase == null) {
                LOGGER.warn("No merge base found between {} and {}", commit1, commit2);
                return null;
            }

            String baseSha = mergeBase.getName();
            LOGGER.info("Merge base between {} and {} is {}",
                    commit1.substring(0, 7), commit2.substring(0, 7), baseSha.substring(0, 7));
            return baseSha;
        }
    }

    /**
     * Creates a temporary directory for loading a commit's state.
     */
    public static Path createTempDir(String prefix) throws IOException {
        return Files.createTempDirectory(prefix);
    }
}
