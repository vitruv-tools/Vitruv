package tools.vitruv.framework.views.changederivation;

import java.io.IOException;
import org.eclipse.emf.compare.utils.UseIdentifiers;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import tools.vitruv.change.testutils.metamodels.UmlMockupCreators;

/** Tests for state-based change propagation in UML models. */
public class UmlStateChangeTest extends StateChangePropagationTest {

  /** Tests renaming of types in UML models. */
  @ParameterizedTest
  @MethodSource("strategiesToTest")
  public void testRenameTypes(StateBasedChangeResolutionStrategy strategyToTest)
      throws IOException {
    umlRoot.getClasses().get(0).setName("RenamedClass");
    umlRoot.getInterfaces().get(0).setName("RenamedInterface");
    compareChanges(umlModel, umlCheckpoint, strategyToTest);
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
  public void testNewAttributes(UseIdentifiers useIdentifiers) throws IOException {
    DefaultStateBasedChangeResolutionStrategy strategyToTest =
        new DefaultStateBasedChangeResolutionStrategy(useIdentifiers);
    umlRoot.getClasses().get(0).getAttributes().add(UmlMockupCreators.uml.Attribute());
    umlRoot
        .getClasses()
        .get(0)
        .getAttributes()
        .get(umlRoot.getClasses().get(0).getAttributes().size() - 1)
        .setAttributeName("NewlyAddedAttribute");
    compareChanges(umlModel, umlCheckpoint, strategyToTest);
  }

  /**
   * Tests adding a new method in UML models.
   *
   * @param strategyToTest The strategy to test.
   * @throws IOException if an I/O error occurs.
   */
  @ParameterizedTest
  @MethodSource("strategiesToTest")
  public void testNewMethod(StateBasedChangeResolutionStrategy strategyToTest) throws IOException {
    umlRoot.getInterfaces().get(0).getMethods().add(UmlMockupCreators.uml.Method());
    umlRoot
        .getInterfaces()
        .get(0)
        .getMethods()
        .get(umlRoot.getInterfaces().get(0).getMethods().size() - 1)
        .setName("NewlyAddedMethod");
    compareChanges(umlModel, umlCheckpoint, strategyToTest);
  }

  /**
   * Tests adding a new class in UML models.
   *
   * @param strategyToTest The strategy to test.
   * @throws IOException if an I/O error occurs.
   */
  @ParameterizedTest
  @MethodSource("strategiesToTest")
  public void testNewClass(StateBasedChangeResolutionStrategy strategyToTest) throws IOException {
    var newClass = UmlMockupCreators.uml.Class();
    newClass.setName("NewlyAddedClass");
    umlRoot.getClasses().add(newClass);
    compareChanges(umlModel, umlCheckpoint, strategyToTest);
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
  public void testReplaceClass(UseIdentifiers useIdentifiers) throws IOException {
    umlRoot.getClasses().remove(0);
    var newClass = UmlMockupCreators.uml.Class();
    newClass.setName("NewlyAddedClass");
    umlRoot.getClasses().add(newClass);
    DefaultStateBasedChangeResolutionStrategy strategyToTest =
        new DefaultStateBasedChangeResolutionStrategy(useIdentifiers);
    compareChanges(umlModel, umlCheckpoint, strategyToTest);
  }

  /**
   * Tests deleting a class in UML models.
   *
   * @param strategyToTest The strategy to test.
   * @throws IOException if an I/O error occurs.
   */
  @ParameterizedTest
  @MethodSource("strategiesToTest")
  public void testDeleteClass(StateBasedChangeResolutionStrategy strategyToTest)
      throws IOException {
    umlRoot.getClasses().remove(0);
    compareChanges(umlModel, umlCheckpoint, strategyToTest);
  }

  /**
   * Tests adding a new interface in UML models.
   *
   * @param strategyToTest The strategy to test.
   * @throws IOException if an I/O error occurs.
   */
  @ParameterizedTest
  @MethodSource("strategiesToTest")
  public void testNewInterface(StateBasedChangeResolutionStrategy strategyToTest)
      throws IOException {
    var newInterface = UmlMockupCreators.uml.Interface();
    newInterface.setName("NewlyAddedInterface");
    umlRoot.getInterfaces().add(newInterface);
    compareChanges(umlModel, umlCheckpoint, strategyToTest);
  }
}
