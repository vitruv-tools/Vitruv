package tools.vitruv.framework.ui.monitorededitor;

import java.util.HashMap;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;

/**
 * {@link ProjectBuildUtils} is a utility class providing methods issuing the execution of a certain
 * builder for all projects which can be built with this builder.
 */
public final class ProjectBuildUtils {
    private static final Logger LOGGER = Logger.getLogger(ProjectBuildUtils.class);

    private ProjectBuildUtils() {

    }

    /**
     * Issues an incremental build of all projects supporting a given builder.
     *
     * @param builderId
     *            An Eclipse builder ID. Only this builder is executed on the currently existing
     *            projects supporting this builder.
     * @throws CoreException
     */
    public static void issueIncrementalBuildForAllProjectsWithBuilder(final String builderId)
            throws IllegalStateException {
        for (IProject project : ResourcesPlugin.getWorkspace().getRoot().getProjects()) {
            if (project.isOpen()) {
                issueIncrementalBuild(project, builderId);
            }
        }
    }

    public static void issueIncrementalBuild(final IProject project, final String builderId)
            throws IllegalStateException {
        LOGGER.debug("Issuing initial build for project " + project.getName());
        try {
            for (ICommand buildCommand : project.getDescription().getBuildSpec()) {
                if (buildCommand.getBuilderName().equals(builderId)) {
                    project.build(IncrementalProjectBuilder.INCREMENTAL_BUILD, builderId, new HashMap<String, String>(),
                            new NullProgressMonitor());
                }
            }
        } catch (CoreException e) {
            String message = "Could not issue initial build for project " + project.getName();
            LOGGER.error(message, e);
            throw new IllegalStateException(message, e);
        }
    }
}
