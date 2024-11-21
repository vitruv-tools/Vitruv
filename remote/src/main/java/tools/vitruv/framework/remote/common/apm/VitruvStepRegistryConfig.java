package tools.vitruv.framework.remote.common.apm;

import io.micrometer.core.instrument.step.StepRegistryConfig;

class VitruvStepRegistryConfig implements StepRegistryConfig {
	@Override
	public String get(String arg0) {
		return null;
	}

	@Override
	public String prefix() {
		return "vitruv-step-config";
	}
}
