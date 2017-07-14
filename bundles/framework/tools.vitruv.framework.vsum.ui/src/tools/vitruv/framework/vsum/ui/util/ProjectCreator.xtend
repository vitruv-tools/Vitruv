package tools.vitruv.framework.vsum.ui.util

import tools.vitruv.framework.tests.util.TestUtil

class ProjectCreator {
	private val String projectName;
	
	new(String projectName) {
		this.projectName = projectName;
	}
	
	public def createProject() {
		TestUtil.createPlatformProject(projectName, false);
	}
}
