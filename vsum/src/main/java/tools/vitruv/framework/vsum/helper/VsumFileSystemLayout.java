package tools.vitruv.framework.vsum.helper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Repository;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;

import static com.google.common.base.Preconditions.*;
import static edu.kit.ipd.sdq.commons.util.org.eclipse.emf.common.util.URIUtil.createFileURI;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.nio.file.Files.createDirectories;

/**
 * Helper class to manage the file system layout of a Vitruv project with VSUM support.
 */
public class VsumFileSystemLayout {
    private static final String CORRESPONDENCES_FILE = "correspondences.correspondence";
    private static final String UUIDS_FILE = "uuid.uuid";
    private static final String MODELS_FILE = "models.models";
    private static final String VSUM_FOLDER_NAME = "vsum";
    private static final String CONSISTENCY_METADATA_FOLDER_NAME = "consistencymetadata";

    /**
     * Linh:new
     */
    private static final String VSUM_BASE_DIR = ".vitruvius/vsum";
    private final String currentBranch;
    private static final Logger LOGGER = LogManager.getLogger(VsumFileSystemLayout.class);

    private final Path vsumProjectFolder;
    private boolean prepared = false;

    /**
     * Creates a new file system layout for the given Vitruv project folder.
     *
     * @param vsumProjectFolder the Vitruv project folder
     */
    public VsumFileSystemLayout(Path vsumProjectFolder) {

        this.vsumProjectFolder = vsumProjectFolder;
        //Linh:new
        this.currentBranch = resolveBranchName();
    }

    /**
     * Linh:new
     * Package-private constructor for tests that do not run inside git repo
     */
    VsumFileSystemLayout(Path vsumProjectFolder, String branchName) {
        this.vsumProjectFolder = vsumProjectFolder;
        this.currentBranch = branchName;
    }

    /**
     * Linh:new
     * Resolve name of the current branch
     */
    private String resolveBranchName() {
        try (Git git = Git.open(vsumProjectFolder.toFile())) {
            Repository repo = git.getRepository();

            //at least one commit is required so that the installed hooks can work correctly
            if (repo.resolve("HEAD") == null) {
                throw new IllegalStateException("Git repository at '" + vsumProjectFolder + "' has no commits." + "Please make an inital commit first");
            }

            var head = repo.findRef("HEAD");
            if (head != null && head.isSymbolic()) {
                String branch = Repository.shortenRefName(head.getTarget().getName());
                LOGGER.debug("Resolved current branch: '{}'", branch);
                return branch;
            }
            LOGGER.warn("HEAD is detached, using 'detached' as branch name");
            return "detached";
        } catch (IllegalStateException e) {
            throw e;
        } catch (Exception e) {
            throw new IllegalStateException("No Git repository found at: '" + vsumProjectFolder + "'. " + "Please run: git init && git add . && git commit -m 'initial'", e);
        }
    }

    /**
     * Prepares the file system layout by creating the necessary folders.
     *
     * @throws IOException if folder creation fails
     */
    public void prepare() throws IOException {
        createDirectories(getVsumFolder());
        createDirectories(getConsistencyMetadataFolder());
        prepared = true;
    }

    private Path getMetadataFilePath(String... metadataKey) {
        checkArgument(
                metadataKey != null && metadataKey.length > 0, "The key must have at least one part!");
        checkArgument(
                metadataKey[metadataKey.length - 1].contains("."),
                "metadataKey is missing a file extension!");

        Path result = Path.of("");
        for (String keyPart : metadataKey) {
            checkArgument(keyPart != null, "A key part must not be null!");
            // URL-encoding the string makes it safe for being a file part,
            // except for the cases '', '.' and '..'
            // we thus use _ as an escape character
            // This also ensures that the resulting path is always located
            // within the metadata folder.
            String preparedKeyPart;
            assert keyPart != null;
            preparedKeyPart =
                    switch (keyPart) {
                        case "." -> "_.";
                        case ".." -> "_._.";
                        case "" -> "_";
                        default -> keyPart.replace("_", "__");
                    };
            result = result.resolve(URLEncoder.encode(preparedKeyPart, UTF_8));
        }
        return result;
    }

    /**
     * Gets the {@link URI} of a model that stores metadata.
     *
     * @param metadataKey The key uniquely identifying the metadata model. The different parts of the
     *                    key can be used to convey some sort of hierarchy in the metadata. The key may contain
     *                    arbitrary characters. The last key part contains the metadata model's file name and
     *                    extension.
     * @return the URI of the specified metadata model
     */
    public URI getConsistencyMetadataModelURI(String... metadataKey) {
        checkPrepared();
        Path metadataPath = getConsistencyMetadataFolder().resolve(getMetadataFilePath(metadataKey));
        return createFileURI(metadataPath.toFile());
    }

