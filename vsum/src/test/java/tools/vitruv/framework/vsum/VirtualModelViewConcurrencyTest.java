package tools.vitruv.framework.vsum;

import static edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.claimOne;
import static edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceSetUtil.withGlobalFactories;
import static edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceUtil.getFirstRootEObject;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static tools.vitruv.change.testutils.metamodels.AllElementTypesCreators.aet;
import static tools.vitruv.framework.vsum.VirtualModelTestUtil.createAndLoadTestVirtualModel;
import static tools.vitruv.framework.vsum.VirtualModelTestUtil.recordChanges;

import allElementTypes.Root;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import tools.vitruv.change.atomic.uuid.UuidResolver;
import tools.vitruv.change.testutils.TestProject;
import tools.vitruv.change.testutils.TestProjectManager;
import tools.vitruv.framework.views.View;
import tools.vitruv.framework.views.ViewTypeFactory;
import tools.vitruv.framework.vsum.internal.InternalVirtualModel;

/**
 * Tests that views read a consistent state of the virtual model while changes are propagated
 * concurrently.
 */
@ExtendWith(TestProjectManager.class)
class VirtualModelViewConcurrencyTest {
  private static final int PROPAGATIONS = 150;
  private static final int ELEMENTS_PER_PROPAGATION = 20;

  @Test
  @DisplayName("create and update views while changes are propagated")
  void createAndUpdateViewsDuringPropagation(@TestProject Path projectFolder) throws Exception {
    var virtualModel =
        (InternalVirtualModel) createAndLoadTestVirtualModel(projectFolder.resolve("vsum"));
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
                          () -> {
                            for (int j = 0; j < ELEMENTS_PER_PROPAGATION; j++) {
                              var nonRoot = aet.NonRoot();
                              nonRoot.setId("element" + round + "_" + j);
                              root.getMultiValuedContainmentEReference().add(nonRoot);
                            }
                          }));
                }
              } catch (RuntimeException e) {
                failures.add("propagation: " + e);
              } finally {
                propagating.set(false);
              }
            });

    propagator.start();
    View updatedView = null;
    int viewReads = 0;
    while (propagating.get()) {
      viewReads++;
      try (View view = createView(virtualModel)) {
        checkConsistent(view, "created view", failures);
        if (updatedView == null) {
          updatedView = createView(virtualModel);
        } else if (updatedView.isOutdated()) {
          updatedView.update();
          checkConsistent(updatedView, "updated view", failures);
        }
      } catch (Exception e) {
        failures.add("view: " + e);
        updatedView = null;
      }
    }
    propagator.join();
    if (updatedView != null) {
      updatedView.close();
    }

    assertEquals(
        List.of(),
        failures.stream().distinct().limit(10).toList(),
        failures.size() + " inconsistent of " + viewReads + " view reads");
    try (View view = createView(virtualModel)) {
      assertEquals(
          PROPAGATIONS * ELEMENTS_PER_PROPAGATION,
          rootOf(view).getMultiValuedContainmentEReference().size());
    }
  }

  private static void checkConsistent(View view, String kind, List<String> failures) {
    int size = rootOf(view).getMultiValuedContainmentEReference().size();
    if (size % ELEMENTS_PER_PROPAGATION != 0) {
      failures.add(kind + " contains a partially propagated change");
    }
  }

  private static Root rootOf(View view) {
    return claimOne(view.getRootObjects(Root.class));
  }

  private static View createView(InternalVirtualModel virtualModel) {
    var selector = virtualModel.createSelector(ViewTypeFactory.createIdentityMappingViewType(""));
    selector.getSelectableElements().forEach(element -> selector.setSelected(element, true));
    return selector.createView();
  }
}
