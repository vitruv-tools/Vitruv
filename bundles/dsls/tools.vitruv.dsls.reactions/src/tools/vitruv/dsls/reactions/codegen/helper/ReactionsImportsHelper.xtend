package tools.vitruv.dsls.reactions.codegen.helper

import java.util.LinkedHashMap
import java.util.Map
import java.util.Set
import java.util.function.Function
import java.util.function.Predicate
import java.util.function.Supplier
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.xtext.nodemodel.util.NodeModelUtils
import org.eclipse.xtext.util.Tuples
import tools.vitruv.dsls.reactions.reactionsLanguage.Reaction
import tools.vitruv.dsls.reactions.reactionsLanguage.ReactionsImport
import tools.vitruv.dsls.reactions.reactionsLanguage.ReactionsLanguagePackage
import tools.vitruv.dsls.reactions.reactionsLanguage.ReactionsSegment
import tools.vitruv.dsls.reactions.reactionsLanguage.Routine
import tools.vitruv.dsls.reactions.reactionsLanguage.RoutineOverrideImportPath
import tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath

import static extension tools.vitruv.dsls.reactions.codegen.helper.ReactionsLanguageHelper.*
import static extension tools.vitruv.dsls.reactions.util.ReactionsLanguageUtil.*
import static com.google.common.base.Preconditions.*

/**
 * Utilities related to reactions imports, import hierarchies, and reaction and routine overrides.
 */
class ReactionsImportsHelper {

	private new() {
	}

	/**
	 * Gets the name of the overridden reactions segment.
	 * <p>
	 * Before accessing the overridden reactions segment directly and therefore resolving the potentially still unresolved
	 * cross-reference, this first attempts to get the parsed overridden reactions segment name.
	 * 
	 * @param reaction the reaction
	 * @return the name of the overridden reactions segment, or <code>null</code> if the given reaction doesn't override any
	 *         other reaction
	 */
	static def String getParsedOverriddenReactionsSegmentName(Reaction reaction) {
		val parsed = reaction.getFeatureNodeText(ReactionsLanguagePackage.Literals.REACTION__OVERRIDDEN_REACTIONS_SEGMENT);
		if (!parsed.nullOrEmpty) return parsed;
		return reaction.overriddenReactionsSegment?.name;
	}

	/**
	 * Gets the reactions segment names of the override import path for the given routine.
	 * <p>
	 * Before accessing the reactions segments inside the override import path directly and therefore resolving the potentially
	 * still unresolved cross-references, this first attempts to get the parsed import path segment names.
	 * <p>
	 * Any incomplete or unresolvable segments inside the import path will get represented by an empty String.
	 * 
	 * @param routine the routine
	 * @return the reactions import path consisting of the found segment names, or <code>null</code> if the given routine does
	 *         not override any other routine
	 */
	static def ReactionsImportPath getParsedOverrideImportPath(Routine routine) {
		return routine.overrideImportPath.parsedOverrideImportPath;
	}

	/**
	 * Gets the reactions segment names of the given override import path.
	 * <p>
	 * Before accessing the reactions segments inside the override import path directly and therefore resolving the potentially
	 * still unresolved cross-references, this first attempts to get the parsed import path segment names.
	 * <p>
	 * Any incomplete or unresolvable segments inside the import path will get represented by an empty String.
	 * 
	 * @param routineOverrideImportPath the routine override import path
	 * @return the reactions import path consisting of the found segment names, or <code>null</code> if the given routine
	 *         override path was <code>null</code>
	 */
	static def ReactionsImportPath getParsedOverrideImportPath(RoutineOverrideImportPath routineOverrideImportPath) {
		if (routineOverrideImportPath === null) return null;
		// getting the parsed text of each individual segment (instead of the whole import path) to skip hidden tokens in between:
		val parsedSegments = routineOverrideImportPath.fullPath.map [
			val parsed = it.getFeatureNodeText(ReactionsLanguagePackage.Literals.ROUTINE_OVERRIDE_IMPORT_PATH__REACTIONS_SEGMENT);
			if (!parsed.isNullOrEmpty) return parsed;
			return (it.reactionsSegment?.name) ?: "";
		];
		return ReactionsImportPath.create(parsedSegments);
	}

