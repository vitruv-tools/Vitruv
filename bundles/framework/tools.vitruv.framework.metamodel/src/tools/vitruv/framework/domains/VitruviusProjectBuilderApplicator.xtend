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

class VitruviusProjectBuilderApplicator {
	public static final String ARGUMENT_VMODEL_NAME = "virtualModelName";
	public static final String ARGUMENT_FILE_EXTENSIONS = "fileExtensions";
	
	val String builderId;
	
	new(String builderId) {
		this.builderId = builderId;
	}
	
	def void addToProject(IProject project, File vmodelFolder, List<String> fileExtensions) {
        if (project !== null) {
            try {
            	val IProjectDescription description = project.getDescription();
                var Map<String, String> builderArguments;
                if (!hasBuilder(project)) {
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
                // TODO could not read/write project description
                e.printStackTrace();
            }
        }
    }
	
	
	def void removeBuilderFromProject(IProject project) {
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
                // TODO could not read/write project description
                e.printStackTrace();
            }
        }
    }
    
	def boolean hasBuilder(IProject project) {
        try {
            for (buildSpec : project.getDescription().getBuildSpec()) {
                if (this.builderId.equals(buildSpec.getBuilderName())) {
                    return true;
                }
            }
        } catch (CoreException e) {
        }

        return false;
    }
    
    def String getBuilderId() {
    	return builderId;
    }
}
