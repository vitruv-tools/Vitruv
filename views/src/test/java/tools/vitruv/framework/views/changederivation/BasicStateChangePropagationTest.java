package tools.vitruv.framework.views.changederivation;

import static edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceSetUtil.withGlobalFactories;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tools.vitruv.change.testutils.matchers.ModelMatchers.containsModelOf;
import static tools.vitruv.change.testutils.metamodels.AllElementTypesCreators.aet;

import allElementTypes.Root;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import tools.vitruv.change.atomic.eobject.CreateEObject;
import tools.vitruv.change.atomic.eobject.DeleteEObject;
import tools.vitruv.change.atomic.feature.attribute.ReplaceSingleValuedEAttribute;
import tools.vitruv.change.atomic.root.InsertRootEObject;
import tools.vitruv.change.atomic.root.RemoveRootEObject;
import tools.vitruv.change.composite.description.VitruviusChangeResolverFactory;
import tools.vitruv.change.testutils.Capture;

/** Tests for state-based change propagation using the basic state change resolution. */
class BasicStateChangePropagationTest extends StateChangePropagationTest {

  private URI getTestUri() {
    return getModelURI("Test.allElementTypes");
  }

  /**
   * creates a new resource and calculates the state-based difference.
   *
   * @param strategyToTest the strategy to test
   * @throws IOException if an I/O error occurs
   */
  @ParameterizedTest
  @DisplayName("create new resource and calculate state-based difference")
  @MethodSource("strategiesToTest")
  void createNewResource(StateBasedChangeResolutionStrategy strategyToTest)
      throws IOException {
    ResourceSetImpl resourceSet = new ResourceSetImpl();
    Root root = aet.Root();
    root.setId("Root");

    Resource modelResource = resourceSet.createResource(getTestUri());
    modelResource.getContents().add(root);

    var changes = strategyToTest.getChangeSequenceForCreated(modelResource);
    assertEquals(3, changes.getEChanges().size());
    assertEquals(
        1, changes.getEChanges().stream().filter(InsertRootEObject.class::isInstance).count());
    assertEquals(1, changes.getEChanges().stream().filter(CreateEObject.class::isInstance).count());
    assertEquals(
        1,
        changes.getEChanges().stream()
            .filter(c -> c instanceof ReplaceSingleValuedEAttribute)
            .count());

    // Create empty resource to apply generated changes to
    ResourceSetImpl validationResourceSet = new ResourceSetImpl();
    VitruviusChangeResolverFactory.forHierarchicalIds(validationResourceSet)
        .resolveAndApply(changes);

    modelResource.save(null);
    assertEquals(1, validationResourceSet.getResources().size());
    assertThat(validationResourceSet.getResources().get(0), containsModelOf(modelResource));
  }

  /**
   * deletes an existing resource and calculates the state-based difference.
   *
   * @param strategyToTest the strategy to test
   * @throws IOException if an I/O error occurs
   */
  @ParameterizedTest
  @DisplayName("delete existing resource and calculate state-based difference")
  @MethodSource("strategiesToTest")
  void deleteResource(StateBasedChangeResolutionStrategy strategyToTest) throws IOException {
    Capture<Resource> modelResource = new Capture<>();
    record(
        resourceSet,
        rs -> {
          Root root = aet.Root();
          root.setId("Root");
          Resource resource = resourceSet.createResource(getTestUri());
          resource.getContents().add(root);
          modelResource.set(resource);
        });

    modelResource.get().save(null);
    var changes = strategyToTest.getChangeSequenceForDeleted(modelResource.get());
    assertEquals(2, changes.getEChanges().size());
    assertEquals(
        1, changes.getEChanges().stream().filter(RemoveRootEObject.class::isInstance).count());
    assertEquals(1, changes.getEChanges().stream().filter(DeleteEObject.class::isInstance).count());

    // Load resource to apply generated changes to
    ResourceSetImpl validationResourceSet = new ResourceSetImpl();
    validationResourceSet.getResource(getTestUri(), true);
    VitruviusChangeResolverFactory.forHierarchicalIds(validationResourceSet)
        .resolveAndApply(changes);

    assertEquals(1, validationResourceSet.getResources().size());
    assertTrue(validationResourceSet.getResources().get(0).getContents().isEmpty());
  }

