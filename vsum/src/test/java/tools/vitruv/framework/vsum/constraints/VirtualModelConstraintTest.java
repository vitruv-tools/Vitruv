package tools.vitruv.framework.vsum.constraints;

import static edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceSetUtil.withGlobalFactories;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tools.vitruv.change.testutils.metamodels.AllElementTypesCreators.aet;
import static tools.vitruv.framework.vsum.VirtualModelTestUtil.recordChanges;

import allElementTypes.Root;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import tools.vitruv.change.atomic.uuid.UuidResolver;
import tools.vitruv.change.correspondence.view.EditableCorrespondenceModelView;
import tools.vitruv.change.interaction.UserInteractionFactory;
import tools.vitruv.change.testutils.TestProject;
import tools.vitruv.change.testutils.TestProjectManager;
import tools.vitruv.framework.vsum.VirtualModel;
import tools.vitruv.framework.vsum.VirtualModelBuilder;
import tools.vitruv.framework.vsum.internal.InternalVirtualModel;

/** Tests for registering, listing and validating constraints on a virtual model. */
@ExtendWith(TestProjectManager.class)
class VirtualModelConstraintTest {
  private static final String NO_BAD_ROOT_ID = "noBadRootId";
  private static final String HAS_ROOT = "hasRoot";

  private Path projectFolder;

  @BeforeEach
  void initializeProjectFolder(@TestProject Path projectFolder) {
    this.projectFolder = projectFolder;
  }

  @Test
  @DisplayName("a virtual model without constraint checkers has no constraints")
  void noCheckerRegistered() throws Exception {
    VirtualModel vsum = buildVsum();

    assertTrue(vsum.getConstraints().isEmpty());
    ConstraintValidationReport report = vsum.validateConstraints();
    assertTrue(report.results().isEmpty());
    assertTrue(report.isSatisfied());
  }

  @Test
  @DisplayName("constraints of all registered checkers are listed")
  void listsConstraintsOfAllCheckers() throws Exception {
    VirtualModel vsum = buildVsum(new BadRootIdChecker(), new HasRootChecker());

    List<String> ids = vsum.getConstraints().stream().map(ConstraintDescriptor::id).toList();
    assertEquals(List.of(NO_BAD_ROOT_ID, HAS_ROOT), ids);
  }

  @Test
  @DisplayName("a constraint without violations is satisfied")
  void satisfiedConstraint() throws Exception {
    InternalVirtualModel vsum = buildVsum(new BadRootIdChecker());
    insertRoots(vsum, "good");

    ConstraintValidationReport report = vsum.validateConstraints();

    assertTrue(report.isSatisfied());
    assertEquals(ConstraintStatus.SATISFIED, report.results().get(0).status());
    assertTrue(report.affectedResourceUris().isEmpty());
  }

  @Test
  @DisplayName("violations are reported with the VSUM UUID and resource of the element")
  void violationIsResolvedToUuid() throws Exception {
    InternalVirtualModel vsum = buildVsum(new BadRootIdChecker());
    insertRoots(vsum, "bad");

    ConstraintValidationReport report = vsum.validateConstraints();

    assertFalse(report.isSatisfied());
    ConstraintValidationResult result = report.results().get(0);
    assertEquals(ConstraintStatus.VIOLATED, result.status());
    assertEquals(1, result.violations().size());
    ResolvedConstraintViolation violation = result.violations().get(0);
    Root violatingRoot = (Root) violation.element();
    assertEquals("bad", violatingRoot.getId());
    assertEquals(vsum.getUuidResolver().getUuid(violatingRoot), violation.uuid());
    assertSame(violatingRoot, vsum.getUuidResolver().getEObject(violation.uuid()));
    assertEquals(violatingRoot.eResource().getURI(), violation.resourceUri());
    assertEquals(ConstraintSeverity.CRITICAL, violation.severity());
    assertEquals(Set.of(violatingRoot.eResource().getURI()), report.affectedResourceUris());
  }

