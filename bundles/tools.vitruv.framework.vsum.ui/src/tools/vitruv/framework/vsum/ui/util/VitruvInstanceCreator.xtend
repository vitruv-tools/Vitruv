package tools.vitruv.framework.vsum.ui.util

import tools.vitruv.framework.applications.VitruvApplication
import java.util.Map
import org.eclipse.core.resources.IProject
import org.eclipse.core.resources.ResourcesPlugin
import tools.vitruv.change.propagation.ChangePropagationSpecification
import java.util.Set
import tools.vitruv.change.interaction.UserInteractionFactory
import org.apache.log4j.Logger
import tools.vitruv.framework.domains.ui.builder.VitruvProjectBuilderApplicator
import tools.vitruv.framework.vsum.VirtualModelBuilder
import tools.vitruv.framework.vsum.VirtualModel

class VitruvInstanceCreator {
	static val LOGGER = Logger.getLogger(VitruvInstanceCreator)

	val Map<IProject, ? extends Set<VitruvProjectBuilderApplicator>> projectToApplicators
	val Iterable<VitruvApplication> applications
	val String name

	new(String name, Map<IProject, Set<VitruvProjectBuilderApplicator>> projectToApplicators, Iterable<VitruvApplication> applications) {
		this.projectToApplicators = projectToApplicators
		this.applications = applications
		this.name = name
	}

	def boolean createVsumProject() {
		val virtualModel = createVirtualModel(name);
		for (project : projectToApplicators.keySet) {
			for (applicator : projectToApplicators.get(project)) {
				try {
					// TODO HK Provide dialog option for enabling automatic propagation
					applicator.setPropagateAfterBuild(true).addBuilder(project, virtualModel.folder, emptySet)
				} catch (IllegalStateException e) {
					LOGGER.error('''Could not initialize V-SUM project for project: «project.name»''')
					return false
				}
			}
		}
		return true
	}

	private def VirtualModel createVirtualModel(String vsumName) {
		val project = ResourcesPlugin.workspace.root.getProject(vsumName)
		project.create(null)
		project.open(null)
		return new VirtualModelBuilder()
			.withStorageFolder(project.location.toFile)
			.withUserInteractor(UserInteractionFactory.instance.createDialogUserInteractor())
			.withChangePropagationSpecifications(createChangePropagationSpecifications())
			.buildAndInitialize()
	}

	private def Iterable<ChangePropagationSpecification> createChangePropagationSpecifications() {
		return applications.map[changePropagationSpecifications].flatten
	}

}