	private static def String getFeatureNodeText(EObject semanticObject, EStructuralFeature structuralFeature) {
		val nodes = NodeModelUtils.findNodesForFeature(semanticObject, structuralFeature);
		if (nodes.isEmpty) return null;
		return NodeModelUtils.getTokenText(nodes.get(0));
	}

	/**
	 * Gets the parsed import paths for all complete routine overrides contained in the given reactions segment.
	 * <p>
	 * The returned import paths are unique, and relative to the given reactions segment.
	 * 
	 * @param reactionsSegment the reactions segment
	 * @return the parsed overridden routines import paths
	 * @see #getParsedOverrideImportPath(Routine)
	 */
	static def Set<ReactionsImportPath> getParsedOverriddenRoutinesImportPaths(ReactionsSegment reactionsSegment) {
		return reactionsSegment.overrideRoutines.filter[it.isComplete].map[it.parsedOverrideImportPath].toSet;
	}

	/**
	 * Checks if all reactions imports of the given reactions segment can be resolved currently.
	 * 
	 * @param reactionsSegment the reactions segment
	 * @return <code>true</code> if all imports are resolvable
	 * @see #isResolvable(ReactionsImport)
	 */
	static def boolean isAllImportsResolvable(ReactionsSegment reactionsSegment) {
		// getting the imported reactions segment from the reactions import triggers a resolve,
		// which will only still be a proxy afterwards if it wasn't resolvable
		return (reactionsSegment.reactionsImports.findFirst[!it.isResolvable] === null);
	}

	/**
	 * Checks if the given reactions import can be resolved currently.
	 * <p>
	 * This checks whether:
	 * <ul>
	 * <li>The reactions import is not <code>null</code>.
	 * <li>The imported reactions segment is not <code>null</code>.
	 * <li>The imported reactions segment is no proxy.
	 * </ul>
	 * Note: This will trigger a resolve of the potentially not yet resolved cross-reference to the imported reactions segment!
	 * 
	 * @param reactionsImport the reactions import
	 * @return <code>true</code> if the given import is resolvable
	 */
	static def boolean isResolvable(ReactionsImport reactionsImport) {
		// getting the imported reactions segment from the reactions import triggers a resolve,
		// which will only still be a proxy afterwards if it wasn't resolvable
		return (reactionsImport !== null && reactionsImport.importedReactionsSegment !== null && !reactionsImport.importedReactionsSegment.eIsProxy);
	}

	/**
	 * This visitor gets called for each reactions segment while walking the import hierarchy, starting with the root reactions segment.
	 * 
	 * @param <D>
	 *            the type of the data being passed along
	 */
	static interface ImportHierarchyVisitor<D> {
		/**
		 * Gets called for each reactions segment along the walked import hierarchy.
		 * 
		 * @param sourceImport
		 *            the reactions import that lead to the current reactions segment, or <code>null</code> at the root segment
		 * @param currentImportPath
		 *            the absolute import path to the current reactions segment, starting with the root reactions segment
		 * @param currentReactionsSegment
		 *            the current reactions segment
		 * @param data
		 *            the data being passed along
		 */
		def void visit(ReactionsImport sourceImport, ReactionsImportPath currentImportPath, ReactionsSegment currentReactionsSegment, D data);
	}

