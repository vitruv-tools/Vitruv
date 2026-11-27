package tools.vitruv.framework.vsum.branch.merge;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import tools.vitruv.change.interaction.InteractionResultProvider;
import tools.vitruv.change.propagation.ChangePropagationSpecification;

/**
 * Git custom merge driver for Vitruvius VSUM repositories.
 *
 * <p>Registered in {@code .gitattributes} (e.g., {@code *.xmi merge=vitruvius})
 * and invoked by Git once per conflicting file during {@code git merge}.
 *
 * <p>On the first invocation within a merge, the driver runs the full semantic
 * merge via {@link SemanticMergeCommand} and caches the resulting VSUM state
 * in {@code .vitruvius/merge-cache/}. Subsequent per-file invocations copy
 * the corresponding merged file from the cache. If the merge succeeds (all
 * drivers return exit code 0), Git creates the merge commit automatically.
 *
 * <h3>Configuration</h3>
 * <p>The driver reads {@code .vitruvius/merge-driver.properties} from the
 * repository root to discover which {@link ChangePropagationSpecification}
 * classes to load. Example:
 * <pre>
 * specifications=com.example.AToB,com.example.BToA,com.example.AToC
 * </pre>
 *
 * <h3>Git setup</h3>
 * <pre>
 * # .gitattributes
 * *.brakesystem merge=vitruvius
 * *.cad merge=vitruvius
 * *.safety merge=vitruvius
 *
 * # .git/config  (or run: git config merge.vitruvius.driver "java -cp ... GitMergeDriver %O %A %B %L %P")
 * [merge "vitruvius"]
 *     name = Vitruvius semantic merge
 *     driver = vitruvius-merge %O %A %B %L %P
 * </pre>
 */
public class GitMergeDriver {

    private static final Logger LOGGER = LogManager.getLogger(GitMergeDriver.class);

    private static final String CACHE_DIR = ".vitruvius/merge-cache";
    private static final String CACHE_DONE_MARKER = ".merge-done";
    private static final String CACHE_CONFLICT_MARKER = ".merge-conflict";
    private static final String CONFIG_FILE = ".vitruvius/merge-driver.properties";

    /**
     * Entry point invoked by Git's merge driver mechanism.
     *
     * @param args {@code %O %A %B %L %P} -- ancestor, ours (overwrite in place),
     *             theirs, conflict-marker-size, file pathname
     */
    public static void main(String[] args) {
        if (args.length < 5) {
            System.err.println("Usage: GitMergeDriver <base> <ours> <theirs> <marker-size> <path>");
            System.exit(1);
        }

        String oursFile = args[1];   // overwrite this with merged content
        String filePath = args[4];   // path relative to repo root

        try {
            Path repoRoot = findRepoRoot();
            Path cacheDir = repoRoot.resolve(CACHE_DIR);

            // Evict a stale cache left by a previously-completed merge. The cache is stale
            // when .merge-done exists but .git/MERGE_HEAD does not: the prior merge already
            // committed and Git is now invoking the driver for a new, unrelated merge.
            // Without this guard the driver would skip runSemanticMerge() and serve outdated
            // files from the previous run.
            if (isCacheStale(repoRoot, cacheDir)) {
                LOGGER.info("Stale merge cache detected (prior merge already committed) -- evicting");
                cleanCache(repoRoot);
            }

            // Run the full merge once, cache results
            if (!Files.exists(cacheDir.resolve(CACHE_DONE_MARKER))
                    && !Files.exists(cacheDir.resolve(CACHE_CONFLICT_MARKER))) {
                runSemanticMerge(repoRoot, cacheDir);
            }

            // If merge had blocking conflicts, signal conflict to Git
            if (Files.exists(cacheDir.resolve(CACHE_CONFLICT_MARKER))) {
                LOGGER.warn("Semantic merge has blocking conflicts; marking {} as conflicted", filePath);
                System.exit(1);
            }

            // Copy merged file from cache to the "ours" temp file
            Path cachedFile = cacheDir.resolve(filePath);
            if (Files.exists(cachedFile)) {
                Files.copy(cachedFile, Path.of(oursFile), StandardCopyOption.REPLACE_EXISTING);
                LOGGER.info("Merged file written: {}", filePath);
                System.exit(0);
            } else {
                // File was not part of the VSUM (e.g., deleted during merge)
                LOGGER.info("No merged version for {}, leaving as-is", filePath);
                System.exit(0);
            }

        } catch (Exception e) {
            LOGGER.error("Merge driver failed for {}", filePath, e);
            System.err.println("Vitruvius merge driver error: " + e.getMessage());
            System.exit(1);
        }
    }

