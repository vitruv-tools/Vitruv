package tools.vitruv.framework.vsum.constraints;

import static edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceSetUtil.withGlobalFactories;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tools.vitruv.change.testutils.metamodels.AllElementTypesCreators.aet;
import static tools.vitruv.framework.vsum.VirtualModelTestUtil.recordChanges;

import allElementTypes.Root;
import java.nio.file.Path;
import java.util.List;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import tools.vitruv.change.atomic.uuid.UuidResolver;
import tools.vitruv.change.interaction.UserInteractionFactory;
import tools.vitruv.change.testutils.TestProject;
import tools.vitruv.change.testutils.TestProjectManager;
import tools.vitruv.framework.vsum.VirtualModelBuilder;
import tools.vitruv.framework.vsum.internal.InternalVirtualModel;

/** Tests for creating, updating and deleting constraints of a running virtual model. */
@ExtendWith(TestProjectManager.class)
class VirtualModelConstraintManagementTest {
  private Path projectFolder;
  private InternalVirtualModel vsum;

  @BeforeEach
  void initializeProjectFolder(@TestProject Path projectFolder) {
    this.projectFolder = projectFolder;
  }

  @AfterEach
  void dispose() {
    if (vsum != null) {
      vsum.dispose();
    }
  }

  @Nested
  @DisplayName("at runtime")
  class AtRuntime {

    @Test
    @DisplayName("a created constraint is listed and validated without rebuilding the VSUM")
    void create() throws Exception {
      vsum = build(new PrefixConstraintChecker());
      insertRoots("bad1", "good");

      ConstraintDescriptor created =
          vsum.createConstraint(PrefixConstraintChecker.LANGUAGE, "noBad:bad");

      assertEquals("noBad", created.id());
      assertEquals(List.of("noBad"), ids());
      assertEquals(created, vsum.getConstraint("noBad").orElseThrow());
      assertEquals(ConstraintStatus.VIOLATED, statusOf("noBad"));
    }

    @Test
    @DisplayName("an updated constraint is validated with its new source")
    void update() throws Exception {
      vsum = build(new PrefixConstraintChecker("noBad:bad"));
      insertRoots("bad1");

      vsum.updateConstraint("noBad", "noBad:other");

      assertEquals("noBad:other", vsum.getConstraint("noBad").orElseThrow().source());
      assertEquals(ConstraintStatus.SATISFIED, statusOf("noBad"));
    }

    @Test
    @DisplayName("a deleted constraint is neither listed nor validated")
    void delete() throws Exception {
      vsum = build(new PrefixConstraintChecker("noBad:bad", "noUgly:ugly"));

      vsum.deleteConstraint("noBad");

      assertEquals(List.of("noUgly"), ids());
      assertTrue(vsum.getConstraint("noBad").isEmpty());
      assertThrows(
          UnknownConstraintException.class, () -> vsum.validateConstraints(List.of("noBad")));
    }

    @Test
    @DisplayName("an invalid source is rejected with diagnostics and changes nothing")
    void invalidSource() throws Exception {
      vsum = build(new PrefixConstraintChecker("noBad:bad"));

      InvalidConstraintException exception =
          assertThrows(
              InvalidConstraintException.class,
              () -> vsum.createConstraint(PrefixConstraintChecker.LANGUAGE, "no separator"));

      assertFalse(exception.getDiagnostics().isEmpty());
      assertEquals(List.of("noBad"), ids());
      assertThrows(
          InvalidConstraintException.class, () -> vsum.updateConstraint("noBad", "broken"));
      assertEquals("noBad:bad", vsum.getConstraint("noBad").orElseThrow().source());
    }

    @Test
    @DisplayName("unknown languages, unknown ids and duplicate ids are rejected")
    void rejectsInvalidRequests() throws Exception {
      vsum = build(new PrefixConstraintChecker("noBad:bad"));

      assertThrows(
          UnsupportedConstraintLanguageException.class,
          () -> vsum.createConstraint("unknown", "x:y"));
      assertThrows(UnknownConstraintException.class, () -> vsum.updateConstraint("x", "x:y"));
      assertThrows(UnknownConstraintException.class, () -> vsum.deleteConstraint("x"));
      assertThrows(
          DuplicateConstraintException.class,
          () -> vsum.createConstraint(PrefixConstraintChecker.LANGUAGE, "noBad:other"));
    }

    @Test
    @DisplayName("constraint ids stay unique across checkers")
    void uniqueAcrossCheckers() throws Exception {
      vsum = build(new PrefixConstraintChecker(), new ReadOnlyChecker("shared"));

      assertThrows(
          DuplicateConstraintException.class,
          () -> vsum.createConstraint(PrefixConstraintChecker.LANGUAGE, "shared:x"));
      assertEquals(List.of("shared"), ids());
    }

