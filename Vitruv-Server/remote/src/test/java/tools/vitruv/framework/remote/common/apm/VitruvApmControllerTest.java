package tools.vitruv.framework.remote.common.apm;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.lang.reflect.Field;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class VitruvApmControllerTest {

  @AfterEach
  void tearDown() throws Exception {
    // Ensure monitoring is disabled after each test to maintain isolation
    VitruvApmController.disable();
    resetActiveRegistry(); // Reset the private field for a clean state
  }

  // Reflection helper method to access the private field
  private VitruvStepMeterRegistry getActiveRegistry() throws Exception {
    Field field = VitruvApmController.class.getDeclaredField("activeRegistry");
    field.setAccessible(true); // Allow access to private field
    return (VitruvStepMeterRegistry) field.get(null); // Get the static field value
  }

  // Reset the private field for a clean state
  private void resetActiveRegistry() throws Exception {
    Field field = VitruvApmController.class.getDeclaredField("activeRegistry");
    field.setAccessible(true);
    field.set(null, null); // Set the static field to null
  }

  @Test
  void testEnableMonitoring() throws Exception {
    Path outputPath = Paths.get("monitoring.log");

    // Ensure monitoring is disabled initially
    assertNull(getActiveRegistry(), "Registry should be null before enabling monitoring");

    // Enable monitoring
    VitruvApmController.enable(outputPath);

    // Ensure the registry is initialized
    assertNotNull(getActiveRegistry(), "Registry should be initialized after enabling monitoring");
  }

  @Test
  void testDisableMonitoring() throws Exception {
    Path outputPath = Paths.get("monitoring.log");

    // Enable monitoring first
    VitruvApmController.enable(outputPath);
    assertNotNull(getActiveRegistry(), "Registry should be initialized");

    // Disable monitoring
    VitruvApmController.disable();

    // Ensure the registry is null after disabling
    assertNull(getActiveRegistry(), "Registry should be null after disabling monitoring");
  }

  @Test
  void testEnableMultipleTimesDoesNotCreateNewInstance() throws Exception {
    Path outputPath = Paths.get("monitoring.log");

    // Enable monitoring first time
    VitruvApmController.enable(outputPath);
    VitruvStepMeterRegistry firstInstance = getActiveRegistry();

    // Enable monitoring second time
    VitruvApmController.enable(outputPath);
    VitruvStepMeterRegistry secondInstance = getActiveRegistry();

    // The same instance should be used
    assertSame(
        firstInstance,
        secondInstance,
        "Registry instance should be the same when enabling multiple times");
  }
}
