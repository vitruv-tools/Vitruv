package tools.vitruv.dsls.common.ui

import org.eclipse.emf.ecore.EObject
import org.eclipse.core.resources.ResourcesPlugin
import org.eclipse.core.runtime.Path
import org.eclipse.jdt.core.JavaCore
import org.eclipse.core.resources.IProject
import org.eclipse.pde.core.project.IBundleProjectDescription
import org.eclipse.pde.core.project.IBundleProjectService
import org.eclipse.pde.internal.core.PDECore
import edu.kit.ipd.sdq.activextendannotations.Lazy
import org.eclipse.emf.common.util.URI
import org.eclipse.jdt.core.IJavaProject
import org.eclipse.xtext.ui.editor.model.edit.IModificationContext

class ProjectAccess {
	@Lazy(PACKAGE)
	static val IBundleProjectService projectBundleService = PDECore.^default.acquireService(IBundleProjectService)

	private new() {
	}

	def static IProject getEclipseProject(IModificationContext context) {
		context.xtextDocument.resourceURI.eclipseProject
	}

	def static IProject getEclipseProject(EObject eObject) {
		eObject.eResource.URI.eclipseProject
	}

	def static IProject getEclipseProject(URI uri) {
		new Path(uri.toPlatformString(true)).eclipseProject
	}

	def static IProject getEclipseProject(Path path) {
		ResourcesPlugin.workspace.root.getFile(path).project
	}

	/**
	 * Returns the project as a Java project. There is no check on whether the project is actually a Java project,
	 * use {@link #isJavaProject} for that!
	 */
	def static IJavaProject getJavaProject(IProject project) {
		JavaCore.create(project)
	}

	/**
	 * Returns the project as a plugin project. There is no check on whether the project is actually a plugin project,
	 * use {@link #isPluginProject} for that!
	 */
	def static IBundleProjectDescription getPluginProject(IProject project) {
		projectBundleService.getDescription(project)
	}

	def static getIsPluginProject(IProject project) {
		project.isNatureEnabled(IBundleProjectDescription.PLUGIN_NATURE)
	}

	def static getIsJavaProject(IProject project) {
		project.isNatureEnabled(JavaCore.NATURE_ID)
	}
}
