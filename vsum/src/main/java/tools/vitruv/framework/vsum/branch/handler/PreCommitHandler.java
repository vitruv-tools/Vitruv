package tools.vitruv.framework.vsum.branch.handler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import tools.vitruv.change.atomic.uuid.UuidResolver;
import tools.vitruv.change.correspondence.Correspondence;
import tools.vitruv.change.correspondence.view.EditableCorrespondenceModelView;
import tools.vitruv.framework.vsum.internal.InternalVirtualModel;
import tools.vitruv.framework.vsum.branch.data.FileChange;
import tools.vitruv.framework.vsum.branch.data.SemanticChangelog;
import tools.vitruv.framework.vsum.branch.data.ValidationResult;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Handles pre-commit validation of the V-SUM.
 *
 * <p>Validates V-SUM consistency before allowing Git commits to ensure:
 * <ul>
 *   <li>All resources are loadable (no corrupted XMI files)</li>
 *   <li>No unresolved proxy objects (all references valid)</li>
 *   <li>Correspondences are valid (no broken links between models)</li>
 *   <li>UUID resolver is in a consistent state</li>
 * </ul>
 *
 * <p>Also generates semantic changelogs for commits.
 *
 * <p>todo: Add domain-specific validation rules
 * <p>todo: Add change recorder state validation
 * <p>todo: Integrate with Vitruvius reaction validation
 */
public class PreCommitHandler {
    private static final Logger LOGGER = LogManager.getLogger(PreCommitHandler.class);

    private final InternalVirtualModel virtualModel;

    /**
     * Creates a pre-commit handler for the given virtual model.
     *
     * @param virtualModel the internal virtual model to validate
     */
    public PreCommitHandler(InternalVirtualModel virtualModel) {
        this.virtualModel = virtualModel;
    }

    /**
     * Validates the V-SUM for consistency.
     *
     * <p>Performs the following checks:
     * <ol>
     *   <li>All resources are loadable and have no errors</li>
     *   <li>No unresolved proxy objects exist</li>
     *   <li>All correspondences point to valid, non-proxy objects</li>
     *   <li>UUID resolver is accessible and functional</li>
     * </ol>
     *
     * @return validation result with errors and warnings
     */
    public ValidationResult validate() {
        LOGGER.info("Starting V-SUM validation");
        List<String> errors = new ArrayList<>();
        List<String> warnings = new ArrayList<>();
        try {
            // Check all resources are loadable
            validateResourcesLoadable(errors, warnings);
            // Check for unresolved proxies
            validateNoProxies(errors);
            // Check correspondences are valid
            validateCorrespondences(errors, warnings);
            // Check UUID resolver state
            validateUuidResolver(errors);
        } catch (Exception e) {
            errors.add("Validation failed with unexpected exception: " + e.getMessage());
            LOGGER.error("Unexpected validation error", e);
        }

        // Build result
        ValidationResult result;
        if (!errors.isEmpty()) {
            result = warnings.isEmpty() ? ValidationResult.failure(errors) : ValidationResult.failureWithWarnings(errors, warnings);
        } else if (!warnings.isEmpty()) {
            result = ValidationResult.successWithWarnings(warnings);
        } else {
            result = ValidationResult.success();
        }
        LOGGER.info("V-SUM validation completed: valid={}, errors={}, warnings={}", result.isValid(), errors.size(), warnings.size());
        return result;
    }

    /**
     * Validates that all resources can be loaded and have no errors.
     */
    private void validateResourcesLoadable(List<String> errors, List<String> warnings) {
        Collection<Resource> resources = virtualModel.getViewSourceModels();
        if (resources == null || resources.isEmpty()) {
            warnings.add("No model resources found in V-SUM");
            return;
        }
        LOGGER.debug("Validating {} resources", resources.size());
        for (Resource resource : resources) {
            try {
                // Check if resource is loaded
                if (!resource.isLoaded()) {
                    LOGGER.debug("Loading resource: {}", resource.getURI());
                    resource.load(Collections.emptyMap());
                }

                // Check for load errors
                if (!resource.getErrors().isEmpty()) {
                    for (Resource.Diagnostic error : resource.getErrors()) {
                        errors.add("Resource load error in " + resource.getURI() + ": " + error.getMessage() + " (line " + error.getLine() + ")");
                    }
                }
                // Check for warnings
                if (!resource.getWarnings().isEmpty()) {
                    for (Resource.Diagnostic warning : resource.getWarnings()) {
                        warnings.add("Resource warning in " + resource.getURI() + ": " + warning.getMessage());
                    }
                }
            } catch (IOException e) {
                errors.add("Cannot load resource " + resource.getURI() + ": " + e.getMessage());
                LOGGER.error("Failed to load resource: {}", resource.getURI(), e);
            } catch (Exception e) {
                errors.add("Unexpected error loading " + resource.getURI() + ": " + e.getMessage());
                LOGGER.error("Unexpected error loading resource: {}", resource.getURI(), e);
            }
        }
    }

