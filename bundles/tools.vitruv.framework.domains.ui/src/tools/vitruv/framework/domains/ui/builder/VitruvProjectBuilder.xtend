package tools.vitruv.framework.domains.ui.builder

import org.apache.log4j.Logger
import java.util.Set
import org.eclipse.core.resources.IncrementalProjectBuilder
import tools.vitruv.framework.vsum.VirtualModel
import org.eclipse.core.resources.IProject
import java.util.Map
import org.eclipse.core.runtime.IProgressMonitor
import org.eclipse.core.runtime.CoreException
import static extension tools.vitruv.framework.domains.ui.builder.VitruvProjectBuilderArguments.getVirtualModelFolder
import static extension tools.vitruv.framework.domains.ui.builder.VitruvProjectBuilderArguments.getFileExtensions
import tools.vitruv.framework.vsum.VirtualModelManager

abstract class VitruvProjectBuilder extends IncrementalProjectBuilder {
	static final Logger logger = Logger.getLogger(VitruvProjectBuilder)

	boolean initialized = false

	protected def VirtualModel getVirtualModel() {
		VirtualModelManager.instance.getVirtualModel(command.virtualModelFolder)
	}

	protected def Set<String> getMonitoredFileExtensions() {
		command.fileExtensions
	}
	
	/**
	 * The builder is supposed to monitor only changes of files having one of the monitored file extensions,
	 * according to {@link #getMonitoredFileExtensions()}. Not having any file extensions explicitly specified means
	 * that the builder will monitor changes of every file it can process. 
	 * 
	 * @param fileExtension the file extension to query for being monitored, must not be {@code null}
	 * @return {@code true} if the given file extension is monitored, either because it is specified as a
	 * monitored file extension according to {@link #getMonitoredFileExtensions} or because no restriction of
	 * monitored file extensions is specified, i.e., the return value of 
	 * {@link #getMonitoredFileExtensions} is empty; {@code false} otherwise 
	 */
    protected def boolean isMonitoringChangesOfFilesWithExtension(String fileExtension) {
    	return getMonitoredFileExtensions().isEmpty() || (!fileExtension.nullOrEmpty && getMonitoredFileExtensions().contains(fileExtension));
    }

	private def void initializeBuilder() {
		startMonitoring
		initialized = true
		logger.debug('''Started monitoring for builder «this.class.simpleName» for project «project»''')
	}

	protected override IProject[] build(int kind, Map<String, String> args,
		IProgressMonitor monitor) throws CoreException {
		if (!initialized) {
			initializeBuilder
		}
		return null
	}

	protected abstract def void startMonitoring()

}
