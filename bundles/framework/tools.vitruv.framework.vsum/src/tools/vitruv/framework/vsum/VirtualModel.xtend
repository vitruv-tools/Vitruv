package tools.vitruv.framework.vsum

import java.nio.file.Path
import tools.vitruv.framework.vsum.views.ViewTypeProvider
import tools.vitruv.framework.vsum.models.ChangeableModelRepository
import tools.vitruv.framework.vsum.views.ViewProvider

interface VirtualModel extends ChangeableModelRepository, ViewProvider, ViewTypeProvider {
	def Path getFolder()
}