	/**
	 * Walks the import hierarchy with depth-first order, starting at the given root reactions segment.
	 * <p>
	 * The optional <code>importFilter</code> can be used to determine which import branches in the hierarchy to follow and which
	 * to skip. Only imports for which it returns <code>true</code> are followed further. If not specified, all imports are
	 * followed. Any cyclic imports or imports that cannot be resolved will be considered non-existent and therefore not be
	 * followed, nor passed to the <code>importFilter</code> in the first place.
	 * <p>
	 * The optional <code>earlyVisitor</code> gets called before going deeper in the hierarchy, and <code>lateVisitor</code> is
	 * called after all deeper branches have been visited.
	 * <p>
	 * The optional <code>dataInitializer</code> can be used to initially create some data object that gets passed along to the
	 * visitors while walking the hierarchy. If not specified, then <code>null</code> is passed along as data object.
	 * <p>
	 * The <code>returnValueFunction</code> gets called at the end to calculate a return value.
	 * 
	 * @param <R> the type of the return value
	 * @param <D> the type of the data being passed along
	 * @param rootReactionsSegment the root reactions segment, not <code>null</code>
	 * @param dataInitializer creates a data object which gets then passed around to the called visitors, optional
	 * @param earlyVisitor the visitor that gets called right when reaching a reactions segment in the import hierarchy, optional
	 * @param lateVisitor the visitor that gets called during backtracking, after all imports of a reactions segment have been fully followed, optional
	 * @param importFilter a predicate that decides whether an import is further followed, optional
	 * @param returnValueFunction the return value function, not <code>null</code>
	 * @return the return value calculated by the return value function
	 */
	static def <R, D> R walkImportHierarchy(ReactionsSegment rootReactionsSegment, Supplier<D> dataInitializer,
		ImportHierarchyVisitor<D> earlyVisitor, ImportHierarchyVisitor<D> lateVisitor, Predicate<ReactionsImport> importFilter,
		Function<D, R> returnValueFunction) {
		checkNotNull(rootReactionsSegment, "rootReactionsSegment is null");
		checkNotNull(returnValueFunction, "returnValueFunction is null");

		// initialize the data being passed along:
		val data = dataInitializer?.get();
		// start walking:
		val rootImportPath = ReactionsImportPath.create(rootReactionsSegment.name);
		_walkImportHierarchy(null, rootImportPath, rootReactionsSegment, data, earlyVisitor, lateVisitor, importFilter ?: [true]);
		// return calculated return value:
		return returnValueFunction.apply(data);
	}

	private static def <D> void _walkImportHierarchy(ReactionsImport sourceImport,
		ReactionsImportPath currentImportPath, ReactionsSegment currentReactionsSegment, D data,
		ImportHierarchyVisitor<D> earlyVisitor, ImportHierarchyVisitor<D> lateVisitor, Predicate<ReactionsImport> importFilter) {
		// call early visitor:
		earlyVisitor?.visit(sourceImport, currentImportPath, currentReactionsSegment, data);

		// recursively walk along transitively imported reactions segments, that are resolvable:
		for (nextImport : currentReactionsSegment.reactionsImports.filter[it.isResolvable]) {
			val importedSegment = nextImport.importedReactionsSegment; // not null, not proxy
			// not following imports that are cyclic, or are skipped by the importFilter:
			if (!currentImportPath.segments.contains(importedSegment.name) && importFilter.test(nextImport)) {
				val importedSegmentImportPath = currentImportPath.append(importedSegment.name);
				_walkImportHierarchy(nextImport, importedSegmentImportPath, importedSegment, data, earlyVisitor, lateVisitor, importFilter);
			}
		}

		// call late visitor:
		lateVisitor?.visit(sourceImport, currentImportPath, currentReactionsSegment, data);
	}

	/**
	 * Gets all reactions segments that contribute routines to the given root reactions segment, including the root reactions
	 * segment itself. This basically returns the whole import hierarchy.
	 * <p>
	 * Reactions segments can be contained more than once at different import paths.
	 * <p>
	 * The reactions segments are returned together with their absolute reactions import path (starting with the root segment)
	 * denoting their position in the import hierarchy.
	 * 
	 * @param rootReactionsSegment the root reactions segment, not <code>null</code>
	 * @return all reactions segments in the import hierarchy by their absolute import paths
	 */
	static def Map<ReactionsImportPath, ReactionsSegment> getRoutinesImportHierarchy(ReactionsSegment rootReactionsSegment) {
		return walkImportHierarchy(rootReactionsSegment,
			// data initializer:
			[
				// the reactions segments of the routines import hierarchy, with their absolute import path:
				return new LinkedHashMap<ReactionsImportPath, ReactionsSegment>();
			],
			// early visitor:
			[ sourceImport, currentImportPath, currentReactionsSegment, data |
				val importHierarchy = data;
				// there can be multiple paths to the same reactions segment:
				importHierarchy.put(currentImportPath, currentReactionsSegment);
			],
			// late visitor:
			null,
			// import filter:
			// recursively walk along all imports:
			[true],
			// return value:
			[it]
		);
	}

