package tools.vitruv.framework.propagation.impl

import tools.vitruv.framework.userinteraction.UserInteractor
import tools.vitruv.framework.propagation.ChangePropagationSpecification
import tools.vitruv.framework.propagation.ChangePropagationObserver
import java.util.List
import org.eclipse.emf.ecore.EObject
import tools.vitruv.framework.change.MetamodelDescriptor

abstract class AbstractChangePropagationSpecification implements ChangePropagationSpecification {
	val List<ChangePropagationObserver> propagationObserver;
	var UserInteractor userInteractor;
	var MetamodelDescriptor sourceMetamodelDescriptor;
	var MetamodelDescriptor targetMetamodelDescriptor;

	new(MetamodelDescriptor sourceMetamodelDescriptor, MetamodelDescriptor targetMetamodelDescriptor) {
		this.sourceMetamodelDescriptor = sourceMetamodelDescriptor
		this.targetMetamodelDescriptor = targetMetamodelDescriptor
		this.propagationObserver = newArrayList();
	}

	protected def UserInteractor getUserInteractor() {
		return userInteractor;
	}

	override getSourceMetamodelDescriptor() {
		return sourceMetamodelDescriptor;
	}

	override getTargetMetamodelDescriptor() {
		return targetMetamodelDescriptor;
	}

	override setUserInteractor(UserInteractor userInteractor) {
		this.userInteractor = userInteractor;
	}

	override registerObserver(ChangePropagationObserver observer) {
		if (observer !== null) {
			this.propagationObserver += observer;
		}
	}

	override deregisterObserver(ChangePropagationObserver observer) {
		this.propagationObserver -= observer;
	}

	override notifyObjectCreated(EObject createdObject) {
		this.propagationObserver.forEach[it.objectCreated(createdObject)];
	}

}
