package edu.kit.ipd.sdq.seifermann.thesis.monitorededitor.java.changeresponder

import edu.kit.ipd.sdq.seifermann.thesis.monitorededitor.java.events.MethodBodyChangedEvent
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CompositeChange
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.EMFModelChange
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.ContainmentFactory
import edu.kit.ipd.sdq.vitruvius.framework.model.monitor.ChangeSubmitter
import org.emftext.language.java.members.ClassMethod
import org.emftext.language.java.statements.StatementsPackage

class MethodBodyChangedVisitor extends VisitorBase<MethodBodyChangedEvent> {
	
	override protected getTreatedClassInternal() {
		MethodBodyChangedEvent
	}
	
	override protected visitInternal(MethodBodyChangedEvent changeClassifyingEvent, ChangeSubmitter submitter) {
		val originalMethod = changeClassifyingEvent.originalCompilationUnit.getMethodOrConstructorForMethodDeclaration(changeClassifyingEvent.originalElement)
		val changedMethod = changeClassifyingEvent.changedCompilationUnit.getMethodOrConstructorForMethodDeclaration(changeClassifyingEvent.changedElement)
 
		val compositeChange = new CompositeChange()
		val changeURI = VURI.getInstance(originalMethod.eResource)

		for (stmt : (originalMethod as ClassMethod).statements) {
			val change = ContainmentFactory.eINSTANCE.createDeleteNonRootEObjectInList
			change.oldAffectedEObject = originalMethod
			change.newAffectedEObject = changedMethod
			change.affectedFeature = StatementsPackage.eINSTANCE.statementListContainer_Statements
			change.oldValue = stmt
			change.index = (originalMethod as ClassMethod).statements.indexOf(stmt)
			compositeChange.addChange(new EMFModelChange(change, changeURI))
		}
		
		for (stmt : (changedMethod as ClassMethod).statements) {
			val change = ContainmentFactory.eINSTANCE.createCreateNonRootEObjectInList
			change.oldAffectedEObject = originalMethod
			change.newAffectedEObject = changedMethod
			change.affectedFeature = StatementsPackage.eINSTANCE.statementListContainer_Statements
			change.newValue = stmt
			change.index = (changedMethod as ClassMethod).statements.indexOf(stmt)
			compositeChange.addChange(new EMFModelChange(change, changeURI))
		}
		
		submitter.submitChange(compositeChange)
	}
	
}