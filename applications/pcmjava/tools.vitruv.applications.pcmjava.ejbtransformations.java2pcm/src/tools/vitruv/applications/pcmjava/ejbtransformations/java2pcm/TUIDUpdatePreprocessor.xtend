package tools.vitruv.applications.pcmjava.ejbtransformations.java2pcm

import tools.vitruv.framework.userinteraction.UserInteracting
import tools.vitruv.domains.java.echange.feature.JavaFeatureEChange
import tools.vitruv.framework.correspondence.CorrespondenceModel
import tools.vitruv.framework.tuid.TuidManager
import tools.vitruv.framework.change.processing.impl.AbstractEChangeProcessor
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.util.command.TransformationResult
import tools.vitruv.framework.util.datatypes.MetamodelPair
import tools.vitruv.domains.java.util.JaMoPPNamespace
import tools.vitruv.domains.pcm.util.PCMNamespace

class TUIDUpdatePreprocessor extends AbstractEChangeProcessor {
	private val MetamodelPair metamodelPair;
	
	new(UserInteracting userInteracting) {
		super(userInteracting);
		this.metamodelPair = new MetamodelPair(JaMoPPNamespace.JAMOPP_METAMODEL_NAMESPACE, PCMNamespace.PCM_METAMODEL_NAMESPACE);
	}
	
	override getMetamodelPair() {
		return metamodelPair;
	}
	
	override doesHandleChange(EChange change, CorrespondenceModel correspondenceModel) {
		return change instanceof JavaFeatureEChange<?, ?>;
	}
	
	override propagateChange(EChange change, CorrespondenceModel correspondenceModel) {
		if (change instanceof JavaFeatureEChange<?, ?>) {
			val oldAffectedEObject = change.oldAffectedEObject
			val newAffectedEObject = change.affectedEObject
			if (null != oldAffectedEObject && null != newAffectedEObject) {
				TuidManager.instance.updateTuid(oldAffectedEObject, newAffectedEObject);
			}
		}
		return new TransformationResult();
	}

}
