package tools.vitruvius.applications.jmljava.initializer;

import org.apache.log4j.Logger;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

import tools.vitruvius.applications.jmljava.changesynchronizer.ChangeSynchronizerRegistry;
import tools.vitruvius.applications.jmljava.initializer.CommandState.Commands;
import tools.vitruvius.framework.monitorededitor.registries.MonitoredEditorsRegistry;
import tools.vitruvius.framework.monitorededitor.registries.MonitoredProjectsRegistry;

/**
 * Handler for the deactivation event from the UI. It deactivates the whole synchronisation
 * framework.
 * 
 * @author Stephan Seifermann
 *
 */
public class DeactivateHandler extends AbstractHandler {

    private static final Logger LOGGER = Logger.getLogger(DeactivateHandler.class);

    @Override
    public Object execute(ExecutionEvent event) throws ExecutionException {
        deactivate();
        return null;
    }
    
    public void deactivate() {
    	LOGGER.info("Deactivating Synchronisation Framework");
        MonitoredEditorsRegistry.getInstance().unregisterAllElements();
        MonitoredProjectsRegistry.getInstance().unregisterAllElements();
        ChangeSynchronizerRegistry.getInstance().getChangeSynchronizer().unregister(SynchronisationTimeLogger.getInstance());
        CommandState.changeState(Commands.ACTIVATE, "TRUE");
        ChangeSynchronizerRegistry.init();
    }

}
