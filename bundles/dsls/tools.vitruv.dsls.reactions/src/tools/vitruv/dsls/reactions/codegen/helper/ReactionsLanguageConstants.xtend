package tools.vitruv.dsls.reactions.codegen.helper

import edu.kit.ipd.sdq.activextendannotations.Utility

@Utility class ReactionsLanguageConstants {
	public static val RETRIEVAL_PRECONDITION_METHOD_TARGET = "potentialTarget"
	private static val USER_INTERACTING_NAME = "userInteracting";
	public static val USER_INTERACTING_PARAMETER_NAME = USER_INTERACTING_NAME;
	public static val USER_INTERACTING_FIELD_NAME = USER_INTERACTING_NAME;
	private static val REACTION_EXECUTION_STATE_NAME = "reactionExecutionState";
	public static val REACTION_EXECUTION_STATE_PARAMETER_NAME = REACTION_EXECUTION_STATE_NAME;
	public static val REACTION_EXECUTION_STATE_FIELD_NAME = REACTION_EXECUTION_STATE_NAME;
	public static val CHANGE_PARAMETER_NAME = "change";
	
	public static val EFFECT_FACADE_FIELD_NAME = "actionsFacade";
	public static val EFFECT_FACADE_CALLED_BY_FIELD_NAME = "calledBy";
	
	public static val EFFECT_USER_EXECUTION_SIMPLE_NAME = "ActionUserExecution";
	public static val REACTION_USER_EXECUTION_ROUTINE_CALL_FACADE_PARAMETER_NAME = "_routinesFacade"
	
	public static val CHANGE_AFFECTED_ELEMENT_ATTRIBUTE = "affectedEObject"
	public static val CHANGE_AFFECTED_FEATURE_ATTRIBUTE = "affectedFeature"
	public static val CHANGE_OLD_VALUE_ATTRIBUTE = "oldValue"
	public static val CHANGE_NEW_VALUE_ATTRIBUTE = "newValue"
}