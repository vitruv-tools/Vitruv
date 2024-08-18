package tools.vitruv.framework.remote.common.apm;

import java.nio.file.Path;

import io.micrometer.core.instrument.Clock;
import io.micrometer.core.instrument.Metrics;

public class VitruvApmController {
	private static VitruvStepMeterRegistry ACTIVE_REGISTRY;
	
	public static void enable(Path output) {
		if (ACTIVE_REGISTRY == null) {
			ACTIVE_REGISTRY = new VitruvStepMeterRegistry(new VitruvStepRegistryConfig(), Clock.SYSTEM, output);
			Metrics.globalRegistry.add(ACTIVE_REGISTRY);
		}
	}
	
	public static void disable() {
		if (ACTIVE_REGISTRY != null) {
			ACTIVE_REGISTRY.stop();
			Metrics.globalRegistry.remove(ACTIVE_REGISTRY);
			ACTIVE_REGISTRY = null;
		}
	}
}
