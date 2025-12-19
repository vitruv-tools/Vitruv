package tools.vitruv.framework.views.changederivation;

import java.io.IOException;
import org.eclipse.emf.compare.utils.UseIdentifiers;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import tools.vitruv.change.testutils.metamodels.UmlMockupCreators;

/** Tests for state-based change propagation in UML models. */
class UmlStateChangeTest extends StateChangePropagationTest {

  /** Tests renaming of types in UML models. */
  @ParameterizedTest
  @MethodSource("strategiesToTest")
  void testRenameTypes(StateBasedChangeResolutionStrategy strategyToTest) throws IOException {
    getUmlRoot().getClasses().get(0).setName("RenamedClass");
    getUmlRoot().getInterfaces().get(0).setName("RenamedInterface");
    compareChanges(getUmlModel(), getUmlCheckpoint(), strategyToTest);
  }

  /**
   * Tests adding new attributes in UML models.
   *
   * @param useIdentifiers The identifier matching behavior to use.
   * @throws IOException if an I/O error occurs.
   */
  @ParameterizedTest
  @EnumSource(
      value = UseIdentifiers.class,
      names = {"ONLY"},
      mode = EnumSource.Mode.EXCLUDE)
  void testNewAttributes(UseIdentifiers useIdentifiers) throws IOException {
    DefaultStateBasedChangeResolutionStrategy strategyToTest =
        new DefaultStateBasedChangeResolutionStrategy(useIdentifiers);
    getUmlRoot().getClasses().get(0).getAttributes().add(UmlMockupCreators.uml.Attribute());
    getUmlRoot()
        .getClasses()
        .get(0)
        .getAttributes()
        .get(getUmlRoot().getClasses().get(0).getAttributes().size() - 1)
        .setAttributeName("NewlyAddedAttribute");
    compareChanges(getUmlModel(), getUmlCheckpoint(), strategyToTest);
  }

  /**
   * Tests adding a new method in UML models.
   *
   * @param strategyToTest The strategy to test.
   * @throws IOException if an I/O error occurs.
   */
  @ParameterizedTest
  @MethodSource("strategiesToTest")
  void testNewMethod(StateBasedChangeResolutionStrategy strategyToTest) throws IOException {
    getUmlRoot().getInterfaces().get(0).getMethods().add(UmlMockupCreators.uml.Method());
    getUmlRoot()
        .getInterfaces()
        .get(0)
        .getMethods()
        .get(getUmlRoot().getInterfaces().get(0).getMethods().size() - 1)
        .setName("NewlyAddedMethod");
    compareChanges(getUmlModel(), getUmlCheckpoint(), strategyToTest);
  }

  /**
   * Tests adding a new class in UML models.
   *
   * @param strategyToTest The strategy to test.
   * @throws IOException if an I/O error occurs.
   */
  @ParameterizedTest
  @MethodSource("strategiesToTest")
  void testNewClass(StateBasedChangeResolutionStrategy strategyToTest) throws IOException {
    var newClass = UmlMockupCreators.uml.Class();
    newClass.setName("NewlyAddedClass");
    getUmlRoot().getClasses().add(newClass);
    compareChanges(getUmlModel(), getUmlCheckpoint(), strategyToTest);
  }

  /**
   * Tests replacing a class in UML models.
   *
   * @param useIdentifiers The identifier matching behavior to use.
   * @throws IOException if an I/O error occurs.
   */
  @ParameterizedTest
  @EnumSource(
      value = UseIdentifiers.class,
      names = {"NEVER"},
      mode = EnumSource.Mode.EXCLUDE)
  void testReplaceClass(UseIdentifiers useIdentifiers) throws IOException {
    getUmlRoot().getClasses().remove(0);
    var newClass = UmlMockupCreators.uml.Class();
    newClass.setName("NewlyAddedClass");
    getUmlRoot().getClasses().add(newClass);
    DefaultStateBasedChangeResolutionStrategy strategyToTest =
        new DefaultStateBasedChangeResolutionStrategy(useIdentifiers);
    compareChanges(getUmlModel(), getUmlCheckpoint(), strategyToTest);
  }

  /**
   * Tests deleting a class in UML models.
   *
   * @param strategyToTest The strategy to test.
   * @throws IOException if an I/O error occurs.
   */
  @ParameterizedTest
  @MethodSource("strategiesToTest")
  void testDeleteClass(StateBasedChangeResolutionStrategy strategyToTest) throws IOException {
    getUmlRoot().getClasses().remove(0);
    compareChanges(getUmlModel(), getUmlCheckpoint(), strategyToTest);
  }

  /**
   * Tests adding a new interface in UML models.
   *
   * @param strategyToTest The strategy to test.
   * @throws IOException if an I/O error occurs.
   */
  @ParameterizedTest
  @MethodSource("strategiesToTest")
  void testNewInterface(StateBasedChangeResolutionStrategy strategyToTest) throws IOException {
    var newInterface = UmlMockupCreators.uml.Interface();
    newInterface.setName("NewlyAddedInterface");
    getUmlRoot().getInterfaces().add(newInterface);
    compareChanges(getUmlModel(), getUmlCheckpoint(), strategyToTest);
  }
}
