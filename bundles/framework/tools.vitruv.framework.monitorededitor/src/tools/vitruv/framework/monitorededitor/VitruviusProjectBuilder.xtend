package tools.vitruv.framework.monitorededitor

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
import java.util.Collection
import java.io.File
import tools.vitruv.framework.domains.VitruviusProjectBuilderApplicator

abstract class VitruviusProjectBuilder extends IncrementalProjectBuilder {
	private static final Logger logger = Logger.getLogger(VitruviusProjectBuilder.getSimpleName())
	
	private Set<String> monitoredFileTypes
	private VirtualModel virtualModel;
	private boolean initialized
	
	public new() {
		this.initialized = false;
		this.monitoredFileTypes = new HashSet<String>();
	}
	
	private def void initializeBuilder() {
		val vmodelFolderName = getCommand().getArguments().get(VitruviusProjectBuilderApplicator.ARGUMENT_VMODEL_NAME);
		this.virtualModel = VirtualModelManager.getInstance().getVirtualModel(new File(vmodelFolderName));

		for (String fileExtension : getCommand().getArguments().get(VitruviusProjectBuilderApplicator.ARGUMENT_FILE_EXTENSIONS).split(",").map[trim].filter[!nullOrEmpty]) {
			monitoredFileTypes.add(fileExtension)
		}
		
		initialized = true
		logger.debug("Initialized builder reference to virtual model");
		this.startMonitoring(); 
	}
	
	protected override IProject[] build(int kind, Map<String, String> args, IProgressMonitor monitor) throws CoreException {
		if (!initialized) {
			initializeBuilder();
		}
		return null;
	}
	
	protected def VirtualModel getVirtualModel() {
		return virtualModel;
	}
	
	protected def Collection<String> getMonitoredFileTypes() {
		return monitoredFileTypes;
	}
	
	protected abstract def void startMonitoring();
	
}