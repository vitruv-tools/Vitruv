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
import static extension tools.vitruv.dsls.reactions.codegen.helper.ClassNamesGenerators.*
import static tools.vitruv.dsls.reactions.codegen.helper.ReactionsLanguageConstants.*

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

	public static def String getFormattedReactionName(Reaction reaction) {
		var String reactionName = "";
		if (reaction.isOverriddenReaction) {
			reactionName += reaction.overriddenReactionsSegment.packageName + OVERRIDDEN_REACTIONS_SEGMENT_SEPARATOR;
		}
		reactionName += reaction.name.toFirstUpper;
		return reactionName;
	}

	public static def String getFormattedRoutineName(Routine routine) {
		var String routineName = "";
		if (routine.isOverriddenRoutine) {
			routineName += routine.overriddenReactionsSegment.packageName + OVERRIDDEN_REACTIONS_SEGMENT_SEPARATOR;
		}
		routineName += routine.name.toFirstLower;
		return routineName;
	}

	// regular vs overridden reactions:

	public static def isRegularReaction(Reaction reaction) {
		return !reaction.isOverriddenReaction;
	}

	public static def isOverriddenReaction(Reaction reaction) {
		// check if overridden reactions segment is set, without triggering resolve:
		return reaction.eIsSet(reaction.eClass.getEStructuralFeature(ReactionsLanguagePackage.REACTION__OVERRIDDEN_REACTIONS_SEGMENT));
	}

	public static def getRegularReactions(ReactionsSegment reactionsSegment) {
		return reactionsSegment.reactions.filter[isRegularReaction];
	}

	public static def getOverriddenReactions(ReactionsSegment reactionsSegment) {
		return reactionsSegment.reactions.filter[isOverriddenReaction];
	}

	// regular vs overridden routines:

	public static def isRegularRoutine(Routine routine) {
		return !routine.isOverriddenRoutine;
	}

	public static def isOverriddenRoutine(Routine routine) {
		// check if overridden reactions segment is set, without triggering resolve:
		return routine.eIsSet(routine.eClass.getEStructuralFeature(ReactionsLanguagePackage.ROUTINE__OVERRIDDEN_REACTIONS_SEGMENT));
	}

	public static def getRegularRoutines(ReactionsSegment reactionsSegment) {
		return reactionsSegment.routines.filter[isRegularRoutine];
	}

	public static def getOverriddenRoutines(ReactionsSegment reactionsSegment) {
		return reactionsSegment.routines.filter[isOverriddenRoutine];
	}

	// import of reactions and routines:

	public static def String getImportedRoutinesFacadeFieldName(ReactionsSegment importedReactionsSegment) {
		return importedReactionsSegment.name;
	}

	// keys: all imported segments (including transitively imported segments), values: the root imports the imported segments originated from
	public static def Map<ReactionsSegment, ReactionsImport> getImportedReactionsSegments(ReactionsSegment reactionsSegment) {
		val importedReactionsSegments = new LinkedHashMap<ReactionsSegment, ReactionsImport>();
		for (rootReactionsImport : reactionsSegment.reactionsImports) {
			// add imported segment and recursively all transitively imported segments:
			addImportedReactionsSegments(rootReactionsImport, rootReactionsImport.importedReactionsSegment, importedReactionsSegments);
		}
		return importedReactionsSegments;
	}

	private static def void addImportedReactionsSegments(ReactionsImport rootReactionsImport, ReactionsSegment importedReactionsSegment,
		Map<ReactionsSegment, ReactionsImport> importedReactionsSegments) {
		// add imported segment:
		importedReactionsSegments.putIfAbsent(importedReactionsSegment, rootReactionsImport);

		// recursively add all transitively imported segments:
		for (reactionsImport : importedReactionsSegment.reactionsImports) {
			addImportedReactionsSegments(rootReactionsImport, reactionsImport.importedReactionsSegment, importedReactionsSegments);
		}
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
		for (overriddenReaction : reactionsSegment.overriddenReactions) {
			val reactions = reactionsBySegment.get(overriddenReaction.overriddenReactionsSegment);
			if (reactions !== null) {
				reactions.replaceAll([
					if (it.name.equals(overriddenReaction.name)) {
						return overriddenReaction;
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
