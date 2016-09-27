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

abstract class VitruviusProjectBuilder extends IncrementalProjectBuilder  {
	private static final Logger logger = Logger.getLogger(VitruviusProjectBuilder.getSimpleName())
	public static final String ARGUMENT_VMODEL_NAME = "vmodelName";
	public static final String ARGUMENT_FILE_EXTENSIONS = "fileExtensions";
	
	Set<String> monitoredFileTypes
	private VirtualModel virtualModel;
	boolean initialized
	
	public new() {
		this.initialized = false;
	}
	
	private def void initializeBuilder() {
		if (initialized) return;
		this.virtualModel = VirtualModelManager.getInstance().getVirtualModel(getCommand().getArguments().get(ARGUMENT_VMODEL_NAME)) 
		monitoredFileTypes = new HashSet<String>() 
		for (String fileExtension : getCommand().getArguments().get(ARGUMENT_FILE_EXTENSIONS).split(",")) {
			monitoredFileTypes.add(fileExtension) 
		}
		initialized = true
		logger.debug("Initialized builder reference to virtual model");
		this.startMonitoring(); 
	}
	
	protected override IProject[] build(int kind, Map<String, String> args, IProgressMonitor monitor) throws CoreException {
		initializeBuilder()
		return null;
	}
	
	protected def VirtualModel getVirtualModel() {
		return virtualModel;
	}
	
	protected abstract def void startMonitoring();
	
}