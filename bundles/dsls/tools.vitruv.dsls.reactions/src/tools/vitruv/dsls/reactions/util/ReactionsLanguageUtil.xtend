package tools.vitruv.dsls.reactions.util

import tools.vitruv.dsls.reactions.reactionsLanguage.Reaction
import tools.vitruv.dsls.reactions.reactionsLanguage.ReactionsLanguagePackage
import tools.vitruv.dsls.reactions.reactionsLanguage.ReactionsSegment
import tools.vitruv.dsls.reactions.reactionsLanguage.Routine
import tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath

import static tools.vitruv.dsls.reactions.codegen.helper.ReactionsLanguageConstants.*

import static extension tools.vitruv.dsls.reactions.codegen.helper.ReactionsLanguageHelper.*

/**
 * Utility methods working with the model objects of the Reactions Language, which might be of use outside of the code generation.
 */
final class ReactionsLanguageUtil {

	private new() {
	}

	/**
	 * Gets a formatted representation of the metamodel pair the given reactions segment applies to.
	 * 
	 * @param reactionsSegment the reactions segment
	 * @return the formatted representation of the metamodel pair
	 */
	public static def String getFormattedMetamodelPair(ReactionsSegment reactionsSegment) {
		val sourceDomainName = reactionsSegment.fromDomain?.domain;
		val targetDomainName = reactionsSegment.toDomain?.domain;
		return "(" + sourceDomainName + ", " + targetDomainName + ")";
	}

	// reactions segment name:

	/**
	 * Gets the formatted name of the given reactions segment.
	 * <p>
	 * This returns the reaction segment's name with an upper-case first character.
	 * 
	 * @param reactionsSegment the reactions segment
	 * @return the formatted reactions segment name
	 */
	public static def String getFormattedName(ReactionsSegment reactionsSegment) {
		return reactionsSegment.name.toFirstUpper;
	}

	// reaction names:

	/**
	 * Gets the qualified name of the given reaction.
	 * <p>
	 * The qualified name consists of the reaction's reactions segment name and the
	 * {@link #getFormattedName(Reaction) formatted reaction name}, separated by <code>::</code>. In case the reaction overrides
	 * another reaction, the name of the overridden reactions segment is used instead. The qualified name can therefore not be
	 * used to differentiate between an original reaction and the reaction overriding it!
	 * 
	 * @param reaction the reaction
	 * @return the qualified name
	 */
	public static def String getQualifiedName(Reaction reaction) {
		var String reactionsSegmentName;
		if (reaction.isOverride) {
			// not resolving cross-references here, if not required:
			reactionsSegmentName = reaction.parsedOverriddenReactionsSegmentName;
			if (reactionsSegmentName === null) {
				// getting the name from the overridden reactions segment directly,
				// this might trigger a resolve of the cross-reference though:
				reactionsSegmentName = reaction.overriddenReactionsSegment.name;
			}
		} else {
			reactionsSegmentName = reaction.reactionsSegment.name;
		}
		return reactionsSegmentName + OVERRIDDEN_REACTIONS_SEGMENT_SEPARATOR + reaction.formattedName;
	}

	/**
	 * Gets the display name of the given reaction.
	 * <p>
	 * In case the given reaction overrides another reaction, the returned name is equal to the reaction's
	 * {@link #getQualifiedName(Reaction) qualified name}. Otherwise it consists of only the
	 * {@link #getFormattedName(Reaction) formatted reaction name}.
	 * 
	 * @param reaction the reaction
	 * @return the formatted full name
	 */
	public static def String getDisplayName(Reaction reaction) {
		if (reaction.isOverride) {
			return reaction.qualifiedName;
		} else {
			return reaction.formattedName;
		}
	}

	/**
	 * Gets the formatted name of the given reaction.
	 * <p>
	 * This returns the reaction's name with an upper-case first character.
	 * 
	 * @param reaction the reaction
	 * @return the formatted reaction name
	 */
	public static def String getFormattedName(Reaction reaction) {
		return reaction.name.toFirstUpper;
	}

	// routine names:

	/**
	 * Gets the qualified name of the given routine.
	 * <p>
	 * The qualified name consists of the routine's reactions segment name and the
	 * {@link #getFormattedName(Routine) formatted reaction name}, separated by <code>::</code>. In case the routine overrides
	 * another routine, the name of the overridden reactions segment is used instead. The qualified name can therefore not be
	 * used to differentiate between an original routine and the routine overriding it!
	 * 
	 * @param routine the routine
	 * @return the qualified name
	 */
	public static def String getQualifiedName(Routine routine) {
		var String reactionsSegmentName;
		if (routine.isOverride) {
			reactionsSegmentName = routine.overriddenReactionsSegmentImportPath.last;
		} else {
			reactionsSegmentName = routine.reactionsSegment.name;
		}
		return reactionsSegmentName + OVERRIDDEN_REACTIONS_SEGMENT_SEPARATOR + routine.formattedName;
	}