    /**
     * Gets the URI of the correspondences file.
     *
     * @return the URI of the correspondences file
     */
    public URI getCorrespondencesURI() {
        checkPrepared();
        return createFileURI(getVsumFolder().resolve(CORRESPONDENCES_FILE).toFile());
    }

    /**
     * Gets the URI of the UUIDs file.
     *
     * @return the URI of the UUIDs file
     */
    public URI getUuidsURI() {
        checkPrepared();
        return createFileURI(getVsumFolder().resolve(UUIDS_FILE).toFile());
    }

    /**
     * Gets the path of the models names file.
     *
     * @return the path of the models names file
     */
    public Path getModelsNamesFilesPath() {
        checkPrepared();
        return getVsumFolder().resolve(MODELS_FILE);
    }

    /**
     * Gets the Vitruv project folder.
     *
     * @return the Vitruv project folder
     */
    public Path getVsumProjectFolder() {
        return this.vsumProjectFolder;
    }

    private Path getVsumFolder() {
        //return vsumProjectFolder.resolve(VSUM_FOLDER_NAME);
        //Linh:new
        return vsumProjectFolder.resolve(VSUM_BASE_DIR).resolve(currentBranch);
    }

    private Path getConsistencyMetadataFolder() {
        //return vsumProjectFolder.resolve(CONSISTENCY_METADATA_FOLDER_NAME);
        //Linh:new
        return getVsumFolder().resolve(CONSISTENCY_METADATA_FOLDER_NAME);
    }

    /**
     * Creates a layout for a specific branch, bypassing JGit branch resolution.
     * Used when the branch name is already known, for example during branch switching
     * in {@link tools.vitruv.framework.vsum.branch.BranchAwareVirtualModel}.
     * @param vsumProjectFolder the Vitruv project folder
     * @param branchName  the branch name to use
     * @return a new layout pointing to {@code .vitruvius/vsum/{branchName}/}
     */
    public static VsumFileSystemLayout forBranch(Path vsumProjectFolder, String branchName) {
        return new VsumFileSystemLayout(vsumProjectFolder, branchName);
    }

    /**
     * Linh:new
     */
    public String getCurrentBranch() {
        return currentBranch;
    }

    public void inheritFromBranchIfEmpty(String sourceBranchName) {
        checkState(prepared, "Layout must be prepared before inheriting state");

        Path myUuidsFile = Path.of(getUuidsURI().toFileString());
        if (Files.exists(myUuidsFile)) {
            LOGGER.debug("Branch '{}' already has vsum state, skipping inheritance", currentBranch);
            return;
        }

        LOGGER.info("Branch '{}' has no vsum state — inheriting from '{}'", currentBranch, sourceBranchName);

        // Build source paths directly to avoid triggering checkPrepared() on a
        // layout that was never prepared
        Path sourceVsumFolder = vsumProjectFolder.resolve(VSUM_BASE_DIR).resolve(sourceBranchName);
        Path sourceUuidsFile = sourceVsumFolder.resolve(UUIDS_FILE);
        Path sourceCorrespondencesFile = sourceVsumFolder.resolve(CORRESPONDENCES_FILE);
        Path myCorrespondencesFile = Path.of(getCorrespondencesURI().toFileString());

        try {
            if (Files.exists(sourceUuidsFile)) {
                Files.copy(sourceUuidsFile, myUuidsFile);
                LOGGER.debug("Inherited uuid.uuid from branch '{}'", sourceBranchName);
            } else {
                LOGGER.debug("Source branch '{}' has no uuid.uuid — starting fresh", sourceBranchName);
            }

            if (Files.exists(sourceCorrespondencesFile)) {
                Files.copy(sourceCorrespondencesFile, myCorrespondencesFile);
                LOGGER.debug("Inherited correspondences from branch '{}'", sourceBranchName);
            } else {
                LOGGER.debug("Source branch '{}' has no correspondences — starting fresh", sourceBranchName);
            }

        } catch (IOException e) {
            LOGGER.warn("Could not inherit vsum state from '{}' — starting fresh: {}", sourceBranchName, e.getMessage());
        }
    }

    @Override
    public String toString() {
        //return "@" + vsumProjectFolder;
        //Linh:new
        return "@" + vsumProjectFolder + "[branch=" + currentBranch + "]";
    }

    private void checkPrepared() {
        checkState(prepared, "The file system layout has not been loaded yet!");
    }
}
