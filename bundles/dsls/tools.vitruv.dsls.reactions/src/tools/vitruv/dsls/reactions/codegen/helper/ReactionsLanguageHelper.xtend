package tools.vitruv.dsls.reactions.codegen.helper

import java.util.Map
import java.util.LinkedHashMap
import java.util.List
import java.util.ArrayList
import org.eclipse.xtext.nodemodel.util.NodeModelUtils
import org.eclipse.xtext.xbase.XExpression
import org.eclipse.xtext.xbase.XBlockExpression
import tools.vitruv.dsls.mirbase.mirBase.MetaclassReference
import tools.vitruv.dsls.mirbase.mirBase.DomainReference
import tools.vitruv.dsls.reactions.reactionsLanguage.ReactionsSegment
import edu.kit.ipd.sdq.commons.util.java.Pair
import tools.vitruv.framework.domains.VitruvDomainProvider
import tools.vitruv.framework.domains.VitruvDomain
import tools.vitruv.dsls.reactions.generator.SimpleTextXBlockExpression
import tools.vitruv.dsls.reactions.reactionsLanguage.ReactionsFile
import org.eclipse.emf.ecore.resource.Resource
import static com.google.common.base.Preconditions.*
import tools.vitruv.framework.domains.VitruvDomainProviderRegistry
import tools.vitruv.dsls.reactions.api.generator.ReferenceClassNameAdapter
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EClassifier
import tools.vitruv.dsls.reactions.reactionsLanguage.Reaction
import tools.vitruv.dsls.reactions.reactionsLanguage.Routine
import tools.vitruv.dsls.reactions.reactionsLanguage.ReactionsImport
import tools.vitruv.dsls.reactions.reactionsLanguage.ReactionsLanguagePackage
import static extension tools.vitruv.dsls.reactions.util.ReactionsLanguageUtil.*

final class ReactionsLanguageHelper {
	private new() {
	}

	public static def dispatch String getXBlockExpressionText(XExpression expression) '''
	{
		«NodeModelUtils.getNode(expression).text»
	}'''

	public static def dispatch String getXBlockExpressionText(XBlockExpression expression) {
		NodeModelUtils.getNode(expression).text;
	}

	public static def dispatch String getXBlockExpressionText(SimpleTextXBlockExpression blockExpression) {
		blockExpression.text.toString;
	}
	
	private static def getOptionalReferenceAdapter(EObject element) {
		element.eAdapters.findFirst [isAdapterForType(ReferenceClassNameAdapter)] as ReferenceClassNameAdapter
	}

	public static def getJavaClassName(EClassifier eClassifier) {
		eClassifier.optionalReferenceAdapter?.qualifiedNameForReference ?: eClassifier.instanceClassName
	}
	
	public static def getRuntimeClassName(EObject element) {
		element.optionalReferenceAdapter?.qualifiedNameForReference ?: element.class.canonicalName
	}

	public static def getJavaClassName(MetaclassReference metaclassReference) {
		metaclassReference.metaclass.javaClassName;
	}

	public static def VitruvDomainProvider<?> getProviderForDomain(VitruvDomain domain) {
		return VitruvDomainProviderRegistry.getDomainProvider(domain.name);
	}

	public static def VitruvDomain getDomainForReference(DomainReference domainReference) {
		return getDomainProviderForReference(domainReference).domain;
	}

	public static def VitruvDomainProvider<?> getDomainProviderForReference(DomainReference domainReference) {
		val referencedDomainProvider = VitruvDomainProviderRegistry.getDomainProvider(domainReference.domain)
		if (referencedDomainProvider === null) {
			throw new IllegalStateException("Given domain reference references no existing domain");
		}
		return referencedDomainProvider;
	}

	static def Pair<VitruvDomain, VitruvDomain> getSourceTargetPair(ReactionsSegment reactionsSegment) {
		val sourceDomain = reactionsSegment.fromDomain.domainForReference;
		val targetDomain = reactionsSegment.toDomain.domainForReference;
		if (sourceDomain !== null && targetDomain !== null) {
			return new Pair<VitruvDomain, VitruvDomain>(sourceDomain, targetDomain);
		} else {
			return null;
		}
	}

	def static ReactionsFile getReactionsFile(Resource resource) {
		val firstContentElement = resource?.contents?.head
		checkArgument(firstContentElement instanceof ReactionsFile,
			"The given resource %s was expected to contain a ReactionsFile element! (was %s)", resource,
			firstContentElement?.class?.simpleName);

		return firstContentElement as ReactionsFile;
	}
	
	def static getOptionalReactionsFile(Resource resource) {
		val firstContentElement = resource?.contents?.head
		if (firstContentElement instanceof ReactionsFile) {
			firstContentElement
		} else {
			null
		}
	}
	
	def static containsReactionsFile(Resource resource) {
		resource.optionalReactionsFile !== null
	}

	// routine call method name:
	public static def String getCallMethodName(Routine routine) {
		return routine.name;
	}

	// import of reactions and routines:

	/**
	 * Gets the parsed imported reactions segment name for the given reactions import, without actually resolving the cross-reference. 
	 */
	public static def String getParsedImportedReactionsSegmentName(ReactionsImport reactionsImport) {
		val nodes = NodeModelUtils.findNodesForFeature(reactionsImport, ReactionsLanguagePackage.Literals.REACTIONS_IMPORT__IMPORTED_REACTIONS_SEGMENT);
		if (nodes.isEmpty) return null;
		// TODO use NodeModelUtils.getTokenText(), just in case?
		return nodes.get(0).text;
	}