  @Test
  @DisplayName("an empty selection of constraints results in an empty report")
  void emptySelection() throws Exception {
    VirtualModel vsum = buildVsum(new BadRootIdChecker());

    assertTrue(vsum.validateConstraints(List.of()).results().isEmpty());
  }

  @Test
  @DisplayName("duplicate requested ids are validated once")
  void duplicateRequestedIds() throws Exception {
    VirtualModel vsum = buildVsum(new BadRootIdChecker());

    ConstraintValidationReport report =
        vsum.validateConstraints(List.of(NO_BAD_ROOT_ID, NO_BAD_ROOT_ID));

    assertEquals(1, report.results().size());
  }

  @Test
  @DisplayName("a stack overflow in a checker results in an error status")
  void checkerStackOverflowYieldsError() throws Exception {
    VirtualModel vsum = buildVsum(new FailingChecker(new StackOverflowError()));

    assertEquals(
        ConstraintStatus.ERROR, resultFor(vsum.validateConstraints(), "failing").status());
  }

  @Test
  @DisplayName("checkers get a correspondence model that cannot be modified")
  void correspondenceModelIsReadOnly() throws Exception {
    List<Object> seen = new ArrayList<>();
    VirtualModel vsum =
        buildVsum(
            new FailingChecker(null) {
              @Override
              public List<ConstraintResult> check(
                  ConstraintCheckContext context, Collection<String> constraintIds) {
                seen.add(context.getCorrespondenceModel());
                return List.of();
              }
            });

    vsum.validateConstraints();

    assertEquals(1, seen.size());
    assertFalse(seen.get(0) instanceof EditableCorrespondenceModelView);
  }

  @Test
  @DisplayName("only the requested constraints are validated")
  void filterByIds() throws Exception {
    InternalVirtualModel vsum = buildVsum(new BadRootIdChecker(), new HasRootChecker());
    insertRoots(vsum, "bad");

    ConstraintValidationReport report = vsum.validateConstraints(List.of(HAS_ROOT));

    assertEquals(1, report.results().size());
    assertEquals(HAS_ROOT, report.results().get(0).descriptor().id());
    assertTrue(report.isSatisfied());
  }

  @Test
  @DisplayName("requesting an unknown constraint fails")
  void unknownConstraintId() throws Exception {
    VirtualModel vsum = buildVsum(new BadRootIdChecker());

    assertThrows(
        IllegalArgumentException.class, () -> vsum.validateConstraints(List.of("doesNotExist")));
  }

  @Test
  @DisplayName("an exception in a checker results in an error status instead of a failure")
  void checkerExceptionYieldsError() throws Exception {
    VirtualModel vsum =
        buildVsum(
            new FailingChecker(new IllegalStateException("evaluation failed")),
            new HasRootChecker());

    ConstraintValidationReport report = vsum.validateConstraints();

    ConstraintValidationResult failed = resultFor(report, "failing");
    assertEquals(ConstraintStatus.ERROR, failed.status());
    assertFalse(failed.errors().isEmpty());
    assertFalse(report.isSatisfied());
    assertEquals(ConstraintStatus.VIOLATED, resultFor(report, HAS_ROOT).status());
  }

  @Test
  @DisplayName("a requested constraint without result from its checker results in an error")
  void missingResultYieldsError() throws Exception {
    VirtualModel vsum = buildVsum(new SilentChecker());

    ConstraintValidationReport report = vsum.validateConstraints();

    assertEquals(ConstraintStatus.ERROR, resultFor(report, "silent").status());
  }

  @Test
  @DisplayName("constraint ids must be unique across all checkers")
  void duplicateIdsRejected() {
    assertThrows(
        IllegalArgumentException.class,
        () -> buildVsum(new BadRootIdChecker(), new BadRootIdChecker()));
  }

  private InternalVirtualModel buildVsum(ConstraintChecker... checkers) throws Exception {
    return new VirtualModelBuilder()
        .withStorageFolder(projectFolder.resolve("vsum"))
        .withUserInteractor(
            UserInteractionFactory.instance.createUserInteractor(
                UserInteractionFactory.instance.createPredefinedInteractionResultProvider(null)))
        .withConstraintCheckers(checkers)
        .buildAndInitialize();
  }

