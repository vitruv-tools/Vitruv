package tools.vitruv.framework.domains

import org.eclipse.core.resources.IProject
import org.eclipse.core.resources.ICommand
import java.util.Arrays
import org.eclipse.core.runtime.CoreException
import java.util.HashMap
import java.io.File
import org.apache.log4j.Logger
import org.eclipse.xtend.lib.annotations.Accessors
import static extension tools.vitruv.framework.util.ProjectBuildUtils.hasBuilder
import java.util.Set
import static com.google.common.base.Preconditions.checkState
import static com.google.common.base.Preconditions.checkArgument

class VitruviusProjectBuilderApplicator {
	static val LOGGER = Logger.getLogger(VitruviusProjectBuilderApplicator)

	public static val ARGUMENT_VMODEL_NAME = "virtualModelName"
	public static val ARGUMENT_FILE_EXTENSIONS = "fileExtensions"

	@Accessors(PUBLIC_GETTER)
	val String builderId

	new(String builderId) {
		this.builderId = builderId
	}

	/**
	 * Adds the builder for the virtual model in the given folder and for the given file extensions to the given project.
	 * None of the arguments must be {@code null} and {@code fileExtensions} must not be empty.
	 *
	 * @throw IllegalStateException if the builder could be added to the given project
	 */
	def void addBuilder(IProject project, File virtualModelFolder,
		Set<String> fileExtensions) throws IllegalStateException {
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
				buildCommand.virtualModel = virtualModelFolder
				buildCommand.addFileExtensions(fileExtensions)
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
	}

	private def setVirtualModel(ICommand command, File folder) {
		val copiedArguments = command.arguments
		checkState(
			!copiedArguments.containsKey(
				ARGUMENT_VMODEL_NAME), '''Virtual model has already been set to folder «copiedArguments.get(ARGUMENT_VMODEL_NAME)»''')
		copiedArguments.put(ARGUMENT_VMODEL_NAME, folder.toString)
		command.arguments = copiedArguments
	}

	private def addFileExtensions(ICommand command, Set<String> additionalFileExtensions) {
		val copiedArguments = command.arguments
		val existingfileExtensions = copiedArguments.getOrDefault(ARGUMENT_FILE_EXTENSIONS, "").split("\\s*,\\s*").
			filter[!nullOrEmpty].toSet
		val fileExtensions = existingfileExtensions + additionalFileExtensions
		val fileExtensionsString = '''«FOR fileExtension : fileExtensions SEPARATOR ", "»«fileExtension»«ENDFOR»'''
		copiedArguments.put(ARGUMENT_FILE_EXTENSIONS, fileExtensionsString)
		command.arguments = copiedArguments
	}

	/**
	 * Removes the builder from the given project, which must not be {@code null}.
	 *
	 * @throw IllegalStateException if the builder could be removed from the given project
	 */
	def void removeBuilder(IProject project) throws IllegalStateException {
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