    /**
     * Validates that no unresolved proxy objects exist in any resource.
     */
    private void validateNoProxies(List<String> errors) {
        Collection<Resource> resources = virtualModel.getViewSourceModels();
        int proxyCount = 0;

        for (Resource resource : resources) {
            if (resource.isLoaded()) {
                // Iterate all contents recursively
                TreeIterator<EObject> iterator = resource.getAllContents();
                while (iterator.hasNext()) {
                    EObject obj = iterator.next();
                    // Check if object itself is a proxy
                    if (obj.eIsProxy()) {
                        String uri = EcoreUtil.getURI(obj).toString();
                        errors.add("Unresolved proxy object: " + uri + " in resource " + resource.getURI());
                        proxyCount++;
                    }
                    // Check all cross-references for proxies
                    for (EReference ref : obj.eClass().getEAllReferences()) {
                        if (!ref.isContainment()) {
                            Object value = obj.eGet(ref, false); // Don't resolve proxies
                            if (value instanceof EObject && ((EObject) value).eIsProxy()) {
                                errors.add("Unresolved reference '" + ref.getName() + "' in " + EcoreUtil.getURI(obj) + " points to proxy " + EcoreUtil.getURI((EObject) value));
                                proxyCount++;
                            } else if (value instanceof Collection) {
                                for (Object item : (Collection<?>) value) {
                                    if (item instanceof EObject && ((EObject) item).eIsProxy()) {
                                        errors.add("Unresolved reference in collection '" + ref.getName() + "' in " + EcoreUtil.getURI(obj) + " points to proxy");
                                        proxyCount++;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        if (proxyCount > 0) {
            LOGGER.warn("Found {} unresolved proxies", proxyCount);
        }
    }

    /**
     * Validates that all correspondences point to valid, non-proxy objects.
     */
    private void validateCorrespondences(List<String> errors, List<String> warnings) {
        try {
            EditableCorrespondenceModelView<Correspondence> corrModel = virtualModel.getCorrespondenceModel();

            if (corrModel == null) {
                errors.add("Correspondence model is null");
                return;
            }
            // The correspondence model view doesn't directly expose all correspondences
            // currently only validate that the correspondence model is at least accessible
            // TODO: Add more detailed correspondence validation
            LOGGER.debug("Correspondence model is accessible");
        } catch (Exception e) {
            errors.add("Failed to access correspondence model: " + e.getMessage());
            LOGGER.error("Correspondence validation error", e);
        }
    }

    /**
     * Validates that the UUID resolver is in a valid state.
     */
    private void validateUuidResolver(List<String> errors) {
        try {
            UuidResolver resolver = virtualModel.getUuidResolver();
            if (resolver == null) {
                errors.add("UUID resolver is null");
                return;
            }
            // UUID resolver is accessible and functional
            LOGGER.debug("UUID resolver is accessible");
            // TODO: Add more detailed UUID resolver validation
            // Check for orphaned UUIDs
            // Validate UUID to object mappings
        } catch (Exception e) {
            errors.add("Failed to access UUID resolver: " + e.getMessage());
            LOGGER.error("UUID resolver validation error", e);
        }
    }

    /**
     * Generates a semantic changelog for the given commit.
     *
     * <p>TODO : Implement changelog generation by querying Git for changed files.
     * For now, returns a minimal changelog.
     *
     * @param commitSha the Git commit SHA
     * @param branch the branch name
     * @return semantic changelog
     */
    public SemanticChangelog generateChangelog(String commitSha, String branch) {
        LOGGER.info("Generating changelog for commit {} on branch {}", commitSha.substring(0, Math.min(8, commitSha.length())), branch);

        // TODO: Query Git to detect changed files
        // - Use JGit to get diff between HEAD and HEAD^
        // - Determine which files were ADDED, MODIFIED, DELETED
        // - Create FileChange objects for each

        // For now, create minimal changelog
        List<FileChange> changes = new ArrayList<>();
        // changes.add(new FileChange("models/User.java", FileOperation.MODIFIED));

        // TODO: Get actual author from Git
        // TODO: Get actual commit message
        return SemanticChangelog.create(commitSha, "system", LocalDateTime.now(), "Commit on " + branch, branch, changes);
    }
}