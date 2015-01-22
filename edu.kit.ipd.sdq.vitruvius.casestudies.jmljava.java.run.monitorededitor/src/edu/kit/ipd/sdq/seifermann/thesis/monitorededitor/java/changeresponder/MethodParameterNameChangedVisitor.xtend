package edu.kit.ipd.sdq.seifermann.thesis.monitorededitor.java.changeresponder

import edu.kit.ipd.sdq.seifermann.thesis.monitorededitor.java.events.MethodParameterNameChangedEvent
import edu.kit.ipd.sdq.vitruvius.framework.model.monitor.ChangeSubmitter
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.attribute.AttributeFactory
import org.eclipse.emf.ecore.EAttribute
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.EMFModelChange
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI

class MethodParameterNameChangedVisitor extends VisitorBase<MethodParameterNameChangedEvent> {
	
	override protected getTreatedClassInternal() {
		return MethodParameterNameChangedEvent
	}
	
	override protected visitInternal(MethodParameterNameChangedEvent changeClassifyingEvent, ChangeSubmitter submitter) {
		val originalMethod = changeClassifyingEvent.originalCompilationUnit.getMethodForMethodDeclaration(changeClassifyingEvent.originalElement)
		val changedMethod = changeClassifyingEvent.changedCompilationUnit.getMethodForMethodDeclaration(changeClassifyingEvent.changedElement)
		val originalParam = originalMethod.parameters.findFirst[name.equals(changeClassifyingEvent.paramOriginal.name.identifier)]
		val changedParam = changedMethod.parameters.findFirst[name.equals(changeClassifyingEvent.paramChanged.name.identifier)]
		
		val change = AttributeFactory.eINSTANCE.createUpdateSingleValuedEAttribute
		change.newAffectedEObject = changedParam
		change.oldAffectedEObject = originalParam
		change.affectedFeature = originalParam.eClass.getEStructuralFeature("name") as EAttribute
		change.newValue = change.newAffectedEObject.eGet(change.affectedFeature)
		change.oldValue = change.oldAffectedEObject.eGet(change.affectedFeature)
		
		submitter.submitChange(new EMFModelChange(change, VURI.getInstance(change.oldAffectedEObject.eResource)))
	}
	
}