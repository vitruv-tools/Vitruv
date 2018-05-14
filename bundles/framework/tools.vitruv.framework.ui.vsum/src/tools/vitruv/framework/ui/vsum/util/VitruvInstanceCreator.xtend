package tools.vitruv.framework.ui.vsum.util

import tools.vitruv.framework.tuid.TuidManager
import tools.vitruv.framework.vsum.InternalVirtualModel
import tools.vitruv.framework.domains.VitruvDomain
import tools.vitruv.framework.applications.VitruvApplication
import java.util.Map
import org.eclipse.core.resources.IProject
import tools.vitruv.framework.ui.monitorededitor.ProjectBuildUtils
import org.eclipse.core.resources.ResourcesPlugin
import tools.vitruv.framework.change.processing.ChangePropagationSpecification
import java.util.Set
import tools.vitruv.framework.vsum.VirtualModelConfigurationBuilder
import tools.vitruv.framework.vsum.VirtualModelImpl
import tools.vitruv.framework.userinteraction.impl.UserInteractorImpl

class VitruvInstanceCreator {
	private final Map<IProject, ? extends Set<VitruvDomain>> projectToDomains;
	private final Iterable<VitruvApplication> applications;
	private final String name;
	
	new(String name, Map<IProject, Set<VitruvDomain>> projectToDomains, Iterable<VitruvApplication> applications) {
		this.projectToDomains = projectToDomains;
		this.applications = applications;
		this.name = name;
	}
	
	public def void createVsumProject() {
		TuidManager.instance.reinitialize();
        val virtualModel = createVirtualModel(name);
        virtualModel.userInteractor = new UserInteractorImpl();
        for (project : projectToDomains.keySet) {
        	for (domain : projectToDomains.get(project)) {
        		domain.builderApplicator.addToProject(project, virtualModel.folder, domain.fileExtensions.toList);
        		ProjectBuildUtils.issueIncrementalBuild(project, domain.builderApplicator.builderId);
        	}
        	
        }
	}
	
	private def InternalVirtualModel createVirtualModel(String vsumName) {
		val domains = this.getDomains();
		val project = ResourcesPlugin.workspace.root.getProject(vsumName);
		project.create(null);
    	project.open(null);
    	val configuration = VirtualModelConfigurationBuilder.create()
    		.addDomains(domains)
    		.addChangePropagationSpecifications(createChangePropagationSpecifications)
    		.toConfiguration();
		val virtualModel = new VirtualModelImpl(project.location.toFile, new UserInteractorImpl(), configuration);
		return virtualModel;
	}
	
	private def Iterable<VitruvDomain> getDomains() {
		return projectToDomains.values.flatten.toSet;
	}
	
	private def Iterable<ChangePropagationSpecification> createChangePropagationSpecifications() {
		return applications.map[changePropagationSpecifications].flatten
	}
	
}
