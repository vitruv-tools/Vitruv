package tools.vitruv.framework.vsum

import java.nio.file.Path
import tools.vitruv.framework.vsum.views.ViewTypeProvider
import tools.vitruv.framework.vsum.models.ChangeableModelRepository
import org.eclipse.emf.ecore.resource.ResourceSet
import com.google.common.annotations.Beta

interface VirtualModel extends ChangeableModelRepository, ViewTypeProvider {
	def Path getFolder()

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
