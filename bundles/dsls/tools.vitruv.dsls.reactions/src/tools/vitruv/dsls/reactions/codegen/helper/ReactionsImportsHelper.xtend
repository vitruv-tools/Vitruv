package tools.vitruv.dsls.reactions.codegen.helper

import java.util.LinkedHashMap
import java.util.LinkedHashSet
import java.util.Map
import java.util.Set
import tools.vitruv.dsls.reactions.reactionsLanguage.Reaction
import tools.vitruv.dsls.reactions.reactionsLanguage.ReactionsSegment
import tools.vitruv.dsls.reactions.reactionsLanguage.Routine
import tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath

import static tools.vitruv.dsls.reactions.codegen.helper.ReactionsLanguageConstants.*

import static extension tools.vitruv.dsls.reactions.util.ReactionsLanguageUtil.*

class ReactionsImportsHelper {

	private new() {
	}

	// gets all reactions found in the import hierarchy of the given root reactions segment, with overridden reactions replaced,
	// together with their absolute reactions import path (starting with the root segment) denoting their position in the import hierarchy
	public static def Map<Reaction, ReactionsImportPath> getActiveReactions(ReactionsSegment rootReactionsSegment) {
		val reactionsByQualifiedName = new LinkedHashMap<String, Pair<Reaction, ReactionsImportPath>>();
		val rootImportPath = ReactionsImportPath.create(rootReactionsSegment.name);
		addActiveReactions(rootReactionsSegment, rootImportPath, reactionsByQualifiedName);
		return reactionsByQualifiedName.values.toMap([it.key],[it.value]);
	}

	// recursively adds all transitively imported reactions, and then adds its own and replaces overridden reactions
	private static def void addActiveReactions(ReactionsSegment currentReactionsSegment, ReactionsImportPath currentImportPath,
		Map<String, Pair<Reaction, ReactionsImportPath>> reactionsByQualifiedName) {
		// recursively add all transitively imported reactions:
		for (reactionsImport : currentReactionsSegment.reactionsImports.filter[!it.isRoutinesOnly]) {
			val importedReactionsSegment = reactionsImport.importedReactionsSegment;
			val importedSegmentImportPath = currentImportPath.append(importedReactionsSegment.name); 
			addActiveReactions(importedReactionsSegment, importedSegmentImportPath, reactionsByQualifiedName);
		}

		// add own reactions and replace overridden reactions:
		for (reaction : currentReactionsSegment.reactions) {
			val qualifiedName = reaction.qualifiedName;
			reactionsByQualifiedName.put(qualifiedName, new Pair<Reaction, ReactionsImportPath>(reaction, currentImportPath));
		}
	}

	// gets all routines that are included in the routines facade of the specified reactions segment,
	// together with their absolute reactions import path (starting with the root segment) denoting their position in the import hierarchy:
	// this includes the own routines, as well as routines directly and transitively imported without qualified names, with overridden routines replaced
	public static def Map<Routine, ReactionsImportPath> getIncludedRoutines(ReactionsSegment rootReactionsSegment) {
		val routinesByFullyQualifiedName = new LinkedHashMap<String, Pair<Routine, ReactionsImportPath>>();
		val rootImportPath = ReactionsImportPath.create(rootReactionsSegment.name);
		addIncludedRoutines(rootReactionsSegment, rootImportPath, routinesByFullyQualifiedName);
		return routinesByFullyQualifiedName.values.toMap([it.key],[it.value]);
	}

