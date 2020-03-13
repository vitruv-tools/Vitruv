package tools.vitruv.dsls.commonalities.generator

import com.google.inject.Inject
import com.google.inject.Injector

package class InjectingFactoryBase {

	@Inject Injector injector

	package new() {
	}

	def protected <T> T injectMembers(T object) {
		injector.injectMembers(object)
		return object
	}
}
