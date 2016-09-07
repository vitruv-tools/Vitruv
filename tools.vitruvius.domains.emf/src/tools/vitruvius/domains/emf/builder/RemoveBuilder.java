package tools.vitruvius.domains.emf.builder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.runtime.CoreException;

public class RemoveBuilder extends AbstractHandler implements IHandler {

    private final String builderId;

    protected RemoveBuilder(final String builderId) {
        this.builderId = builderId;
    }

    @Override
    public Object execute(final ExecutionEvent event) throws ExecutionException {
        final IProject project = AddBuilder.getProject(event);

        return removeBuilderFromProject(project);
    }

    public Object removeBuilderFromProject(final IProject project) {
        if (project != null) {
            try {
                final IProjectDescription description = project.getDescription();
                final List<ICommand> commands = new ArrayList<ICommand>();
                commands.addAll(Arrays.asList(description.getBuildSpec()));

                for (final ICommand buildSpec : description.getBuildSpec()) {
                    if (this.builderId.equals(buildSpec.getBuilderName())) {
                        // remove builder
                        commands.remove(buildSpec);
                    }
                }

                description.setBuildSpec(commands.toArray(new ICommand[commands.size()]));
                project.setDescription(description, null);
            } catch (final CoreException e) {
                // TODO could not read/write project description
                e.printStackTrace();
            }
        }

        return null;
    }
}