	// recursively adds all transitively included routines, and then adds its own and replaces overridden routines
	private static def void addIncludedRoutines(ReactionsSegment currentReactionsSegment, ReactionsImportPath currentImportPath,
		Map<String, Pair<Routine, ReactionsImportPath>> routinesByFullyQualifiedName) {
		// recursively add all routines imported without qualified names:
		for (reactionsImport : currentReactionsSegment.reactionsImports.filter[!it.useQualifiedNames]) {
			val importedReactionsSegment = reactionsImport.importedReactionsSegment;
			val importedSegmentImportPath = currentImportPath.append(importedReactionsSegment.name); 
			addIncludedRoutines(importedReactionsSegment, importedSegmentImportPath, routinesByFullyQualifiedName);
		}

		// add own reactions and replace overridden reactions:
		for (routine : currentReactionsSegment.routines) {
			var fullyQualifiedName = currentImportPath.pathString;
			if (routine.isOverride) {
				fullyQualifiedName += ReactionsImportPath.create(routine.overriddenReactionsSegmentImportPath).pathString;
			}
			fullyQualifiedName += OVERRIDDEN_REACTIONS_SEGMENT_SEPARATOR + routine.formattedName;

			// only include override routines if the overridden routine is included:
			if (!routine.isOverride || routinesByFullyQualifiedName.containsKey(fullyQualifiedName)) {
				routinesByFullyQualifiedName.put(fullyQualifiedName, new Pair<Routine, ReactionsImportPath>(routine, currentImportPath));
			}
		}
	}

	// gets all reactions segments whose routines facades are included in the routines facade of the specified reactions segment,
	// together with their absolute reactions import path (starting with the root segment) denoting their position in the import hierarchy:
	// this includes transitively included routines facades
	public static def Map<ReactionsSegment, ReactionsImportPath> getIncludedRoutinesFacades(ReactionsSegment rootReactionsSegment) {
		val segmentsByName = new LinkedHashMap<String, Pair<ReactionsSegment, ReactionsImportPath>>();
		val rootImportPath = ReactionsImportPath.create(rootReactionsSegment.name);
		addIncludedRoutinesFacades(rootReactionsSegment, rootImportPath, segmentsByName);
		return segmentsByName.values.toMap([it.key],[it.value]);
	}

	// recursively adds all transitively included routines facades:
	private static def void addIncludedRoutinesFacades(ReactionsSegment currentReactionsSegment, ReactionsImportPath currentImportPath,
		Map<String, Pair<ReactionsSegment, ReactionsImportPath>> segmentsByName) {
		for (reactionsImport : currentReactionsSegment.reactionsImports.filter[!it.useQualifiedNames]) {
			val importedReactionsSegment = reactionsImport.importedReactionsSegment;
			val importedSegmentImportPath = currentImportPath.append(importedReactionsSegment.name); 
			if (reactionsImport.useQualifiedNames) {
				// add included routines facade:
				val importedReactionsSegmentName = importedReactionsSegment.name;
				segmentsByName.put(importedReactionsSegmentName, new Pair<ReactionsSegment, ReactionsImportPath>(importedReactionsSegment, importedSegmentImportPath));
			} else {
				// recursively add all included routines facades for imports without qualified names:
				addIncludedRoutinesFacades(importedReactionsSegment, importedSegmentImportPath, segmentsByName);
			}
		}
	}

	// follows only imports that import reactions, can contain each reactions segment only once, does not contain the root reactions segment,
	// uses absolute import paths
	public static def Map<ReactionsImportPath, ReactionsSegment> getReactionsImportHierarchy(ReactionsSegment rootReactionsSegment) {
		val importHierarchy = new LinkedHashMap<ReactionsImportPath, ReactionsSegment>();
		val rootImportPath = ReactionsImportPath.create(rootReactionsSegment.name);
		addReactionsImportHierarchy(rootReactionsSegment, rootImportPath, importHierarchy, true);
		return importHierarchy;
	}

	private static def void addReactionsImportHierarchy(ReactionsSegment currentReactionsSegment, ReactionsImportPath currentImportPath,
		Map<ReactionsImportPath, ReactionsSegment> importHierarchy, boolean isRootReactionsSegment) {
		// include the current segment if it is not the root of the import hierarchy:
		if (!isRootReactionsSegment) {
			// there can be only one path to each reactions segment with imported reactions:
			if (!importHierarchy.containsValue(currentReactionsSegment)) {
				importHierarchy.put(currentImportPath, currentReactionsSegment);
			}
		}
		// recursively add imported reactions segments:
		for (reactionsImport : currentReactionsSegment.reactionsImports.filter[!it.routinesOnly]) {
			val importedReactionsSegment = reactionsImport.importedReactionsSegment;
			// skip if we currently cannot resolve cross-references to imported reactions segments:
			// TODO expect hierarchy to be resolvable here? -> test if scope provider might call this while cross-refs are not resolvable
			if (importedReactionsSegment !== null && !importedReactionsSegment.eIsProxy) {
				addReactionsImportHierarchy(importedReactionsSegment, currentImportPath.append(importedReactionsSegment.name), importHierarchy, false);
			} else {
				// TODO debugging:
				System.out.println("DEBUG reaction hierarchy: proxy " + importedReactionsSegment);
			}
		}
	}

