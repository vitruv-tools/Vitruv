package tools.vitruv.dsls.mirbase.validation;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.osgi.service.resolver.VersionRange;
import org.eclipse.pde.core.project.IBundleProjectDescription;
import org.eclipse.pde.core.project.IBundleProjectService;
import org.eclipse.pde.core.project.IRequiredBundleDescription;
import org.eclipse.pde.internal.core.PDECore;
import org.eclipse.pde.internal.core.WorkspaceModelManager;

import com.google.common.collect.Lists;

import java.lang.reflect.*;
import java.util.Optional;

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
		// The acquireService method signature was changes in 2018 from expecting the class name as string
		// to expecting the class itself. To avoid version incompatibilities, we employ reflection to
		// invoke the correct method version, depending on which is available.
		try {
			Optional<Method> potentialAcquireMethod = Lists.newArrayList(PDECore.class.getMethods()).stream().filter(method -> method.getName().equals("acquireService")).findFirst();
			if (!potentialAcquireMethod.isPresent()) {
				throw new IllegalStateException("Method acquireService was not found on PDECore");
			}
			Method acquireMethod = potentialAcquireMethod.get();
			AnnotatedType[] parameters = acquireMethod.getAnnotatedParameterTypes();
			AnnotatedType firstParameter = parameters[0];
			Object argument;
			if (firstParameter.getType().getTypeName().contains("String")) {
				argument = IBundleProjectService.class.getName();
			} else {
				argument = IBundleProjectService.class;
			}
			return (IBundleProjectService) acquireMethod.invoke(PDECore.getDefault(), argument);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | SecurityException e) {
			e.printStackTrace();
			throw new IllegalStateException("Method acquireService could not be called on PDECore");
		}
		
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
			String[] bundleNameOptionsSplit = bundleName.split(";");
			if (bundleNameOptionsSplit.length < 1) {
				throw new IllegalStateException("There must be at least one segment in the name");
			}
			String bundleWithoutOptions = bundleNameOptionsSplit[0];
			if (description.getName().equals(bundleWithoutOptions)) {
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
