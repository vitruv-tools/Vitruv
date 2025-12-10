package tools.vitruv.framework.views.changederivation;

import java.io.IOException;
import java.util.stream.IntStream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import tools.vitruv.change.testutils.metamodels.PcmMockupCreators;

/** Tests for state-based change propagation in PCM models. */
public class PcmStateChangeTest extends StateChangePropagationTest {

  @ParameterizedTest
  @MethodSource("strategiesToTest")
  public void testAddComponent(StateBasedChangeResolutionStrategy strategyToTest)
      throws IOException {
    var newComponent = PcmMockupCreators.pcm.Component();
    newComponent.setName("NewlyAddedComponent");
    pcmRoot.getComponents().add(newComponent);
    compareChanges(pcmModel, pcmCheckpoint, strategyToTest);
  }

  @ParameterizedTest
  @MethodSource("strategiesToTest")
  public void testRenameComponent(StateBasedChangeResolutionStrategy strategyToTest)
      throws IOException {
    pcmRoot.getComponents().get(0).setName("RenamedComponent");
    compareChanges(pcmModel, pcmCheckpoint, strategyToTest);
  }

  @ParameterizedTest
  @MethodSource("strategiesToTest")
  public void testDeleteComponent(StateBasedChangeResolutionStrategy strategyToTest)
      throws IOException {
    pcmRoot.getComponents().remove(0);
    compareChanges(pcmModel, pcmCheckpoint, strategyToTest);
  }

  @ParameterizedTest
  @MethodSource("strategiesToTest")
  public void testAddProvidedInterface(StateBasedChangeResolutionStrategy strategyToTest)
      throws IOException {
    var newInterface = PcmMockupCreators.pcm.Interface();
    newInterface.setName("NewlyAddedInterface");
    pcmRoot.getInterfaces().add(PcmMockupCreators.pcm.Interface());
    var newMethod = PcmMockupCreators.pcm.Method();
    newMethod.setName("newMethod");
    newInterface.getMethods().add(newMethod);
    pcmRoot.getComponents().get(0).setProvidedInterface(newInterface);
    compareChanges(pcmModel, pcmCheckpoint, strategyToTest);
  }

  @ParameterizedTest
  @MethodSource("strategiesToTest")
  public void testInterfaceWithMultipleMethods(StateBasedChangeResolutionStrategy strategyToTest)
      throws IOException {
    var newInterface = PcmMockupCreators.pcm.Interface();
    newInterface.setName("NewlyAddedInterface");
    pcmRoot.getInterfaces().add(newInterface);
    IntStream.rangeClosed(0, 5)
        .forEach(
            index -> {
              var method = PcmMockupCreators.pcm.Method();
              method.setName("newMethod" + index);
              newInterface.getMethods().add(method);
            });
    pcmRoot.getComponents().get(0).setProvidedInterface(newInterface);
    compareChanges(pcmModel, pcmCheckpoint, strategyToTest);
  }

  @ParameterizedTest
  @MethodSource("strategiesToTest")
  public void testAddDifferentProvidedInterface(StateBasedChangeResolutionStrategy strategyToTest)
      throws IOException {
    var firstInterface = PcmMockupCreators.pcm.Interface();
    firstInterface.setName("NewlyAddedInterface");
    var secondInterface = PcmMockupCreators.pcm.Interface();
    secondInterface.setName("NewlyAddedInterface2");
    pcmRoot.getInterfaces().add(firstInterface);
    pcmRoot.getInterfaces().add(secondInterface);
    pcmRoot.getComponents().get(0).setProvidedInterface(firstInterface);
    pcmRoot.getComponents().get(0).setProvidedInterface(secondInterface);
    compareChanges(pcmModel, pcmCheckpoint, strategyToTest);
  }

  @ParameterizedTest
  @MethodSource("strategiesToTest")
  public void testAddMultipleInterfaces(StateBasedChangeResolutionStrategy strategyToTest)
      throws IOException {
    IntStream.rangeClosed(1, 3)
        .forEach(
            index -> {
              var iface = PcmMockupCreators.pcm.Interface();
              iface.setName("NewlyAddedInterface" + index);
              pcmRoot.getInterfaces().add(iface);
            });
    pcmRoot
        .getInterfaces()
        .forEach(
            iface -> {
              var method = PcmMockupCreators.pcm.Method();
              method.setName("newMethod");
              iface.getMethods().add(method);
            });
    compareChanges(pcmModel, pcmCheckpoint, strategyToTest);
  }
}
