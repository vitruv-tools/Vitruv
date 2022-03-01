package tools.vitruv.framework.propagation.impl

import tools.vitruv.framework.userinteraction.UserInteractor
import tools.vitruv.framework.propagation.ChangePropagationSpecification
import tools.vitruv.framework.propagation.ChangePropagationObserver
import java.util.List
import org.eclipse.emf.ecore.EObject
import java.util.Set
import java.util.HashSet

abstract class AbstractChangePropagationSpecification implements ChangePropagationSpecification {
	val List<ChangePropagationObserver> propagationObserver;
	var UserInteractor userInteractor;
	var Set<String> sourceMetamodelRootNsUris;
	var Set<String> targetMetamodelRootNsUris;

	new(Set<String> sourceMetamodelRootNsUris, Set<String> targetMetamodelRootNsUris) {
		this.sourceMetamodelRootNsUris = new HashSet(sourceMetamodelRootNsUris)
		this.targetMetamodelRootNsUris = new HashSet(targetMetamodelRootNsUris)
		this.propagationObserver = newArrayList();
	}

	protected def UserInteractor getUserInteractor() {
		return userInteractor;
	}

	override getSourceMetamodelRootNsUris() {
		return new HashSet(sourceMetamodelRootNsUris);
	}

	override getTargetMetamodelRootNsUris() {
		return new HashSet(targetMetamodelRootNsUris);
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
