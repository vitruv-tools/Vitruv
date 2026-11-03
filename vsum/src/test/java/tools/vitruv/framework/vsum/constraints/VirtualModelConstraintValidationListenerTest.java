package tools.vitruv.framework.vsum.constraints;

import static edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceSetUtil.withGlobalFactories;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tools.vitruv.change.testutils.metamodels.AllElementTypesCreators.aet;
import static tools.vitruv.framework.vsum.VirtualModelTestUtil.recordChanges;

import allElementTypes.Root;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import tools.vitruv.change.atomic.uuid.UuidResolver;
import tools.vitruv.change.interaction.UserInteractionFactory;
import tools.vitruv.change.testutils.TestProject;
import tools.vitruv.change.testutils.TestProjectManager;
import tools.vitruv.framework.vsum.VirtualModelBuilder;
import tools.vitruv.framework.vsum.constraints.ConstraintValidationListener.Trigger;
import tools.vitruv.framework.vsum.internal.InternalVirtualModel;

/** Tests for validating constraints automatically and notifying listeners. */
@ExtendWith(TestProjectManager.class)
class VirtualModelConstraintValidationListenerTest {
  private Path projectFolder;
  private final List<Trigger> triggers = new ArrayList<>();
  private final List<ConstraintValidationReport> reports = new ArrayList<>();

  @BeforeEach
  void initializeProjectFolder(@TestProject Path projectFolder) {
    this.projectFolder = projectFolder;
  }

  @Test
  @DisplayName("propagated changes are validated and reported to listeners if enabled")
  void validatesAfterPropagation() throws Exception {
    InternalVirtualModel vsum = buildVsum(true, new PrefixConstraintChecker("noBad:bad"));
    vsum.addConstraintValidationListener(this::record);

    insertRoot(vsum, "a.allElementTypes", "good");
    insertRoot(vsum, "b.allElementTypes", "bad");

    assertEquals(List.of(Trigger.PROPAGATION, Trigger.PROPAGATION), triggers);
    assertTrue(reports.get(0).isSatisfied());
    assertFalse(reports.get(1).isSatisfied());
    Root violating = (Root) reports.get(1).results().get(0).violations().get(0).element();
    assertEquals("bad", violating.getId());
  }

  @Test
  @DisplayName("nothing is validated after propagation by default")
  void disabledByDefault() throws Exception {
    InternalVirtualModel vsum = buildVsum(false, new PrefixConstraintChecker("noBad:bad"));
    vsum.addConstraintValidationListener(this::record);

    insertRoot(vsum, "a.allElementTypes", "bad");
    vsum.createConstraint(PrefixConstraintChecker.LANGUAGE, "noUgly:ugly");

    assertFalse(vsum.isConstraintValidationAfterPropagation());
    assertTrue(triggers.isEmpty());
  }

  @Test
  @DisplayName("validation after propagation can be switched at runtime")
  void switchesAtRuntime() throws Exception {
    InternalVirtualModel vsum = buildVsum(false, new PrefixConstraintChecker("noBad:bad"));
    vsum.addConstraintValidationListener(this::record);

    vsum.setConstraintValidationAfterPropagation(true);
    insertRoot(vsum, "a.allElementTypes", "good");
    vsum.setConstraintValidationAfterPropagation(false);
    insertRoot(vsum, "b.allElementTypes", "good");

    assertEquals(List.of(Trigger.PROPAGATION), triggers);
  }

  @Test
  @DisplayName("changes of constraints are validated and reported if enabled")
  void validatesAfterConstraintChanges() throws Exception {
    InternalVirtualModel vsum = buildVsum(true, new PrefixConstraintChecker("noBad:bad"));
    insertRoot(vsum, "a.allElementTypes", "ugly");
    vsum.addConstraintValidationListener(this::record);

    vsum.createConstraint(PrefixConstraintChecker.LANGUAGE, "noUgly:ugly");
    vsum.updateConstraint("noUgly", "noUgly:nothing");
    vsum.deleteConstraint("noUgly");

    assertEquals(
        List.of(
            Trigger.CONSTRAINTS_CHANGED,
            Trigger.CONSTRAINTS_CHANGED,
            Trigger.CONSTRAINTS_CHANGED),
        triggers);
    assertFalse(reports.get(0).isSatisfied(), "the new constraint is violated by ugly");
    assertTrue(reports.get(1).isSatisfied());
    assertEquals(1, reports.get(2).results().size(), "only noBad remains");
  }

