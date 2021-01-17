package tools.vitruv.domains.emf;

import java.util.Iterator;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;

public class AddRemoveEmfNatureHandler extends AbstractHandler {

    private final String projectNatureId;

    // private ISelection selection;

    public AddRemoveEmfNatureHandler(final String projectNatureId) {
        this.projectNatureId = projectNatureId;
    }

    @Override
    public Object execute(final ExecutionEvent event) throws ExecutionException {
        // TODO Auto-generated method stub
        final ISelection selection = HandlerUtil.getCurrentSelection(event);
        //
        if (selection instanceof IStructuredSelection) {
            for (final Iterator<?> it = ((IStructuredSelection) selection).iterator(); it.hasNext();) {
                final Object element = it.next();
                IProject project = null;
                if (element instanceof IProject) {
                    project = (IProject) element;
                } else if (element instanceof IAdaptable) {
                    project = (IProject) ((IAdaptable) element).getAdapter(IProject.class);
                }
                if (project != null) {
                    try {
                        this.toggleNature(project);
                    } catch (final CoreException e) {
                        // TODO log something
                        throw new ExecutionException("Failed to toggle nature", e);
                    }
                }
            }
        }

        return null;
    }

    /**
     * Toggles sample nature on a project
     * 
     * @param project
     *            to have sample nature added or removed
     */
    private void toggleNature(final IProject project) throws CoreException {
        final IProjectDescription description = project.getDescription();
        final String[] natures = description.getNatureIds();

        for (int i = 0; i < natures.length; ++i) {
            if (this.projectNatureId.equals(natures[i])) {
                // Remove the nature
                final String[] newNatures = new String[natures.length - 1];
                System.arraycopy(natures, 0, newNatures, 0, i);
                System.arraycopy(natures, i + 1, newNatures, i, natures.length - i - 1);
                description.setNatureIds(newNatures);
                project.setDescription(description, null);
                return;
            }
        }

        // Add the nature
        final String[] newNatures = new String[natures.length + 1];
        System.arraycopy(natures, 0, newNatures, 0, natures.length);
        newNatures[natures.length] = this.projectNatureId;
        description.setNatureIds(newNatures);
        project.setDescription(description, null);
    }

}