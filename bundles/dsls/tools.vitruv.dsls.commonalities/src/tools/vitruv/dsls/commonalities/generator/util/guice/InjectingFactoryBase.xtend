package tools.vitruv.dsls.commonalities.generator.util.guice

import com.google.inject.Inject
import com.google.inject.Injector

class InjectingFactoryBase {

	@Inject Injector injector

	new() {
	}

	protected def <T> T injectMembers(T object) {
		injector.injectMembers(object)
		return object
	}
}
