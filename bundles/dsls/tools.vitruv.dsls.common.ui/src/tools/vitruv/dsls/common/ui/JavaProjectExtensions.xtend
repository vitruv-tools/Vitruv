package tools.vitruv.dsls.common.ui

import org.eclipse.jdt.core.search.SearchEngine
import org.eclipse.jdt.core.search.SearchPattern
import org.eclipse.jdt.core.search.IJavaSearchConstants
import org.eclipse.jdt.core.IJavaProject
import java.util.concurrent.atomic.AtomicBoolean
import org.eclipse.jdt.core.search.SearchRequestor
import org.eclipse.jdt.core.search.SearchMatch
import org.eclipse.core.runtime.CoreException
import org.eclipse.core.runtime.NullProgressMonitor
import edu.kit.ipd.sdq.activextendannotations.Utility
import org.eclipse.jdt.internal.core.JrtPackageFragmentRoot
import org.eclipse.pde.internal.core.search.PluginJavaSearchUtil
import org.eclipse.jdt.internal.core.ExternalPackageFragmentRoot
import org.eclipse.jdt.core.search.IJavaSearchScope
import org.eclipse.jdt.core.IJavaElement
import org.eclipse.xtend.lib.annotations.Delegate
import java.util.Set
import org.eclipse.core.runtime.IPath
import org.eclipse.core.runtime.Path

@Utility
class JavaProjectExtensions {
	def static hasTypeOnClassPath(IJavaProject javaProject, String fullyQualifiedName) {
		val javaProjectScope = new JavaProjectWithExternalFoldersScope(javaProject)

		val javaSearchEngine = new SearchEngine()

		val typePattern = SearchPattern.createPattern(fullyQualifiedName, IJavaSearchConstants.TYPE,
			IJavaSearchConstants.DECLARATIONS, SearchPattern.R_EXACT_MATCH.bitwiseOr(SearchPattern.R_CASE_SENSITIVE))
		val foundType = new AtomicBoolean(false)
		val requestor = new SearchRequestor() {
			override acceptSearchMatch(SearchMatch match) throws CoreException {
				foundType.set(true)
			}
		}
		javaSearchEngine.search(typePattern, #[SearchEngine.defaultSearchParticipant], javaProjectScope, requestor,
			new NullProgressMonitor())
		return foundType.get
	}

	/**
	 * The normal {@link IJavaSearchScope} does not enclose external folders, most likely because of a bug. This
	 * scope fixes this shortcoming.
	 */
	private static class JavaProjectWithExternalFoldersScope implements IJavaSearchScope {
		@Delegate
		val IJavaSearchScope delegate
		val Set<IPath> externalFoldePaths

		new(IJavaProject javaProject) {
			delegate = SearchEngine.createJavaSearchScope(true, #[javaProject], true)
			externalFoldePaths = javaProject.allPackageFragmentRoots.filter(ExternalPackageFragmentRoot).map[path].toSet
		}

		override encloses(String resourcePath) {
			val path = new Path(resourcePath)
			externalFoldePaths.exists[it.isPrefixOf(path)] || delegate.encloses(resourcePath)
		}

		override encloses(IJavaElement element) {
			externalFoldePaths.exists[it.isPrefixOf(element.path)] || delegate.encloses(element)
		}
	}
}
