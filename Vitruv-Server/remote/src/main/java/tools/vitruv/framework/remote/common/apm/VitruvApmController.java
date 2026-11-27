package tools.vitruv.framework.remote.common.apm;

import io.micrometer.core.instrument.Clock;
import io.micrometer.core.instrument.Metrics;
import java.nio.file.Path;

/** This class allows controlling the Vitruvius monitoring. */
public final class VitruvApmController {
  private static VitruvStepMeterRegistry activeRegistry; // Use the same variable name consistently

  /**
   * Constructor to initialize the monitoring (No initialization needed here, since we're using
   * static methods).
   */
  private VitruvApmController() {
    // Private constructor to prevent instantiation
  }

  /**
   * Enables the monitoring for Vitruvius.
   *
   * @param output Path to a file in which all measurements are stored.
   */
  public static void enable(Path output) {
    if (activeRegistry == null) { // Use the correct variable name here
      activeRegistry =
          new VitruvStepMeterRegistry(new VitruvStepRegistryConfig(), Clock.SYSTEM, output);
      Metrics.globalRegistry.add(activeRegistry); // Register the registry globally
    }
  }

  /** Disables the monitoring for Vitruvius. */
  public static void disable() {
    if (activeRegistry != null) {
      activeRegistry.stop(); // Stop the active registry
      Metrics.globalRegistry.remove(activeRegistry); // Remove the registry from global registry
      activeRegistry = null; // Nullify the reference to indicate it's disabled
    }
  }
}
