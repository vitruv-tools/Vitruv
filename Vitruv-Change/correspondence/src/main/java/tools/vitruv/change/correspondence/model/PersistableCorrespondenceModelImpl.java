package tools.vitruv.change.correspondence.model;

import static com.google.common.base.Preconditions.checkState;
import static edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceSetUtil.loadOrCreateResource;
import static edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceSetUtil.withGlobalFactories;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import tools.vitruv.change.correspondence.Correspondence;
import tools.vitruv.change.correspondence.CorrespondenceFactory;
import tools.vitruv.change.correspondence.Correspondences;

/** A persistable representation of a {@link CorrespondenceModel}. */
class PersistableCorrespondenceModelImpl implements PersistableCorrespondenceModel {
  private static final Logger logger =
      LogManager.getLogger(PersistableCorrespondenceModelImpl.class);
  private final Correspondences correspondences;
  private final Resource correspondencesResource;

  /**
   * Creates a new correspondence model with the given resource URI.
   *
   * @param resourceUri the URI of the resource to store the correspondences in
   */
  public PersistableCorrespondenceModelImpl(URI resourceUri) {
    this.correspondences = CorrespondenceFactory.eINSTANCE.createCorrespondences();
    if (resourceUri != null) {
      this.correspondencesResource =
          withGlobalFactories(new ResourceSetImpl()).createResource(resourceUri);
      this.correspondencesResource.getContents().add(correspondences);
    } else {
      this.correspondencesResource = null;
    }
  }

  @Override
  public void loadSerializedCorrespondences(ResourceSet resolveIn) {
    checkState(
        correspondencesResource != null,
        "Correspondences resource must be specified to load existing correspondences");
    Resource loadedResource =
        loadOrCreateResource(
            withGlobalFactories(new ResourceSetImpl()), correspondencesResource.getURI());
    if (!loadedResource.getContents().isEmpty()) {
      Correspondences loadedCorrespondences = (Correspondences) loadedResource.getContents().get(0);
      for (Correspondence correspondence : loadedCorrespondences.getCorrespondences()) {
        List<EObject> resolvedLeftObjects = resolve(correspondence.getLeftEObjects(), resolveIn);
        replace(correspondence.getLeftEObjects(), resolvedLeftObjects);
        List<EObject> resolvedRightObjects = resolve(correspondence.getRightEObjects(), resolveIn);
        replace(correspondence.getRightEObjects(), resolvedRightObjects);
      }
      this.correspondences.getCorrespondences().addAll(loadedCorrespondences.getCorrespondences());
    }
  }

  private static void replace(List<EObject> originalList, List<EObject> elementsToReplaceWith) {
    originalList.clear();
    originalList.addAll(elementsToReplaceWith);
  }

  private static List<EObject> resolve(List<EObject> eObjects, ResourceSet resolveIn) {
    List<EObject> resolvedEObjects =
        eObjects.stream().map(eObject -> EcoreUtil.resolve(eObject, resolveIn)).toList();
    for (EObject resolvedEObject : resolvedEObjects) {
      checkState(
          !resolvedEObject.eIsProxy(),
          "object %s is referenced in correspondence but could not be resolved",
          resolvedEObject);
    }
    return resolvedEObjects;
  }

  @Override
  public void save() {
    removeCorrespondencesForRemovedElements();
    if (this.correspondencesResource != null) {
      try {
        this.correspondencesResource.save(null);
      } catch (IOException e) {
        logger.error("Could not save correspondences resource", e);
      }
    }
  }

  @Override
  public <C extends Correspondence> C addCorrespondenceBetween(
      List<EObject> firstEObjects,
      List<EObject> secondEObjects,
      String tag,
      Supplier<C> correspondenceCreator) {
    C correspondence = correspondenceCreator.get();
    correspondence.getLeftEObjects().addAll(firstEObjects);
    correspondence.getRightEObjects().addAll(secondEObjects);
    correspondence.setTag(tag);
    this.correspondences.getCorrespondences().add(correspondence);
    return correspondence;
  }

