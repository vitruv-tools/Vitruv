package tools.vitruv.framework.domains.ui.builder

import org.eclipse.core.resources.IProject
import org.eclipse.core.resources.ICommand
import java.util.Arrays
import org.eclipse.core.runtime.CoreException
import java.util.HashMap
import org.apache.log4j.Logger
import static extension tools.vitruv.framework.util.ProjectBuildUtils.hasBuilder
import java.util.Set
import static com.google.common.base.Preconditions.checkArgument
import static extension tools.vitruv.framework.domains.ui.builder.VitruvProjectBuilderArguments.*
import static extension tools.vitruv.framework.util.ProjectBuildUtils.buildIncrementally
import java.nio.file.Path

final class VitruvProjectBuilderApplicatorImpl implements VitruvProjectBuilderApplicator {
	static val LOGGER = Logger.getLogger(VitruvProjectBuilderApplicatorImpl)

	val String builderId
	var Boolean isPropagateAfterBuild = null
	var Integer millisecondsToPropagateAfter = null
	
	new(String builderId) {
		this.builderId = builderId
	}

	override setPropagateAfterBuild(boolean enabled) {
		isPropagateAfterBuild = enabled
		this
	}
	
	override setPropagateAfterChangeMilliseconds(int milliseconds) {
		millisecondsToPropagateAfter = milliseconds
		this
	}

	override void addBuilder(IProject project, Path virtualModelFolder, Set<String> fileExtensions) {
		checkArgument(project !== null, "Project must not be null")
		checkArgument(virtualModelFolder !== null, "Virtual model folder must not be null")
		checkArgument(!fileExtensions.nullOrEmpty, "File extensions of builder must not be null or empty")
		try {
			val copiedProjectDescription = project.description
			if (!project.hasBuilder(builderId)) {
				LOGGER.info('''Adding builder with id «builderId» to project «project.name»''')
				val ICommand buildCommand = copiedProjectDescription.newCommand()
				buildCommand.arguments = new HashMap<String, String>()
				buildCommand.builderName = builderId
				buildCommand.virtualModelFolder = virtualModelFolder
				buildCommand.addFileExtensions(fileExtensions)
				if (isPropagateAfterBuild !== null) {
					buildCommand.propagateAfterBuild = isPropagateAfterBuild
				}
				if (millisecondsToPropagateAfter !== null) {
					buildCommand.propagateAfterChangeMilliseconds = millisecondsToPropagateAfter
				}
				val newBuildSpec = Arrays.copyOf(copiedProjectDescription.buildSpec,
					copiedProjectDescription.buildSpec.size + 1)
				newBuildSpec.set(newBuildSpec.size - 1, buildCommand)
				copiedProjectDescription.buildSpec = newBuildSpec
			} else {
				val copiedBuildSpec = copiedProjectDescription.buildSpec
				copiedBuildSpec.filter[builderName == builderId].forEach [
					addFileExtensions(fileExtensions)
				]
				copiedProjectDescription.buildSpec = copiedBuildSpec
			}
			project.setDescription(copiedProjectDescription, null)
		} catch (CoreException e) {
			val message = '''Could not add the builder with id «builderId» to project description of project «project.name»'''
			LOGGER.error(message, e)
			throw new IllegalStateException(message, e)
		}
		buildIncrementally(project, builderId)
	}

	override void removeBuilder(IProject project) throws IllegalStateException {
		checkArgument(project !== null, "Project must not be null")
		try {
			val copiedProjectDescription = project.description
			val remainingBuildSpec = copiedProjectDescription.buildSpec.filter[builderName != builderId]
			copiedProjectDescription.setBuildSpec(remainingBuildSpec.toList.toArray(newArrayOfSize(0)))
			project.setDescription(copiedProjectDescription, null)
		} catch (CoreException e) {
			val message = '''Could not remove the builder with id «builderId» to project description of project «project.name»'''
			LOGGER.error(message, e)
			throw new IllegalStateException(message, e)
		}
	}

}
