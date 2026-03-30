package tools.vitruv.framework.vsum.branch.handler;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
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
import tools.vitruv.framework.vsum.branch.data.ValidationResult;
import tools.vitruv.framework.vsum.branch.util.MergeResultFile;
import tools.vitruv.framework.vsum.internal.InternalVirtualModel;

/**
 * Handles post-merge operations: validates the merged VSUM state, detects conflict markers,
 * generates merge metadata, and propagates VSUM state from the source branch.
 */
public class PostMergeHandler {

  private static final Logger LOGGER = LogManager.getLogger(PostMergeHandler.class);

  private static final List<String> CONFLICT_MARKERS = List.of("<<<<<<<", "=======", ">>>>>>>");

  private final InternalVirtualModel virtualModel;

  private final Path repositoryRoot;

  /**
   * Creates a post-merge handler for the given VirtualModel and repository root.
   *
   * @param virtualModel   the VirtualModel to validate after a merge, must not be null.
   * @param repositoryRoot the root directory of the Git repository, must not be null.
   */
  public PostMergeHandler(InternalVirtualModel virtualModel, Path repositoryRoot) {
    this.virtualModel = checkNotNull(virtualModel, "virtual model must not be null");
    this.repositoryRoot = checkNotNull(repositoryRoot, "repository root must not be null");
  }

  /**
   * Validates the merged VirtualModel state. Returns a {@link ValidationResult} capturing
   * any errors (conflict markers, unresolved proxies, inaccessible subsystems) and warnings.
   *
   * @return the validation result collected from all post-merge checks.
   */
  public ValidationResult validate() {
    LOGGER.info("Starting post-merge VirtualModel validation");
    List<String> errors = new ArrayList<>();
    List<String> warnings = new ArrayList<>();

    try {
      validateResourcesLoadable(errors, warnings);
      validateNoConflictMarkers(errors);
      validateNoProxies(errors);
      validateCorrespondences(errors, warnings);
      validateUuidResolver(errors);
    } catch (Exception e) {
      errors.add("Post-merge validation failed with unexpected exception: " + e.getMessage());
      LOGGER.error("Unexpected post-merge validation error", e);
    }

    ValidationResult result;
    if (!errors.isEmpty()) {
      result = warnings.isEmpty()
          ? ValidationResult.failure(errors)
          : ValidationResult.failureWithWarnings(errors, warnings);
    } else if (!warnings.isEmpty()) {
      result = ValidationResult.successWithWarnings(warnings);
    } else {
      result = ValidationResult.success();
    }
    LOGGER.info("Post-merge VirtualModel validation completed: valid={}, errors={}, warnings={}",
        result.isValid(), errors.size(), warnings.size());
    return result;
  }

  /**
   * Writes merge metadata to the given result file.
   *
   * @param mergeResultFile  the file to write metadata to.
   * @param mergeCommitSha   the SHA of the merge commit.
   * @param sourceBranch     the branch that was merged in.
   * @param targetBranch     the branch that received the merge.
   * @param result           the validation result for the merged state.
   * @param conflictingFiles relative paths of files with conflict markers.
   * @throws IOException if writing the metadata file fails.
   */
  public void generateMergeMetadata(MergeResultFile mergeResultFile, String mergeCommitSha,
      String sourceBranch, String targetBranch, ValidationResult result,
      List<String> conflictingFiles) throws IOException {
    mergeResultFile.writeMetadata(mergeCommitSha, sourceBranch, targetBranch,
        result, conflictingFiles);
  }

  private void validateUuidResolver(List<String> errors) {
    try {
      UuidResolver resolver = virtualModel.getUuidResolver();
      if (resolver == null) {
        errors.add("UUID resolver is null after merge");
        return;
      }
      LOGGER.debug("UUID resolver is accessible after merge");
    } catch (Exception e) {
      errors.add("Failed to access UUID resolver after merge: " + e.getMessage());
      LOGGER.error("UUID resolver access failed after merge", e);
    }
  }

  private void validateCorrespondences(List<String> errors, List<String> warnings) {
    try {
      EditableCorrespondenceModelView<Correspondence> corrModel =
          virtualModel.getCorrespondenceModel();
      if (corrModel == null) {
        errors.add("Correspondence model is null after merge");
        return;
      }
      LOGGER.debug("Correspondence model is accessible after merge");
    } catch (Exception e) {
      errors.add("Failed to access correspondence model after merge: " + e.getMessage());
      LOGGER.error("Correspondence model access failed after merge", e);
    }
  }

  private void validateNoProxies(List<String> errors) {
    Collection<Resource> resources = virtualModel.getViewSourceModels();
    if (resources == null) {
      return;
    }
    int proxyCount = 0;
    for (Resource resource : resources) {
      if (!resource.isLoaded()) {
        continue;
      }
      TreeIterator<EObject> iterator = resource.getAllContents();
      while (iterator.hasNext()) {
        EObject object = iterator.next();
        if (object.eIsProxy()) {
          errors.add("Unresolved proxy object after merge: " + EcoreUtil.getURI(object)
              + " in resource " + resource.getURI());
          proxyCount++;
        }
        for (EReference ref : object.eClass().getEAllReferences()) {
          if (!ref.isContainment()) {
            Object value = object.eGet(ref, false);
            if (value instanceof EObject && ((EObject) value).eIsProxy()) {
              errors.add("Unresolved reference '" + ref.getName() + "' after merge in "
                  + EcoreUtil.getURI(object));
              proxyCount++;
            } else if (value instanceof Collection) {
              for (Object item : (Collection<?>) value) {
                if (item instanceof EObject && ((EObject) item).eIsProxy()) {
                  errors.add("Unresolved reference in collection '" + ref.getName()
                      + "' after merge in " + EcoreUtil.getURI(object));
                  proxyCount++;
                }
              }
            }
          }
        }
      }
    }
    if (proxyCount > 0) {
      LOGGER.warn("Found {} unresolved proxies after merge", proxyCount);
    }
  }