	/**
	 * Gets all reactions segments that contribute reactions to the given root reactions segment, including the root reactions
	 * segment itself.
	 * <p>
	 * This walks the import hierarchy and only follows imports that include reactions.
	 * <p>
	 * Each reactions segment can be contained only once. So there exists exactly one path from the root reactions segment to
	 * each of the contained reactions segments.
	 * <p>
	 * The reactions segments are returned together with their absolute reactions import path (starting with the root segment)
	 * denoting their position in the import hierarchy.
	 * 
	 * @param rootReactionsSegment the root reactions segment, not <code>null</code>
	 * @return all reactions segments in the import hierarchy, that contribute reactions, by their absolute import paths
	 */
	static def Map<ReactionsImportPath, ReactionsSegment> getReactionsImportHierarchy(ReactionsSegment rootReactionsSegment) {
		return walkImportHierarchy(rootReactionsSegment,
			// data initializer:
			[
				// the reactions segments of the reactions import hierarchy, with their absolute import path:
				return new LinkedHashMap<ReactionsImportPath, ReactionsSegment>();
			],
			// early visitor:
			[ sourceImport, currentImportPath, currentReactionsSegment, data |
				val importHierarchy = data;
				// there can be only one path to each reactions segment with imported reactions:
				if (!importHierarchy.containsValue(currentReactionsSegment)) {
					importHierarchy.put(currentImportPath, currentReactionsSegment);
				}
			],
			// late visitor:
			null,
			// import filter:
			// recursively walk along imports that include reactions:
			[!it.routinesOnly],
			// return value:
			[it]
		);
	}

	/**
	 * Gets all reactions that are included in the executor of the given root reactions segment.
	 * <p>
	 * This includes the own reactions, as well as all reactions found in the reactions import hierarchy, with overridden
	 * reactions being replaced. This can contain reactions with duplicate names, as long as those originate from differently
	 * named reactions segments.
	 * <p>
	 * The reactions are returned together with the absolute reactions import paths (starting with the root segment) denoting the
	 * positions of the their reactions segments in the import hierarchy.
	 * 
	 * @param rootReactionsSegment the root reactions segment, not <code>null</code>
	 * @return all included reactions with their absolute import paths
	 */
	static def Map<Reaction, ReactionsImportPath> getIncludedReactions(ReactionsSegment rootReactionsSegment) {
		return walkImportHierarchy(rootReactionsSegment,
			// data initializer:
			[
				return Tuples.create(
					// included reactions by qualified name:
					new LinkedHashMap<String, Reaction>(),
					// included reactions with their absolute import path:
					new LinkedHashMap<Reaction, ReactionsImportPath>()
				);
			],
			// early visitor:
			null,
			// late visitor:
			[ sourceImport, currentImportPath, currentReactionsSegment, data |
				val reactionsByQualifiedName = data.first;
				val reactionsToImportPath = data.second;
				// add own reactions and replace overridden reactions:
				for (reaction : currentReactionsSegment.reactions.filter[it.isComplete]) {
					val qualifiedName = reaction.qualifiedName;
					// this replaces any overridden reaction for the same qualified name:
					val previousReaction = reactionsByQualifiedName.put(qualifiedName, reaction);
					if (previousReaction !== null) {
						reactionsToImportPath.remove(previousReaction);
					}
					reactionsToImportPath.put(reaction, currentImportPath);
				}
			],
			// import filter:
			// recursively walk along imports that include reactions:
			[!it.isRoutinesOnly],
			// return value:
			[it.second]
		);
	}

