package edu.kit.ipd.sdq.vitruvius.dsls.response.scoping

import com.google.inject.Inject
//import edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.ConstraintBlock
//import edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.ConstraintExpression
//import edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.ContextVariable
//import edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.FeatureOfContextVariable
//import edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.Mapping
//import edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.MappingLanguagePackage
//import edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.NamedEClass
//import edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.RequiredMapping
//import edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.RequiredMappingPathBase
//import edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.RequiredMappingPathTail
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.Pair
import java.util.Iterator
import java.util.List
import java.util.function.Function
import org.apache.log4j.Logger
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EClassifier
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.EcorePackage
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.mwe2.language.scoping.QualifiedNameProvider
import org.eclipse.xtext.naming.QualifiedName
import org.eclipse.xtext.resource.EObjectDescription
import org.eclipse.xtext.resource.IEObjectDescription
import org.eclipse.xtext.scoping.IScope
import org.eclipse.xtext.scoping.impl.SimpleScope
import org.eclipse.xtext.xbase.scoping.XImportSectionNamespaceScopeProvider

import static edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.ResponseLanguagePackage.Literals.*

//import static extension edu.kit.ipd.sdq.vitruvius.dsls.mapping.helpers.EMFHelper.*
//import static extension edu.kit.ipd.sdq.vitruvius.dsls.mapping.helpers.MappingLanguageHelper.*
//import static extension edu.kit.ipd.sdq.vitruvius.framework.mir.executor.helpers.JavaHelper.*
import static extension java.util.Objects.*
import org.eclipse.xtext.scoping.impl.FilteringScope
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.FeatureOfElement
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.Import
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.ResponseLanguagePackage
import org.eclipse.emf.ecore.EPackage
import org.eclipse.emf.ecore.impl.EPackageImpl
import org.eclipse.emf.ecore.impl.EcoreFactoryImpl
import org.eclipse.emf.common.util.URI
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.ResponseLanguageFactory
import java.util.ArrayList

/**
 * Copy of edu.kit.ipd.sdq.vitruvius.dsls.mapping.scoping.MappingLanguageScopeProviderDelegate by Dominik Werle
 */
// TODO HK refactor to only one implementation
class ResponseLanguageScopeProviderDelegate extends XImportSectionNamespaceScopeProvider {
	private static val LOGGER = Logger.getLogger(ResponseLanguageScopeProviderDelegate)
	private static val String CHANGE_MM_URI = "http://edu.kit.ipd.sdq.vitruvius/Change/1.0";
	
//	@Inject
//	QualifiedNameProvider qualifiedNameProvider;

	def <T extends EObject> IScope createPairScope(IScope parentScope, Iterator<Pair<String, T>> elements) {
		createScope(parentScope, elements, [
			EObjectDescription.create(it.first, it.second)
		])
	}

	def <T extends EObject> IScope createScope(IScope parentScope, Iterator<T> elements) {
		createScope(parentScope, elements.filter[hasQualifiedName], [
			val qn = qualifiedNameProvider.getFullyQualifiedName(it)
			if ((qn != null) && (!qn.isEmpty))
				EObjectDescription.create(qn, it)
			else
				null
		])
	}

	def <T> IScope createScope(IScope parentScope, Iterator<T> elements,
		Function<T, IEObjectDescription> descriptionCreation) {
		new SimpleScope(
			parentScope,
			elements.map [
				descriptionCreation.apply(it)
			].filterNull.toList
		)
	}
	
