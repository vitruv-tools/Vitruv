package tools.vitruvius.applications.jmljava.initializer;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.JavaCore;

import tools.vitruvius.domains.jml.monitorededitor.JMLMonitoredEditor;
import tools.vitruvius.applications.jmljava.changesynchronizer.ChangeSynchronizerRegistry;
import tools.vitruvius.applications.jmljava.initializer.CommandState.Commands;
import tools.vitruvius.applications.jmljava.monitorededitor.java.JavaMonitoredEditor;
import tools.vitruvius.framework.userinteraction.UserInteractionType;
import tools.vitruvius.framework.userinteraction.impl.UserInteractor;
import tools.vitruvius.framework.monitorededitor.registries.MonitoredEditorsRegistry;
import tools.vitruvius.framework.monitorededitor.registries.MonitoredProjectsRegistry;

/**
 * Handler for the activation command for the synchronisation engine.
 * 
 * It displays a selection for the projects to monitor and initializes the synchronisation framework
 * afterwards.
 * 
 * @author Stephan Seifermann
 *
 */
public class ActivateHandler extends AbstractHandler {

    private static final Logger LOGGER = Logger.getLogger(ActivateHandler.class);
    
    @Override
    public Object execute(ExecutionEvent event) throws ExecutionException {
        try {
            List<String> projectNames = new ArrayList<String>();
            IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
            for (IProject project : root.getProjects()) {
                if (project.isOpen() && project.hasNature(JavaCore.NATURE_ID)) {
                    projectNames.add(project.getName());
                }
            }

            UserInteractor userInteractor = new UserInteractor();
            int selection = userInteractor.selectFromMessage(UserInteractionType.MODAL,
                    "Please select the project to monitor.", projectNames.toArray(new String[0]));
            if (selection < 0) {
                LOGGER.info("The user aborted the activation process.");
                return null;
            }

            activate(projectNames.get(selection));
            return null;
        } catch (CoreException e) {
            throw new ExecutionException("Error while determining the selectable projects.", e);
        }
    }
    
    public void activate(String projectName) {
    	LOGGER.info("Activating Synchronisation Framework");
    	
        String monitoredProjectName = projectName;
        LOGGER.info("Monitored Project: " + monitoredProjectName);
        MonitoredProjectsRegistry.getInstance().registerElement(monitoredProjectName);
        
        MonitoredEditorsRegistry.getInstance().registerElement(new JavaMonitoredEditor());
        MonitoredEditorsRegistry.getInstance().registerElement(new JMLMonitoredEditor());
        
        ChangeSynchronizerRegistry.getInstance().getChangeSynchronizer().register(SynchronisationTimeLogger.getInstance());
        
        CommandState.changeState(Commands.ACTIVATE, "");
    }

}
