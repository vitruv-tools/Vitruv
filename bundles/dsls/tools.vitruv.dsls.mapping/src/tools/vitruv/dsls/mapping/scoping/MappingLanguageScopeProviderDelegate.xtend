package tools.vitruv.dsls.mapping.scoping

import com.google.inject.Inject
import tools.vitruv.dsls.mapping.mappingLanguage.ConstraintExpression
import tools.vitruv.dsls.mapping.mappingLanguage.ContextVariable
import tools.vitruv.dsls.mapping.mappingLanguage.FeatureOfContextVariable
import tools.vitruv.dsls.mapping.mappingLanguage.Mapping
import tools.vitruv.dsls.mapping.mappingLanguage.MappingFile
import tools.vitruv.dsls.mapping.mappingLanguage.RequiredMapping
import tools.vitruv.dsls.mapping.mappingLanguage.RequiredMappingPathBase
import tools.vitruv.dsls.mapping.mappingLanguage.RequiredMappingPathTail
import tools.vitruv.dsls.mirbase.scoping.MirBaseScopeProviderDelegate
import edu.kit.ipd.sdq.commons.util.java.Pair
import java.util.Iterator
import java.util.List
import org.apache.log4j.Logger
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.EcorePackage
import org.eclipse.emf.mwe2.language.scoping.QualifiedNameProvider
import org.eclipse.xtext.resource.EObjectDescription
import org.eclipse.xtext.scoping.IScope

import static tools.vitruv.dsls.mapping.mappingLanguage.MappingLanguagePackage.Literals.*

import static extension tools.vitruv.dsls.mapping.helpers.EMFHelper.*
import static extension tools.vitruv.dsls.mapping.helpers.MappingLanguageHelper.*
import static extension java.util.Objects.*
import tools.vitruv.dsls.mirbase.mirBase.MetaclassReference

/**
 * @author Dominik Werle
 */
class MappingLanguageScopeProviderDelegate extends MirBaseScopeProviderDelegate {
	private static val LOGGER = Logger.getLogger(MappingLanguageScopeProviderDelegate)

	@Inject
	QualifiedNameProvider qualifiedNameProvider;

	def <T extends EObject> IScope createPairScope(IScope parentScope, Iterator<Pair<String, T>> elements) {
		createScope(parentScope, elements, [
			if ((it.first !== null) && (it.second !== null))
				EObjectDescription.create(it.first, it.second)
			else
				null
		])
	}

	def <T extends EObject> IScope createScope(IScope parentScope, Iterator<T> elements) {
		createScope(parentScope, elements.filter[hasQualifiedName], [
			val qn = qualifiedNameProvider.getFullyQualifiedName(it)
			if ((qn !== null) && (!qn.isEmpty))
				EObjectDescription.create(qn, it)
			else
				null
		])
	}

	override getScope(EObject context, EReference reference) {
		if (reference.EType.equals(EcorePackage.Literals.ECLASS))
			return createQualifiedEClassScope(context.eResource)
		else if ((reference.equals(FEATURE_OF_CONTEXT_VARIABLE__FEATURE))
			&& (context instanceof FeatureOfContextVariable))
			return createEStructuralFeatureScope((context as FeatureOfContextVariable)?.context?.targetClass?.metaclass)
		else if (reference.equals(REQUIRED_MAPPING_PATH_BASE__REQUIRED_MAPPING))
			return createRequiredMappingPathBaseScope(context)
		else if (reference.equals(REQUIRED_MAPPING_PATH_TAIL__REQUIRED_MAPPING))
			return createRequiredMappingPathTailScope(context)
		else if (reference.equals(CONTEXT_VARIABLE__TARGET_CLASS))
			return createTargetClassScope(context)
		else if (reference.equals(REQUIRED_MAPPING__MAPPING))
			return createRequiredMappingMappingScope(context)

		super.getScope(context, reference)
	}
	
	def IScope createRequiredMappingMappingScope(EObject context) {
		val allMappings = context.getContainerOfType(MappingFile).eAllContents.filter(Mapping)
		
		createPairScope(IScope.NULLSCOPE, allMappings.map[namePair].filter[first !== null && second !== null])
	}
	
	def IScope createMappingBaseScope(IScope parentScope, Mapping mapping) {
		var baseScope = parentScope
		for (it : mapping.requires) {
			baseScope = recursiveCreateMappingBaseScope(baseScope, it.mapping)
		}
		baseScope
	}

	def IScope recursiveCreateMappingBaseScope(IScope parentScope, Mapping mapping) {
		var baseScope = parentScope
		for (it : mapping.requires) {
			baseScope = recursiveCreateMappingBaseScope(baseScope, it.mapping)
		}

		for (it : mapping.signatures.filterNull) {
			baseScope = createScope(baseScope, it.eContents.validModelElements.iterator)
		}

		baseScope
	}
//
	def <T> Iterable<MetaclassReference> validModelElements(Iterable<T> iterable) {
		iterable.filter(MetaclassReference).filter[hasQualifiedName]
	}

	def dispatch Pair<String, EObject> namePair(EObject eObject) {
		new Pair(eObject.toString, eObject)
	}
	
	def dispatch Pair<String, EObject> namePair(Mapping mapping) {
		new Pair(mapping.name, mapping)
	}
	
	def dispatch Pair<String, EObject> namePair(RequiredMapping mapping) {
		new Pair(mapping.name, mapping)
	}
	
	def createRequiredMappingPathBaseScope(EObject context) {
		if (!(context instanceof RequiredMappingPathBase || context instanceof ConstraintExpression))
			LOGGER.debug("Unexpected context for base scope: " + context.toString)

		val containerMapping = context.getContainerOfType(Mapping)
		val mappingFile = context.getContainerOfType(MappingFile)
		return createPairScope(IScope.NULLSCOPE,
			(containerMapping.requires + mappingFile.defaultRequirements).map[namePair].filter[first !== null && second !== null].iterator)
	}
	
	def createRequiredMappingPathTailScope(EObject context) {
		if (context instanceof RequiredMappingPathTail) {
			return createPairScope(IScope.NULLSCOPE, (context.eContainer.requiredMappings).map[namePair].filter[first !== null && second !== null].iterator)
		} else {
			LOGGER.debug("Unexpected context for tail scope: " + context.toString)
			return IScope.NULLSCOPE
		}
	}
	
	def dispatch List<RequiredMapping> getRequiredMappings(RequiredMappingPathBase pathBase) {
		return pathBase.requiredMapping?.mapping?.requires ?: #[]
	}
	
	def dispatch List<RequiredMapping> getRequiredMappings(RequiredMappingPathTail pathTail) {
		return pathTail.requiredMapping?.mapping?.requires ?: #[]
	}

	def createTargetClassScope(EObject object) {
		val context =
			if (object instanceof ContextVariable)
				object as ContextVariable
			else
				null
		
		val containingMapping = object.getContainerOfType(Mapping).requireNonNull

		var Mapping contextMapping
		if (context?.requiredMappingPath === null) {
//			LOGGER.debug("ContextVariable has no target mapping: " + context.toString)
			contextMapping = containingMapping
		} else {
			contextMapping = (context as ContextVariable).requiredMappingPath.lastMapping.mapping ?: containingMapping
		}
		
		createPairScope(IScope.NULLSCOPE,
			contextMapping.signatures.filterNull.map[elements].flatten.map[new Pair(it.name, it)].iterator)
	}
}