	/**
	 * Gets all routines that are included in the routines facade of the given root reactions segment.
	 * <p>
	 * This includes all routines that are directly and transitively imported without qualified names. Duplicately included or
	 * named routines are only contained once.
	 * <p>
	 * The <code>includeRootRoutines</code> parameter controls whether the routines of the given root reactions segment are
	 * included in the result.
	 * <p>
	 * The <code>resolveOverrides</code> parameter controls whether overridden routines get replaced with their overriding
	 * routine. This considers the overrides of the root reactions segment regardless of the <code>includeRootRoutines</code> parameter. 
	 * <p>
	 * The routines are returned together with the absolute reactions import paths (starting with the root segment) denoting the
	 * position of the their reactions segments in the import hierarchy.
	 * 
	 * @param rootReactionsSegment the root reactions segment, not <code>null</code>
	 * @param includeRootRoutines whether to include the routines of the root reactions segment
	 * @param resolveOverrides whether to replace routines with their overriding routine
	 * @return all included routines with their absolute import paths
	 */
	static def Map<Routine, ReactionsImportPath> getIncludedRoutines(ReactionsSegment rootReactionsSegment,
		boolean includeRootRoutines, boolean resolveOverrides) {
		return walkImportHierarchy(rootReactionsSegment,
			// data initializer:
			[
				return Tuples.create(
					// included routines by name:
					new LinkedHashMap<String, Routine>(),
					// included routines by fully qualified name:
					new LinkedHashMap<String, Routine>(),
					// included routines with their absolute import path:
					new LinkedHashMap<Routine, ReactionsImportPath>()
				);
			],
			// early visitor:
			[ sourceImport, currentImportPath, currentReactionsSegment, data |
				// skip root segment if its routines shall not get included:
				if (!includeRootRoutines && currentImportPath.length == 1) return;
				val routinesByName = data.first;
				val routinesByFullyQualifiedName = data.second;
				val routinesToImportPath = data.third;
				// add own routines:
				for (routine : currentReactionsSegment.regularRoutines.filter[it.isComplete]) {
					// only include one (top-most) routine for each unique formatted routine name:
					if (routinesByName.putIfAbsent(routine.formattedName, routine) === null) {
						// fully qualified name originating from root reactions segment:
						var fullyQualifiedName = routine.getFullyQualifiedName(currentImportPath);
						routinesByFullyQualifiedName.put(fullyQualifiedName, routine);
						routinesToImportPath.put(routine, currentImportPath);
					}
				}
			],
			// late visitor:
			[ sourceImport, currentImportPath, currentReactionsSegment, data |
				if (!resolveOverrides) return;
				val routinesByName = data.first;
				val routinesByFullyQualifiedName = data.second;
				val routinesToImportPath = data.third;
				// replace overridden routines:
				for (routine : currentReactionsSegment.overrideRoutines.filter[it.isComplete]) {
					// fully qualified name originating from root reactions segment:
					var fullyQualifiedName = routine.getFullyQualifiedName(currentImportPath);
					// only include override routines if the overridden routine (same fully qualified name) is included:
					val previousRoutine = routinesByFullyQualifiedName.replace(fullyQualifiedName, routine);
					if (previousRoutine !== null) {
						// update import path and routines-by-name mappings:
						routinesToImportPath.remove(previousRoutine);
						routinesToImportPath.put(routine, currentImportPath);
						routinesByName.put(routine.formattedName, routine);
					}
				}
			],
			// import filter:
			// recursively walk along imports that import routines without qualified names:
			[!it.useQualifiedNames],
			// return value:
			[it.third]
		);
	}

	/**
	 * Gets all reactions segments whose routines facades are included in the routines facade of the given root reactions segment.
	 * <p>
	 * This includes all reactions segments for imports using qualified names, as well as all reactions segments for routines
	 * facades that are transitively included via imports not using qualified names. Duplicately included or named reactions
	 * segments are only contained once.
	 * <p>
	 * The reactions segments are returned together with their absolute reactions import path (starting with the root segment)
	 * denoting their position in the import hierarchy.
	 * 
	 * @param rootReactionsSegment the root reactions segment, not <code>null</code>
	 * @return all reactions segments whose routines facades are included, with their absolute import paths
	 */
	static def Map<ReactionsSegment, ReactionsImportPath> getIncludedRoutinesFacades(ReactionsSegment rootReactionsSegment) {
		return walkImportHierarchy(rootReactionsSegment,
			// data initializer:
			[
				return Tuples.create(
					// included reactions segments by name:
					new LinkedHashMap<String, ReactionsSegment>(),
					// included reactions segments with their absolute import path:
					new LinkedHashMap<ReactionsSegment, ReactionsImportPath>()
				);
			],
			// early visitor:
			[ sourceImport, currentImportPath, currentReactionsSegment, data |
				val segmentsByName = data.first;
				val segmentsToImportPath = data.second;
				// add included routines facades:
				val currentSegmentIncludedImports = currentReactionsSegment.reactionsImports.filter[it.isResolvable && it.useQualifiedNames];
				val currentSegmentIncludedSegments = currentSegmentIncludedImports.map[it.importedReactionsSegment];
				for (includedSegment : currentSegmentIncludedSegments) {
					val includedSegmentFormattedName = includedSegment.formattedName;
					val includedSegmentImportPath = currentImportPath.append(includedSegment.name); 
					// only include one reactions segment for each unique formatted reactions segment name:
					if (segmentsByName.putIfAbsent(includedSegmentFormattedName, includedSegment) === null) {
						segmentsToImportPath.put(includedSegment, includedSegmentImportPath);
					}
				}
			],
			// late visitor:
			null,
			// import filter:
			// recursively walk along imports that import routines without qualified names,
			// in order to include all the transitively included routines facades:
			[!it.useQualifiedNames],
			// return value:
			[it.second]
		);
	}

