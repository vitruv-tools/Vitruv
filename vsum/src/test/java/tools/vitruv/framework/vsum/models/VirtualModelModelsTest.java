package tools.vitruv.framework.vsum.models;

import static edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceSetUtil.withGlobalFactories;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tools.vitruv.change.testutils.metamodels.AllElementTypesCreators.aet;
import static tools.vitruv.framework.vsum.VirtualModelTestUtil.recordChanges;

import allElementTypes.AllElementTypesPackage;
import allElementTypes.Root;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import tools.vitruv.change.atomic.uuid.Uuid;
import tools.vitruv.change.atomic.uuid.UuidResolver;
import tools.vitruv.change.interaction.UserInteractionFactory;
import tools.vitruv.change.testutils.TestProject;
import tools.vitruv.change.testutils.TestProjectManager;
import tools.vitruv.framework.vsum.VirtualModel;
import tools.vitruv.framework.vsum.VirtualModelBuilder;
import tools.vitruv.framework.vsum.VirtualModelTestUtil;
import tools.vitruv.framework.vsum.internal.InternalVirtualModel;

/** Tests for reading the models and metamodels of a virtual model. */
@ExtendWith(TestProjectManager.class)
class VirtualModelModelsTest {
  private Path projectFolder;

  @BeforeEach
  void initializeProjectFolder(@TestProject Path projectFolder) {
    this.projectFolder = projectFolder;
  }

  @Test
  @DisplayName("an empty virtual model has no models and no metamodels")
  void emptyVirtualModel() throws Exception {
    VirtualModel vsum = buildVsum();

    assertTrue(vsum.getModels().isEmpty());
    assertTrue(vsum.getMetamodels().isEmpty());
  }

  @Test
  @DisplayName("models are described with their metamodels and number of roots, sorted by URI")
  void describesModels() throws Exception {
    InternalVirtualModel vsum = buildVsum();
    URI second = insertRoots(vsum, "b.allElementTypes", "r1", "r2");
    URI first = insertRoots(vsum, "a.allElementTypes", "r3");

    List<ModelDescriptor> models = vsum.getModels();

    assertEquals(List.of(first, second), models.stream().map(ModelDescriptor::uri).toList());
    MetamodelReference allElementTypes =
        new MetamodelReference("allElementTypes", AllElementTypesPackage.eNS_URI);
    assertEquals(
        new ModelDescriptor(first, "../a.allElementTypes", List.of(allElementTypes), 1),
        models.get(0));
    assertEquals(
        new ModelDescriptor(second, "../b.allElementTypes", List.of(allElementTypes), 2),
        models.get(1));
    assertEquals(List.of(AllElementTypesPackage.eINSTANCE), vsum.getMetamodels());
  }

  @Test
  @DisplayName("models are identified by their URI relative to the folder of the virtual model")
  void identifiesModelsRelativeToTheFolder() throws Exception {
    InternalVirtualModel vsum = buildVsum();
    insertRoots(vsum, "vsum/models/a.allElementTypes", "r1");

    assertEquals("models/a.allElementTypes", vsum.getModels().get(0).id());
  }

  @Test
  @DisplayName("models without a common folder are identified by their full URI")
  void identifiesForeignModelsByTheirUri() {
    URI folder = URI.createFileURI(projectFolder.resolve("vsum").toString()).appendSegment("");
    URI platformUri = URI.createPlatformResourceURI("/project/a.allElementTypes", true);
    URI relativeUri = URI.createURI("a.allElementTypes");

    assertEquals(platformUri.toString(), ModelDescriptor.idOf(platformUri, folder));
    assertEquals("a.allElementTypes", ModelDescriptor.idOf(relativeUri, folder));
    assertEquals(platformUri.toString(), ModelDescriptor.idOf(platformUri, null));
  }

  @Test
  @DisplayName("the metamodels of change propagation specifications are provided without models")
  void providesTheMetamodelsOfTheSpecifications() throws Exception {
    VirtualModel vsum =
        VirtualModelTestUtil.createAndLoadTestVirtualModelWithConsistencyPreservation(
            projectFolder.resolve("vsum"));

    assertTrue(vsum.getModels().isEmpty());
    assertEquals(List.of(AllElementTypesPackage.eINSTANCE), vsum.getMetamodels());
  }

