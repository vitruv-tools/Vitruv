package edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.synchronizers.java

import edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.synchronizers.SynchronisationAbortedListener
import com.google.inject.Inject
import edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.synchronizers.EObjectMappingTransformationBase
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.EMFModelChange
import org.apache.log4j.Logger

abstract class AbortableEObjectMappingTransformationBase extends EObjectMappingTransformationBase{
	
	val private static Logger logger = Logger.getLogger(AbortableEObjectMappingTransformationBase) 
	var private EMFModelChange syncAbortChange;
	
	protected SynchronisationAbortedListener syncAbortedListener
	
	@Inject
	new (SynchronisationAbortedListener syncAbortedListener){
		this.syncAbortedListener = syncAbortedListener
	}
	
	
	
	def protected EMFModelChange getSynchAbortChange(){
		if(null == syncAbortChange){
			logger.warn("Can not create useful synchAbort info cause syncAbortChange is null")	
		}
		return syncAbortChange
	}
	
	def public setSyncAbortChange(EMFModelChange syncAbortChange){
		this.syncAbortChange = syncAbortChange
	}
}