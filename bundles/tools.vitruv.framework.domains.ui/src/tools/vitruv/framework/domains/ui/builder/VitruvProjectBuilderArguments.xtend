package tools.vitruv.framework.domains.ui.builder

import org.eclipse.core.resources.ICommand
import edu.kit.ipd.sdq.activextendannotations.Utility
import static com.google.common.base.Preconditions.checkState
import static com.google.common.base.Preconditions.checkArgument
import java.util.Set
import java.nio.file.Path

@Utility
class VitruvProjectBuilderArguments {
	public static val ARGUMENT_VMODEL_NAME = "virtualModelName"
	public static val ARGUMENT_FILE_EXTENSIONS = "fileExtensions"
	public static val ARGUMENT_AUTO_PROPAGATE_CHANGE_INTERVAL = "propagateAfterChangeMilliseconds"
	public static val ARGUMENT_AUTO_PROPAGATE_BUILD = "propagateAfterBuild"

	static def Path getVirtualModelFolder(ICommand command) {
		checkState(command.arguments.containsKey(ARGUMENT_VMODEL_NAME), '''Virtual model folder is not set''')
		Path.of(command.arguments.get(ARGUMENT_VMODEL_NAME))
	}

	static def Set<String> getFileExtensions(ICommand command) {
		command.arguments.getOrDefault(ARGUMENT_FILE_EXTENSIONS, "").split("\\s*,\\s*").filter[!nullOrEmpty].toSet
	}

	static def int getPropagateAfterChangeMilliseconds(ICommand command) {
		Integer.parseInt(command.arguments.getOrDefault(ARGUMENT_AUTO_PROPAGATE_CHANGE_INTERVAL, "-1"))
	}

	static def boolean getPropagateAfterBuild(ICommand command) {
		Boolean.parseBoolean(command.arguments.getOrDefault(ARGUMENT_AUTO_PROPAGATE_BUILD, "false"))
	}

	static def void setVirtualModelFolder(ICommand command, Path virtualModelFolder) {
		checkArgument(virtualModelFolder !== null, "Virtual model folder must not be null")
		checkState(
			!command.arguments.containsKey(
				ARGUMENT_VMODEL_NAME), '''Virtual model has already been set to folder «command.arguments.get(ARGUMENT_VMODEL_NAME)»''')
		command.setArgument(ARGUMENT_VMODEL_NAME, virtualModelFolder.toString)
	}

	static def void addFileExtensions(ICommand command, Set<String> additionalFileExtensions) {
		val existingfileExtensions = command.fileExtensions
		val fileExtensions = existingfileExtensions + additionalFileExtensions
		val fileExtensionsString = '''«FOR fileExtension : fileExtensions SEPARATOR ", "»«fileExtension»«ENDFOR»'''
		command.setArgument(ARGUMENT_FILE_EXTENSIONS, fileExtensionsString)
	}
	
	static def void setPropagateAfterChangeMilliseconds(ICommand command, int milliseconds) {
		command.setArgument(ARGUMENT_AUTO_PROPAGATE_CHANGE_INTERVAL, milliseconds.toString)
	}

	static def void setPropagateAfterBuild(ICommand command, boolean enabled) {
		command.setArgument(ARGUMENT_AUTO_PROPAGATE_BUILD, enabled.toString)
	}

	private static def setArgument(ICommand command, String key, String value) {
		val copiedArguments = command.arguments
		copiedArguments.put(key, value)
		command.arguments = copiedArguments
	}

}
