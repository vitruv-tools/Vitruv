package tools.vitruv.framework.remote.common.apm;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.TimeUnit;

import io.micrometer.core.instrument.Clock;
import io.micrometer.core.instrument.Timer;
import io.micrometer.core.instrument.step.StepMeterRegistry;
import io.micrometer.core.instrument.step.StepRegistryConfig;
import io.micrometer.core.instrument.util.NamedThreadFactory;

class VitruvStepMeterRegistry extends StepMeterRegistry {
	private Path output;
	
	VitruvStepMeterRegistry(StepRegistryConfig config, Clock clock, Path output) {
		super(config, clock);
		this.output = output.toAbsolutePath();
		this.start(new NamedThreadFactory("vitruv-tf"));
	}

	@Override
	protected void publish() {
		try (BufferedWriter writer = Files.newBufferedWriter(output, StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
			for (var meter : getMeters()) {
				for (var measurement : meter.measure()) {
					writer.append(meter.getId().toString() + ", " + measurement.getValue() + "\n");
				}
			}
		} catch (IOException e) {
			System.err.println("Could not write metrics because: " + e.getMessage());
		}
	}

	@Override
	protected TimeUnit getBaseTimeUnit() {
		return TimeUnit.MILLISECONDS;
	}
}
