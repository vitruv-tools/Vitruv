package tools.vitruv.framework.ui.monitorededitor

import org.apache.log4j.Logger
import java.util.Set
import org.eclipse.core.resources.IncrementalProjectBuilder
import tools.vitruv.framework.vsum.VirtualModel
import org.eclipse.core.resources.IProject
import java.util.Map
import org.eclipse.core.runtime.IProgressMonitor
import org.eclipse.core.runtime.CoreException
import static extension tools.vitruv.framework.ui.monitorededitor.VitruvProjectBuilderArguments.getVirtualModelFolder
import static extension tools.vitruv.framework.ui.monitorededitor.VitruvProjectBuilderArguments.getFileExtensions
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
