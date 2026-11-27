package tools.vitruv.change.atomic.uuid;

import static edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceSetUtil.withGlobalFactories;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static tools.vitruv.change.testutils.metamodels.AllElementTypesCreators.aet;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
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
class UuidSerializationTest {
  private ResourceSet storeResourceSet;
  private UuidResolver storeUuidResolver;
  private ResourceSet loadResourceSet;
  private UuidResolver loadUuidResolver;
  private Path testProjectPath;

  private URI getSerializationUri() {
    return URI.createFileURI(testProjectPath.resolve("UUID.uuid").toString());
  }

  @BeforeEach
  void setup(@TestProject Path testProjectPath) {
    this.testProjectPath = testProjectPath;
    this.storeResourceSet = withGlobalFactories(new ResourceSetImpl());
    this.storeUuidResolver = UuidResolver.create(storeResourceSet);
    this.loadResourceSet = withGlobalFactories(new ResourceSetImpl());
    this.loadUuidResolver = UuidResolver.create(loadResourceSet);
  }

  @ParameterizedTest(name = "{0} element(s)")
  @ValueSource(ints = {1, 2, 5, 10, 100})
  @DisplayName("store and load elements")
  void storeAndLoadElements(int count) {
    Map<EObject, Uuid> uuidMapping = new HashMap<>();
    Map<EObject, EObject> loadToStoreElementsMapping = new HashMap<>();
    for (int i = 0; i < count; i++) {
      URI uri = URI.createFileURI(testProjectPath.resolve("root" + i + ".aet").toString());

      Resource storeResource = storeResourceSet.createResource(uri);
      var storeRoot = aet.Root();
      storeResource.getContents().add(storeRoot);
      Uuid uuid = storeUuidResolver.registerEObject(storeRoot);
      uuidMapping.put(storeRoot, uuid);

      Resource loadResource = loadResourceSet.createResource(uri);
      var loadRoot = aet.Root();
      loadResource.getContents().add(loadRoot);
      loadToStoreElementsMapping.put(loadRoot, storeRoot);
    }

    URI serializationUri = getSerializationUri();
    assertDoesNotThrow(() -> storeUuidResolver.storeAtUri(serializationUri));
    assertDoesNotThrow(() -> loadUuidResolver.loadFromUri(serializationUri));

    loadToStoreElementsMapping.forEach(
        (loadElement, storeElement) -> {
          Uuid uuid = uuidMapping.get(storeElement);
          assertEquals(
              storeElement,
              storeUuidResolver.getEObject(uuid),
              "stored element does not match UUID");
          assertEquals(
              uuid, storeUuidResolver.getUuid(storeElement), "stored UUID does not match element");
          assertEquals(
              loadElement, loadUuidResolver.getEObject(uuid), "loaded element does not match UUID");
          assertEquals(
              uuid, loadUuidResolver.getUuid(loadElement), "loaded UUID does not match element");
        });
  }

  @Test
  @DisplayName("store dangling element")
  void storeDanglingElement() {
    var danglingElement = aet.Root();
    storeUuidResolver.registerEObject(danglingElement);
    assertThrows(
        IllegalStateException.class, () -> storeUuidResolver.storeAtUri(getSerializationUri()));
  }

  @Test
  @DisplayName("load with non-empty resolver")
  void loadWithNonEmptyResolver() {
    URI uri = URI.createFileURI(testProjectPath.resolve("root.aet").toString());
    Resource storeResource = storeResourceSet.createResource(uri);
    var storeRoot = aet.Root();
    storeResource.getContents().add(storeRoot);
    storeUuidResolver.registerEObject(storeRoot);
    URI serializationUri = getSerializationUri();
    assertDoesNotThrow(() -> storeUuidResolver.storeAtUri(serializationUri));

    loadUuidResolver.registerEObject(aet.Root());
    assertThrows(IllegalStateException.class, () -> loadUuidResolver.loadFromUri(serializationUri));
  }
}
