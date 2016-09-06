package edu.kit.ipd.sdq.vitruvius.applications.jmljava.synchronizers.java

import edu.kit.ipd.sdq.vitruvius.applications.jmljava.synchronizers.SynchronisationAbortedListener
import com.google.inject.Inject
import edu.kit.ipd.sdq.vitruvius.applications.jmljava.synchronizers.EObjectMappingTransformationBase
import edu.kit.ipd.sdq.vitruvius.framework.change.description.GeneralChange
import org.apache.log4j.Logger

abstract class AbortableEObjectMappingTransformationBase extends EObjectMappingTransformationBase{
	
	val private static Logger logger = Logger.getLogger(AbortableEObjectMappingTransformationBase) 
	var private GeneralChange syncAbortChange;
	
	protected SynchronisationAbortedListener syncAbortedListener
	
	@Inject
	new (SynchronisationAbortedListener syncAbortedListener){
		this.syncAbortedListener = syncAbortedListener
	}
	
	
	
	def protected GeneralChange getSynchAbortChange(){
		if(null == syncAbortChange){
			logger.warn("Can not create useful synchAbort info cause syncAbortChange is null")	
		}
		return syncAbortChange
	}
	
	def public setSyncAbortChange(GeneralChange syncAbortChange){
		this.syncAbortChange = syncAbortChange
	}
}