	/**
	 * Gets the parsed overridden reactions segment name for the given reaction, without actually resolving the cross-reference. 
	 */
	public static def String getParsedOverriddenReactionsSegmentName(Reaction reaction) {
		val nodes = NodeModelUtils.findNodesForFeature(reaction, ReactionsLanguagePackage.Literals.REACTION__OVERRIDDEN_REACTIONS_SEGMENT);
		if (nodes.isEmpty) return null;
		// TODO use NodeModelUtils.getTokenText(), just in case?
		return nodes.get(0).text;
	}

	/**
	 * Gets the parsed overridden reactions segment name for the given routine, without actually resolving the cross-reference. 
	 */
	public static def String getParsedOverriddenReactionsSegmentName(Routine routine) {
		val nodes = NodeModelUtils.findNodesForFeature(routine, ReactionsLanguagePackage.Literals.ROUTINE__OVERRIDDEN_REACTIONS_SEGMENT);
		if (nodes.isEmpty) return null;
		// TODO use NodeModelUtils.getTokenText(), just in case?
		return nodes.get(0).text;
	}

	public static def String getImportedRoutinesFacadeFieldName(ReactionsSegment importedReactionsSegment) {
		return importedReactionsSegment.name;
	}

	/**
	 * Searches through the reactions import hierarchy for the first reactions segment that imports the specified reactions
	 * segment. If no such reactions segment is found, the imported reactions segment itself is returned.
	 * The search starts at the reactions imports of the given reactions segment. Depending on the checkSegmentItself parameter,
	 * the given reactions segment gets considered as possible root (in case it directly imports the specified reactions
	 * segment), or not (in which case only the imported reactions segments get considered as possible root). 
	 */
	public static def ReactionsSegment getImportedReactionsSegmentRoot(ReactionsSegment reactionsSegment,
		ReactionsSegment importedReactionsSegment, boolean checkSegmentItself) {
		// search recursively through all directly and transitively imported segments:
		val root = reactionsSegment.findImportedReactionsSegmentRoot(importedReactionsSegment, checkSegmentItself);
		// return imported reactions segment itself if no root was found:
		return root ?: importedReactionsSegment;
	}

	private static def ReactionsSegment findImportedReactionsSegmentRoot(ReactionsSegment reactionsSegment,
		ReactionsSegment importedReactionsSegment, boolean checkImportsOfCurrentSegment) {
		val importedReactionsSegments = reactionsSegment.reactionsImports.map[it.importedReactionsSegment];
		// check if the current reactions segment imports the specified reactions segment:
		if (checkImportsOfCurrentSegment) {
			// TODO NPE during Project>Clean with open editor windows
			if (importedReactionsSegments.exists[it.name.equals(importedReactionsSegment.name)]) {
				return reactionsSegment;
			}
		}
		// search recursively through all transitively imported segments, returns null if no root was found:
		return importedReactionsSegments.map [
			it.findImportedReactionsSegmentRoot(importedReactionsSegment, true);
		].findFirst[it !== null];
	}

	// includes transitively imported reactions segments:
	public static def List<ReactionsSegment> getAllImportedReactionsSegments(ReactionsSegment reactionsSegment) {
		val allImportedReactionsSegments = new ArrayList<ReactionsSegment>();
		reactionsSegment.addAllImportedReactionsSegments(allImportedReactionsSegments)
		return allImportedReactionsSegments;
	}

	private static def void addAllImportedReactionsSegments(ReactionsSegment reactionsSegment, List<ReactionsSegment> allImportedReactionsSegments) {
		// recursively add all directly and transitively imported reactions segments that are not yet contained:
		val importedReactionsSegments = reactionsSegment.reactionsImports.map[it.importedReactionsSegment];
		importedReactionsSegments.forEach [
			val importedReactionsSegmentName = it.name;
			if (allImportedReactionsSegments.findFirst[it.name.equals(importedReactionsSegmentName)] === null) {
				allImportedReactionsSegments.add(it);
				it.addAllImportedReactionsSegments(allImportedReactionsSegments);
			}
		];
	}

	// gets all reactions of the given segment, including own reactions and directly and transitively imported reactions, with overridden reactions being replaced
	// keys: the segment itself and all imported segments, values: all reactions of that segment, with overridden reactions being replaced
	public static def Map<ReactionsSegment, List<Reaction>> getAllReactions(ReactionsSegment reactionsSegment) {
		val reactionsBySegment = new LinkedHashMap<ReactionsSegment, List<Reaction>>();
		// recursively add all transitively imported reactions, replace overridden reactions and add own reactions:
		addReactions(reactionsSegment, reactionsBySegment);
		return reactionsBySegment;
	}

	// recursively adds all transitively imported reactions, then replaces overridden reactions and adds its own reactions
	private static def void addReactions(ReactionsSegment reactionsSegment, Map<ReactionsSegment, List<Reaction>> reactionsBySegment) {
		// recursively add all transitively imported reactions:
		for (reactionsImport : reactionsSegment.reactionsImports) {
			addReactions(reactionsImport.importedReactionsSegment, reactionsBySegment);
		}

		// replace overridden reactions:
		for (overrideReaction : reactionsSegment.overrideReactions) {
			val reactions = reactionsBySegment.get(overrideReaction.overriddenReactionsSegment);
			if (reactions !== null) {
				reactions.replaceAll([
					if (it.name.equals(overrideReaction.name)) {
						return overrideReaction;
					} else {
						return it;
					}
				]);
			}
		}

		// add own reactions:
		val ownReactions = new ArrayList<Reaction>();
		ownReactions.addAll(reactionsSegment.regularReactions);
		reactionsBySegment.putIfAbsent(reactionsSegment, ownReactions);
	}
}