	override getScope(EObject context, EReference reference) {
		if ((reference.equals(FEATURE_OF_ELEMENT__FEATURE))
			&& (context instanceof FeatureOfElement))
			return createEStructuralFeatureScope(context as FeatureOfElement)
		else if (reference.equals(MODEL_CHANGE_EVENT__CHANGE))
			return createChangeEventsScope(context.eResource)
		else if (reference.getEType.equals(EcorePackage.Literals.ECLASS))
			return createQualifiedEClassScope(context.eResource)
//		else if (reference.equals(REQUIRED_MAPPING_PATH_TAIL__REQUIRED_MAPPING))
//			return createRequiredMappingPathTailScope(context)
//		else if (reference.equals(CONTEXT_VARIABLE__TARGET_CLASS))
//			return createTargetClassScope(context)
//		else if (reference.equals(REQUIRED_MAPPING__MAPPING))
//			return createRequiresScope(context)

		super.getScope(context, reference)
	}
	
	
	/*def IScope createRequiresScope(EObject context) {
		val superScope = super.getScope(context, REQUIRED_MAPPING__MAPPING)
		return new FilteringScope(superScope, [
			val mapping = it.EObjectOrProxy as Mapping
			(mapping.^null) || (!mapping.^default) 
		])
	}*/

//	def IScope createMappingBaseScope(IScope parentScope, Mapping mapping) {
//		var baseScope = parentScope
//		for (it : mapping.requires) {
//			baseScope = recursiveCreateMappingBaseScope(baseScope, it.mapping)
//		}
//		baseScope
//	}
//
//	def IScope recursiveCreateMappingBaseScope(IScope parentScope, Mapping mapping) {
//		var baseScope = parentScope
//		for (it : mapping.requires) {
//			baseScope = recursiveCreateMappingBaseScope(baseScope, it.mapping)
//		}
//
//		for (it : mapping.signatures.filterNull) {
//			baseScope = createScope(baseScope, it.eContents.validNamedEClasses.iterator)
//		}
//
//		baseScope
//	}
//
//	def <T> Iterable<NamedEClass> validNamedEClasses(Iterable<T> iterable) {
//		iterable.filter(NamedEClass).filter[hasQualifiedName]
//	}

	def hasQualifiedName(EObject eObject) {
		val qn = qualifiedNameProvider.getFullyQualifiedName(eObject)
		((qn != null) && (!qn.empty)
		)
	}
	
	def dispatch Pair<String, EObject> namePair(EObject eObject) {
		new Pair(eObject.toString, eObject)
	}
	
//	def dispatch Pair<String, EObject> namePair(Mapping mapping) {
//		new Pair(mapping.name, mapping)
//	}
//	
//	def dispatch Pair<String, EObject> namePair(RequiredMapping mapping) {
//		new Pair(mapping.name, mapping)
//	}
//	
//	def createRequiredMappingPathBaseScope(EObject context) {
//		if (!(context instanceof RequiredMappingPathBase || context instanceof ConstraintExpression))
//			LOGGER.debug("Unexpected context for base scope: " + context.toString)
//
//		val containerMapping = context.getContainerOfType(Mapping)
//		return createPairScope(IScope.NULLSCOPE,
//			(containerMapping.requires).map[namePair].filter[first != null && second != null].iterator)
//	}
//	
//	def createRequiredMappingPathTailScope(EObject context) {
//		if (context instanceof RequiredMappingPathTail) {
//			return createPairScope(IScope.NULLSCOPE, (context.eContainer.requiredMappings).map[namePair].iterator)
//		} else {
//			LOGGER.debug("Unexpected context for tail scope: " + context.toString)
//			return IScope.NULLSCOPE
//		}
//	}
//	
//	def dispatch List<RequiredMapping> getRequiredMappings(RequiredMappingPathBase pathBase) {
//		return pathBase.requiredMapping?.mapping?.requires ?: #[]
//	}
//	
//	def dispatch List<RequiredMapping> getRequiredMappings(RequiredMappingPathTail pathTail) {
//		return pathTail.requiredMapping?.mapping?.requires ?: #[]
//	}
//
////	def createTargetMappingScope(EObject object) {
////		val constraintBlock = object.getContainerOfType(ConstraintBlock)
////		val mapping = constraintBlock.eContainer.requireType(Mapping)
////
////		val requiresScope = createMappingBaseScope(IScope.NULLSCOPE, mapping)
////
////		switch (constraintBlock.eContainingFeature) {
////			case MAPPING__CONSTRAINTS0:
////				return createScope(requiresScope, mapping.signature0.eContents.validNamedEClasses.iterator)
////			case MAPPING__CONSTRAINTS1:
////				return createScope(requiresScope, mapping.signature1.eContents.validNamedEClasses.iterator)
////			case MAPPING__CONSTRAINTS_BODY:
////				return recursiveCreateMappingBaseScope(IScope.NULLSCOPE, mapping)
////		}
////
////		return IScope.NULLSCOPE
////	}
//
//	def createTargetClassScope(EObject object) {
//		if (!(object instanceof ContextVariable)) {
//			LOGGER.debug("Unexpected context: " + object.toString)
//			return IScope.NULLSCOPE
//		}
//		val context = object as ContextVariable
//
//		val containingMapping = object.getContainerOfType(Mapping).requireNonNull
//
//		var Mapping contextMapping
//		if (context.requiredMappingPath == null) {
////			LOGGER.debug("ContextVariable has no target mapping: " + context.toString)
//			contextMapping = containingMapping
//		} else {
//			contextMapping = context.requiredMappingPath.lastMapping.mapping ?: containingMapping
//		}
//		
//		createPairScope(IScope.NULLSCOPE,
//			contextMapping.signatures.filterNull.map[elements].flatten.map[new Pair(it.name, it)].iterator)
//	}
//
	def createEStructuralFeatureScope(FeatureOfElement variable) {
		if (variable?.element != null) {
			createScope(IScope.NULLSCOPE, variable.element.EAllStructuralFeatures.iterator, [
				EObjectDescription.create(it.name, it)
			])
		} else {
			return IScope.NULLSCOPE
		}
	}

