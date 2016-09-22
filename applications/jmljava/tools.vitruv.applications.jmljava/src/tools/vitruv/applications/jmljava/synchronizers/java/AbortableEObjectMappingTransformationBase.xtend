package tools.vitruv.applications.jmljava.synchronizers.java

import tools.vitruv.applications.jmljava.synchronizers.SynchronisationAbortedListener
import com.google.inject.Inject
import tools.vitruv.applications.jmljava.synchronizers.EObjectMappingTransformationBase
import tools.vitruv.framework.change.description.ConcreteChange
import org.apache.log4j.Logger

abstract class AbortableEObjectMappingTransformationBase extends EObjectMappingTransformationBase{
	
	val private static Logger logger = Logger.getLogger(AbortableEObjectMappingTransformationBase) 
	var private ConcreteChange syncAbortChange;
	
	protected SynchronisationAbortedListener syncAbortedListener
	
	@Inject
	new (SynchronisationAbortedListener syncAbortedListener){
		this.syncAbortedListener = syncAbortedListener
	}
	
	
	
	def protected ConcreteChange getSynchAbortChange(){
		if(null == syncAbortChange){
			logger.warn("Can not create useful synchAbort info cause syncAbortChange is null")	
		}
		return syncAbortChange
	}
	
	def public setSyncAbortChange(ConcreteChange syncAbortChange){
		this.syncAbortChange = syncAbortChange
	}
}