package tools.vitruv.framework.remote.common.apm;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.micrometer.core.instrument.Clock;
import io.micrometer.core.instrument.distribution.DistributionStatisticConfig;
import io.micrometer.core.instrument.distribution.pause.PauseDetector;
import io.micrometer.core.instrument.step.StepTimer;

/**
 * Provides a specialized {@link StepTimer}, which records every single measurement.
 */
public class SingleMeasureRecordingTimer extends StepTimer {
	public static record SingleRecordedMeasure(long amount, TimeUnit unit) {}
	
	private List<SingleRecordedMeasure> recordings = new ArrayList<>();
	
	public SingleMeasureRecordingTimer(Id id, Clock clock, DistributionStatisticConfig distributionStatisticConfig,
			PauseDetector pauseDetector, TimeUnit baseTimeUnit, long stepDurationMillis, boolean supportsAggregablePercentiles) {
		super(id, clock, distributionStatisticConfig, pauseDetector, baseTimeUnit, stepDurationMillis, supportsAggregablePercentiles);
	}

	@Override
	protected void recordNonNegative(long amount, TimeUnit unit) {
		super.recordNonNegative(amount, unit);
		recordings.add(new SingleRecordedMeasure(amount, unit));
	}
	
	public List<SingleRecordedMeasure> getRecordings() {
		return List.copyOf(recordings);
	}
	
	public void clear() {
		recordings.clear();
	}
}
