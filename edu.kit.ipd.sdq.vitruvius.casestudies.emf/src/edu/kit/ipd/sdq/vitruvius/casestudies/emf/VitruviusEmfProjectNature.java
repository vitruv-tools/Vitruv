package edu.kit.ipd.sdq.vitruvius.casestudies.emf;

import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IProjectNature;
import org.eclipse.core.runtime.CoreException;

/**
 * 
 * @author Langhamm
 * VitruviusEmfProjectNature: Generic project nature for all projects using EMF based Metamodels.
 *
 */
public abstract class VitruviusEmfProjectNature implements IProjectNature {

    private static Logger logger = Logger.getLogger(VitruviusEmfProjectNature.class);

    private IProject project;
    private String projectNatureName;
    private String projectNatureId;
    private String EMF_BUILDER_ID = "edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emf.builder";
    //private List<String> controlledFileExtensions;

//    public VitruviusEmfProjectNature(IProject project, String projectNatureName, String projectNatureId,
//            String projectBuilderId, List<String> controlledFileExtensions) {
//        this.projectNatureName = projectNatureName;
//        this.projectNatureId = projectNatureId;
//        //this.controlledFileExtensions = controlledFileExtensions;
//        this.project = project;
//    }
    
    public VitruviusEmfProjectNature(String projectNatureId, String projectNatureName){
        this.projectNatureName = projectNatureName;
        this.projectNatureId = projectNatureId;
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.core.resources.IProjectNature#configure()
     */
    public void configure() throws CoreException {
        IProjectDescription desc = project.getDescription();
        ICommand[] commands = desc.getBuildSpec();

        for (int i = 0; i < commands.length; ++i) {
            if (commands[i].getBuilderName().equals(EMF_BUILDER_ID)) {
                return;
            }
        }

        ICommand[] newCommands = new ICommand[commands.length + 1];
        System.arraycopy(commands, 0, newCommands, 0, commands.length);
        ICommand command = desc.newCommand();
        command.setBuilderName(EMF_BUILDER_ID);
        newCommands[newCommands.length - 1] = command;
        desc.setBuildSpec(newCommands);
        project.setDescription(desc, null);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.core.resources.IProjectNature#deconfigure()
     */
    public void deconfigure() throws CoreException {
        IProjectDescription description = getProject().getDescription();
        ICommand[] commands = description.getBuildSpec();
        for (int i = 0; i < commands.length; ++i) {
            if (commands[i].getBuilderName().equals(EMF_BUILDER_ID)) {
                ICommand[] newCommands = new ICommand[commands.length - 1];
                System.arraycopy(commands, 0, newCommands, 0, i);
                System.arraycopy(commands, i + 1, newCommands, i,
                        commands.length - i - 1);
                description.setBuildSpec(newCommands);
                project.setDescription(description, null);          
                return;
            }
        }
    }

    @Override
    public IProject getProject() {
        return project;
    }

    @Override
    public void setProject(IProject project) {
        this.project = project;
    }
}
