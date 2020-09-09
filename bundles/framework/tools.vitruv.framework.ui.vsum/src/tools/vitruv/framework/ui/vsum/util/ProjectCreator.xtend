package tools.vitruv.framework.ui.vsum.util

import static edu.kit.ipd.sdq.commons.util.org.eclipse.core.resources.IProjectUtil.*

class ProjectCreator {
	val String projectName;
	
	new(String projectName) {
		this.projectName = projectName;
	}
	
	def void createProject() {
		createJavaProject(projectName);
	}
}
