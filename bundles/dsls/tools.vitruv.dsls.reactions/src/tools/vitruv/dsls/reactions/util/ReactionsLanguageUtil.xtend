package tools.vitruv.dsls.reactions.util

import tools.vitruv.dsls.reactions.reactionsLanguage.Reaction
import tools.vitruv.dsls.reactions.reactionsLanguage.ReactionsLanguagePackage
import tools.vitruv.dsls.reactions.reactionsLanguage.ReactionsSegment
import tools.vitruv.dsls.reactions.reactionsLanguage.Routine
import static tools.vitruv.dsls.reactions.codegen.helper.ReactionsLanguageConstants.*
import static extension tools.vitruv.dsls.reactions.codegen.helper.ClassNamesGenerators.*
import static extension tools.vitruv.dsls.reactions.codegen.helper.ReactionsLanguageHelper.*;

/**
 * Utility methods working with the model objects of the Reactions Language, which might be of use outside of the code generation
 * package.
 */
final class ReactionsLanguageUtil {

	private new() {
	}

	/**
	 * Gets a formatted name for the given reaction.
	 * 
	 * <p>
	 * If the reaction overrides the reaction of another reactions segment, the returned name includes a formatted name of the
	 * overridden reactions segment.
	 * 
	 * @param reaction the reaction
	 * @return the formatted name
	 */
	public static def String getFormattedReactionName(Reaction reaction) {
		var reactionName = "";
		if (reaction.isOverrideReaction) {
			// not resolving cross-references here, if not required:
			var overriddenReactionsSegmentFormattedName = reaction.parsedOverriddenReactionsSegmentName;
			if (overriddenReactionsSegmentFormattedName !== null) {
				// format overridden reactions segment name:
				overriddenReactionsSegmentFormattedName = overriddenReactionsSegmentFormattedName.reactionsSegmentPackageName;
			} else {
				// We weren't able to get the overridden reactions segment's name from the node model,
				// so we try to get it from the reactions segment directly.
				// This might trigger a resolve of the cross-reference though:
				overriddenReactionsSegmentFormattedName = reaction.overriddenReactionsSegment.packageName;
			}
			reactionName += overriddenReactionsSegmentFormattedName + OVERRIDDEN_REACTIONS_SEGMENT_SEPARATOR;
		}
		reactionName += reaction.name.toFirstUpper;
		return reactionName;
	}

	/**
	 * Gets a formatted name for the given routine.
	 * 
	 * <p>
	 * If the routine overrides the routine of another reactions segment, the returned name includes a formatted name of the
	 * overridden reactions segment.
	 * 
	 * @param routine the routine
	 * @return the formatted name
	 */
	public static def String getFormattedRoutineName(Routine routine) {
		var routineName = "";
		if (routine.isOverrideRoutine) {
			// not resolving cross-references here, if not required:
			var overriddenReactionsSegmentFormattedName = routine.parsedOverriddenReactionsSegmentName;
			if (overriddenReactionsSegmentFormattedName !== null) {
				// format overridden reactions segment name:
				overriddenReactionsSegmentFormattedName = overriddenReactionsSegmentFormattedName.reactionsSegmentPackageName;
			} else {
				// We weren't able to get the overridden reactions segment's name from the node model,
				// so we try to get it from the reactions segment directly.
				// This might trigger a resolve of the cross-reference though:
				overriddenReactionsSegmentFormattedName = routine.overriddenReactionsSegment.packageName;
			}
			routineName += overriddenReactionsSegmentFormattedName + OVERRIDDEN_REACTIONS_SEGMENT_SEPARATOR;
		}
		routineName += routine.name.toFirstLower;
		return routineName;
	}

	// regular vs override reactions:

	/**
	 * Checks whether the given reaction is a 'regular' reaction.
	 * 
	 * <p>
	 * This currently means:
	 * <ul>
	 * <li>It does not override any other reaction.
	 * </ul>
	 * 
	 * @param reaction the reaction
	 * @return <code>true</code> if the given reaction is a regular reaction
	 */
	public static def isRegularReaction(Reaction reaction) {
		return !reaction.isOverrideReaction;
	}

	/**
	 * Checks whether the given reaction overrides another reaction.
	 * 
	 * @param reaction the reaction
	 * @return <code>true</code> if the given reaction overrides another reaction
	 */
	public static def isOverrideReaction(Reaction reaction) {
		// check if overridden reactions segment is set, without resolving the cross-reference:
		return reaction.eIsSet(reaction.eClass.getEStructuralFeature(ReactionsLanguagePackage.REACTION__OVERRIDDEN_REACTIONS_SEGMENT));
	}

	/**
	 * Gets all regular reactions from the given reactions segment.
	 * 
	 * @param reactionsSegment the reactions segment
	 * @return the regular reactions
	 * @see #isRegularReaction(Reaction)
	 */
	public static def getRegularReactions(ReactionsSegment reactionsSegment) {
		return reactionsSegment.reactions.filter[isRegularReaction];
	}

	/**
	 * Gets all reactions from the given reactions segment that are overriding other reactions.
	 * 
	 * @param reactionsSegment the reactions segment
	 * @return the reactions overriding other reactions
	 * @see #isOverrideReaction(Reaction)
	 */
	public static def getOverrideReactions(ReactionsSegment reactionsSegment) {
		return reactionsSegment.reactions.filter[isOverrideReaction];
	}

	// regular vs override routines:

	/**
	 * Checks whether the given routine is a 'regular' routine.
	 * 
	 * <p>
	 * This currently means:
	 * <ul>
	 * <li>It does not override any other routine.
	 * </ul>
	 * 
	 * @param routine the routine
	 * @return <code>true</code> if the given routine is a regular routine
	 */
	public static def isRegularRoutine(Routine routine) {
		return !routine.isOverrideRoutine;
	}

	/**
	 * Checks whether the given routine overrides another routine.
	 * 
	 * @param routine the routine
	 * @return <code>true</code> if the given routine overrides another routine
	 */
	public static def isOverrideRoutine(Routine routine) {
		// check if overridden reactions segment is set, without resolving the cross-reference:
		return routine.eIsSet(routine.eClass.getEStructuralFeature(ReactionsLanguagePackage.ROUTINE__OVERRIDDEN_REACTIONS_SEGMENT));
	}

	/**
	 * Gets all regular routines from the given reactions segment.
	 * 
	 * @param reactionsSegment the reactions segment
	 * @return the regular routines
	 * @see #isRegularRoutine(Routine)
	 */
	public static def getRegularRoutines(ReactionsSegment reactionsSegment) {
		return reactionsSegment.routines.filter[isRegularRoutine];
	}

	/**
	 * Gets all routines from the given reactions segment that are overriding other routines.
	 * 
	 * @param reactionsSegment the reactions segment
	 * @return the routines overriding other routines
	 * @see #isOverrideRoutine(Routine)
	 */
	public static def getOverrideRoutines(ReactionsSegment reactionsSegment) {
		return reactionsSegment.routines.filter[isOverrideRoutine];
	}
}
