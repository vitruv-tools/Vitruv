package tools.vitruvius.dsls.mirbase.validation;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.osgi.service.resolver.VersionRange;
import org.eclipse.pde.core.plugin.IPluginExtension;
import org.eclipse.pde.core.plugin.IPluginModelBase;
import org.eclipse.pde.core.project.IBundleProjectDescription;
import org.eclipse.pde.core.project.IBundleProjectService;
import org.eclipse.pde.core.project.IRequiredBundleDescription;
import org.eclipse.pde.internal.core.PDECore;
import org.eclipse.pde.internal.core.PluginModelManager;
import org.eclipse.pde.internal.core.WorkspaceModelManager;
import org.eclipse.pde.internal.core.WorkspacePluginModelManager;

// TODO DW: move this functionality to another plugin, if necessary. remove dependencies from this project
@SuppressWarnings("restriction")
public class EclipsePluginHelper {
	public static IProject getProject(Resource resource) {
		if ((resource == null) || (resource.getURI() == null)) {
			return null;
		}
		
		String resourcePlatformString = resource.getURI().toPlatformString(true);
		return ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(resourcePlatformString)).getProject();
	}
	
	public static boolean isPluginProject(IProject project) {
		return WorkspaceModelManager.isPluginProject(project);
	}

	public static IBundleProjectService getBundleProjectService() {
		return (IBundleProjectService) PDECore.getDefault().acquireService(IBundleProjectService.class.getName());
	}
	
	public static IBundleProjectDescription getDescription(IProject project) throws CoreException {
		return getBundleProjectService().getDescription(project);
	}
	
	/**
	 * Does not check version.
	 * @param project
	 * @param bundleName
	 * @return
	 * @throws CoreException 
	 */
	public static boolean hasDependency(IProject project, String bundleName) throws CoreException {
		final IRequiredBundleDescription[] requiredBundles = getDescription(project).getRequiredBundles();
		if (requiredBundles == null)
			return false;
		
		for (IRequiredBundleDescription description : requiredBundles) {
			if (description.getName().equals(bundleName)) {
				return true;
			}
		}
		
		return false;
	}
	
	public static void addDependency(IProject project, String bundleName) throws CoreException {
		final IBundleProjectDescription description = getDescription(project);
		if (hasDependency(project, bundleName)) {
			throw new IllegalArgumentException("Project " + project.getName() + " already has dependency to " + bundleName);
		}
		IRequiredBundleDescription[] requiredBundles = description.getRequiredBundles();
		IRequiredBundleDescription[] newBundles;
		if (requiredBundles != null) {
			newBundles = new IRequiredBundleDescription[requiredBundles.length + 1];
			System.arraycopy(requiredBundles, 0, newBundles, 0, requiredBundles.length);
		} else {
			newBundles = new IRequiredBundleDescription[1];
		}
		newBundles[newBundles.length - 1] = getBundleProjectService().newRequiredBundle(bundleName, null, false, false);
		description.setRequiredBundles(newBundles);
		description.apply(null);
	}
	
	public static IRequiredBundleDescription newRequiredBundle(IBundleProjectService service, String name, VersionRange range, boolean optional, boolean export) {
		return service.newRequiredBundle(name, range, optional, export);
	}
}
