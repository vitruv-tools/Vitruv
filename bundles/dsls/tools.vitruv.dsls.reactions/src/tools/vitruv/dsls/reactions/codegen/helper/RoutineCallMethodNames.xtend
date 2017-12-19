package tools.vitruv.dsls.reactions.codegen.helper

import tools.vitruv.dsls.reactions.reactionsLanguage.Routine
import tools.vitruv.dsls.reactions.reactionsLanguage.RoutinesImport

import static tools.vitruv.dsls.reactions.codegen.helper.ReactionsLanguageConstants.*

final class RoutineCallMethodNames {

	private new() {
	}

	public static def String getCallMethodName(Routine routine) {
		return routine.name;
	}

	public static def String getImportedCallMethodName(RoutinesImport routinesImport, Routine routine) {
		val prefix = routinesImport.prefix;
		if (prefix === null || prefix.isEmpty) return routine.callMethodName;
		return prefix + IMPORTED_ROUTINE_PREFIX_SEPARATOR + routine.callMethodName;
	}
}