    /**
     * Runs the full semantic merge and caches the resulting model files.
     */
    private static void runSemanticMerge(Path repoRoot, Path cacheDir) throws Exception {
        Files.createDirectories(cacheDir);

        // Read branch SHAs from Git state
        String oursSha = readGitFile(repoRoot, "HEAD").trim();
        // HEAD may be a symbolic ref ("ref: refs/heads/main"), resolve it
        if (oursSha.startsWith("ref:")) {
            String ref = oursSha.substring(4).trim();
            oursSha = readGitFile(repoRoot, ref).trim();
        }
        String theirsSha = readGitFile(repoRoot, "MERGE_HEAD").trim();

        LOGGER.info("Semantic merge: ours={} theirs={}", shorten(oursSha), shorten(theirsSha));

        // Load ChangePropagationSpecifications from config
        Collection<ChangePropagationSpecification> specs = loadSpecifications(repoRoot);
        InteractionResultProvider interactionProvider = createNonInteractiveProvider();

        // Run the merge
        GitStateLoader loader = new GitStateLoader(repoRoot);
        String baseSha = loader.findMergeBase(oursSha, theirsSha);
        if (baseSha == null) {
            throw new IOException("No common ancestor found");
        }

        SemanticMergeEngine engine = new SemanticMergeEngine(
                repoRoot, specs, interactionProvider, null);
        SemanticMergeResult result = engine.mergeWithInterleaving(baseSha, oursSha, theirsSha);

        if (result.isSuccess()) {
            // Copy merged VSUM state to cache
            copyVsumFiles(result.getMergedStateFolder(), cacheDir, repoRoot);
            Files.createFile(cacheDir.resolve(CACHE_DONE_MARKER));
            LOGGER.info("Semantic merge succeeded, cached in {}", cacheDir);
        } else {
            // Write conflict info and mark as conflicted
            StringBuilder sb = new StringBuilder("Blocking conflicts:\n");
            for (MergeConflict c : result.getConflicts()) {
                sb.append("  - ").append(c).append("\n");
            }
            Files.writeString(cacheDir.resolve(CACHE_CONFLICT_MARKER), sb.toString());
            LOGGER.warn("Semantic merge found {} blocking conflicts", result.getConflicts().size());
        }
    }

    /**
     * Copies model files from the merged VSUM temp directory to the cache,
     * preserving their paths relative to the repository root.
     *
     * <p>The merged state folder mirrors the repo structure (model files at
     * the root, VSUM metadata in {@code vsum/}). We copy all non-VSUM-internal
     * files so Git can pick them up per-file.
     */
    private static void copyVsumFiles(Path mergedFolder, Path cacheDir, Path repoRoot) throws IOException {
        try (var stream = Files.walk(mergedFolder)) {
            stream.filter(Files::isRegularFile).forEach(source -> {
                try {
                    Path relative = mergedFolder.relativize(source);
                    Path target = cacheDir.resolve(relative);
                    Files.createDirectories(target.getParent());
                    Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException e) {
                    LOGGER.error("Failed to cache {}", source, e);
                }
            });
        }
    }

    /**
     * Loads {@link ChangePropagationSpecification} instances from the config file.
     */
    @SuppressWarnings("unchecked")
    private static Collection<ChangePropagationSpecification> loadSpecifications(Path repoRoot) throws Exception {
        Path configPath = repoRoot.resolve(CONFIG_FILE);
        if (!Files.exists(configPath)) {
            throw new IOException("Missing merge driver config: " + configPath
                    + "\nCreate " + CONFIG_FILE + " with a 'specifications' property listing"
                    + " fully qualified ChangePropagationSpecification class names, comma-separated.");
        }

        Properties props = new Properties();
        try (InputStream is = Files.newInputStream(configPath)) {
            props.load(is);
        }

        String specNames = props.getProperty("specifications", "").trim();
        if (specNames.isEmpty()) {
            throw new IOException("No 'specifications' property in " + CONFIG_FILE);
        }

        List<ChangePropagationSpecification> specs = new ArrayList<>();
        for (String className : specNames.split(",")) {
            className = className.trim();
            if (className.isEmpty()) continue;
            Class<?> clazz = Class.forName(className);
            specs.add((ChangePropagationSpecification) clazz.getDeclaredConstructor().newInstance());
        }

        LOGGER.info("Loaded {} ChangePropagationSpecifications from {}", specs.size(), CONFIG_FILE);
        return specs;
    }

