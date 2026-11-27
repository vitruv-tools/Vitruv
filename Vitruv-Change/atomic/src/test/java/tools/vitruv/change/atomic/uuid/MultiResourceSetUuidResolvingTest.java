package tools.vitruv.change.atomic.uuid;

import static edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceSetUtil.withGlobalFactories;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static tools.vitruv.change.testutils.metamodels.AllElementTypesCreators.aet;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import tools.vitruv.change.testutils.RegisterMetamodelsInStandalone;
import tools.vitruv.change.testutils.TestProject;
import tools.vitruv.change.testutils.TestProjectManager;

@ExtendWith({TestProjectManager.class, RegisterMetamodelsInStandalone.class})
class MultiResourceSetUuidResolvingTest {
  private ResourceSet sourceResourceSet;
  private UuidResolver sourceUuidResolver;
  private ResourceSet targetResourceSet;
  private UuidResolver targetUuidResolver;
  private Path testProjectPath;

  @BeforeEach
  void setup(@TestProject Path testProjectPath) {
    this.testProjectPath = testProjectPath;
    this.sourceResourceSet = withGlobalFactories(new ResourceSetImpl());
    this.sourceUuidResolver = UuidResolver.create(sourceResourceSet);
    this.targetResourceSet = withGlobalFactories(new ResourceSetImpl());
    this.targetUuidResolver = UuidResolver.create(targetResourceSet);
  }

  @ParameterizedTest(name = "{0} resource(s)")
  @ValueSource(ints = {1, 2, 5, 10})
  @DisplayName("resolve resource(s)")
  void resolveResources(int resourcesCount) {
    Map<EObject, Uuid> uuidMapping = new HashMap<>();
    Map<EObject, EObject> targetToSourceElementMapping = new HashMap<>();
    Map<Resource, Resource> sourceToTargetResourceMap = new HashMap<>();
    for (int i = 0; i < resourcesCount; i++) {
      URI uri = URI.createFileURI(testProjectPath.resolve("root" + i + ".aet").toString());
      Resource sourceResource = sourceResourceSet.createResource(uri);
      Resource targetResource = targetResourceSet.createResource(uri);
      sourceToTargetResourceMap.put(sourceResource, targetResource);

      var sourceRoot = aet.Root();
      sourceResource.getContents().add(sourceRoot);
      var sourceNonRoot = aet.NonRoot();
      sourceRoot.setSingleValuedContainmentEReference(sourceNonRoot);

      var targetRoot = aet.Root();
      targetResource.getContents().add(targetRoot);
      var targetNonRoot = aet.NonRoot();
      targetRoot.setSingleValuedContainmentEReference(targetNonRoot);
      targetToSourceElementMapping.put(targetRoot, sourceRoot);
      targetToSourceElementMapping.put(targetNonRoot, sourceNonRoot);

      List.of(sourceRoot, sourceNonRoot)
          .forEach(
              element -> {
                Uuid uuid = sourceUuidResolver.registerEObject(element);
                uuidMapping.put(element, uuid);
              });
    }

    sourceUuidResolver.resolveResources(sourceToTargetResourceMap, targetUuidResolver);
    targetToSourceElementMapping.forEach(
        (targetElement, sourceElement) -> {
          Uuid uuid = uuidMapping.get(sourceElement);

          assertEquals(
              sourceElement,
              sourceUuidResolver.getEObject(uuid),
              "source element does not match UUID");
          assertEquals(
              uuid,
              sourceUuidResolver.getUuid(sourceElement),
              "source UUID does not match element");
          assertThrows(
              IllegalStateException.class,
              () -> sourceUuidResolver.getUuid(targetElement),
              "target element registered in source resolver");

          assertEquals(
              targetElement,
              targetUuidResolver.getEObject(uuid),
              "target element does not match UUID");
          assertEquals(
              uuid,
              targetUuidResolver.getUuid(targetElement),
              "target UUID does not match element");
          assertThrows(
              IllegalStateException.class,
              () -> targetUuidResolver.getUuid(sourceElement),
              "source element registered in target resolver");
        });
  }

  @Test
  @DisplayName("resolve only subset of available resources")
  void resolveSubsetResources() {
    URI resolveUri = URI.createFileURI(testProjectPath.resolve("root1.aet").toString());
    URI notResolvedUri = URI.createFileURI(testProjectPath.resolve("root2.aet").toString());
    List.of(resolveUri, notResolvedUri)
        .forEach(
            (uri) -> {
              Resource sourceResource = sourceResourceSet.createResource(uri);
              Resource targetResource = targetResourceSet.createResource(uri);
              var sourceRoot = aet.Root();
              sourceResource.getContents().add(sourceRoot);
              targetResource.getContents().add(EcoreUtil.copy(sourceRoot));
              sourceUuidResolver.registerEObject(sourceRoot);
            });

    sourceUuidResolver.resolveResource(
        sourceResourceSet.getResource(resolveUri, false),
        targetResourceSet.getResource(resolveUri, false),
        targetUuidResolver);
    var resolvedRoot = targetResourceSet.getResource(resolveUri, false).getContents().get(0);
    var notResolvedRoot = targetResourceSet.getResource(notResolvedUri, false).getContents().get(0);
    assertDoesNotThrow(
        () -> targetUuidResolver.getUuid(resolvedRoot), "element missing in target UUID resolver");
    assertThrows(
        IllegalStateException.class,
        () -> targetUuidResolver.getUuid(notResolvedRoot),
        "element must not be registered in target UUID resolver");
  }

  @Test
  @DisplayName("resolve already resolved resource")
  void resolveRegisteredResource() {
    URI uri = URI.createFileURI(testProjectPath.resolve("root.aet").toString());
    Resource sourceResource = sourceResourceSet.createResource(uri);
    Resource targetResource = targetResourceSet.createResource(uri);
    var sourceRoot = aet.Root();
    var targetRoot = aet.Root();
    sourceResource.getContents().add(sourceRoot);
    targetResource.getContents().add(targetRoot);
    Uuid uuid = sourceUuidResolver.registerEObject(sourceRoot);

    sourceUuidResolver.resolveResource(sourceResource, targetResource, targetUuidResolver);
    assertEquals(uuid, targetUuidResolver.getUuid(targetRoot));

    // re-resolve resource
    assertDoesNotThrow(
        () ->
            sourceUuidResolver.resolveResource(sourceResource, targetResource, targetUuidResolver));
    assertEquals(uuid, targetUuidResolver.getUuid(targetRoot));
  }

  @Test
  @DisplayName("resolve resource with dangling element")
  void resolveResourceWithDanglingElement() {
    URI uri = URI.createFileURI(testProjectPath.resolve("root.aet").toString());
    Resource sourceResource = sourceResourceSet.createResource(uri);
    Resource targetResource = targetResourceSet.createResource(uri);
    var danglingElement = aet.Root();
    Uuid danglingUuid = sourceUuidResolver.registerEObject(danglingElement);

    Map<Resource, Resource> sourceToTargetMapping = Map.of(sourceResource, targetResource);
    assertThrows(
        IllegalStateException.class,
        () -> sourceUuidResolver.resolveResources(sourceToTargetMapping, targetUuidResolver));
    assertThrows(
        IllegalStateException.class,
        () -> targetUuidResolver.getEObject(danglingUuid),
        "target UUID resolver must not be modified when resolving fails");
    assertThrows(
        IllegalStateException.class,
        () -> targetUuidResolver.getUuid(danglingElement),
        "target UUID resolver must not be modified when resolving fails");
  }
}
