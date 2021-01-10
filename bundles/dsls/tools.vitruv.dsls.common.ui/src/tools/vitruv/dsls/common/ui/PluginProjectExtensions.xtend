package tools.vitruv.dsls.common.ui

import edu.kit.ipd.sdq.activextendannotations.Utility
import org.eclipse.pde.core.project.IBundleProjectDescription
import org.eclipse.pde.core.project.IRequiredBundleDescription

@Utility
class PluginProjectExtensions {
	/**
	 * Adds a dependency on the provided {@code requiredBundleName} if it is not required yet. The dependency is not 
	 * exported, not optional, and does not define any version constraints. Do not forget to call
	 * {@link IBundleProjectDescription#apply} to actually apply the change!
	 */
	def static addRequiredBundle(IBundleProjectDescription pluginProject, String requiredBundleName) {
		val currentlyRequiredBundles = pluginProject.requiredBundles
		if (currentlyRequiredBundles !== null && currentlyRequiredBundles.exists[name == requiredBundleName]) return;

		val currentSize = if (currentlyRequiredBundles === null) 0 else currentlyRequiredBundles.length
		val newRequiredBundles = <IRequiredBundleDescription>newArrayOfSize(currentSize + 1)
		if (currentSize > 0) {
			System.arraycopy(currentlyRequiredBundles, 0, newRequiredBundles, 0, currentSize)
		}
		newRequiredBundles.set(currentSize,
			ProjectAccess.projectBundleService.newRequiredBundle(requiredBundleName, null, false, false))
		pluginProject.requiredBundles = newRequiredBundles
	}

	def static requiresBundleDirectly(IBundleProjectDescription pluginProject, String requiredBundleName) {
		val requiredBundles = pluginProject.requiredBundles
		if (requiredBundles === null) false else requiredBundles.exists[name == requiredBundleName]
	}
}
