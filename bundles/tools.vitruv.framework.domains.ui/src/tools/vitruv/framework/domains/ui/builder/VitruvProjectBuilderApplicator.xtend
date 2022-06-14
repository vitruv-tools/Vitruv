package tools.vitruv.framework.domains.ui.builder

import org.eclipse.core.resources.IProject
import java.util.Set
import tools.vitruv.framework.domains.VitruvDomain
import com.google.common.collect.Sets
import java.nio.file.Path

interface VitruvProjectBuilderApplicator {
	/**
	 * Enables or disables automatically running change propagation after a build was triggered in the generated builder.
	 */
	def VitruvProjectBuilderApplicator setPropagateAfterBuild(boolean enabled)

	/**
	 * Specifies that if no further change occurred after another change within the given time period, the builder will trigger change propagation.
	 */
	def VitruvProjectBuilderApplicator setPropagateAfterChangeMilliseconds(int milliseconds)

	/**
	 * Adds the builder for the virtual model in the given folder and for the given file extensions to the given project.
	 * It runs an incremental build of the given project to initialize the builder.
	 * <p>
	 * None of the arguments must be {@code null} and {@code fileExtensions} must not be empty.
	 * 
	 * @throws IllegalStateException if the builder could be added to the given project
	 */
	def void addBuilder(IProject project, Path virtualModelFolder,
		Set<String> fileExtensions) throws IllegalStateException

	/**
	 * Removes the builder from the given project. {@code project} must not be {@code null}.
	 * 
	 * @throws IllegalStateException if the builder could be removed from the given project
	 */
	def void removeBuilder(IProject project) throws IllegalStateException
	
	static def Set<VitruvProjectBuilderApplicator> getApplicatorsForVitruvDomain(VitruvDomain domain) {
		Sets.newHashSet(VitruvProjectBuilderRegistry.INSTANCE.getProjectBuilderIds(domain).map[new VitruvProjectBuilderApplicatorImpl(it)])
	}
	
}