  private void insertRoots(InternalVirtualModel vsum, String... ids) {
    var resourceSet = withGlobalFactories(new ResourceSetImpl());
    URI uri = URI.createFileURI(projectFolder.resolve("root.allElementTypes").toString());
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
  }

  private static ConstraintValidationResult resultFor(
      ConstraintValidationReport report, String id) {
    return report.results().stream()
        .filter(result -> result.descriptor().id().equals(id))
        .findFirst()
        .orElseThrow();
  }

  private static List<Root> allRoots(ConstraintCheckContext context) {
    List<Root> roots = new ArrayList<>();
    for (Resource resource : context.getModelResources()) {
      resource.getAllContents().forEachRemaining(
          object -> {
            if (object instanceof Root root) {
              roots.add(root);
            }
          });
    }
    return roots;
  }

  /** Every {@link Root} must have an id that does not start with "bad". */
  private static final class BadRootIdChecker implements ConstraintChecker {
    private static final ConstraintDescriptor DESCRIPTOR =
        new ConstraintDescriptor(
            NO_BAD_ROOT_ID,
            NO_BAD_ROOT_ID,
            "allElementTypes::Root",
            ConstraintKind.INVARIANT,
            ConstraintSeverity.CRITICAL,
            "test",
            "context Root inv noBadRootId: not id.startsWith('bad')");

    @Override
    public List<ConstraintDescriptor> getConstraints() {
      return List.of(DESCRIPTOR);
    }

    @Override
    public List<ConstraintResult> check(
        ConstraintCheckContext context, Collection<String> constraintIds) {
      List<ConstraintViolation> violations =
          allRoots(context).stream()
              .filter(root -> root.getId() != null && root.getId().startsWith("bad"))
              .map(
                  root ->
                      new ConstraintViolation(
                          root, ConstraintSeverity.CRITICAL, "Root id must not start with bad"))
              .toList();
      return List.of(ConstraintResult.of(DESCRIPTOR, violations));
    }
  }

  /** The virtual model must contain at least one {@link Root}. */
  private static final class HasRootChecker implements ConstraintChecker {
    private static final ConstraintDescriptor DESCRIPTOR =
        new ConstraintDescriptor(
            HAS_ROOT,
            HAS_ROOT,
            null,
            ConstraintKind.INVARIANT,
            ConstraintSeverity.WARNING,
            "test",
            null);

    @Override
    public List<ConstraintDescriptor> getConstraints() {
      return List.of(DESCRIPTOR);
    }

    @Override
    public List<ConstraintResult> check(
        ConstraintCheckContext context, Collection<String> constraintIds) {
      if (allRoots(context).isEmpty()) {
        return List.of(ConstraintResult.violated(DESCRIPTOR, List.of()));
      }
      return List.of(ConstraintResult.satisfied(DESCRIPTOR));
    }
  }

  /** A checker whose evaluation always throws the given throwable. */
  private static class FailingChecker implements ConstraintChecker {
    private final Throwable failure;

    FailingChecker(Throwable failure) {
      this.failure = failure;
    }

    @Override
    public List<ConstraintDescriptor> getConstraints() {
      return List.of(
          new ConstraintDescriptor(
              "failing", "failing", null, ConstraintKind.INVARIANT, null, "test", null));
    }

    @Override
    public List<ConstraintResult> check(
        ConstraintCheckContext context, Collection<String> constraintIds) {
      if (failure instanceof Error error) {
        throw error;
      }
      throw (RuntimeException) failure;
    }
  }

  /** A checker that returns no results at all. */
  private static final class SilentChecker implements ConstraintChecker {
    @Override
    public List<ConstraintDescriptor> getConstraints() {
      return List.of(
          new ConstraintDescriptor(
              "silent", "silent", null, ConstraintKind.INVARIANT, null, "test", null));
    }

    @Override
    public List<ConstraintResult> check(
        ConstraintCheckContext context, Collection<String> constraintIds) {
      return List.of();
    }
  }
}