  private void validateResourcesLoadable(List<String> errors, List<String> warnings) {
    Collection<Resource> resources = virtualModel.getViewSourceModels();
    if (resources == null || resources.isEmpty()) {
      warnings.add("No model resources found in VSUM after merge");
      return;
    }
    LOGGER.debug("Validating {} resources after merge", resources.size());

    for (Resource resource : resources) {
      try {
        if (!resource.isLoaded()) {
          resource.load(Collections.emptyMap());
        }
        for (Resource.Diagnostic error : resource.getErrors()) {
          errors.add("Resource parse error in " + resource.getURI() + ": "
              + error.getMessage() + " (line " + error.getLine() + ")");
        }
        for (Resource.Diagnostic warning : resource.getWarnings()) {
          warnings.add("Resource warning in " + resource.getURI()
              + ": " + warning.getMessage());
        }
      } catch (IOException e) {
        errors.add("Cannot load resource " + resource.getURI()
            + " after merge: " + e.getMessage());
        LOGGER.error("Failed to load resource after merge: {}", resource.getURI(), e);
      } catch (Exception e) {
        errors.add("Unexpected error loading " + resource.getURI()
            + " after merge: " + e.getMessage());
        LOGGER.error("Unexpected error loading resource after merge: {}", resource.getURI(), e);
      }
    }
  }

  private void validateNoConflictMarkers(List<String> errors) {
    Collection<Resource> resources = virtualModel.getViewSourceModels();
    if (resources == null) {
      return;
    }
    for (Resource resource : resources) {
      // only check file-based URIs
      if (resource.getURI() == null || !resource.getURI().isFile()) {
        continue;
      }
      Path resourcePath = Path.of(resource.getURI().toFileString());
      if (!Files.exists(resourcePath)) {
        continue;
      }
      try {
        String content = Files.readString(resourcePath);
        for (String marker : CONFLICT_MARKERS) {
          if (content.contains(marker)) {
            errors.add("Git conflict marker '" + marker + "' found in model file: "
                + resourcePath.getFileName()
                + ". Resolve the conflict and commit the resolution.");
            // report only the first marker per file to avoid duplicate errors.
            break;
          }
        }
      } catch (IOException e) {
        LOGGER.warn("Could not read resource file for conflict marker check: {}",
            resourcePath, e);
      }
    }
  }

  /**
   * Copies the VSUM state from the source branch directory to the target branch directory.
   * Used after a merge to bring the target branch's VSUM in line with the merged state.
   *
   * @param sourceBranch the branch whose VSUM state should be copied.
   * @param targetBranch the branch that should receive the copied state.
   */
  public void copyVsumFromSourceBranch(String sourceBranch, String targetBranch) {
    checkNotNull(sourceBranch, "sourceBranch must not be null");
    checkNotNull(targetBranch, "targetBranch must not be null");

    Path sourceVsum = repositoryRoot.resolve(".vitruvius/vsum").resolve(sourceBranch);
    Path targetVsum = repositoryRoot.resolve(".vitruvius/vsum").resolve(targetBranch);

    if (!Files.exists(sourceVsum)) {
      LOGGER.warn("Source branch '{}' has no VSUM state to copy", sourceBranch);
      return;
    }

    try {
      // remove stale target state before copying so no old files linger
      if (Files.exists(targetVsum)) {
        deleteDirectory(targetVsum);
        LOGGER.debug("Removed stale VSUM state for target branch '{}'", targetBranch);
      }

      copyDirectory(sourceVsum, targetVsum);
      LOGGER.info("Copied VSUM state from '{}' to '{}' after merge", sourceBranch, targetBranch);

    } catch (IOException e) {
      LOGGER.error("Failed to copy VSUM state from '{}' to '{}': {}",
          sourceBranch, targetBranch, e.getMessage(), e);
    }
  }

  private void copyDirectory(Path source, Path target) throws IOException {
    Files.createDirectories(target);
    try (var stream = Files.walk(source)) {
      stream.forEach(sourcePath -> {
        try {
          Path targetPath = target.resolve(source.relativize(sourcePath));
          if (Files.isDirectory(sourcePath)) {
            Files.createDirectories(targetPath);
          } else {
            Files.copy(sourcePath, targetPath, java.nio.file.StandardCopyOption.REPLACE_EXISTING);
          }
        } catch (IOException e) {
          throw new RuntimeException("Failed to copy " + sourcePath, e);
        }
      });
    }
  }

  private void deleteDirectory(Path directory) throws IOException {
    try (var stream = Files.walk(directory)) {
      stream.sorted(java.util.Comparator.reverseOrder()).forEach(path -> {
        try {
          Files.delete(path);
        } catch (IOException e) {
          throw new RuntimeException("Failed to delete " + path, e);
        }
      });
    }
  }
}
