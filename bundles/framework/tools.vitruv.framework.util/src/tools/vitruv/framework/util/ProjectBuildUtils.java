package tools.vitruv.framework.util;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkState;

import java.util.HashMap;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;

/**
 * {@link ProjectBuildUtils} is a utility class providing methods issuing a
 * refresh, complete build or build with a certain builder for a specific or all
 * projects in the workspace.
 */
public final class ProjectBuildUtils {
	private static final Logger LOGGER = Logger.getLogger(ProjectBuildUtils.class);

	private ProjectBuildUtils() {

	}

	/**
	 * Performs an incremental build of the builder with the given ID for all open
	 * projects in the workspace.
	 *
	 * @param builderId the ID of the builder to run
	 * @throws IllegalStateException if some error occurs during build
	 */
	public static void buildAllProjectsIncrementally(final String builderId) throws IllegalStateException {
		for (IProject project : ResourcesPlugin.getWorkspace().getRoot().getProjects()) {
			if (project.isOpen() && hasBuilder(project, builderId)) {
				buildIncrementally(project, builderId);
			}
		}
	}

	/**
	 * Performs an incremental build of the builder with the given ID in the given
	 * {@link IProject}. The project must not be <code>null</code> and must be open.
	 *
	 * @param project   the project to build
	 * @param builderId the ID of the builder to run
	 * @throws IllegalStateException if some error occurs during build
	 */
	public static void buildIncrementally(final IProject project, final String builderId) throws IllegalStateException {
		checkArgument(project != null, "Project must not be null");
		checkState(project.isOpen(), "Project must be open to built");
		LOGGER.debug("Run builder " + builderId + " for project " + project.getName());
		try {
			project.build(IncrementalProjectBuilder.INCREMENTAL_BUILD, builderId, new HashMap<String, String>(), null);
		} catch (CoreException e) {
			String message = "Could not run builder " + builderId + " for project " + project.getName();
			LOGGER.error(message, e);
			throw new IllegalStateException(message, e);
		}
	}

	/**
	 * Refreshes the given project and performs an incremental build. The project
	 * must not be <code>null</code> and must be open.
	 *
	 * @param project the project to refresh and build
	 * @throws IllegalStateException if some error occurs during refresh or build
	 */
	public static void refreshAndBuildIncrementally(final IProject project) {
		checkArgument(project != null, "Project must not be null");
		checkState(project.isOpen(), "Project must be open to be refreshed and built");
		LOGGER.debug("Refresh and build project " + project.getName());
		try {
			project.refreshLocal(IResource.DEPTH_INFINITE, null);
		} catch (CoreException e) {
			String message = "Could not refresh project " + project.getName();
			LOGGER.error(message, e);
			throw new IllegalStateException(message, e);
		}
		try {
			project.build(IncrementalProjectBuilder.INCREMENTAL_BUILD, null);
		} catch (CoreException e) {
			String message = "Could not build project " + project.getName();
			LOGGER.error(message, e);
			throw new IllegalStateException(message, e);
		}
	}

	/**
	 * Checks whether the given project has a builder with the given ID. The project
	 * must not be <code>null</code> and must be open.
	 *
	 * @param project   the project to check for the builder
	 * @param builderId the ID of the builder to check to project for
	 * @return whether the project has a builder with the given ID
	 */
	public static boolean hasBuilder(final IProject project, final String builderId) {
		checkArgument(project != null, "Project must not be null");
		checkState(project.isOpen(), "Project must be open");
		try {
			for (ICommand buildSpec : project.getDescription().getBuildSpec()) {
				if (builderId.equals(buildSpec.getBuilderName())) {
					return true;
				}
			}
		} catch (CoreException e) {
			String message = "Could not read description of project " + project.getName(); 
			LOGGER.error(message, e);
			throw new IllegalStateException(message, e);
		}

		return false;
	}
}