	// returns the whole import hierarchy, can contain the same reactions segment multiple times at different paths, does not contain the root reactions segment,
	// uses absolute import paths
	public static def Map<ReactionsImportPath, ReactionsSegment> getRoutinesImportHierarchy(ReactionsSegment rootReactionsSegment) {
		val importHierarchy = new LinkedHashMap<ReactionsImportPath, ReactionsSegment>();
		val rootImportPath = ReactionsImportPath.create(rootReactionsSegment.name);
		addRoutinesImportHierarchy(rootReactionsSegment, rootImportPath, importHierarchy, true);
		return importHierarchy;
	}

	private static def void addRoutinesImportHierarchy(ReactionsSegment currentReactionsSegment, ReactionsImportPath currentImportPath,
		Map<ReactionsImportPath, ReactionsSegment> importHierarchy, boolean isRootReactionsSegment) {
		// include the current segment if it is not the root of the import hierarchy:
		if (!isRootReactionsSegment) {
			// there can be multiple paths to the same reactions segment:
			importHierarchy.put(currentImportPath, currentReactionsSegment);
		}
		// recursively add imported reactions segments:
		for (reactionsImport : currentReactionsSegment.reactionsImports) {
			val importedReactionsSegment = reactionsImport.importedReactionsSegment;
			// skip if we currently cannot resolve cross-references to imported reactions segments:
			// TODO expect hierarchy to be resolvable here? -> test if scope provider might call this while cross-refs are not resolvable
			if (importedReactionsSegment !== null && !importedReactionsSegment.eIsProxy) {
				addRoutinesImportHierarchy(importedReactionsSegment, currentImportPath.append(importedReactionsSegment.name), importHierarchy, false);
			} else {
				// TODO debugging:
				System.out.println("DEBUG routine hierarchy: proxy " + importedReactionsSegment);
			}
		}
	}

	/**
	 * Calculates a return value for each reactions segment while walking along a certain reactions import path, starting from a
	 * root reactions segment.
	 * 
	 * @param <T>
	 *            the type of the return value
	 */
	public static interface ImportPathReactionsSegmentVisitor<T> {
		/**
		 * Calculates a return value for the reactions segment at the current position of the reactions import path
		 * being walked.
		 * 
		 * @param currentImportPath
		 *            the absolute import path to the current reactions segment, starting with the root reactions segment
		 * @param currentReactionsSegment
		 *            the current reactions segment
		 * @param remainingImportPath
		 *            the remaining import path to walk, relative to the current reactions segment, or <code>null</code>
		 *            if the end of the path is reached
		 * @return the return value
		 */
		def T visit(ReactionsImportPath currentImportPath, ReactionsSegment currentReactionsSegment, ReactionsImportPath remainingImportPath);
	}

	/**
	 * Walks down the import hierarchy starting at the given root reactions segment along the given reactions import path and
	 * asks the <code>reactionsSegmentVisitor</code> at each reactions segment along the path (including the root reactions segment)
	 * for a return value and returns the first one that isn't </code>null</code>.
	 * <p>
	 * The given reactions import path is expected to be relative to the specified root reactions segment.<br>
	 * Returns <code>null</code> if the end of the path is reached without a non-null return value, or the path is invalid / does
	 * not exist in the import hierarchy of the root reactions segment.
	 */
	public static def <T> T walkImportPath(ReactionsSegment rootReactionsSegment, ReactionsImportPath reactionsImportPath,
		ImportPathReactionsSegmentVisitor<T> reactionsSegmentVisitor) {
		if (rootReactionsSegment === null || reactionsSegmentVisitor === null) {
			// invalid arguments:
			return null;
		}
		val rootImportPath = ReactionsImportPath.create(rootReactionsSegment.name);
		return _walkImportPath(rootImportPath, rootReactionsSegment, reactionsImportPath, reactionsSegmentVisitor);
	}

