package tools.vitruv.framework.vsum

import java.nio.file.Path
import tools.vitruv.framework.change.propagation.ChangeableModelRepository
import tools.vitruv.framework.views.ViewProvider
import tools.vitruv.framework.views.ViewTypeProvider

interface VirtualModel extends ChangeableModelRepository, ViewProvider, ViewTypeProvider {
	def Path getFolder()
}
