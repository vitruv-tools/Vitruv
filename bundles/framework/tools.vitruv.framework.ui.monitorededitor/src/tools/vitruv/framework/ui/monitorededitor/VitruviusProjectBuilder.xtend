package tools.vitruv.framework.ui.monitorededitor

import org.apache.log4j.Logger
import java.util.Set
import tools.vitruv.framework.vsum.VirtualModelManager
import org.eclipse.core.resources.IncrementalProjectBuilder
import tools.vitruv.framework.vsum.VirtualModel
import java.util.HashSet
import org.eclipse.core.resources.IProject
import java.util.Map
import org.eclipse.core.runtime.IProgressMonitor
import org.eclipse.core.runtime.CoreException
import java.io.File
import tools.vitruv.framework.domains.VitruviusProjectBuilderApplicator
import org.eclipse.xtend.lib.annotations.Accessors

abstract class VitruviusProjectBuilder extends IncrementalProjectBuilder {
	static final Logger logger = Logger.getLogger(VitruviusProjectBuilder)

	@Accessors(PROTECTED_GETTER)
	final Set<String> monitoredFileTypes
	@Accessors(PROTECTED_GETTER)
	VirtualModel virtualModel
	boolean initialized

	new() {
		this.initialized = false
		this.monitoredFileTypes = new HashSet<String>()
	}

	private def void initializeBuilder() {
		val vmodelFolderName = command.arguments.get(VitruviusProjectBuilderApplicator.ARGUMENT_VMODEL_NAME)
		virtualModel = VirtualModelManager.instance.getVirtualModel(new File(vmodelFolderName))
		monitoredFileTypes +=
			command.arguments.get(VitruviusProjectBuilderApplicator.ARGUMENT_FILE_EXTENSIONS).split("\\s*,\\s*").filter [
				!nullOrEmpty
			]
		initialized = true
		logger.debug("Initialized builder reference to virtual model")
		startMonitoring
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