  @Test
  @DisplayName("validating all constraints on request is reported, selected ones are not")
  void reportsRequestedValidationOfAllConstraints() throws Exception {
    InternalVirtualModel vsum = buildVsum(false, new PrefixConstraintChecker("noBad:bad"));
    vsum.addConstraintValidationListener(this::record);

    ConstraintValidationReport report = vsum.validateConstraints();
    vsum.validateConstraints(List.of("noBad"));

    assertEquals(List.of(Trigger.REQUEST), triggers);
    assertEquals(report, reports.get(0));
  }

  @Test
  @DisplayName("a failing listener does not affect propagation or other listeners")
  void failingListenerIsIsolated() throws Exception {
    InternalVirtualModel vsum = buildVsum(true, new PrefixConstraintChecker("noBad:bad"));
    vsum.addConstraintValidationListener(
        (report, trigger) -> {
          throw new IllegalStateException("listener fails");
        });
    vsum.addConstraintValidationListener(this::record);

    insertRoot(vsum, "a.allElementTypes", "good");

    assertEquals(List.of(Trigger.PROPAGATION), triggers);
    assertEquals(1, vsum.getModels().size());
  }

  @Test
  @DisplayName("removed listeners and virtual models without constraints are not notified")
  void removedListenersAndNoConstraints() throws Exception {
    InternalVirtualModel vsum = buildVsum(true, new PrefixConstraintChecker());
    vsum.addConstraintValidationListener(this::record);
    insertRoot(vsum, "a.allElementTypes", "good");

    ConstraintValidationListener listener = this::record;
    vsum.addConstraintValidationListener(listener);
    vsum.removeConstraintValidationListener(listener);
    vsum.createConstraint(PrefixConstraintChecker.LANGUAGE, "noBad:bad");

    assertEquals(List.of(Trigger.CONSTRAINTS_CHANGED), triggers, "no constraints, no validation");
  }

  @Test
  @DisplayName("deleting the last constraint is reported with an empty report")
  void reportsDeletionOfTheLastConstraint() throws Exception {
    InternalVirtualModel vsum = buildVsum(true, new PrefixConstraintChecker("noBad:bad"));
    vsum.addConstraintValidationListener(this::record);

    vsum.deleteConstraint("noBad");

    assertEquals(List.of(Trigger.CONSTRAINTS_CHANGED), triggers);
    assertTrue(reports.get(0).results().isEmpty());
  }

  private void record(ConstraintValidationReport report, Trigger trigger) {
    reports.add(report);
    triggers.add(trigger);
  }

  private InternalVirtualModel buildVsum(
      boolean validateAfterPropagation, ConstraintChecker checker) throws Exception {
    return new VirtualModelBuilder()
        .withStorageFolder(projectFolder.resolve("vsum"))
        .withUserInteractor(
            UserInteractionFactory.instance.createUserInteractor(
                UserInteractionFactory.instance.createPredefinedInteractionResultProvider(null)))
        .withConstraintChecker(checker)
        .withConstraintValidationAfterPropagation(validateAfterPropagation)
        .buildAndInitialize();
  }

  private void insertRoot(InternalVirtualModel vsum, String fileName, String id) {
    var resourceSet = withGlobalFactories(new ResourceSetImpl());
    URI uri = URI.createFileURI(projectFolder.resolve(fileName).toString());
    var change =
        recordChanges(
            resourceSet,
            UuidResolver.create(resourceSet),
            () -> {
              Resource resource = resourceSet.createResource(uri);
              Root root = aet.Root();
              root.setId(id);
              resource.getContents().add(root);
            });
    vsum.propagateChange(change);
  }
}
