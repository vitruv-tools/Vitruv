package tools.vitruv.testutils

import org.junit.jupiter.api.^extension.BeforeAllCallback
import org.junit.jupiter.api.^extension.ExtensionContext
import org.eclipse.emf.ecore.plugin.EcorePlugin

/**
 * JUnit extension that registers all EMF models that are offered through
 * extension points when running in standalone mode.
 */
class RegisterMetamodelsInStandalone implements BeforeAllCallback {
	override beforeAll(ExtensionContext context) {
		EcorePlugin.ExtensionProcessor.process(null)
	}
}