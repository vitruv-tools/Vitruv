package tools.vitruv.framework.vsum

import java.nio.file.Path
import tools.vitruv.change.composite.propagation.ChangeableModelRepository
import tools.vitruv.framework.views.ViewProvider
import tools.vitruv.framework.views.ViewTypeProvider
import tools.vitruv.change.propagation.ChangePropagationMode

interface VirtualModel extends ChangeableModelRepository, ViewProvider, ViewTypeProvider {
	def Path getFolder()

	/**
	 * Defines how changes are propagated when passed to {@link #propagateChange(VitruviusChange)}.
	 * By default, {@link ChangePropagationMode#TRANSITIVE_CYCLIC} is used, i.e., changes are
	 * transitively propagated until no further changes are produced.
	 */
	def void setChangePropagationMode(ChangePropagationMode changePropagationMode)
}