	/**
	 * Returns all elements with the given EClass inside the Resource res.
	 */
	def getAllContentsOfEClass(Resource res, EClass namedParent, boolean allContents) {
		var contents = if (allContents)
				res.allContents.toList
			else
				res.contents

		return contents.filter[eClass.equals(namedParent)]

	}

	/**
	 * Returns all packages that have been imported by import statements
	 * in the given resource.
	 */
	def getImports(Resource res) {
		var contents = res.getAllContentsOfEClass(ResponseLanguagePackage.eINSTANCE.getImport, true).toList
		val validImports = contents.filter(Import).filter[package != null].map[it.name = it.name ?: it.package.name; it]

		return validImports
	}

	/**
	 * Creates and returns a {@link EObjectDescription} with a
	 * qualified name that also includes the name of the given
	 * {@link Import}.
	 */
	def createEObjectDescription(EClassifier classifier, Import imp) {
		if (classifier == null) {
			return null
		}

		return EObjectDescription.create(
			QualifiedName.create(imp.name).append(qualifiedNameProvider.getFullyQualifiedName(classifier).skipFirst(1)),
			classifier
		)
	}

	/**
	 * Create an {@link IScope} that represents all {@link EClass}es
	 * that are referencable inside the {@link Resource} via {@link Import}s
	 * by a fully qualified name.
	 * 
	 * @see MIRScopeProviderDelegate#createQualifiedEClassifierScope(Resource)
	 */
	def createQualifiedEClassScope(Resource res) {
		val classifierDescriptions = res.imports.map [ import |
			import.package.EClassifiers.filter(EClass).map[it.createEObjectDescription(import)]
		].flatten

		var resultScope = new SimpleScope(IScope.NULLSCOPE, classifierDescriptions)
		return resultScope
	}
	
	
	def createChangeEventsScope(Resource res) {
		val changePckg = EPackage.Registry.INSTANCE.get(CHANGE_MM_URI) as EPackage;
		val classifierDescriptions = collectObjectDescriptions(changePckg);
		val resultScope = new SimpleScope(IScope.NULLSCOPE, classifierDescriptions);
		return resultScope
	}
	
	def Iterable<IEObjectDescription> collectObjectDescriptions(EPackage pckg) {
		var recursiveResult = pckg.ESubpackages.map[it | collectObjectDescriptions(it)].flatten
		var result = pckg.EClassifiers.filter(EClass).map[EObjectDescription.create(
			qualifiedNameProvider.getFullyQualifiedName(it).skipFirst(1), it)];
		return recursiveResult + result;
	}
	
}