  /**
   * replaces the root element in an existing resource and calculates the state-based difference.
   *
   * @param strategyToTest the strategy to test
   * @throws IOException if an I/O error occurs
   */
  @ParameterizedTest
  @DisplayName("replace root element and calculate state-based difference")
  @MethodSource("strategiesToTest")
  void replaceRootElement(StateBasedChangeResolutionStrategy strategyToTest)
      throws IOException {
    Capture<Resource> modelResource = new Capture<>();
    record(
        resourceSet,
        rs -> {
          Root root = aet.Root();
          root.setId("Root");
          Resource resource = resourceSet.createResource(getTestUri());
          resource.getContents().add(root);
          modelResource.set(resource);
        });

    modelResource.get().save(null);

    record(
        resourceSet,
        rs -> {
          modelResource.get().getContents().clear();
          Root newRoot = aet.Root();
          newRoot.setId("Root2");
          modelResource.get().getContents().add(newRoot);
        });

    ResourceSet validationResourceSet = withGlobalFactories(new ResourceSetImpl());
    Resource oldState = validationResourceSet.getResource(getTestUri(), true);
    var changes = strategyToTest.getChangeSequenceBetween(modelResource.get(), oldState);

    VitruviusChangeResolverFactory.forHierarchicalIds(validationResourceSet)
        .resolveAndApply(changes);

    assertEquals(1, validationResourceSet.getResources().size());
    assertThat(validationResourceSet.getResources().get(0), containsModelOf(modelResource.get()));
  }

  /**
   * change a root element property and calculate the state-based difference.
   *
   * @param strategyToTest the strategy to test
   * @throws IOException if an I/O error occurs
   */
  @ParameterizedTest
  @DisplayName("change a root element property and calculate state-based difference")
  @MethodSource("strategiesToTest")
  void changeRootElementFeature(StateBasedChangeResolutionStrategy strategyToTest)
      throws IOException {
    Capture<Resource> modelResource = new Capture<>();
    Root root = aet.Root();

    record(
        resourceSet,
        rs -> {
          root.setId("Root");
          Resource resource = resourceSet.createResource(getTestUri());
          resource.getContents().add(root);
          modelResource.set(resource);
        });

    modelResource.get().save(null);

    record(
        resourceSet,
        rs -> {
          root.setSingleValuedEAttribute(2);
        });

    ResourceSet validationResourceSet = withGlobalFactories(new ResourceSetImpl());
    Resource oldState = validationResourceSet.getResource(getTestUri(), true);
    var changes = strategyToTest.getChangeSequenceBetween(modelResource.get(), oldState);
    assertEquals(1, changes.getEChanges().size());
    assertEquals(
        1,
        changes.getEChanges().stream()
            .filter(c -> c instanceof ReplaceSingleValuedEAttribute)
            .count());

    VitruviusChangeResolverFactory.forHierarchicalIds(validationResourceSet)
        .resolveAndApply(changes);

    assertEquals(1, validationResourceSet.getResources().size());
    assertThat(validationResourceSet.getResources().get(0), containsModelOf(modelResource.get()));
  }

  /**
   * change a root element's id and calculate the state-based difference.
   *
   * @param strategyToTest the strategy to test
   * @throws IOException if an I/O error occurs
   */
  @SuppressWarnings("null")
  @ParameterizedTest
  @DisplayName("change a root element's id and calculate state-based difference")
  @MethodSource("strategiesToTest")
  void changeRootElementId(DefaultStateBasedChangeResolutionStrategy strategyToTest)
      throws IOException {
    Capture<Resource> modelResource = new Capture<>();
    Root root = aet.Root();

    record(
        resourceSet,
        rs -> {
          root.setId("Root");
          Resource resource = resourceSet.createResource(getTestUri());
          resource.getContents().add(root);
          modelResource.set(resource);
        });

    modelResource.get().save(null);

    record(
        resourceSet,
        rs -> {
          root.setId("Root2");
        });

    ResourceSet validationResourceSet = withGlobalFactories(new ResourceSetImpl());
    Resource oldState = validationResourceSet.getResource(getTestUri(), true);
    var unresolvedChanges = strategyToTest.getChangeSequenceBetween(modelResource.get(), oldState);
    var changes =
        VitruviusChangeResolverFactory.forHierarchicalIds(validationResourceSet)
            .resolveAndApply(unresolvedChanges);

    switch (strategyToTest.getUseIdentifiers()) {
      case ONLY, WHEN_AVAILABLE:
        {
          List<DeleteEObject<?>> deleteChanges =
              changes.getEChanges().stream()
                  .filter(DeleteEObject.class::isInstance)
                  .map(c -> (DeleteEObject<?>) c)
                  .collect(Collectors.toList());
          assertEquals(1, deleteChanges.size());
          assertTrue(deleteChanges.get(0).getAffectedElement() instanceof Root);
          assertEquals("Root", ((Root) deleteChanges.get(0).getAffectedElement()).getId());

          List<CreateEObject<?>> createChanges =
              changes.getEChanges().stream()
                  .filter(CreateEObject.class::isInstance)
                  .map(c -> (CreateEObject<?>) c)
                  .collect(Collectors.toList());
          assertEquals(1, createChanges.size());
          assertTrue(createChanges.get(0).getAffectedElement() instanceof Root);
          assertEquals("Root2", ((Root) createChanges.get(0).getAffectedElement()).getId());
          break;
        }
      case NEVER:
        {
          assertEquals(1, changes.getEChanges().size());
          assertEquals(
              1,
              changes.getEChanges().stream()
                  .filter(c -> c instanceof ReplaceSingleValuedEAttribute)
                  .count());
          break;
        }
      default:
        break;
    }

    assertEquals(1, validationResourceSet.getResources().size());
    assertThat(validationResourceSet.getResources().get(0), containsModelOf(modelResource.get()));
  }

