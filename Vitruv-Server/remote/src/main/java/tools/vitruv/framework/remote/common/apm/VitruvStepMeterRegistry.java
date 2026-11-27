package tools.vitruv.framework.remote.common.apm;

import io.micrometer.core.instrument.Clock;
import io.micrometer.core.instrument.Meter.Id;
import io.micrometer.core.instrument.Timer;
import io.micrometer.core.instrument.distribution.DistributionStatisticConfig;
import io.micrometer.core.instrument.distribution.pause.PauseDetector;
import io.micrometer.core.instrument.step.StepMeterRegistry;
import io.micrometer.core.instrument.step.StepRegistryConfig;
import io.micrometer.core.instrument.util.NamedThreadFactory;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Provides a specialized {@link StepMeterRegistry} for Vitruvius, which stores measurements in a
 * file.
 */
class VitruvStepMeterRegistry extends StepMeterRegistry {
  private Path output;
  private StepRegistryConfig config;
  private static final Logger LOGGER = LogManager.getLogger(VitruvStepMeterRegistry.class);

  /**
   * Creates a new {@link VitruvStepMeterRegistry}.
   *
   * @param config The step registry configuration.
   * @param clock the clock to use.
   * @param output Path to a file in which all measurements are stored.
   */
  VitruvStepMeterRegistry(StepRegistryConfig config, Clock clock, Path output) {
    super(config, clock);
    this.output = output.toAbsolutePath();
    this.config = config;
    this.start(new NamedThreadFactory("vitruv-tf"));
  }

  @Override
  protected void publish() {
    try (BufferedWriter writer =
        Files.newBufferedWriter(output, StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
      for (var meter : getMeters()) {
        if (meter instanceof SingleMeasureRecordingTimer timer) {
          for (var recording : timer.getRecordings()) {
            writer.append(
                meter.getId().toString()
                    + ","
                    + recording.unit().toMillis(recording.amount())
                    + "\n");
          }
          timer.clear();
        } else {
          for (var measurement : meter.measure()) {
            writer.append(meter.getId().toString() + "," + measurement.getValue() + "\n");
          }
        }
      }
    } catch (IOException e) {
      LOGGER.error("Could not write metrics because: {}", e.getMessage());
    }
  }

  @Override
  protected TimeUnit getBaseTimeUnit() {
    return TimeUnit.MILLISECONDS;
  }

  @Override
  protected Timer newTimer(Id id, DistributionStatisticConfig config, PauseDetector detector) {
    return new SingleMeasureRecordingTimer(
        id, this.clock, config, detector, getBaseTimeUnit(), this.config.step().toMillis(), false);
  }
}