  @Test
  @DisplayName("the models are read with the UUIDs of their elements")
  void readsModelsWithUuids() throws Exception {
    InternalVirtualModel vsum = buildVsum();
    URI uri = insertRoots(vsum, "a.allElementTypes", "r1");

    Root root =
        vsum.readModels(
            access -> (Root) access.getModelResource(uri).orElseThrow().getContents().get(0));
    Uuid uuid = vsum.readModels(access -> access.getUuid(root).orElseThrow());
    boolean unknownModelMissing =
        vsum.readModels(access -> access.getModelResource(URI.createURI("x:/y")).isEmpty());
    final boolean foreignElementWithoutUuid =
        vsum.readModels(access -> access.getUuid(aet.Root()).isEmpty());

    assertEquals("r1", root.getId());
    assertEquals(vsum.getUuidResolver().getUuid(root), uuid);
    assertTrue(unknownModelMissing);
    assertTrue(foreignElementWithoutUuid);
  }

  @Test
  @DisplayName("the access cannot be used after the reader returned")
  void accessIsOnlyValidDuringTheCall() throws Exception {
    InternalVirtualModel vsum = buildVsum();
    AtomicReference<ModelReadAccess> leaked = new AtomicReference<>();

    vsum.readModels(
        access -> {
          leaked.set(access);
          return null;
        });

    assertThrows(IllegalStateException.class, () -> leaked.get().getModelResources());
    assertThrows(IllegalStateException.class, () -> leaked.get().getUuid(aet.Root()));
  }

  @Test
  @DisplayName("the model resources cannot be removed through the access")
  void resourcesAreUnmodifiable() throws Exception {
    InternalVirtualModel vsum = buildVsum();
    insertRoots(vsum, "a.allElementTypes", "r1");

    assertThrows(
        UnsupportedOperationException.class,
        () -> vsum.readModels(access -> access.getModelResources().removeIf(r -> true)));
    assertEquals(1, vsum.getModels().size());
  }

  @Test
  @DisplayName("the metamodel of an element is its outermost package")
  void rootPackageOfNestedPackages() {
    EPackage outer = EcoreFactory.eINSTANCE.createEPackage();
    outer.setName("outer");
    outer.setNsURI("http://outer");
    EPackage inner = EcoreFactory.eINSTANCE.createEPackage();
    inner.setName("inner");
    inner.setNsURI("http://outer/inner");
    outer.getESubpackages().add(inner);
    EClass eClass = EcoreFactory.eINSTANCE.createEClass();
    inner.getEClassifiers().add(eClass);

    assertSame(outer, MetamodelReference.rootPackageOf(eClass));
    assertEquals(new MetamodelReference("outer", "http://outer"), MetamodelReference.of(outer));
  }

  @Test
  @DisplayName("virtual models that do not provide their models say so")
  void unsupportedByDefault() {
    VirtualModel vsum =
        (VirtualModel)
            Proxy.newProxyInstance(
                VirtualModel.class.getClassLoader(),
                new Class<?>[] {VirtualModel.class},
                (proxy, method, args) -> {
                  if (method.isDefault()) {
                    return InvocationHandler.invokeDefault(proxy, method, args);
                  }
                  throw new AssertionError("not expected: " + method);
                });

    assertThrows(UnsupportedOperationException.class, vsum::getModels);
  }

  private InternalVirtualModel buildVsum() throws Exception {
    return new VirtualModelBuilder()
        .withStorageFolder(projectFolder.resolve("vsum"))
        .withUserInteractor(
            UserInteractionFactory.instance.createUserInteractor(
                UserInteractionFactory.instance.createPredefinedInteractionResultProvider(null)))
        .buildAndInitialize();
  }

  private URI insertRoots(InternalVirtualModel vsum, String fileName, String... ids) {
    var resourceSet = withGlobalFactories(new ResourceSetImpl());
    URI uri = URI.createFileURI(projectFolder.resolve(fileName).toString());
    var change =
        recordChanges(
            resourceSet,
            UuidResolver.create(resourceSet),
            () -> {
              Resource resource = resourceSet.createResource(uri);
              for (String id : ids) {
                Root root = aet.Root();
                root.setId(id);
                resource.getContents().add(root);
              }
            });
    vsum.propagateChange(change);
    return uri;
  }
}
