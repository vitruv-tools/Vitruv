package tools.vitruv.framework.vsum

import java.nio.file.Path
import tools.vitruv.change.composite.propagation.ChangeableModelRepository
import tools.vitruv.framework.views.ViewProvider
import tools.vitruv.framework.views.ViewTypeProvider

interface VirtualModel extends ChangeableModelRepository, ViewProvider, ViewTypeProvider {
	def Path getFolder()
	
	/**
	 * Enables or disables transitive propagation of changes when passed to {@link #propagateChange(VitruviusChange)}.
	 * Transitive propagation is enabled by default.
	 */
	def void setTransitivePropagationEnabled(boolean enabled)
}
