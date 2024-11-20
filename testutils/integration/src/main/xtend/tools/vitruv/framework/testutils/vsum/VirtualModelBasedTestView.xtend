package tools.vitruv.framework.testutils.integration

import tools.vitruv.framework.vsum.VirtualModel
import tools.vitruv.change.testutils.views.TestView

/**
 * A {@link TestView} that uses a {@link VirtualModel} for model management
 * and change propagation.
 */
interface VirtualModelBasedTestView extends TestView, AutoCloseable {
	def VirtualModel getVirtualModel()
}
