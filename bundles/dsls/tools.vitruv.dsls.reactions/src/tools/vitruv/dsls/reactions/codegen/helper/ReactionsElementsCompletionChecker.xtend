package tools.vitruv.dsls.reactions.codegen.helper

import tools.vitruv.dsls.reactions.language.RetrieveModelElement
import org.eclipse.emf.ecore.EClass
import tools.vitruv.dsls.reactions.language.toplevelelements.Reaction
import tools.vitruv.dsls.reactions.language.toplevelelements.Routine
import tools.vitruv.dsls.reactions.language.toplevelelements.RoutineOverrideImportPath
import static extension tools.vitruv.dsls.reactions.util.ReactionsLanguageUtil.*
import tools.vitruv.dsls.reactions.language.RequireAbscenceOfModelElement
import tools.vitruv.dsls.reactions.language.CreateModelElement
import tools.vitruv.dsls.reactions.language.DeleteModelElement
import tools.vitruv.dsls.reactions.language.UpdateModelElement
import tools.vitruv.dsls.reactions.language.CreateCorrespondence
import tools.vitruv.dsls.reactions.language.toplevelelements.CodeBlock
import tools.vitruv.dsls.reactions.language.toplevelelements.RoutineInput
import tools.vitruv.dsls.reactions.language.toplevelelements.NamedJavaElementReference
import tools.vitruv.dsls.common.elements.NamedMetaclassReference
import tools.vitruv.dsls.reactions.language.ModelAttributeChange
import tools.vitruv.dsls.common.elements.MetaclassEAttributeReference
import tools.vitruv.dsls.common.elements.MetaclassEReferenceReference
import tools.vitruv.dsls.reactions.language.ModelElementChange
import tools.vitruv.dsls.reactions.language.toplevelelements.ReactionsSegment
import tools.vitruv.dsls.common.elements.MetaclassReference
import static extension tools.vitruv.dsls.reactions.codegen.helper.ReactionsLanguageHelper.*
import edu.kit.ipd.sdq.activextendannotations.Utility
import tools.vitruv.dsls.common.elements.DomainReference
import tools.vitruv.dsls.reactions.language.ArbitraryModelChange
import tools.vitruv.dsls.reactions.language.RoutineCallStatement
import tools.vitruv.dsls.reactions.language.ExecuteActionStatement
import tools.vitruv.dsls.reactions.language.RemoveCorrespondence

@Utility
class ReactionsElementsCompletionChecker {
	
 	def static dispatch boolean isComplete(ReactionsSegment reactionsSegment) {
		return reactionsSegment.name !== null
			&& reactionsSegment.fromDomain !== null
			&& reactionsSegment.fromDomain.isComplete
			&& reactionsSegment.toDomain !== null
			&& reactionsSegment.toDomain.isComplete
			&& (reactionsSegment.reactions === null || reactionsSegment.reactions.forall[it !== null && it.isComplete])
			&& (reactionsSegment.routines === null ||  reactionsSegment.routines.forall[it !== null && it.isComplete])
	}
	
	def static dispatch boolean isComplete(DomainReference domainReference) {
		return domainReference.domain !== null
	}
	
	def static dispatch boolean isComplete(Reaction reaction) {
		return reaction.name !== null
			&& reaction.trigger !== null
			&& reaction.trigger.isComplete
			&& reaction.callRoutine !== null
			&& reaction.callRoutine.isComplete
	}
	
	def static dispatch boolean isComplete(ArbitraryModelChange arbitraryChange) {
		return true
	}
	
	def static dispatch boolean isComplete(ModelAttributeChange attributeChange) {
		return attributeChange.feature !== null
			&& attributeChange.feature.isComplete
	}
	
	def static dispatch boolean isComplete(ModelElementChange elementChange) {
		return (elementChange.elementType === null || elementChange.elementType.isComplete)
			&& elementChange.changeType !== null
	}
	
	def static dispatch boolean isComplete(Routine routine) {
		return routine.name !== null
			&& routine.input !== null
			&& routine.input.isComplete
			&& (routine.matcher === null || routine.matcher.matcherStatements.forall[it !== null && isComplete])
			&& routine.action !== null
			&& routine.action.actionStatements.forall[it !== null && it.isComplete]
	}
	
