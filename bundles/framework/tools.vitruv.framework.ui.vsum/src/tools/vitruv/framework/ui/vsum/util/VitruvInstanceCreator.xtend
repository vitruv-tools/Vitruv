package tools.vitruv.framework.ui.vsum.util

import tools.vitruv.framework.tuid.TuidManager
import tools.vitruv.framework.vsum.InternalVirtualModel
import tools.vitruv.framework.domains.VitruvDomain
import tools.vitruv.framework.applications.VitruvApplication
import java.util.Map
import org.eclipse.core.resources.IProject
import org.eclipse.core.resources.ResourcesPlugin
import tools.vitruv.framework.change.processing.ChangePropagationSpecification
import java.util.Set
import tools.vitruv.framework.vsum.VirtualModelConfigurationBuilder
import tools.vitruv.framework.vsum.VirtualModelImpl
import tools.vitruv.framework.userinteraction.UserInteractionFactory
import org.apache.log4j.Logger
import static tools.vitruv.framework.util.ProjectBuildUtils.buildIncrementally

class VitruvInstanceCreator {
	static val LOGGER = Logger.getLogger(VitruvInstanceCreator)

	val Map<IProject, ? extends Set<VitruvDomain>> projectToDomains
	val Iterable<VitruvApplication> applications
	val String name

	new(String name, Map<IProject, Set<VitruvDomain>> projectToDomains, Iterable<VitruvApplication> applications) {
		this.projectToDomains = projectToDomains
		this.applications = applications
		this.name = name
	}

	def boolean createVsumProject() {
		TuidManager.instance.reinitialize();
		val virtualModel = createVirtualModel(name);
		for (project : projectToDomains.keySet) {
			for (domain : projectToDomains.get(project)) {
				try {
					// TODO HK Provide dialog option for enabling automatic propagation
					domain.builderApplicator.setPropagateAfterBuild(true).addBuilder(project, virtualModel.folder, domain.fileExtensions.toSet)
					buildIncrementally(project, domain.builderApplicator.builderId)
					return true
				} catch (IllegalStateException e) {
					LOGGER.error('''Could not initialize V-SUM project for project: «project.name»''')
					return false
				}
			}

		}
	}

	private def InternalVirtualModel createVirtualModel(String vsumName) {
		val domains = this.getDomains()
		val project = ResourcesPlugin.workspace.root.getProject(vsumName)
		project.create(null)
		project.open(null)
		val configuration = VirtualModelConfigurationBuilder.create().addDomains(domains).
			addChangePropagationSpecifications(createChangePropagationSpecifications).toConfiguration()
		val virtualModel = new VirtualModelImpl(project.location.toFile,
			UserInteractionFactory.instance.createDialogUserInteractor(), configuration)
		return virtualModel
	}

	private def Iterable<VitruvDomain> getDomains() {
		return projectToDomains.values.flatten.toSet
	}

	private def Iterable<ChangePropagationSpecification> createChangePropagationSpecifications() {
		return applications.map[changePropagationSpecifications].flatten
	}

}
