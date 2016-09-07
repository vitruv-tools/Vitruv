package tools.vitruvius.domains.jml.monitorededitor;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;

import tools.vitruvius.applications.jmljava.extensions.SourceDirProvider;
import tools.vitruvius.domains.jml.monitorededitor.changeinjection.CommandState;
import tools.vitruvius.domains.jml.monitorededitor.changeinjection.CommandState.Commands;
import tools.vitruvius.framework.monitorededitor.registries.MonitoredProjectsRegistry;
import tools.vitruvius.framework.monitorededitor.registries.RegisteredMonitoredEditor;

/**
 * A monitored editor for JML. At the moment it only supports returning source
 * directories for JML files but no change detection.
 */
public class JMLMonitoredEditor implements RegisteredMonitoredEditor {

	private static final String[] SUPPORTED_EXTENSIONS = { "jml" };
	private static final Logger LOGGER = Logger
			.getLogger(JMLMonitoredEditor.class);

	/**
	 * A provider for JML source dirs.
	 */
	public static class JmlSourceDirProvider implements SourceDirProvider {

		@Override
		public String[] getContainedExtensions() {
			return SUPPORTED_EXTENSIONS;
		}

		@Override
		public IResource[] getSourceDirs() {
			List<IResource> sourceDirs = new ArrayList<IResource>();

			// TODO we could try to extract the spec dirs from OpenJML nature
			// instead
			IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
			for (String projectName : MonitoredProjectsRegistry.getInstance().getRegisteredElements()) {
				IProject project = root.getProject(projectName);
				sourceDirs.add(project.getFolder("specs"));
			}

			return sourceDirs.toArray(new IResource[0]);
		}

	}
	
	/**
	 * Constructor
	 */
	public JMLMonitoredEditor() {
		LOGGER.info("Initialized JML monitored editor.");
		CommandState.changeState(Commands.JMLREFACTORINGS, "TRUE");
	}

    @Override
    public void shutdown() {
        CommandState.changeState(Commands.JMLREFACTORINGS, "");
    }

}
