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
import tools.vitruv.framework.vsum.branch.data.FileChange;
import tools.vitruv.framework.vsum.branch.data.SemanticChangelog;
import tools.vitruv.framework.vsum.branch.data.ValidationResult;
import tools.vitruv.framework.vsum.internal.InternalVirtualModel;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Validates the VSUM for consistency before allowing a Git commit to proceed, and generates the semantic changelog entry for the commit.
 *
 * <p>Validation checks performed:
 * <ol>
 *   <li>All model resources are loadable and contain no XMI parse errors.</li>
 *   <li>No unresolved proxy objects exist (all cross-references can be resolved).</li>
 *   <li>The correspondence model is accessible and not null.</li>
 *   <li>The UUID resolver is accessible and not null.</li>
 * </ol>
 *
 * <p>todo: domain-specific validation rules, change recorder state validation, integration with Vitruvius reaction validation, detailed correspondence link checking, and orphaned UUID detection are planned for a future iteration.
 */
public class PreCommitHandler {
    private static final Logger LOGGER = LogManager.getLogger(PreCommitHandler.class);

    private final InternalVirtualModel virtualModel;

    /**
     * Creates a pre-commit handler for the given VirtualModel.
     * @param virtualModel the internal VirtualModel to validate, must not be null.
     */
    public PreCommitHandler(InternalVirtualModel virtualModel) {
        this.virtualModel = checkNotNull(virtualModel, "VirtualModel must not be null");
    }

    /**
     * Validates the VirtualModel for consistency. Returns a {@link ValidationResult} that indicates whether the commit is safe to proceed,
     * with error messages describing any detected inconsistencies and warning messages for non-blocking issues.
     * <p>If an unexpected exception is thrown during any validation step, it is caught, logged, and added to the error list rather than propagated,
     * so that the caller always receives a well-formed result.
     * @return the validation result with errors and warnings collected during all checks.
     */
    public ValidationResult validate() {
        LOGGER.info("Starting VirtualModel validation");
        List<String> errors = new ArrayList<>();
        List<String> warnings = new ArrayList<>();
        try {
            // check all resources are loadable and free of XMI parse errors
            validateResourcesLoadable(errors, warnings);
            // check that no cross-references point to unresolved proxy objects
            validateNoProxies(errors);
            // check that the correspondence model is reachable.
            validateCorrespondences(errors, warnings);
            // check that the UUID resolver is reachable.
            validateUuidResolver(errors);
        } catch (Exception e) {
            errors.add("Validation failed with unexpected exception: " + e.getMessage());
            LOGGER.error("Unexpected validation error", e);
        }

        // build the result from the collected errors and warnings.
        ValidationResult result;
        if (!errors.isEmpty()) {
            result = warnings.isEmpty() ? ValidationResult.failure(errors) : ValidationResult.failureWithWarnings(errors, warnings);
        } else if (!warnings.isEmpty()) {
            result = ValidationResult.successWithWarnings(warnings);
        } else {
            result = ValidationResult.success();
        }
        LOGGER.info("VirtualModel validation completed: valid={}, errors={}, warnings={}", result.isValid(), errors.size(), warnings.size());
        return result;
    }

    /**
     * Checks that every model resource can be loaded and has no XMI parse errors.
     * Resources that are not yet loaded are loaded eagerly so that parse errors are detected immediately.
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
                // load the resource if it has not been loaded yet, so that parse errors surface now rather than silently remaining undetected.
                if (!resource.isLoaded()) {
                    LOGGER.debug("Loading resource: {}", resource.getURI());
                    resource.load(Collections.emptyMap());
                }
                // collect all XMI parse errors reported by the resource.
                if (!resource.getErrors().isEmpty()) {
                    for (Resource.Diagnostic error : resource.getErrors()) {
                        errors.add("Resource load error in " + resource.getURI() + ": " + error.getMessage() + " (line " + error.getLine() + ")");
                    }
                }
                // collect parse warnings, which do not block the commit but should be visible.
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
     * Checks that no object in any loaded resource is an unresolved proxy, and that no non-containment cross-reference points to a proxy.
     * Unresolved proxies indicate that the VirtualModel references objects in files that no longer exist or have moved.
     */
    private void validateNoProxies(List<String> errors) {
        Collection<Resource> resources = virtualModel.getViewSourceModels();
        int proxyCount = 0;

        for (Resource resource : resources) {
            if (resource.isLoaded()) {
                // traverse all model objects in the resource recursively.
                TreeIterator<EObject> iterator = resource.getAllContents();
                while (iterator.hasNext()) {
                    EObject obj = iterator.next();
                    // an object that is itself a proxy means its containing resource was not resolved during load.
                    if (obj.eIsProxy()) {
                        String uri = EcoreUtil.getURI(obj).toString();
                        errors.add("Unresolved proxy object: " + uri + " in resource " + resource.getURI());
                        proxyCount++;
                    }
                    // check non-containment references without resolving them, so that dangling proxies are reported rather than silently auto-resolved.
                    for (EReference ref : obj.eClass().getEAllReferences()) {
                        if (!ref.isContainment()) {
                            Object value = obj.eGet(ref, false);
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
     * Checks that the correspondence model is accessible. Detailed validation of individual correspondence links is planned for a future iteration once the correspondence model
     * view exposes a way to enumerate all stored correspondences.
     */
    private void validateCorrespondences(List<String> errors, List<String> warnings) {
        try {
            EditableCorrespondenceModelView<Correspondence> corrModel = virtualModel.getCorrespondenceModel();
            if (corrModel == null) {
                errors.add("Correspondence model is null");
                return;
            }
            // accessibility is the only check possible at this stage because the correspondence model view does not currently expose a full enumeration API.
            LOGGER.debug("Correspondence model is accessible");
        } catch (Exception e) {
            errors.add("Failed to access correspondence model: " + e.getMessage());
            LOGGER.error("Correspondence validation error", e);
        }
    }

    /**
     * Checks that the UUID resolver is accessible. Detailed validation such as detecting
     * orphaned UUIDs or verifying UUID-to-object mappings is planned for a future iteration.
     */
    private void validateUuidResolver(List<String> errors) {
        try {
            UuidResolver resolver = virtualModel.getUuidResolver();
            if (resolver == null) {
                errors.add("UUID resolver is null");
                return;
            }
            // accessibility is the only check performed at this stage
            // deeper validation such as orphaned UUID detection will be added in a future iteration.
            LOGGER.debug("UUID resolver is accessible");
        } catch (Exception e) {
            errors.add("Failed to access UUID resolver: " + e.getMessage());
            LOGGER.error("UUID resolver validation error", e);
        }
    }

    /**
     * Generates a minimal semantic changelog entry for the given commit.
     * Returns a placeholder changelog because querying Git for the actual changed files via JGit is planned for a future iteration.
     * At that point, this method will diff HEAD against HEAD^ and produce a {@link FileChange} entry for each added, modified, or deleted file.
     * @param commitSha the full Git commit SHA.
     * @param branch    the branch on which the commit was made.
     * @return a {@link SemanticChangelog} with an empty change list and a placeholder author.
     */
    public SemanticChangelog generateChangelog(String commitSha, String branch) {
        // use the standard seven-character short SHA that matches the Git convention and the format used in SemanticChangelog.toString().
        LOGGER.info("Generating changelog for commit {} on branch {}", commitSha.substring(0, Math.min(7, commitSha.length())), branch);

        // actual file changes will be populated once JGit diff integration is available.
        List<FileChange> changes = new ArrayList<>();

        return SemanticChangelog.create(commitSha, "system", LocalDateTime.now(), "Commit on " + branch, branch, changes);
    }
}