  /**
   * change a non-root element property and calculate the state-based difference.
   *
   * @param strategyToTest the strategy to test
   * @throws IOException if an I/O error occurs
   */
  @ParameterizedTest
  @DisplayName("change a non-root element property and calculate state-based difference")
  @MethodSource("strategiesToTest")
  void changeNonRootElementFeature(StateBasedChangeResolutionStrategy strategyToTest)
      throws IOException {
    Capture<Resource> modelResource = new Capture<>();
    Root root = aet.Root();
    Root containedRoot = aet.Root();

    record(
        resourceSet,
        rs -> {
          root.setId("Root");
          containedRoot.setId("ContainedRoot");
          containedRoot.setSingleValuedEAttribute(0);
          root.setRecursiveRoot(containedRoot);
          Resource resource = resourceSet.createResource(getTestUri());
          resource.getContents().add(root);
          modelResource.set(resource);
        });

    modelResource.get().save(null);

    record(
        resourceSet,
        rs -> {
          containedRoot.setSingleValuedEAttribute(1);
        });

    ResourceSet validationResourceSet = withGlobalFactories(new ResourceSetImpl());
    Resource oldState = validationResourceSet.getResource(getTestUri(), true);
    var changes = strategyToTest.getChangeSequenceBetween(modelResource.get(), oldState);
    assertEquals(1, changes.getEChanges().size());
    assertEquals(
        1,
        changes.getEChanges().stream()
            .filter(c -> c instanceof ReplaceSingleValuedEAttribute)
            .count());

    VitruviusChangeResolverFactory.forHierarchicalIds(validationResourceSet)
        .resolveAndApply(changes);

    assertEquals(1, validationResourceSet.getResources().size());
    assertThat(validationResourceSet.getResources().get(0), containsModelOf(modelResource.get()));
  }

  /**
   * change a non-root element's id and calculate the state-based difference.
   *
   * @param strategyToTest the strategy to test
   * @throws IOException if an I/O error occurs
   */
  @SuppressWarnings("null")
  @ParameterizedTest
  @DisplayName("change a non-root element's id and calculate state-based difference")
  @MethodSource("strategiesToTest")
  void changeNonRootElementId(DefaultStateBasedChangeResolutionStrategy strategyToTest)
      throws IOException {
    Capture<Resource> modelResource = new Capture<>();
    Root root = aet.Root();
    Root containedRoot = aet.Root();

    record(
        resourceSet,
        rs -> {
          root.setId("Root");
          containedRoot.setId("ContainedRoot");
          containedRoot.setSingleValuedEAttribute(0);
          root.setRecursiveRoot(containedRoot);
          Resource resource = resourceSet.createResource(getTestUri());
          resource.getContents().add(root);
          modelResource.set(resource);
        });

    modelResource.get().save(null);

    record(
        resourceSet,
        rs -> {
          containedRoot.setId("ContainedRoot2");
        });

    ResourceSet validationResourceSet = withGlobalFactories(new ResourceSetImpl());
    Resource oldState = validationResourceSet.getResource(getTestUri(), true);
    var unresolvedChanges = strategyToTest.getChangeSequenceBetween(modelResource.get(), oldState);
    var changes =
        VitruviusChangeResolverFactory.forHierarchicalIds(validationResourceSet)
            .resolveAndApply(unresolvedChanges);

    switch (strategyToTest.getUseIdentifiers()) {
      case ONLY:
      case WHEN_AVAILABLE:
        {
          List<DeleteEObject<?>> deleteChanges =
              changes.getEChanges().stream()
                  .filter(DeleteEObject.class::isInstance)
                  .map(c -> (DeleteEObject<?>) c)
                  .collect(Collectors.toList());
          assertEquals(1, deleteChanges.size());
          assertTrue(deleteChanges.get(0).getAffectedElement() instanceof Root);
          assertEquals("ContainedRoot", ((Root) deleteChanges.get(0).getAffectedElement()).getId());

          List<CreateEObject<?>> createChanges =
              changes.getEChanges().stream()
                  .filter(CreateEObject.class::isInstance)
                  .map(c -> (CreateEObject<?>) c)
                  .collect(Collectors.toList());
          assertEquals(1, createChanges.size());
          assertTrue(createChanges.get(0).getAffectedElement() instanceof Root);
          assertEquals(
              "ContainedRoot2", ((Root) createChanges.get(0).getAffectedElement()).getId());
          break;
        }
      case NEVER:
        {
          assertEquals(1, changes.getEChanges().size());
          assertEquals(
              1,
              changes.getEChanges().stream()
                  .filter(c -> c instanceof ReplaceSingleValuedEAttribute)
                  .count());
          break;
        }
      default:
        break;
    }

    assertEquals(1, validationResourceSet.getResources().size());
    assertThat(validationResourceSet.getResources().get(0), containsModelOf(modelResource.get()));
  }

