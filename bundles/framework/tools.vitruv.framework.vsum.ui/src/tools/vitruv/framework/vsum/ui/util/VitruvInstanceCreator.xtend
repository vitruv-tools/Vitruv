package tools.vitruv.framework.vsum.ui.util

import tools.vitruv.framework.tuid.TuidManager
import tools.vitruv.framework.vsum.InternalVirtualModel
import tools.vitruv.framework.domains.VitruvDomain
import tools.vitruv.framework.applications.VitruvApplication
import java.util.Map
import org.eclipse.core.resources.IProject
import tools.vitruv.framework.userinteraction.impl.UserInteractor
import tools.vitruv.framework.monitorededitor.ProjectBuildUtils
import org.eclipse.core.resources.ResourcesPlugin
import tools.vitruv.framework.tests.util.TestUtil
import tools.vitruv.framework.change.processing.ChangePropagationSpecification
import java.util.Set

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
        virtualModel.userInteractor = new UserInteractor();
        for (project : projectToDomains.keySet) {
        	for (domain : projectToDomains.get(project)) {
        		domain.builderApplicator.addToProject(project, virtualModel.folder, domain.fileExtensions.toList);
        		ProjectBuildUtils.issueIncrementalBuild(project, domain.builderApplicator.builderId);
        	}
        	
        }
	}
	
	private def InternalVirtualModel createVirtualModel(String vsumName) {
		val metamodels = this.getDomains();
		val project = ResourcesPlugin.workspace.root.getProject(vsumName);
		project.create(null);
    	project.open(null);
		val virtualModel = TestUtil.createVirtualModel(project.location.toFile, false, metamodels, createChangePropagationSpecifications(),
			new UserInteractor()
		);
		return virtualModel;
	}
	
	private def Iterable<VitruvDomain> getDomains() {
		return projectToDomains.values.flatten.toSet;
	}
	
	private def Iterable<ChangePropagationSpecification> createChangePropagationSpecifications() {
		return applications.map[changePropagationSpecifications].flatten
	}
	
}
