package tools.vitruv.extensions.constructionsimulation.handler;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;

import tools.vitruv.extensions.constructionsimulation.LoggerConfigurator;
import tools.vitruv.framework.userinteraction.UserInteractionFactory;
import tools.vitruv.framework.userinteraction.UserInteractor;
import tools.vitruv.framework.userinteraction.UserInteractionOptions.WindowModality;
import tools.vitruv.framework.vsum.InternalVirtualModel;

/**
 * Handler for the context menu event created when a user rightclicks a model and selects
 * "integrate".
 *
 * @author Sven Leonhardt
 * @param <T>
 */
public abstract class IntegrationHandler<T> extends AbstractHandler {

    private final Class<T> type;

    public IntegrationHandler(final Class<T> type) {
        this.type = type;
    }

    private final Logger logger = LogManager.getRootLogger();
    protected boolean keepOldModel;
    protected InternalVirtualModel vsum;

    /*
     * (non-Javadoc)
     *
     * @see
     * org.eclipse.core.commands.AbstractHandler#execute(org.eclipse.core.commands.ExecutionEvent)
     */
    @SuppressWarnings("unchecked")
	@Override
    public Object execute(final ExecutionEvent event) throws ExecutionException {

        LoggerConfigurator.setUpLogger();

        final ISelection selection = HandlerUtil.getActiveMenuSelection(event);
        final IStructuredSelection structuredSelection = (IStructuredSelection) selection;

        final Object firstElement = structuredSelection.getFirstElement();

        final UserInteractor dialog = UserInteractionFactory.instance.createDialogUserInteractor();
        this.keepOldModel = dialog.getConfirmationDialogBuilder().message("Keep old model?").windowModality(WindowModality.MODAL).startInteraction();

        if (this.type.isInstance(firstElement)) {
            this.handleSelectedElement((T) firstElement);
        } else {
            throw new IllegalArgumentException("Selected entry must be a file or project");
        }

        // only for testing, so we don't need to manually delete it every time
        this.cleanUpIntegration();

        return null;
    }

    protected abstract void handleSelectedElement(T firstElement);

    /**
     * Save the old model
     *
     * @param resource
     *            old model
     */
    protected void saveOldModel(final IResource resource) {

        IPath newPath = resource.getFullPath();
        newPath = this.createPathToOldModel(newPath);

        // save old model
        try {
            resource.copy(newPath, true, null);
        } catch (final CoreException e1) {
            this.logger.error("Could not save the old model.");
        }
    }

    protected IPath createPathToOldModel(final IPath path) {
        final String extension = "_beforeIntegration.repository";
        IPath newPath = path.removeFileExtension();

        String fileName = newPath.lastSegment();
        fileName = fileName.concat(extension);

        newPath = newPath.removeLastSegments(1);
        newPath = newPath.append(fileName);
        return newPath;
    }

    /**
     * Cleanup after integration
     */
    private void cleanUpIntegration() {

        vsum.getFolder().toFile().delete();
        LogManager.shutdown();

    }

}