	/**
	 * This visitor gets called for each reactions segment while walking along a certain import path, starting with the root
	 * reactions segment.
	 * <p>
	 * It can abort the walking by returning a <code>non-null</code> return value.
	 * 
	 * @param <T>
	 *            the type of the return value
	 */
	static interface ImportPathVisitor<T> {
		/**
		 * Gets called for each reactions segment along the walked import path and can abort the walking by returning a
		 * <code>non-null</code> return value.
		 * 
		 * @param sourceImport
		 *            the reactions import that lead to the current reactions segment, or <code>null</code> at the root segment
		 * @param currentImportPath
		 *            the absolute import path to the current reactions segment, starting with the root reactions segment
		 * @param currentReactionsSegment
		 *            the current reactions segment
		 * @param remainingImportPath
		 *            the remaining import path to walk, relative to the current reactions segment, empty if the end of the path
		 *            is reached
		 * @return the return value, or <code>null</code> to continue walking
		 */
		def T visit(ReactionsImport sourceImport, ReactionsImportPath currentImportPath, ReactionsSegment currentReactionsSegment,
			ReactionsImportPath remainingImportPath);
	}

	/**
	 * Walks down the import hierarchy starting at the given root reactions segment along the given reactions import path and
	 * asks the <code>visitor</code> at each reactions segment along the path (including the root reactions segment)
	 * for a return value and returns the first one that isn't </code>null</code>.
	 * <p>
	 * The given import path is expected to be relative to the specified root reactions segment.
	 * <p>
	 * Any cyclic imports or imports that cannot be resolved along the path will be considered non-existent and therefore not be
	 * followed.
	 * 
	 * @param <T> the type of the return value
	 * @param rootReactionsSegment the reactions segment to start the walk at, not <code>null</code>
	 * @param relativeImportPath the relative import path to walk, not <code>null</code>
	 * @param visitor the visitor, not <code>null</code>
	 * @return the return value provided by the visitor, or <code>null</code> if the end of the path is reached without the
	 *         visitor providing a <code>non-null</code> return value, or if the path does not exist in the import hierarchy of
	 *         the root reactions segment
	 */
	static def <T> T walkImportPath(ReactionsSegment rootReactionsSegment, ReactionsImportPath relativeImportPath, ImportPathVisitor<T> visitor) {
		checkNotNull(rootReactionsSegment, "rootReactionsSegment is null");
		checkNotNull(relativeImportPath, "relativeImportPath is null");
		checkNotNull(visitor, "visitor is null");

		// check if the requested import path contains a cyclic sequence:
		val uniquePathSegments = relativeImportPath.segments.toSet;
		if (uniquePathSegments.size != relativeImportPath.length || uniquePathSegments.contains(rootReactionsSegment.name)) {
			return null;
		}

		val rootImportPath = ReactionsImportPath.create(rootReactionsSegment.name);
		return _walkImportPath(null, rootImportPath, rootReactionsSegment, relativeImportPath, visitor);
	}

