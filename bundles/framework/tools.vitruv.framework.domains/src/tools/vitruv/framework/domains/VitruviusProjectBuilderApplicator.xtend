package tools.vitruv.framework.domains

import org.eclipse.core.resources.IProject
import org.eclipse.core.resources.IProjectDescription
import org.eclipse.core.resources.ICommand
import java.util.Map
import java.util.List
import java.util.ArrayList
import java.util.Arrays
import org.eclipse.core.runtime.CoreException
import java.util.HashMap
import java.io.File
import org.apache.log4j.Logger
import org.eclipse.xtend.lib.annotations.Accessors

class VitruviusProjectBuilderApplicator {
	static val LOGGER = Logger.getLogger(VitruviusProjectBuilderApplicator)

	public static val ARGUMENT_VMODEL_NAME = "virtualModelName";
	public static val ARGUMENT_FILE_EXTENSIONS = "fileExtensions";

	@Accessors(PUBLIC_GETTER)
	val String builderId;

	new(String builderId) {
		this.builderId = builderId;
	}

	def void addToProject(IProject project, File vmodelFolder, List<String> fileExtensions) throws IllegalStateException {
		if (project !== null) {
			try {
				val IProjectDescription description = project.getDescription();
				var Map<String, String> builderArguments;
				if (!hasBuilder(project)) {
					LOGGER.info('''Adding builder with id «builderId» to project «project.name»''')
					val ICommand buildCommand = description.newCommand();
					buildCommand.setBuilderName(this.builderId);
					builderArguments = new HashMap<String, String>()
					// add builder to project properties
					builderArguments.put(ARGUMENT_VMODEL_NAME, vmodelFolder.toString);
					var String fileExtensionsString = "";
					for (fileExtension : fileExtensions) {
						fileExtensionsString += fileExtension + ", ";
					}
					builderArguments.put(ARGUMENT_FILE_EXTENSIONS, fileExtensionsString);
					buildCommand.setArguments(builderArguments);
					val List<ICommand> commands = new ArrayList<ICommand>();
					commands.addAll(Arrays.asList(description.getBuildSpec()));
					commands.add(buildCommand);
					description.setBuildSpec(commands.toArray(<ICommand>newArrayOfSize(commands.size())));
				} else {
					val buildSpec = project.getDescription().getBuildSpec()
					for (buildCommand : buildSpec) {
						if (this.builderId.equals(buildCommand.getBuilderName())) {
							builderArguments = buildCommand.arguments
							// add builder to project properties
							var String fileExtensionsString = builderArguments.get(ARGUMENT_FILE_EXTENSIONS);
							for (fileExtension : fileExtensions) {
								fileExtensionsString += fileExtension + ", ";
							}
							builderArguments.put(ARGUMENT_FILE_EXTENSIONS, fileExtensionsString);
							buildCommand.setArguments(builderArguments);
						}
					}
					description.setBuildSpec(buildSpec);
				}
				project.setDescription(description, null);
			} catch (CoreException e) {
				val message = '''Could not add the builder with id «builderId» to project description of project «project.name»''';
				LOGGER.error(message, e)
				throw new IllegalStateException(message, e);
			}
		}
	}

	def void removeBuilderFromProject(IProject project) throws IllegalStateException {
		if (project !== null) {
			try {
				val IProjectDescription description = project.getDescription();
				val List<ICommand> commands = new ArrayList<ICommand>();
				commands.addAll(Arrays.asList(description.getBuildSpec()));

				for (buildSpec : description.getBuildSpec()) {
					if (this.builderId.equals(buildSpec.getBuilderName())) {
						// remove builder
						commands.remove(buildSpec);
					}
				}

				description.setBuildSpec(commands.toArray(<ICommand>newArrayOfSize(commands.size())));
				project.setDescription(description, null);
			} catch (CoreException e) {
				val message = '''Could not remove the builder with id «builderId» to project description of project «project.name»''';
				LOGGER.error(message, e)
				throw new IllegalStateException(message, e);
			}
		}
	}

	def boolean hasBuilder(IProject project) throws IllegalStateException {
		try {
			for (buildSpec : project.getDescription().getBuildSpec()) {
				if (this.builderId.equals(buildSpec.getBuilderName())) {
					return true;
				}
			}
		} catch (CoreException e) {
			throw new IllegalStateException('''Could not read description of project «project.name»''', e);
		}

		return false;
	}

}
