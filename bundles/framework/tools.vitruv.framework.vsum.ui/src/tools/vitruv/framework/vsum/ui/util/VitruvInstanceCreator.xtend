package tools.vitruv.framework.vsum.ui.util

import tools.vitruv.framework.vsum.InternalVirtualModel
import tools.vitruv.framework.domains.VitruvDomain
import tools.vitruv.framework.applications.VitruvApplication
import java.util.Map
import org.eclipse.core.resources.IProject
import org.eclipse.core.resources.ResourcesPlugin
import tools.vitruv.framework.change.processing.ChangePropagationSpecification
import java.util.Set
import tools.vitruv.framework.userinteraction.UserInteractionFactory
import org.apache.log4j.Logger
import tools.vitruv.framework.domains.ui.builder.VitruvProjectBuilderApplicator
import tools.vitruv.framework.vsum.VirtualModelBuilder

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
		val virtualModel = createVirtualModel(name);
		for (project : projectToDomains.keySet) {
			for (domain : projectToDomains.get(project)) {
				try {
					// TODO HK Provide dialog option for enabling automatic propagation
					VitruvProjectBuilderApplicator.getApplicatorsForVitruvDomain(domain).forEach[
						setPropagateAfterBuild(true).addBuilder(project, virtualModel.folder, domain.fileExtensions.toSet)
					]
				} catch (IllegalStateException e) {
					LOGGER.error('''Could not initialize V-SUM project for project: «project.name»''')
					return false
				}
			}
		}
		return true
	}

	private def InternalVirtualModel createVirtualModel(String vsumName) {
		val domains = this.getDomains()
		val project = ResourcesPlugin.workspace.root.getProject(vsumName)
		project.create(null)
		project.open(null)
		return new VirtualModelBuilder()
			.withStorageFolder(project.location.toFile)
			.withUserInteractor(UserInteractionFactory.instance.createDialogUserInteractor())
			.withDomains(domains)
			.withChangePropagationSpecifications(createChangePropagationSpecifications())
			.buildAndInitialize()
	}

	private def Iterable<VitruvDomain> getDomains() {
		return projectToDomains.values.flatten.toSet
	}

	private def Iterable<ChangePropagationSpecification> createChangePropagationSpecifications() {
		return applications.map[changePropagationSpecifications].flatten
	}

}
