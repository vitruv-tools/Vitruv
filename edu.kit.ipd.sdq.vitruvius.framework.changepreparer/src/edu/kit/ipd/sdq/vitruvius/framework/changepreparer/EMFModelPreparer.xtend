package edu.kit.ipd.sdq.vitruvius.framework.changepreparer

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.EMFModelChange
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.CorrespondenceProviding
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.ModelProviding
import edu.kit.ipd.sdq.vitruvius.framework.contracts.util.bridges.EMFCommandBridge
import java.util.concurrent.Callable
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.FeatureEChange

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
			if (eChange instanceof FeatureEChange<?,?>) {
				val featureChange = eChange as FeatureEChange<?,?>
				val affectedEObject = featureChange.affectedEObject
				// TODO HK What does this do? 
				if (affectedEObject != null) {
					val correspondenceInstances = this.correspondenceProviding.
						getOrCreateAllCorrespondenceInstances(emc.URI)
					for (correspondenceInstance : correspondenceInstances) {
						EMFCommandBridge.createAndExecuteVitruviusRecordingCommand(new Callable<Void>() {
							override call() throws Exception {
								//correspondenceInstance.updateTUID(oldEObject, newEObject)
								return null
							}

						}, this.modelProviding)
					}
				}
			}
			return emc
		} else {
			throw new IllegalArgumentException("Cannot prepare the change '" + change + "' as EMFModelChange")
		}
	}

}
