package tools.vitruv.testutils

import org.junit.jupiter.api.^extension.BeforeAllCallback
import org.junit.jupiter.api.^extension.BeforeEachCallback
import org.junit.jupiter.api.^extension.ExtensionContext
import org.junit.jupiter.api.^extension.ExtensionContext.Store.CloseableResource
import org.junit.jupiter.api.^extension.ExtensionContext.Namespace
import static edu.kit.ipd.sdq.commons.util.org.eclipse.core.resources.IWorkspaceUtil.turnOffAutoBuildIfOn
import static edu.kit.ipd.sdq.commons.util.org.eclipse.core.resources.IWorkspaceUtil.turnOnAutoBuild
import org.eclipse.core.resources.ResourcesPlugin

/**
 * Disables automatic building in an Eclipse workspace during the test, and sets it back
 * to its old value afterwards. Disables automatic building both before all tests and
 * before each test. That means that @BeforeAll
 */
class DisableAutoBuild implements BeforeAllCallback, BeforeEachCallback {
	override beforeAll(ExtensionContext context) throws Exception {
		disableAutoBuilding(context)
	}
	
	override beforeEach(ExtensionContext context) throws Exception {
		disableAutoBuilding(context)
	}
	
	def private disableAutoBuilding(ExtensionContext context) {
		if (turnOffAutoBuildIfOn(ResourcesPlugin.workspace)) {
			context.getStore(Namespace.GLOBAL).put(EnableAutoBuildGuard.name, new EnableAutoBuildGuard)
		}
	}
	
	private static class EnableAutoBuildGuard implements CloseableResource {
		override close() throws Throwable {
			turnOnAutoBuild(ResourcesPlugin.workspace)
		}
	}
}