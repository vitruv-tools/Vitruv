package tools.vitruv.framework.ui.vsum.util

import static edu.kit.ipd.sdq.commons.util.org.eclipse.core.resources.IProjectUtil.*

class ProjectCreator {
	private val String projectName;
	
	new(String projectName) {
		this.projectName = projectName;
	}
	
	public def void createProject() {
		createJavaProject(projectName);
	}
}
