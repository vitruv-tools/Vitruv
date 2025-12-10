package tools.vitruv.framework.views.changederivation;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import org.eclipse.emf.ecore.resource.Resource;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/** Tests for edge cases in state-based change propagation. */
public class EdgeCaseStateChangeTest extends StateChangePropagationTest {

  /** Tests the comparison of two states with no changes for the uml mockup model. */
  @ParameterizedTest
  @MethodSource("strategiesToTest")
  public void testNoUmlChange(StateBasedChangeResolutionStrategy strategyToTest)
      throws IOException {
    compareChanges(getUmlModel(), getUmlCheckpoint(), strategyToTest);
  }

  /** Tests the comparison of two states with no changes for the pcm mockup model. */
  @ParameterizedTest
  @MethodSource("strategiesToTest")
  public void testNoPcmChange(StateBasedChangeResolutionStrategy strategyToTest)
      throws IOException {
    compareChanges(getUmlModel(), getUmlCheckpoint(), strategyToTest);
  }

  /** Tests invalid input: null instead of state resources. */
  @ParameterizedTest
  @MethodSource("strategiesToTest")
  public void testNullResources(StateBasedChangeResolutionStrategy strategyToTest) {
    Resource nullResource = null;
    Throwable exception1 =
        assertThrows(
            IllegalArgumentException.class,
            () -> strategyToTest.getChangeSequenceForCreated(nullResource));
    Throwable exception2 =
        assertThrows(
            IllegalArgumentException.class,
            () -> strategyToTest.getChangeSequenceBetween(nullResource, nullResource));
    Throwable exception3 =
        assertThrows(
            IllegalArgumentException.class,
            () -> strategyToTest.getChangeSequenceForDeleted(nullResource));
  }
}