	private static def <T> T _walkImportPath(ReactionsImportPath currentImportPath, ReactionsSegment currentReactionsSegment,
		ReactionsImportPath remainingImportPath, ImportPathReactionsSegmentVisitor<T> reactionsSegmentVisitor) {
		// check for a return value:
		val returnValue = reactionsSegmentVisitor.visit(currentImportPath, currentReactionsSegment, remainingImportPath);
		if (returnValue !== null) return returnValue;
		// continue search recursively:
		if (remainingImportPath === null) {
			// reached end of path without non-null return value:
			return null;
		}
		val nextReactionsSegmentName = remainingImportPath.firstSegment;
		val nextImportPath = currentImportPath.append(nextReactionsSegmentName);
		val nextRemainingImportPath = remainingImportPath.tail; // can be null
		val nextReactionsSegment = currentReactionsSegment.reactionsImports.map[it.importedReactionsSegment].findFirst [
			it.name.equals(nextReactionsSegmentName);
		];
		if (nextReactionsSegment === null) {
			// invalid import path:
			return null;
		}
		return _walkImportPath(nextImportPath, nextReactionsSegment, nextRemainingImportPath, reactionsSegmentVisitor);
	}

	/**
	 * Gets the reactions segment at the specified reactions import path.
	 * <p>
	 * This walks down the import hierarchy starting at the given root reactions segment along the given reactions import path.<br>
	 * The given reactions import path is expected to be relative to the specified root reactions segment.<br>
	 * Returns <code>null</code> if no reactions segment is found for the specified path.
	 */
	public static def ReactionsSegment getReactionsSegment(ReactionsSegment rootReactionsSegment, ReactionsImportPath reactionsImportPath) {
		return walkImportPath(rootReactionsSegment, reactionsImportPath, [ currentPath, currentReactionsSegment, remainingPath |
			if (remainingPath === null) {
				// reached end of path:
				return currentReactionsSegment;
			} else {
				// continue walking:
				return null;
			}
		]);
	}

	/**
	 * Searches through the reactions import hierarchy for the first reactions segment that overrides routines of the specified
	 * reactions segment. If no such reactions segment is found, the overridden reactions segment itself is returned.
	 * <p>
	 * The search starts at the given root reactions segment. The <code>checkRootReactionsSegment</code> parameter controls
	 * whether the given root reactions segment gets considered as possible override root (in case it itself overrides routines
	 * of the specified reactions segment).<br>
	 * The <code>overriddenRoutineImportPath</code> is expected to be relative to the specified root reactions segment.
	 */
	public static def ReactionsSegment getRoutinesOverrideRoot(ReactionsSegment rootReactionsSegment,
		ReactionsImportPath overriddenRoutineImportPath, boolean checkRootReactionsSegment) {
		return walkImportPath(rootReactionsSegment, overriddenRoutineImportPath, [ currentPath, currentReactionsSegment, remainingPath |
			if (remainingPath === null) {
				// reached end of path:
				return currentReactionsSegment;
			} else {
				// check the routine overrides of the current segment, if it is not the root or we are checking the root as well:
				if (checkRootReactionsSegment || currentPath.length > 1) {
					// check if the current reactions segment contains a routine override for the remaining import path:
					val overriddenRoutinesImportPaths = currentReactionsSegment.overrideRoutines.map[it.overriddenReactionsSegmentImportPath];
					if (overriddenRoutinesImportPaths.findFirst[remainingPath.segments.equals(it)] !== null) {
						return currentReactionsSegment;
					}
				}
				// continue walking:
				return null;
			}
		]);
	}

	// returned imports paths are relative to the reactions segment:
	public static def Set<ReactionsImportPath> getOverriddenRoutinesImportPaths(ReactionsSegment reactionsSegment) {
		val overriddenRoutinesImportPaths = new LinkedHashSet<ReactionsImportPath>();
		for (overrideRoutine : reactionsSegment.overrideRoutines) {
			overriddenRoutinesImportPaths.add(ReactionsImportPath.create(overrideRoutine.overriddenReactionsSegmentImportPath));
		}
		return overriddenRoutinesImportPaths;
	}
}
