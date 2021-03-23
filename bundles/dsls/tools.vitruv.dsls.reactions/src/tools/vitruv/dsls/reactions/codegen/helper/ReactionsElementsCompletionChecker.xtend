package tools.vitruv.dsls.reactions.codegen.helper

import tools.vitruv.dsls.reactions.language.toplevelelements.Reaction
import tools.vitruv.dsls.reactions.language.toplevelelements.Routine
import tools.vitruv.dsls.reactions.language.toplevelelements.RoutineOverrideImportPath
import static extension tools.vitruv.dsls.reactions.util.ReactionsLanguageUtil.*
import tools.vitruv.dsls.reactions.language.toplevelelements.ReactionsSegment
import edu.kit.ipd.sdq.activextendannotations.Utility
import tools.vitruv.dsls.common.elements.DomainReference
import tools.vitruv.dsls.reactions.language.toplevelelements.ReactionsImport

@Utility
class ReactionsElementsCompletionChecker {

 	def static boolean isReferenceable(ReactionsSegment reactionsSegment) {
		return reactionsSegment !== null
			&& !reactionsSegment.name.nullOrEmpty
			&& reactionsSegment.fromDomain.isReferenceable
			&& reactionsSegment.toDomain.isReferenceable
	}
	
	def static boolean isReferenceable(DomainReference domainReference) {
		return domainReference !== null
			&& domainReference.domain !== null
	}
	
	def static boolean isReferenceable(ReactionsImport reactionsImport) {
		return reactionsImport !== null 
			&& reactionsImport.importedReactionsSegment !== null
	}
	
	def static boolean isReferenceable(Reaction reaction) {
		return reaction !== null
			&& !reaction.name.nullOrEmpty

	}
	
	def static boolean isReferenceable(Routine routine) {
		return routine !== null
			&& !routine.name.nullOrEmpty
	}
	
	// note: this triggers a resolve of cross-references
	def static boolean isComplete(RoutineOverrideImportPath routineOverrideImportPath) {
		return routineOverrideImportPath !== null
			&& !routineOverrideImportPath.fullPath.exists[it.reactionsSegment === null || it.reactionsSegment.name.nullOrEmpty]
	}
	
}