	private static def <T> T _walkImportPath(ReactionsImport sourceImport, ReactionsImportPath currentImportPath, ReactionsSegment currentReactionsSegment,
		ReactionsImportPath remainingImportPath, ImportPathVisitor<T> visitor) {
		// check for a return value:
		val returnValue = visitor.visit(sourceImport, currentImportPath, currentReactionsSegment, remainingImportPath);
		if (returnValue !== null) return returnValue;
		// continue search recursively:
		if (remainingImportPath.isEmpty) {
			// reached end of path without non-null return value:
			return null;
		}
		val nextReactionsSegmentName = remainingImportPath.firstSegment;
		val nextImportPath = currentImportPath.append(nextReactionsSegmentName);
		val nextRemainingImportPath = remainingImportPath.relativeToRoot; // can be empty
		// skipping unresolvable imports:
		val nextImport = currentReactionsSegment.reactionsImports.filter[it.isResolvable].findFirst [
			nextReactionsSegmentName.equals(it.importedReactionsSegment.name);
		];
		if (nextImport === null) {
			// invalid import path:
			return null;
		}
		val nextReactionsSegment = nextImport.importedReactionsSegment;
		return _walkImportPath(nextImport, nextImportPath, nextReactionsSegment, nextRemainingImportPath, visitor);
	}

	/**
	 * Gets the reactions segment at the specified import path.
	 * <p>
	 * This walks the import hierarchy along the specified import path, starting from the given root reactions segment. The
	 * import path is expected to be relative to the specified root reactions segment.
	 * 
	 * @param rootReactionsSegment the reactions segment to start the walk at, not <code>null</code>
	 * @param relativeImportPath the relative import path to walk, not <code>null</code>
	 * @return the reactions segment at the end of the specified import path, or <code>null</code> if no reactions segment is
	 *         found for the specified path
	 */
	static def ReactionsSegment getReactionsSegment(ReactionsSegment rootReactionsSegment, ReactionsImportPath relativeImportPath) {
		return walkImportPath(rootReactionsSegment, relativeImportPath,
			[ sourceImport, currentImportPath, currentReactionsSegment, remainingPath |
				if (remainingPath.isEmpty) {
					// reached end of path:
					return currentReactionsSegment;
				} else {
					// continue walking:
					return null;
				}
			]
		);
	}

	/**
	 * Searches through the import hierarchy for the first reactions segment that overrides routines of the specified reactions
	 * segment. If no such reactions segment is found, the overridden reactions segment itself is returned.
	 * <p>
	 * The search starts at the given root reactions segment. The <code>checkRootReactionsSegment</code> parameter controls
	 * whether the given root reactions segment gets considered as possible override root (in case it itself overrides routines
	 * of the specified reactions segment).
	 * <p>
	 * The <code>overriddenRoutineImportPath</code> is expected to be relative to the specified root reactions segment. If it is
	 * empty, the root reactions segment is returned (regardless of the <code>checkRootReactionsSegment</code> parameter).
	 * 
	 * @param rootReactionsSegment the reactions segment to start the walk at, not <code>null</code>
	 * @param overriddenRoutineImportPath the relative import path to the overridden segment, not <code>null</code>
	 * @param checkRootReactionsSegment whether to consider the root reactions segment as possible override root
	 * @return the first reactions segment that overrides routines of the specified reactions segment, or the overridden
	 *         reactions segment itself, together with its absolute reactions import path (starting with the root segment)
	 */
	static def Pair<ReactionsImportPath, ReactionsSegment> getRoutinesOverrideRoot(ReactionsSegment rootReactionsSegment,
		ReactionsImportPath overriddenRoutineImportPath, boolean checkRootReactionsSegment) {
		return walkImportPath(rootReactionsSegment, overriddenRoutineImportPath,
			[ sourceImport, currentImportPath, currentReactionsSegment, remainingPath |
				if (remainingPath.isEmpty) {
					// reached end of path:
					return Pair.of(currentImportPath, currentReactionsSegment);
				} else {
					// check the routine overrides of the current segment, if it is not the root or we are checking the root as well:
					if (checkRootReactionsSegment || currentImportPath.length > 1) {
						// check if the current reactions segment contains a routine override for the remaining import path:
						val overriddenRoutinesImportPaths = currentReactionsSegment.overrideRoutines.map[it.overrideImportPath.toReactionsImportPath];
						if (overriddenRoutinesImportPaths.findFirst[it.equals(remainingPath)] !== null) {
							return Pair.of(currentImportPath, currentReactionsSegment);
						}
					}
					// continue walking:
					return null;
				}
			]
		);
	}
}
