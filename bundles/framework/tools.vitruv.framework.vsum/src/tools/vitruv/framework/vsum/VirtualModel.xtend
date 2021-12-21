package tools.vitruv.framework.vsum

import java.util.Collection
import java.nio.file.Path
import tools.vitruv.framework.vsum.models.ChangeableModelRepository
import tools.vitruv.framework.vsum.views.ViewType
import org.eclipse.emf.ecore.resource.ResourceSet
import com.google.common.annotations.Beta

interface VirtualModel extends ChangeableModelRepository {
	def Path getFolder()

	def boolean isCompatible(ViewType viewType) {
		viewTypes.contains(viewType) // TODO TS What do we understand as a ViewType being compatible with a VSUM?
	}

	def Collection<ViewType> getViewTypes()

	/**
	 * IMPORTANT: This method is only temporary, and should be replaced a inversion of control based mechanism to supply
	 * viewtypes with the necessary resources for view creation.
	 */
	@Deprecated
	@Beta
	def ResourceSet getResourceSet() {
	    return null // prevents test models to be adapted
	}
}
