package tools.vitruv.framework.vsum

import java.nio.file.Path
import tools.vitruv.framework.vsum.views.ViewTypeProvider
import tools.vitruv.framework.vsum.models.ChangeableModelRepository

interface VirtualModel extends ChangeableModelRepository, ViewTypeProvider {
	def Path getFolder()
}
