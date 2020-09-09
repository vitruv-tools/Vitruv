package tools.vitruv.dsls.reactions.codegen

import edu.kit.ipd.sdq.activextendannotations.Utility

@Utility class ReactionsLanguageConstants {
	public static val OVERRIDDEN_REACTIONS_SEGMENT_SEPARATOR = "::";
	
	public static val RETRIEVAL_PRECONDITION_METHOD_TARGET = "potentialTarget"
	static val ROUTINES_FACADE_NAME = "routinesFacade";
	public static val ROUTINES_FACADE_PARAMETER_NAME = ROUTINES_FACADE_NAME;
	public static val ROUTINES_FACADE_FIELD_NAME = ROUTINES_FACADE_NAME;
	static val USER_INTERACTING_NAME = "userInteractor";
	public static val USER_INTERACTING_PARAMETER_NAME = USER_INTERACTING_NAME;
	public static val USER_INTERACTING_FIELD_NAME = USER_INTERACTING_NAME;
	static val REACTION_EXECUTION_STATE_NAME = "reactionExecutionState";
	public static val REACTION_EXECUTION_STATE_PARAMETER_NAME = REACTION_EXECUTION_STATE_NAME;
	public static val REACTION_EXECUTION_STATE_FIELD_NAME = REACTION_EXECUTION_STATE_NAME;
	public static val CHANGE_PARAMETER_NAME = "change";
	
	public static val ROUTINES_FACADE_CALLER_FIELD_NAME = "caller";
	
	public static val EFFECT_USER_EXECUTION_SIMPLE_NAME = "ActionUserExecution";
	public static val REACTION_USER_EXECUTION_ROUTINE_CALL_FACADE_PARAMETER_NAME = "_routinesFacade"
	
	public static val CHANGE_AFFECTED_ELEMENT_ATTRIBUTE = "affectedEObject"
	public static val CHANGE_AFFECTED_FEATURE_ATTRIBUTE = "affectedFeature"
	public static val CHANGE_OLD_VALUE_ATTRIBUTE = "oldValue"
	public static val CHANGE_NEW_VALUE_ATTRIBUTE = "newValue"
	public static val CHANGE_INDEX_ATTRIBUTE = "index"
}