	/**
	 * Gets the fully qualified name of the given routine.
	 * <p>
	 * The qualified name consists of the routine's reactions segment name and the
	 * {@link #getFormattedName(Routine) formatted routine name}, separated by <code>::</code>. In case the routine overrides
	 * another routine, the <b>import path</b> to the overridden reactions segment is used instead. The fully qualified name is
	 * therefore only valid inside the routine's own reactions segment, because it depends on the import hierarchy.
	 * 
	 * @param routine the routine
	 * @return the fully qualified name
	 */
	public static def String getFullyQualifiedName(Routine routine) {
		var String reactionsSegmentName;
		if (routine.isOverride) {
			reactionsSegmentName = ReactionsImportPath.create(routine.overriddenReactionsSegmentImportPath).pathString;
		} else {
			reactionsSegmentName = routine.reactionsSegment.name;
		}
		return reactionsSegmentName + OVERRIDDEN_REACTIONS_SEGMENT_SEPARATOR + routine.formattedName;
	}

	/**
	 * Gets the display name of the given routine.
	 * <p>
	 * In case the given routine overrides another routine, the returned name is equal to the routine's
	 * {@link #getFullyQualifiedName(Routine) fully qualified name}. Otherwise it consists of only the
	 * {@link #getFormattedName(Routine) formatted routine name}.
	 * 
	 * @param routine the routine
	 * @return the formatted full name
	 */
	public static def String getDisplayName(Routine routine) {
		if (routine.isOverride) {
			return routine.fullyQualifiedName;
		} else {
			return routine.formattedName;
		}
	}

	/**
	 * Gets the formatted name of the given routine.
	 * <p>
	 * This returns the routine's name with a lower-case first character.
	 * 
	 * @param routine the routine
	 * @return the formatted routine name
	 */
	public static def String getFormattedName(Routine routine) {
		return routine.name.toFirstLower;
	}

	// regular vs override reactions:

	/**
	 * Checks whether the given reaction is a 'regular' reaction.
	 * <p>
	 * This means that it does not override any other reaction.
	 * 
	 * @param reaction the reaction
	 * @return <code>true</code> if the given reaction is a regular reaction
	 */
	public static def isRegular(Reaction reaction) {
		return !reaction.isOverride;
	}

	/**
	 * Checks whether the given reaction overrides another reaction.
	 * 
	 * @param reaction the reaction
	 * @return <code>true</code> if the given reaction overrides another reaction
	 */
	public static def isOverride(Reaction reaction) {
		// check if overridden reactions segment is set, without resolving the cross-reference:
		return reaction.eIsSet(reaction.eClass.getEStructuralFeature(ReactionsLanguagePackage.REACTION__OVERRIDDEN_REACTIONS_SEGMENT));
	}

	/**
	 * Gets all regular reactions from the given reactions segment.
	 * 
	 * @param reactionsSegment the reactions segment
	 * @return the regular reactions
	 * @see #isRegular(Reaction)
	 */
	public static def getRegularReactions(ReactionsSegment reactionsSegment) {
		return reactionsSegment.reactions.filter[isRegular];
	}

	/**
	 * Gets all reactions from the given reactions segment that are overriding other reactions.
	 * 
	 * @param reactionsSegment the reactions segment
	 * @return the reactions overriding other reactions
	 * @see #isOverride(Reaction)
	 */
	public static def getOverrideReactions(ReactionsSegment reactionsSegment) {
		return reactionsSegment.reactions.filter[isOverride];
	}

	// regular vs override routines:

	/**
	 * Checks whether the given routine is a 'regular' routine.
	 * <p>
	 * This means that it does not override any other routine.
	 * 
	 * @param routine the routine
	 * @return <code>true</code> if the given routine is a regular routine
	 */
	public static def isRegular(Routine routine) {
		return !routine.isOverride;
	}

	/**
	 * Checks whether the given routine overrides another routine.
	 * 
	 * @param routine the routine
	 * @return <code>true</code> if the given routine overrides another routine
	 */
	public static def isOverride(Routine routine) {
		return !routine.overriddenReactionsSegmentImportPath.isEmpty;
	}

	/**
	 * Gets all regular routines from the given reactions segment.
	 * 
	 * @param reactionsSegment the reactions segment
	 * @return the regular routines
	 * @see #isRegular(Routine)
	 */
	public static def getRegularRoutines(ReactionsSegment reactionsSegment) {
		return reactionsSegment.routines.filter[isRegular];
	}

	/**
	 * Gets all routines from the given reactions segment that are overriding other routines.
	 * 
	 * @param reactionsSegment the reactions segment
	 * @return the routines overriding other routines
	 * @see #isOverride(Routine)
	 */
	public static def getOverrideRoutines(ReactionsSegment reactionsSegment) {
		return reactionsSegment.routines.filter[isOverride];
	}
}
