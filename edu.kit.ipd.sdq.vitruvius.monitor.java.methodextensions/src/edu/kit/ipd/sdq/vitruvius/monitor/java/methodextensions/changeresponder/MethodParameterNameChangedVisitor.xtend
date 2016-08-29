package edu.kit.ipd.sdq.vitruvius.monitor.java.methodextensions.changeresponder

import edu.kit.ipd.sdq.vitruvius.framework.model.monitor.ChangeSubmitter
import org.eclipse.emf.ecore.EAttribute
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.VURI
import edu.kit.ipd.sdq.vitruvius.monitor.java.methodextensions.events.MethodParameterNameChangedEvent
import edu.kit.ipd.sdq.vitruvius.domains.java.echange.feature.attribute.AttributeFactory
import edu.kit.ipd.sdq.vitruvius.framework.change.description.VitruviusChangeFactory

class MethodParameterNameChangedVisitor extends VisitorBase<MethodParameterNameChangedEvent> {
	
	override protected getTreatedClassInternal() {
		return MethodParameterNameChangedEvent
	}
	
	override protected visitInternal(MethodParameterNameChangedEvent changeClassifyingEvent, ChangeSubmitter submitter) {
		val originalMethod = changeClassifyingEvent.originalCompilationUnit.getMethodOrConstructorForMethodDeclaration(changeClassifyingEvent.originalElement)
		val changedMethod = changeClassifyingEvent.changedCompilationUnit.getMethodOrConstructorForMethodDeclaration(changeClassifyingEvent.changedElement)
		val originalParam = originalMethod.parameters.findFirst[name.equals(changeClassifyingEvent.paramOriginal.name.identifier)]
		val changedParam = changedMethod.parameters.findFirst[name.equals(changeClassifyingEvent.paramChanged.name.identifier)]
		 
		val change = AttributeFactory.eINSTANCE.createJavaReplaceSingleValuedEAttribute
		change.affectedEObject = changedParam
		change.oldAffectedEObject = originalParam
		change.affectedFeature = originalParam.eClass.getEStructuralFeature("name") as EAttribute
		change.newValue = change.affectedEObject.eGet(change.affectedFeature)
		change.oldValue = change.oldAffectedEObject.eGet(change.affectedFeature)
		
		submitter.submitChange(VitruviusChangeFactory.instance.createGeneralChange(#[change], VURI.getInstance(change.oldAffectedEObject.eResource)));
	}
	
}