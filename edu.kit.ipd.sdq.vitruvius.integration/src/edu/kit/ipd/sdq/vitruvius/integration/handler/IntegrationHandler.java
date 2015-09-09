package edu.kit.ipd.sdq.vitruvius.integration.handler;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.UserInteractionType;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.UserInteracting;
import edu.kit.ipd.sdq.vitruvius.framework.model.monitor.userinteractor.UserInteractor;
import edu.kit.ipd.sdq.vitruvius.framework.vsum.VSUMConstants;
import edu.kit.ipd.sdq.vitruvius.framework.vsum.VSUMImpl;
import edu.kit.ipd.sdq.vitruvius.integration.LoggerConfigurator;

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
    protected int keepOldModel;
    protected VSUMImpl vsum;

    /*
     * (non-Javadoc)
     *
     * @see
     * org.eclipse.core.commands.AbstractHandler#execute(org.eclipse.core.commands.ExecutionEvent)
     */
    @Override
    public Object execute(final ExecutionEvent event) throws ExecutionException {

        LoggerConfigurator.setUpLogger();

        final ISelection selection = HandlerUtil.getActiveMenuSelection(event);
        final IStructuredSelection structuredSelection = (IStructuredSelection) selection;

        final Object firstElement = structuredSelection.getFirstElement();

        final UserInteracting dialog = new UserInteractor();
        this.keepOldModel = dialog.selectFromMessage(UserInteractionType.MODAL, "Keep old model?", "No", "Yes");

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

        // Delete vitruvius.meta project
        final IWorkspace workspace = ResourcesPlugin.getWorkspace();
        final IWorkspaceRoot root = workspace.getRoot();
        final IProject metaFiles = root.getProject(VSUMConstants.VSUM_PROJECT_NAME);
        try {
            metaFiles.delete(true, null);
        } catch (final CoreException e) {
            e.printStackTrace();
        }
        try {
            root.refreshLocal(0, null);
        } catch (final CoreException e) {
            e.printStackTrace();
        }

        LogManager.shutdown();

    }

}