    @Test
    @DisplayName("constraints of checkers that are not editable cannot be changed")
    void readOnlyChecker() throws Exception {
      vsum = build(new ReadOnlyChecker("fixed"));

      assertThrows(
          UnsupportedOperationException.class, () -> vsum.updateConstraint("fixed", "fixed:x"));
      assertThrows(UnsupportedOperationException.class, () -> vsum.deleteConstraint("fixed"));
    }
  }

  @Nested
  @DisplayName("after a restart")
  class AfterRestart {

    @Test
    @DisplayName("created, updated and deleted constraints are restored")
    void restoresRuntimeChanges() throws Exception {
      vsum = build(new PrefixConstraintChecker("builtIn:a", "removed:b"));
      vsum.createConstraint(PrefixConstraintChecker.LANGUAGE, "runtime:c");
      vsum.updateConstraint("builtIn", "builtIn:changed");
      vsum.deleteConstraint("removed");
      restart(new PrefixConstraintChecker("builtIn:a", "removed:b"));

      assertEquals(List.of("builtIn", "runtime"), ids());
      assertEquals("builtIn:changed", vsum.getConstraint("builtIn").orElseThrow().source());
      assertEquals("runtime:c", vsum.getConstraint("runtime").orElseThrow().source());
    }

    @Test
    @DisplayName("built-in constraints changed in the build replace unchanged stored ones")
    void takesChangedBuiltInConstraints() throws Exception {
      vsum = build(new PrefixConstraintChecker("unchanged:a", "edited:b"));
      vsum.updateConstraint("edited", "edited:runtime");
      restart(new PrefixConstraintChecker("unchanged:new", "edited:new", "added:c"));

      assertEquals("unchanged:new", vsum.getConstraint("unchanged").orElseThrow().source());
      assertEquals("edited:runtime", vsum.getConstraint("edited").orElseThrow().source());
      assertEquals("added:c", vsum.getConstraint("added").orElseThrow().source());
    }

    @Test
    @DisplayName("built-in constraints removed from the build are dropped unless changed")
    void dropsRemovedBuiltInConstraints() throws Exception {
      vsum = build(new PrefixConstraintChecker("dropped:a", "kept:b"));
      vsum.updateConstraint("kept", "kept:runtime");
      restart(new PrefixConstraintChecker());

      assertEquals(List.of("kept"), ids());
      assertEquals("kept:runtime", vsum.getConstraint("kept").orElseThrow().source());
    }

    @Test
    @DisplayName("constraints of a language without checker are kept for later")
    void keepsConstraintsOfMissingLanguages() throws Exception {
      vsum = build(new PrefixConstraintChecker());
      vsum.createConstraint(PrefixConstraintChecker.LANGUAGE, "runtime:c");
      restart();
      assertTrue(ids().isEmpty());

      restart(new PrefixConstraintChecker());

      assertEquals(List.of("runtime"), ids());
    }
  }

  private InternalVirtualModel build(ConstraintChecker... checkers) throws Exception {
    return new VirtualModelBuilder()
        .withStorageFolder(projectFolder.resolve("vsum"))
        .withUserInteractor(
            UserInteractionFactory.instance.createUserInteractor(
                UserInteractionFactory.instance.createPredefinedInteractionResultProvider(null)))
        .withConstraintCheckers(checkers)
        .buildAndInitialize();
  }

  private void restart(ConstraintChecker... checkers) throws Exception {
    vsum.dispose();
    vsum = build(checkers);
  }

  private List<String> ids() {
    return vsum.getConstraints().stream().map(ConstraintDescriptor::id).toList();
  }

  private ConstraintStatus statusOf(String id) {
    return vsum.validateConstraints(List.of(id)).results().get(0).status();
  }

  private void insertRoots(String... ids) {
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

  /** A checker whose constraints cannot be changed. */
  private static final class ReadOnlyChecker implements ConstraintChecker {
    private final List<ConstraintDescriptor> constraints;

    ReadOnlyChecker(String id) {
      constraints =
          List.of(new ConstraintDescriptor(id, id, null, null, null, "readonly", null));
    }

    @Override
    public List<ConstraintDescriptor> getConstraints() {
      return constraints;
    }

    @Override
    public List<ConstraintResult> check(
        ConstraintCheckContext context, java.util.Collection<String> constraintIds) {
      return constraints.stream().map(ConstraintResult::satisfied).toList();
    }
  }
}
