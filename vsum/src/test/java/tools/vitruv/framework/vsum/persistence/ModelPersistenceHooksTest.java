package tools.vitruv.framework.vsum.persistence;

import static edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceSetUtil.withGlobalFactories;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tools.vitruv.change.testutils.metamodels.AllElementTypesCreators.aet;
import static tools.vitruv.framework.vsum.VirtualModelTestUtil.createTestModelResourceUri;
import static tools.vitruv.framework.vsum.VirtualModelTestUtil.recordChanges;

import allElementTypes.AllElementTypesPackage;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import tools.vitruv.change.atomic.EChange;
import tools.vitruv.change.atomic.uuid.Uuid;
import tools.vitruv.change.atomic.uuid.UuidResolver;
import tools.vitruv.change.composite.MetamodelDescriptor;
import tools.vitruv.change.composite.description.PropagatedChange;
import tools.vitruv.change.composite.description.TransactionalChange;
import tools.vitruv.change.composite.description.VitruviusChange;
import tools.vitruv.change.interaction.UserInteractionFactory;
import tools.vitruv.change.testutils.TestProject;
import tools.vitruv.change.testutils.TestProjectManager;
import tools.vitruv.framework.vsum.VirtualModelBuilder;
import tools.vitruv.framework.vsum.VirtualModelTestUtil.RedundancyChangePropagationSpecification;
import tools.vitruv.framework.vsum.internal.InternalVirtualModel;

/** Tests that a configured {@link ModelPersistence} receives its calls in application order. */
@ExtendWith(TestProjectManager.class)
class ModelPersistenceHooksTest {

  @Test
  @DisplayName("a configured strategy receives load, change hooks, save and close in order")
  void strategyReceivesHooksInApplicationOrder(@TestProject Path projectFolder) throws Exception {
    Path vsumFolder = projectFolder.resolve("vsum");
    RecordingPersistence persistence = new RecordingPersistence();
    InternalVirtualModel virtualModel =
        new VirtualModelBuilder()
            .withStorageFolder(vsumFolder)
            .withChangePropagationSpecification(
                new RedundancyChangePropagationSpecification(
                    MetamodelDescriptor.with(AllElementTypesPackage.eNS_URI),
                    MetamodelDescriptor.with(AllElementTypesPackage.eNS_URI)))
            .withUserInteractor(
                UserInteractionFactory.instance.createUserInteractor(
                    UserInteractionFactory.instance.createPredefinedInteractionResultProvider(
                        null)))
            .withModelPersistence(persistence)
            .buildAndInitialize();
    assertEquals(List.of("load"), persistence.events);

    ResourceSet resourceSet = withGlobalFactories(new ResourceSetImpl());
    VitruviusChange<Uuid> change =
        recordChanges(
            resourceSet,
            UuidResolver.create(resourceSet),
            () -> {
              var root = aet.Root();
              root.setId("root");
              resourceSet
                  .createResource(createTestModelResourceUri(vsumFolder, ""))
                  .getContents()
                  .add(root);
            });
    final List<PropagatedChange> result = virtualModel.propagateChange(change);
    virtualModel.dispose();

    assertEquals(
        List.of("load", "applied", "recorded", "finished", "save", "close"), persistence.events);
    assertSame(change, persistence.appliedChanges.get(0));
    // The applied change with model elements is the one the propagation started from.
    assertSame(result.get(0).getOriginalChange(), persistence.appliedEObjectChanges.get(0));
    assertSame(result, persistence.finishedPropagations.get(0));
    // The recorded changes are exactly the consequential changes of the propagation, in order.
    assertEquals(
        result.stream().flatMap(it -> it.getConsequentialChanges().getEChanges().stream()).toList(),
        persistence.recordedChanges.stream()
            .flatMap(it -> it.getEChanges().stream())
            .toList());
    // Each recorded change comes with its counterpart with UUIDs.
    VitruviusChange<Uuid> changeWithIds = persistence.recordedChangesWithIds.get(0);
    List<EChange<Uuid>> eChangesWithIds = changeWithIds.getEChanges();
    assertEquals(persistence.recordedChanges.get(0).getEChanges().size(), eChangesWithIds.size());
    assertFalse(eChangesWithIds.isEmpty());
    // The stub writes nothing, so nothing of the state-based files exists.
    assertFalse(Files.exists(vsumFolder.resolve("vsum").resolve("uuid.uuid")));
    assertTrue(Files.isDirectory(vsumFolder.resolve("vsum")));
  }

  /** Records the calls it receives and stores nothing. */
  private static final class RecordingPersistence implements ModelPersistence {
    final List<String> events = new ArrayList<>();
    final List<VitruviusChange<Uuid>> appliedChanges = new ArrayList<>();
    final List<VitruviusChange<EObject>> appliedEObjectChanges = new ArrayList<>();
    final List<TransactionalChange<EObject>> recordedChanges = new ArrayList<>();
    final List<VitruviusChange<Uuid>> recordedChangesWithIds = new ArrayList<>();
    final List<List<PropagatedChange>> finishedPropagations = new ArrayList<>();

    @Override
    public void load(ModelPersistenceContext context) {
      events.add("load");
    }

    @Override
    public void changeApplied(
        ModelPersistenceContext context,
        VitruviusChange<Uuid> change,
        VitruviusChange<EObject> appliedChange) {
      events.add("applied");
      appliedChanges.add(change);
      appliedEObjectChanges.add(appliedChange);
    }

    @Override
    public void changeRecorded(
        ModelPersistenceContext context,
        TransactionalChange<EObject> recordedChange,
        VitruviusChange<Uuid> changeWithIds) {
      events.add("recorded");
      recordedChanges.add(recordedChange);
      recordedChangesWithIds.add(changeWithIds);
    }

    @Override
    public void propagationFinished(
        ModelPersistenceContext context, List<PropagatedChange> propagatedChanges) {
      events.add("finished");
      finishedPropagations.add(propagatedChanges);
    }

    @Override
    public void save(ModelPersistenceContext context) {
      events.add("save");
    }

    @Override
    public void close(ModelPersistenceContext context) {
      events.add("close");
    }
  }
}
