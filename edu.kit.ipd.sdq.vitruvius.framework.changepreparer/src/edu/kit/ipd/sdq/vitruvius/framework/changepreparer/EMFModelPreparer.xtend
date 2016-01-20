package edu.kit.ipd.sdq.vitruvius.framework.changepreparer

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.EMFModelChange
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.ModelProviding
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.EFeatureChange
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.CorrespondenceProviding

package class EMFModelPreparer extends ConcreteChangePreparer {
	val ModelProviding modelProviding
	val CorrespondenceProviding correspondenceProviding
	
	new(ModelProviding modelProviding, CorrespondenceProviding correspondenceProviding) {
		super()
		this.modelProviding = modelProviding
		this.correspondenceProviding = correspondenceProviding
	}
	
	override Change prepareChange(Change change) {
		if (change instanceof EMFModelChange) {
			val emc = change as EMFModelChange
			this.modelProviding.forceReloadModelInstanceOriginalIfExisting(emc.URI)
			val eChange = emc.EChange
			if (eChange instanceof EFeatureChange<?>) {
				val featureChange = eChange as EFeatureChange<?>
				val oldEObject = featureChange.oldAffectedEObject
				val newEObject = featureChange.newAffectedEObject				
				val correspondenceInstances = this.correspondenceProviding.getOrCreateAllCorrespondenceInstances(emc.URI)
				for (correspondenceInstance : correspondenceInstances) {
					correspondenceInstance.updateTUID(oldEObject,newEObject)
				}
			}
			return emc
		} else {
			throw new IllegalArgumentException("Cannot prepare the change '" + change + "' as EMFModelChange")
		}
	}

}
