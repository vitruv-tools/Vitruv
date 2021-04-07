package tools.vitruv.framework.propagation.impl

import tools.vitruv.framework.userinteraction.UserInteractor
import tools.vitruv.framework.propagation.ChangePropagationSpecification
import tools.vitruv.framework.domains.VitruvDomain
import tools.vitruv.framework.propagation.ChangePropagationObserver
import java.util.List
import org.eclipse.emf.ecore.EObject

abstract class AbstractChangePropagationSpecification implements ChangePropagationSpecification {
	val List<ChangePropagationObserver> propagationObserver;
	var UserInteractor userInteractor;
	var VitruvDomain sourceDomain;
	var VitruvDomain targetDomain;
	
	new(VitruvDomain sourceDomain, VitruvDomain targetDomain) {
		setVitruvDomains(sourceDomain, targetDomain);
		this.propagationObserver = newArrayList();
	}
	
	protected def UserInteractor getUserInteractor() {
		return userInteractor;
	}
	
	private def setVitruvDomains(VitruvDomain sourceDomain, VitruvDomain targetDomain) {
		this.sourceDomain = sourceDomain;
		this.targetDomain = targetDomain;
	}
	
	override getSourceDomain() {
		return sourceDomain;
	}
	
	override getTargetDomain() {
		return targetDomain;
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
