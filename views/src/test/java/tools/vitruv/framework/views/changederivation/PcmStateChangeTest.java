package tools.vitruv.framework.views.changederivation;

import java.io.IOException;
import java.util.stream.IntStream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import tools.vitruv.change.testutils.metamodels.PcmMockupCreators;

/** Tests for state-based change propagation in PCM models. */
class PcmStateChangeTest extends StateChangePropagationTest {

  /**
   * Tests adding a component to the PCM model.
   *
   * @param strategyToTest the strategy to test
   * @throws IOException if an I/O error occurs
   */
  @ParameterizedTest
  @MethodSource("strategiesToTest")
  void testAddComponent(StateBasedChangeResolutionStrategy strategyToTest) throws IOException {
    var newComponent = PcmMockupCreators.pcm.Component();
    newComponent.setName("NewlyAddedComponent");
    getPcmRoot().getComponents().add(newComponent);
    compareChanges(getPcmModel(), getPcmCheckpoint(), strategyToTest);
  }

  /**
   * Tests renaming a component in the PCM model.
   *
   * @param strategyToTest the strategy to test
   * @throws IOException if an I/O error occurs
   */
  @ParameterizedTest
  @MethodSource("strategiesToTest")
  void testRenameComponent(StateBasedChangeResolutionStrategy strategyToTest) throws IOException {
    getPcmRoot().getComponents().get(0).setName("RenamedComponent");
    compareChanges(getPcmModel(), getPcmCheckpoint(), strategyToTest);
  }

  /**
   * Tests deleting a component from the PCM model.
   *
   * @param strategyToTest the strategy to test
   * @throws IOException if an I/O error occurs
   */
  @ParameterizedTest
  @MethodSource("strategiesToTest")
  void testDeleteComponent(StateBasedChangeResolutionStrategy strategyToTest) throws IOException {
    getPcmRoot().getComponents().remove(0);
    compareChanges(getPcmModel(), getPcmCheckpoint(), strategyToTest);
  }

  /**
   * Tests adding a provided interface to a component in the PCM model.
   *
   * @param strategyToTest the strategy to test
   * @throws IOException if an I/O error occurs
   */
  @ParameterizedTest
  @MethodSource("strategiesToTest")
  void testAddProvidedInterface(StateBasedChangeResolutionStrategy strategyToTest)
      throws IOException {
    var newInterface = PcmMockupCreators.pcm.Interface();
    newInterface.setName("NewlyAddedInterface");
    getPcmRoot().getInterfaces().add(PcmMockupCreators.pcm.Interface());
    var newMethod = PcmMockupCreators.pcm.Method();
    newMethod.setName("newMethod");
    newInterface.getMethods().add(newMethod);
    getPcmRoot().getComponents().get(0).setProvidedInterface(newInterface);
    compareChanges(getPcmModel(), getPcmCheckpoint(), strategyToTest);
  }

  /**
   * Tests adding an interface with multiple methods to a component in the PCM model.
   *
   * @param strategyToTest the strategy to test
   * @throws IOException if an I/O error occurs
   */
  @ParameterizedTest
  @MethodSource("strategiesToTest")
  void testInterfaceWithMultipleMethods(StateBasedChangeResolutionStrategy strategyToTest)
      throws IOException {
    var newInterface = PcmMockupCreators.pcm.Interface();
    newInterface.setName("NewlyAddedInterface");
    getPcmRoot().getInterfaces().add(newInterface);
    IntStream.rangeClosed(0, 5)
        .forEach(
            index -> {
              var method = PcmMockupCreators.pcm.Method();
              method.setName("newMethod" + index);
              newInterface.getMethods().add(method);
            });
    getPcmRoot().getComponents().get(0).setProvidedInterface(newInterface);
    compareChanges(getPcmModel(), getPcmCheckpoint(), strategyToTest);
  }

  /**
   * Tests adding different provided interfaces to a component in the PCM model.
   *
   * @param strategyToTest the strategy to test
   * @throws IOException if an I/O error occurs
   */
  @ParameterizedTest
  @MethodSource("strategiesToTest")
  void testAddDifferentProvidedInterface(StateBasedChangeResolutionStrategy strategyToTest)
      throws IOException {
    var firstInterface = PcmMockupCreators.pcm.Interface();
    firstInterface.setName("NewlyAddedInterface");
    var secondInterface = PcmMockupCreators.pcm.Interface();
    secondInterface.setName("NewlyAddedInterface2");
    getPcmRoot().getInterfaces().add(firstInterface);
    getPcmRoot().getInterfaces().add(secondInterface);
    getPcmRoot().getComponents().get(0).setProvidedInterface(firstInterface);
    getPcmRoot().getComponents().get(0).setProvidedInterface(secondInterface);
    compareChanges(getPcmModel(), getPcmCheckpoint(), strategyToTest);
  }

  /**
   * Tests adding multiple interfaces with methods to the PCM model.
   *
   * @param strategyToTest the strategy to test
   * @throws IOException if an I/O error occurs
   */
  @ParameterizedTest
  @MethodSource("strategiesToTest")
  void testAddMultipleInterfaces(StateBasedChangeResolutionStrategy strategyToTest)
      throws IOException {
    IntStream.rangeClosed(1, 3)
        .forEach(
            index -> {
              var iface = PcmMockupCreators.pcm.Interface();
              iface.setName("NewlyAddedInterface" + index);
              getPcmRoot().getInterfaces().add(iface);
            });
    getPcmRoot()
        .getInterfaces()
        .forEach(
            iface -> {
              var method = PcmMockupCreators.pcm.Method();
              method.setName("newMethod");
              iface.getMethods().add(method);
            });
    compareChanges(getPcmModel(), getPcmCheckpoint(), strategyToTest);
  }
}
