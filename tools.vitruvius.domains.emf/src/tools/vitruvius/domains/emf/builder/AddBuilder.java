package tools.vitruvius.domains.emf.builder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;

public class AddBuilder extends AbstractHandler implements IHandler {

    private final String builderId;

    protected AddBuilder(final String builderId) {
        this.builderId = builderId;
    }

    @Override
    public Object execute(final ExecutionEvent event) {
        final IProject project = getProject(event);

        return this.addBuilderToProject(project);
    }

    public Object addBuilderToProject(final IProject project) {
        if (project != null) {
            try {
                // verify already registered builders
                if (this.hasBuilder(project)) {
                    // already enabled
                    return null;
                }

                // add builder to project properties
                final IProjectDescription description = project.getDescription();
                final ICommand buildCommand = description.newCommand();
                buildCommand.setBuilderName(this.builderId);

                final List<ICommand> commands = new ArrayList<ICommand>();
                commands.addAll(Arrays.asList(description.getBuildSpec()));
                commands.add(buildCommand);

                description.setBuildSpec(commands.toArray(new ICommand[commands.size()]));
                project.setDescription(description, null);

            } catch (final CoreException e) {
                // TODO could not read/write project description
                e.printStackTrace();
            }
        }

        return null;
    }

    public static IProject getProject(final ExecutionEvent event) {
        final ISelection selection = HandlerUtil.getCurrentSelection(event);
        if (selection instanceof IStructuredSelection) {
            final Object element = ((IStructuredSelection) selection).getFirstElement();

            return (IProject) Platform.getAdapterManager().getAdapter(element, IProject.class);
        }
        return null;
    }

    public boolean hasBuilder(final IProject project) {
        try {
            for (final ICommand buildSpec : project.getDescription().getBuildSpec()) {
                if (this.builderId.equals(buildSpec.getBuilderName())) {
                    return true;
                }
            }
        } catch (final CoreException e) {
        }

        return false;
    }
}
