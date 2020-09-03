package tools.vitruv.dsls.reactions.util

import java.util.ArrayList
import java.util.List
import tools.vitruv.dsls.reactions.reactionsLanguage.Reaction
import tools.vitruv.dsls.reactions.reactionsLanguage.ReactionsLanguagePackage
import tools.vitruv.dsls.reactions.reactionsLanguage.ReactionsSegment
import tools.vitruv.dsls.reactions.reactionsLanguage.Routine
import tools.vitruv.dsls.reactions.reactionsLanguage.RoutineOverrideImportPath
import tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath

import static tools.vitruv.dsls.reactions.codegen.ReactionsLanguageConstants.*

/**
 * Utility methods working with the model objects of the Reactions Language, which might be of use outside of code generation.
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
	static def String getFormattedMetamodelPair(ReactionsSegment reactionsSegment) {
		val sourceDomainName = reactionsSegment.fromDomain?.domain;
		val targetDomainName = reactionsSegment.toDomain?.domain;
		return "(" + sourceDomainName + ", " + targetDomainName + ")";
	}

	// reactions segment name:

	/**
	 * Gets the formatted name of the given reactions segment.
	 * <p>
	 * This returns the reaction segment's name with a lower-case first character.
	 * 
	 * @param reactionsSegment the reactions segment
	 * @return the formatted reactions segment name
	 */
	static def String getFormattedName(ReactionsSegment reactionsSegment) {
		return reactionsSegment.name.formattedReactionsSegmentName;
	}

	/**
	 * Formats the given reactions segment name.
	 * <p>
	 * This returns the reaction segment name with a lower-case first character.
	 * 
	 * @param reactionsSegmentName the reactions segment name
	 * @return the formatted reactions segment name
	 */
	static def String getFormattedReactionsSegmentName(String reactionsSegmentName) {
		return reactionsSegmentName.toFirstLower;
	}

	// reaction names:

	/**
	 * Gets the formatted name of the given reaction.
	 * <p>
	 * This returns the reaction's name with an upper-case first character.
	 * 
	 * @param reaction the reaction
	 * @return the formatted reaction name
	 */
	static def String getFormattedName(Reaction reaction) {
		return reaction.name.toFirstUpper;
	}

	/**
	 * Gets the qualified name of the given reaction.
	 * <p>
	 * The qualified name consists of the reaction's reactions segment name and the
	 * {@link #getFormattedName(Reaction) formatted reaction name}, separated by <code>::</code>. In case the reaction overrides
	 * another reaction, the name of the overridden reactions segment is used instead of the reaction's own reactions segment
	 * name.
	 * <p>
	 * The qualified name can therefore not be used to differentiate between an original reaction and the reaction overriding it.
	 * 
	 * @param reaction the reaction
	 * @return the qualified name
	 */
	static def String getQualifiedName(Reaction reaction) {
		var String reactionsSegmentName;
		if (reaction.isOverride) {
			reactionsSegmentName = reaction.overriddenReactionsSegment?.name;
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
	static def String getDisplayName(Reaction reaction) {
		if (reaction.isOverride) {
			return reaction.qualifiedName;
		} else {
			return reaction.formattedName;
		}
	}

	// routine names:

	/**
	 * Gets the formatted name of the given routine.
	 * <p>
	 * This returns the routine's name with a lower-case first character.
	 * 
	 * @param routine the routine
	 * @return the formatted routine name
	 */
	static def String getFormattedName(Routine routine) {
		return routine.name.toFirstLower;
	}

	/**
	 * Gets the qualified name of the given routine.
	 * <p>
	 * The qualified name consists of the routine's reactions segment name and the
	 * {@link #getFormattedName(Routine) formatted reaction name}, separated by <code>::</code>. In case the routine overrides
	 * another routine, the name of the overridden reactions segment is used instead of the routine's own reactions segment
	 * name.
	 * <p>
	 * The qualified name can therefore not be used to differentiate between an original routine and the routine overriding it.
	 * 
	 * @param routine the routine
	 * @return the qualified name
	 */
	static def String getQualifiedName(Routine routine) {
		var String reactionsSegmentName;
		if (routine.isOverride) {
			reactionsSegmentName = routine.overrideImportPath.reactionsSegment?.name;
		} else {
			reactionsSegmentName = routine.reactionsSegment.name;
		}
		return reactionsSegmentName + OVERRIDDEN_REACTIONS_SEGMENT_SEPARATOR + routine.formattedName;
	}

	/**
	 * Gets the fully qualified name of the given routine.
	 * <p>
	 * This is equivalent to calling {@link #getFullyQualifiedName(Routine, ReactionsImportPath)} with <code>null</code> as
	 * <code>importPath</code> parameter.
	 * 
	 * @param routine the routine
	 * @return the fully qualified name
	 * 
	 * @see #getFullyQualifiedName(Routine, ReactionsImportPath)
	 */
	static def String getFullyQualifiedName(Routine routine) {
		return routine.getFullyQualifiedName(null);
	}

	/**
	 * Gets the fully qualified name of the given routine.
	 * <p>
	 * The fully qualified name consists of the routine's reactions segment name and the
	 * {@link #getFormattedName(Routine) formatted routine name}, separated by <code>::</code>. In case the routine overrides
	 * another routine, the relative <b>import path</b> to the overridden reactions segment is used instead of the routine's
	 * own reactions segment name.
	 * <p>
	 * The optional <code>importPath</code> parameter allows creation of fully qualified names relative to some other reactions
	 * segment. If it is specified, it acts as prefix for the fully qualified name. If it doesn't already point to the routine's
	 * reactions segment, the routine's reaction segment name gets appended.
	 * <p>
	 * Note: The returned fully qualified name relies on relative import paths and is therefore only valid inside the routine's
	 * own reactions segment, or, if used in conjunction with the <code>importPath</code> parameter, some root reactions segment.
	 * 
	 * @param routine the routine
	 * @param importPath the import path leading to the reactions segment containing the routine, or <code>null</code> or empty
	 * @return the fully qualified name
	 */
	static def String getFullyQualifiedName(Routine routine, ReactionsImportPath importPath) {
		val reactionsSegmentName = routine.reactionsSegment.name;
		val importPathSpecified = (importPath !== null && !importPath.isEmpty);
		var String fullyQualifiedName = "";
		if (importPathSpecified) {
			fullyQualifiedName += importPath.pathString;
			if (!importPath.lastSegment.equals(reactionsSegmentName)) {
				fullyQualifiedName += ReactionsImportPath.PATH_STRING_SEPARATOR + reactionsSegmentName;
			}
		}
		if (routine.isOverride) {
			if (importPathSpecified) {
				fullyQualifiedName += ReactionsImportPath.PATH_STRING_SEPARATOR;
			}
			fullyQualifiedName += routine.overrideImportPath.toReactionsImportPath.pathString;
		} else if (!importPathSpecified) {
			fullyQualifiedName += reactionsSegmentName;
		}
		fullyQualifiedName += OVERRIDDEN_REACTIONS_SEGMENT_SEPARATOR + routine.formattedName;
		return fullyQualifiedName;
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
	static def String getDisplayName(Routine routine) {
		if (routine.isOverride) {
			return routine.fullyQualifiedName;
		} else {
			return routine.formattedName;
		}
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
	static def isRegular(Reaction reaction) {
		return !reaction.isOverride;
	}

	/**
	 * Checks whether the given reaction overrides another reaction.
	 * 
	 * @param reaction the reaction
	 * @return <code>true</code> if the given reaction overrides another reaction
	 */
	static def isOverride(Reaction reaction) {
		// check if overridden reactions segment is set, without resolving the cross-reference:
		return reaction.eIsSet(ReactionsLanguagePackage.Literals.REACTION__OVERRIDDEN_REACTIONS_SEGMENT);
	}

	/**
	 * Gets all regular reactions from the given reactions segment.
	 * 
	 * @param reactionsSegment the reactions segment
	 * @return the regular reactions
	 * @see #isRegular(Reaction)
	 */
	static def getRegularReactions(ReactionsSegment reactionsSegment) {
		return reactionsSegment.reactions.filter[isRegular];
	}

	/**
	 * Gets all reactions from the given reactions segment that are overriding other reactions.
	 * 
	 * @param reactionsSegment the reactions segment
	 * @return the reactions overriding other reactions
	 * @see #isOverride(Reaction)
	 */
	static def getOverrideReactions(ReactionsSegment reactionsSegment) {
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
	static def isRegular(Routine routine) {
		return !routine.isOverride;
	}

	/**
	 * Checks whether the given routine overrides another routine.
	 * 
	 * @param routine the routine
	 * @return <code>true</code> if the given routine overrides another routine
	 */
	static def isOverride(Routine routine) {
		return (routine.overrideImportPath !== null);
	}

	/**
	 * Gets all regular routines from the given reactions segment.
	 * 
	 * @param reactionsSegment the reactions segment
	 * @return the regular routines
	 * @see #isRegular(Routine)
	 */
	static def getRegularRoutines(ReactionsSegment reactionsSegment) {
		return reactionsSegment.routines.filter[isRegular];
	}

	/**
	 * Gets all routines from the given reactions segment that are overriding other routines.
	 * 
	 * @param reactionsSegment the reactions segment
	 * @return the routines overriding other routines
	 * @see #isOverride(Routine)
	 */
	static def getOverrideRoutines(ReactionsSegment reactionsSegment) {
		return reactionsSegment.routines.filter[isOverride];
	}

	/**
	 * Converts the given {@link RoutineOverrideImportPath} to a corresponding {@link ReactionsImportPath}.
	 * <p>
	 * Any incomplete or unresolvable segments inside the {@link RoutineOverrideImportPath} will get represented by the String
	 * {@literal "<unresolved>"} inside the {@link ReactionsImportPath}.
	 * 
	 * @param routineOverrideImportPath the routine override import path, can be <code>null</code>
	 * @return the corresponding reactions import path, or <code>null</code> if the given routine override import path was <code>null</code>
	 */
	static def ReactionsImportPath toReactionsImportPath(RoutineOverrideImportPath routineOverrideImportPath) {
		if (routineOverrideImportPath === null) return null;
		val fullPathSegments = routineOverrideImportPath.fullPath.map [
			val segment = it.reactionsSegment;
			// handle segments that are incomplete or cannot be resolved:
			if ((segment?.name).nullOrEmpty) {
				return "<unresolved>";
			} else {
				return segment.name;
			}
		];
		return ReactionsImportPath.create(fullPathSegments);
	}

	/**
	 * Gets the entries of the full routine override import path by traversing the parents of the given routine override import path.
	 * <p>
	 * The returned full path contains the given routine override import path as last entry.
	 * 
	 * @param routineOverrideImportPath the routine override import path, can be <code>null</code>
	 * @return the segments of the full routine override import path, or <code>null</code> if the given routine override import path was <code>null</code>
	 */
	static def List<RoutineOverrideImportPath> getFullPath(RoutineOverrideImportPath routineOverrideImportPath) {
		if (routineOverrideImportPath === null) return null;
		val pathSegments = new ArrayList<RoutineOverrideImportPath>();
		var currentPath = routineOverrideImportPath;
		while (currentPath !== null) {
			pathSegments.add(currentPath);
			currentPath = currentPath.parent;
		}
		return pathSegments.reverse;
	}
}
