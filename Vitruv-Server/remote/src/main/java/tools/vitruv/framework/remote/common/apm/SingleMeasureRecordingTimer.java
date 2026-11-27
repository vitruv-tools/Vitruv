package tools.vitruv.framework.remote.common.apm;

import io.micrometer.core.instrument.Clock;
import io.micrometer.core.instrument.distribution.DistributionStatisticConfig;
import io.micrometer.core.instrument.distribution.pause.PauseDetector;
import io.micrometer.core.instrument.step.StepTimer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/** Provides a specialized {@link StepTimer}, which records every single measurement. */
public class SingleMeasureRecordingTimer extends StepTimer {
  public static record SingleRecordedMeasure(long amount, TimeUnit unit) {}

  private final List<SingleRecordedMeasure> recordings = new ArrayList<>();

  /**
   * Creates a new SingleMeasureRecordingTimer.
   *
   * @param id the id of the timer
   * @param clock the clock to use
   * @param distributionStatisticConfig the distribution statistic configuration
   * @param pauseDetector the pause detector
   * @param baseTimeUnit the base time unit
   * @param stepDurationMillis the step duration in milliseconds
   * @param supportsAggregablePercentiles whether the timer supports aggregable percentiles
   */
  public SingleMeasureRecordingTimer(
      Id id,
      Clock clock,
      DistributionStatisticConfig distributionStatisticConfig,
      PauseDetector pauseDetector,
      TimeUnit baseTimeUnit,
      long stepDurationMillis,
      boolean supportsAggregablePercentiles) {
    super(
        id,
        clock,
        distributionStatisticConfig,
        pauseDetector,
        baseTimeUnit,
        stepDurationMillis,
        supportsAggregablePercentiles);
  }

  @Override
  protected void recordNonNegative(long amount, TimeUnit unit) {
    super.recordNonNegative(amount, unit);
    recordings.add(new SingleRecordedMeasure(amount, unit));
  }

  /**
   * Gets the list of single recorded measurements.
   *
   * @return the list of single recorded measurements
   */
  public List<SingleRecordedMeasure> getRecordings() {
    return List.copyOf(recordings);
  }

  /** Clears the recorded measurements. */
  public void clear() {
    recordings.clear();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof SingleMeasureRecordingTimer)) {
      return false;
    }
    return super.equals(obj);
  }

  @Override
  public int hashCode() {
    return super.hashCode();
  }
}
