package tools.vitruv.framework.vsum;

import static edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceSetUtil.withGlobalFactories;
import static edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceUtil.getFirstRootEObject;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static tools.vitruv.change.testutils.metamodels.AllElementTypesCreators.aet;
import static tools.vitruv.framework.vsum.VirtualModelTestUtil.createAndLoadTestVirtualModelWithConsistencyPreservation;
import static tools.vitruv.framework.vsum.VirtualModelTestUtil.recordChanges;

import allElementTypes.Root;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import tools.vitruv.change.atomic.EChange;
import tools.vitruv.change.atomic.uuid.Uuid;
import tools.vitruv.change.atomic.uuid.UuidResolver;
import tools.vitruv.change.composite.description.PropagatedChange;
import tools.vitruv.change.composite.description.VitruviusChange;
import tools.vitruv.change.composite.propagation.ChangePropagationListener;
import tools.vitruv.change.propagation.ChangePropagationObserver;
import tools.vitruv.change.propagation.ChangePropagationSpecification;
import tools.vitruv.change.testutils.TestProject;
import tools.vitruv.change.testutils.TestProjectManager;
import tools.vitruv.framework.vsum.internal.VirtualModelImpl;

/**
 * Tests that observers and listeners can be registered and deregistered while changes are
 * propagated.
 */
@ExtendWith(TestProjectManager.class)
class VirtualModelObserverConcurrencyTest {
  private static final int PROPAGATIONS = 300;

  @Test
  @DisplayName("register observers and listeners while changes are propagated")
  void registerObserversDuringPropagation(@TestProject Path projectFolder) throws Exception {
    var virtualModel =
        (VirtualModelImpl)
            createAndLoadTestVirtualModelWithConsistencyPreservation(projectFolder.resolve("vsum"));
    var resourceSet = withGlobalFactories(new ResourceSetImpl());
    var uuidResolver = UuidResolver.create(resourceSet);
    var modelUri = VirtualModelTestUtil.createTestModelResourceUri(projectFolder, "");
    virtualModel.propagateChange(
        recordChanges(
            resourceSet,
            uuidResolver,
            () -> {
              var root = aet.Root();
              root.setId("root");
              resourceSet.createResource(modelUri).getContents().add(root);
            }));

    List<String> failures = new CopyOnWriteArrayList<>();
    var propagating = new AtomicBoolean(true);
    var propagator =
        new Thread(
            () -> {
              try {
                var root = (Root) getFirstRootEObject(resourceSet.getResource(modelUri, false));
                for (int i = 0; i < PROPAGATIONS; i++) {
                  int round = i;
                  virtualModel.propagateChange(
                      recordChanges(
                          resourceSet,
                          uuidResolver,
                          () -> root.setSingleValuedEAttribute(round + 1)));
                }
              } catch (RuntimeException e) {
                failures.add("propagation: " + e);
              } finally {
                propagating.set(false);
              }
            });

    // registered observers make the propagation iterate the observer list longer
    for (int i = 0; i < 200; i++) {
      virtualModel.registerObserver(new NoOpObserver());
    }
    propagator.start();
    while (propagating.get()) {
      var observer = new NoOpObserver();
      var listener = new NoOpListener();
      virtualModel.registerObserver(observer);
      virtualModel.addChangePropagationListener(listener);
      virtualModel.deregisterObserver(observer);
      virtualModel.removeChangePropagationListener(listener);
    }
    propagator.join();

    assertEquals(List.of(), failures.stream().distinct().limit(10).toList());
    virtualModel.dispose();
  }

  private static class NoOpObserver implements ChangePropagationObserver {
    @Override
    public void objectCreated(EObject createdObject) {}

    @Override
    public void changePropagationStarted(
        ChangePropagationSpecification specification, EChange<EObject> change) {}

    @Override
    public void changePropagationStopped(
        ChangePropagationSpecification specification, EChange<EObject> change) {}
  }

  private static class NoOpListener implements ChangePropagationListener {
    @Override
    public void startedChangePropagation(VitruviusChange<Uuid> changeToPropagate) {}

    @Override
    public void finishedChangePropagation(Iterable<PropagatedChange> propagatedChanges) {}
  }
}