  /**
   * move a resource to new location and calculate the state-based difference.
   *
   * @param strategyToTest the strategy to test
   * @throws IOException if an I/O error occurs
   */
  @ParameterizedTest
  @DisplayName("move a resource to new location and calculate state-based difference")
  @MethodSource("strategiesToTest")
  void moveResource(StateBasedChangeResolutionStrategy strategyToTest) throws IOException {
    Capture<Resource> modelResource = new Capture<>();
    Root root = aet.Root();

    record(
        resourceSet,
        rs -> {
          root.setId("Root");
          Resource resource = resourceSet.createResource(getTestUri());
          resource.getContents().add(root);
          modelResource.set(resource);
        });

    modelResource.get().save(null);

    ResourceSet validationResourceSet = withGlobalFactories(new ResourceSetImpl());
    Resource oldState = validationResourceSet.getResource(getTestUri(), true);

    URI movedResourceUri = getModelURI("moved.allElementTypes");
    record(
        resourceSet,
        rs -> {
          Resource movedResource = resourceSet.createResource(movedResourceUri);
          movedResource.getContents().add(root);
          modelResource.set(movedResource);
        });

    var changes = strategyToTest.getChangeSequenceBetween(modelResource.get(), oldState);
    assertEquals(2, changes.getEChanges().size());
    assertEquals(
        1, changes.getEChanges().stream().filter(RemoveRootEObject.class::isInstance).count());
    assertEquals(
        1, changes.getEChanges().stream().filter(InsertRootEObject.class::isInstance).count());

    VitruviusChangeResolverFactory.forHierarchicalIds(validationResourceSet)
        .resolveAndApply(changes);

    modelResource.get().save(null);
    assertEquals(2, validationResourceSet.getResources().size());
    assertThat(
        validationResourceSet.getResource(movedResourceUri, false),
        containsModelOf(modelResource.get()));
  }

  /**
   * move a resource to new location changing root feature and calculate the state-based difference.
   *
   * @param strategyToTest the strategy to test
   * @throws IOException if an I/O error occurs
   */
  @ParameterizedTest
  @DisplayName(
      "move a resource to new location changing root feature and calculate state-based difference")
  @MethodSource("strategiesToTest")
  void moveResourceAndChangeRootFeature(StateBasedChangeResolutionStrategy strategyToTest)
      throws IOException {
    Capture<Resource> modelResource = new Capture<>();
    Root root = aet.Root();

    record(
        resourceSet,
        rs -> {
          root.setId("Root");
          Resource resource = resourceSet.createResource(getTestUri());
          resource.getContents().add(root);
          modelResource.set(resource);
        });

    modelResource.get().save(null);

    ResourceSet validationResourceSet = withGlobalFactories(new ResourceSetImpl());
    Resource oldState = validationResourceSet.getResource(getTestUri(), true);

    URI movedResourceUri = getModelURI("moved.allElementTypes");

    record(
        resourceSet,
        rs -> {
          modelResource.get().getContents().remove(root);
          root.setSingleValuedEAttribute(2);
          Resource movedResource = resourceSet.createResource(movedResourceUri);
          movedResource.getContents().add(root);
          modelResource.set(movedResource);
        });

    var changes = strategyToTest.getChangeSequenceBetween(modelResource.get(), oldState);
    assertEquals(3, changes.getEChanges().size());
    assertEquals(
        1, changes.getEChanges().stream().filter(RemoveRootEObject.class::isInstance).count());
    assertEquals(
        1, changes.getEChanges().stream().filter(InsertRootEObject.class::isInstance).count());
    assertEquals(
        1,
        changes.getEChanges().stream()
            .filter(c -> c instanceof ReplaceSingleValuedEAttribute)
            .count());

    VitruviusChangeResolverFactory.forHierarchicalIds(validationResourceSet)
        .resolveAndApply(changes);

    modelResource.get().save(null);
    assertEquals(2, validationResourceSet.getResources().size());
    assertThat(
        validationResourceSet.getResource(movedResourceUri, false),
        containsModelOf(modelResource.get()));
  }
}