  private void removeCorrespondencesForRemovedElements() {
    Iterator<Correspondence> iterator = correspondences.getCorrespondences().iterator();
    while (iterator.hasNext()) {
      Correspondence element = iterator.next();
      if (element.getLeftEObjects().stream().anyMatch(this::isNotInManagedResource)
          || element.getRightEObjects().stream().anyMatch(this::isNotInManagedResource)) {
        checkState(
            element.getLeftEObjects().stream().allMatch(this::isNotInManagedResource)
                || element.getRightEObjects().stream().allMatch(this::isNotInManagedResource),
            "Correspondence between %s and %s contains elements %s that are not contained in a"
                + " resource anymore.",
            element.getLeftEObjects(),
            element.getRightEObjects(),
            Stream.concat(element.getLeftEObjects().stream(), element.getRightEObjects().stream())
                .filter(this::isNotInManagedResource)
                .toList());
        iterator.remove();
        if (logger.isTraceEnabled()) {
          logger.trace(
              "Correspondence between {} and {} has been removed as all its elements have been removed from resources.",
              element.getLeftEObjects(),
              element.getRightEObjects()
          );

        }
      }
    }
  }

  private boolean isNotInManagedResource(EObject object) {
    return !(object instanceof EClass)
        && (object.eResource() == null || object.eResource().getResourceSet() == null);
  }

  private void removeCorrespondence(Correspondence correspondence) {
    EcoreUtil.remove(correspondence);
  }

  @Override
  public <C extends Correspondence> Set<C> removeCorrespondencesBetween(
      Class<C> correspondenceType, List<EObject> aEObjects, List<EObject> bEObjects, String tag) {
    Set<Correspondence> correspondencesBetween = getCorrespondencesBetween(aEObjects, bEObjects);
    Set<C> correspondencesToRemove =
        filterCorrespondenceTypeAndTag(correspondencesBetween, correspondenceType, tag);
    correspondencesToRemove.stream().forEach(this::removeCorrespondence);
    return correspondencesToRemove;
  }

  private <C extends Correspondence> Set<C> filterCorrespondenceTypeAndTag(
      Set<Correspondence> original, Class<C> filteredType, String expectedTag) {
    return original.stream()
        .filter(filteredType::isInstance)
        .map(filteredType::cast)
        .filter(
            correspondence -> expectedTag == null || expectedTag.equals(correspondence.getTag()))
        .collect(Collectors.toSet());
  }

  private Set<Correspondence> getCorrespondences(List<EObject> eObjects) {
    return this.correspondences.getCorrespondences().stream()
        .filter(correspondence -> isEitherSideOfCorrespondence(correspondence, eObjects))
        .collect(Collectors.toSet());
  }

  private Set<Correspondence> getCorrespondencesBetween(
      List<EObject> firstEObjects, List<EObject> secondEObjects) {
    return this.correspondences.getCorrespondences().stream()
        .filter(
            correspondence ->
                isEitherSideOfCorrespondence(correspondence, firstEObjects)
                    && isEitherSideOfCorrespondence(correspondence, secondEObjects)
                    && (!firstEObjects.equals(secondEObjects)
                        || correspondence
                            .getLeftEObjects()
                            .equals(correspondence.getRightEObjects())))
        .collect(Collectors.toSet());
  }

  private boolean isEitherSideOfCorrespondence(
      Correspondence correspondence, List<EObject> elementsToBeEitherSide) {
    return elementsToBeEitherSide.equals(correspondence.getLeftEObjects())
        || elementsToBeEitherSide.equals(correspondence.getRightEObjects());
  }

  private Set<List<EObject>> mapToCorrespondingEObjects(
      Set<? extends Correspondence> correspondences, List<EObject> originalEObjects) {
    return correspondences.stream()
        .map(correspondence -> getCorrespondingEObjects(correspondence, originalEObjects))
        .collect(Collectors.toSet());
  }

  private List<EObject> getCorrespondingEObjects(
      Correspondence correspondence, List<EObject> eObjects) {
    if (correspondence.getLeftEObjects().equals(eObjects)) {
      return correspondence.getRightEObjects();
    } else {
      return correspondence.getLeftEObjects();
    }
  }

  @Override
  public Set<List<EObject>> getCorrespondingEObjects(
      Class<? extends Correspondence> correspondenceType, List<EObject> eObjects, String tag) {
    Set<Correspondence> objectsCorrespondences = getCorrespondences(eObjects);
    Set<? extends Correspondence> properlyTaggedAndTypedCorrespondences =
        filterCorrespondenceTypeAndTag(objectsCorrespondences, correspondenceType, tag);
    return mapToCorrespondingEObjects(properlyTaggedAndTypedCorrespondences, eObjects);
  }

  @Override
  public boolean hasCorrespondences(List<EObject> eObjects) {
    Set<Correspondence> tempCorrespondences = this.getCorrespondences(eObjects);
    return tempCorrespondences != null && tempCorrespondences.isEmpty();
  }

  @Override
  public void close() throws Exception {
    if (correspondencesResource != null) {
      correspondencesResource.unload();
    }
  }
}