	// note: this triggers a resolve of cross-references
	def static dispatch boolean isComplete(RoutineOverrideImportPath routineOverrideImportPath) {
		return !routineOverrideImportPath.fullPath.exists[it.reactionsSegment === null || it.reactionsSegment.name === null]
	}
	
	def static dispatch boolean isComplete(RoutineInput routineInput) {
		return routineInput.modelInputElements.forall[it !== null && isComplete]
			&& routineInput.javaInputElements.forall[it !== null && isComplete]
	}
	
	def static dispatch boolean isComplete(RetrieveModelElement retrievelElementStatement) {
		return retrievelElementStatement.elementType !== null
			&& retrievelElementStatement.elementType.isComplete
			&& retrievelElementStatement.correspondenceSource !== null
			&& retrievelElementStatement.correspondenceSource.isComplete
	}
	
	def static dispatch boolean isComplete(RequireAbscenceOfModelElement requireAbscenceStatement) {
		return requireAbscenceStatement.elementType.isComplete
	}
	
	def static dispatch boolean isComplete(ExecuteActionStatement executeActionStatement) {
		return executeActionStatement.code !== null
	}
	
	def static dispatch boolean isComplete(RoutineCallStatement routineCallStatement) {
		return routineCallStatement.code !== null
	}
	
	def static dispatch boolean isComplete(CreateModelElement createModelElement) {
		return createModelElement.name !== null 
			&& createModelElement.elementType !== null
			&& createModelElement.elementType.isComplete
	}
	
	def static dispatch boolean isComplete(DeleteModelElement deleteModelElement) {
		return deleteModelElement.element !== null
			&& deleteModelElement.element.isComplete
	}
	
	def static dispatch boolean isComplete(UpdateModelElement updateModelElement) {
		return updateModelElement.element !== null
			&& updateModelElement.element.isComplete
			&& updateModelElement.updateBlock !== null
			&& updateModelElement.updateBlock.isComplete
	}
	
	def static dispatch boolean isComplete(CreateCorrespondence createCorrespondence) {
		return createCorrespondence.firstElement !== null
			&& createCorrespondence.firstElement.isComplete
			&& createCorrespondence.secondElement !== null
			&& createCorrespondence.secondElement.isComplete
	}
	
	def static dispatch boolean isComplete(RemoveCorrespondence removeCorrespondence) {
		return removeCorrespondence.firstElement !== null
			&& removeCorrespondence.firstElement.isComplete
			&& removeCorrespondence.secondElement !== null
			&& removeCorrespondence.secondElement.isComplete
	}
	
	def static dispatch boolean isComplete(CodeBlock codeBlock) {
		return codeBlock.code !== null
	}
	
	def static dispatch boolean isComplete(MetaclassReference metaclassReference) {
		return metaclassReference.metaclass !== null
			&& metaclassReference.metaclass.isComplete
	}
	
	def static dispatch boolean isComplete(MetaclassEAttributeReference metaclassEAttributeReference) {
		return metaclassEAttributeReference.metaclass !== null
			&& metaclassEAttributeReference.metaclass.isComplete
			&& metaclassEAttributeReference.feature !== null
	}
	
	def static dispatch boolean isComplete(MetaclassEReferenceReference metaclassEReferenceReference) {
		return metaclassEReferenceReference.metaclass !== null
			&& metaclassEReferenceReference.metaclass.isComplete
			&& metaclassEReferenceReference.feature !== null
	}
	
	def static dispatch boolean isComplete(NamedMetaclassReference metaclassReference) {
		return metaclassReference.name !== null
			&& metaclassReference.metaclass !== null
			&& metaclassReference.metaclass.isComplete
	}
	
	def static dispatch boolean isComplete(NamedJavaElementReference javaElementReference) {
		return javaElementReference.type !== null
			&& javaElementReference.type.qualifiedName !== null
	}
	
	def static dispatch boolean isComplete(EClass eClass) {
		return eClass.javaClassName !== null
	}
	
}