    /**
     * Creates a non-interactive provider that auto-selects the first option.
     * Suitable for automated merge where no user interaction is available.
     */
    private static InteractionResultProvider createNonInteractiveProvider() {
        return new InteractionResultProvider() {
            @Override
            public boolean getConfirmationInteractionResult(
                    tools.vitruv.change.interaction.UserInteractionOptions.WindowModality m,
                    String title, String message, String pos, String neg, String cancel) {
                LOGGER.info("Auto-confirming: {}", message);
                return true;
            }

            @Override
            public void getNotificationInteractionResult(
                    tools.vitruv.change.interaction.UserInteractionOptions.WindowModality m,
                    String title, String message, String pos,
                    tools.vitruv.change.interaction.UserInteractionOptions.NotificationType type) {
                LOGGER.info("Notification: {}", message);
            }

            @Override
            public String getTextInputInteractionResult(
                    tools.vitruv.change.interaction.UserInteractionOptions.WindowModality m,
                    String title, String message, String pos, String cancel,
                    tools.vitruv.change.interaction.UserInteractionOptions.InputValidator validator) {
                LOGGER.warn("Text input requested during non-interactive merge: {}", message);
                return "";
            }

            @Override
            public int getMultipleChoiceSingleSelectionInteractionResult(
                    tools.vitruv.change.interaction.UserInteractionOptions.WindowModality m,
                    String title, String message, String pos, String cancel, Iterable<String> choices) {
                LOGGER.info("Auto-selecting first choice for: {}", message);
                return 0;
            }

            @Override
            public Iterable<Integer> getMultipleChoiceMultipleSelectionInteractionResult(
                    tools.vitruv.change.interaction.UserInteractionOptions.WindowModality m,
                    String title, String message, String pos, String cancel, Iterable<String> choices) {
                LOGGER.info("Auto-selecting first choice for: {}", message);
                return List.of(0);
            }
        };
    }

    /**
     * Reads a file from the {@code .git/} directory.
     */
    private static String readGitFile(Path repoRoot, String name) throws IOException {
        Path gitDir = repoRoot.resolve(".git");
        // Handle both direct path (e.g., "MERGE_HEAD") and ref path (e.g., "refs/heads/main")
        Path file = gitDir.resolve(name);
        if (!Files.exists(file)) {
            throw new IOException("Git file not found: " + file);
        }
        return Files.readString(file);
    }

    private static Path findRepoRoot() throws IOException {
        Path dir = Path.of("").toAbsolutePath();
        while (dir != null) {
            if (Files.isDirectory(dir.resolve(".git"))) {
                return dir;
            }
            dir = dir.getParent();
        }
        throw new IOException("Not inside a Git repository");
    }

    private static String shorten(String sha) {
        return sha.length() > 7 ? sha.substring(0, 7) : sha;
    }

    /**
     * Returns {@code true} when the merge cache holds results from a previously-completed
     * merge and must be evicted before the current merge runs.
     *
     * <p>The cache is stale when {@code .merge-done} exists (a prior run finished) but
     * {@code .git/MERGE_HEAD} does not (that merge already committed and Git is now starting
     * a fresh merge). Package-private for unit testing.
     */
    static boolean isCacheStale(Path repoRoot, Path cacheDir) {
        return Files.exists(cacheDir.resolve(CACHE_DONE_MARKER))
                && !Files.exists(repoRoot.resolve(".git/MERGE_HEAD"));
    }

    /**
     * Cleans up the merge cache. Should be called after the merge commit
     * is created (e.g., from a post-merge hook or manually).
     */
    public static void cleanCache(Path repoRoot) throws IOException {
        Path cacheDir = repoRoot.resolve(CACHE_DIR);
        if (Files.exists(cacheDir)) {
            try (var stream = Files.walk(cacheDir).sorted(java.util.Comparator.reverseOrder())) {
                stream.forEach(p -> {
                    try { Files.deleteIfExists(p); } catch (IOException ignored) {}
                });
            }
            LOGGER.info("Cleaned merge cache at {}", cacheDir);
        }
    }
}
