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
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.ResponseLanguagePackage
import org.eclipse.emf.ecore.EPackage
import org.eclipse.emf.ecore.impl.EPackageImpl
import org.eclipse.emf.ecore.impl.EcoreFactoryImpl
import org.eclipse.emf.common.util.URI
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.ResponseLanguageFactory
import java.util.ArrayList
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.NamespaceImport
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.Effects
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.Response
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.ModelChangeEvent
import org.eclipse.xtext.xbase.XBlockExpression
import org.eclipse.xtext.xbase.XVariableDeclaration
import org.eclipse.xtext.xbase.XbaseFactory
import org.eclipse.xtext.xbase.XbasePackage
import org.eclipse.xtext.scoping.Scopes
import org.eclipse.xtext.xbase.scoping.batch.XbaseBatchScopeProvider
import org.eclipse.xtext.xbase.scoping.XbaseQualifiedNameProvider
import org.eclipse.xtext.naming.IQualifiedNameProvider
import java.util.List
import org.eclipse.xtext.scoping.impl.ImportNormalizer
import com.google.common.collect.Lists
import static extension edu.kit.ipd.sdq.vitruvius.dsls.response.helper.ResponseLanguageHelper.*;

/**
 * Copy of edu.kit.ipd.sdq.vitruvius.dsls.mapping.scoping.MappingLanguageScopeProviderDelegate by Dominik Werle
 */
// TODO HK refactor to only one implementation
class ResponseLanguageScopeProviderDelegate extends XImportSectionNamespaceScopeProvider {
	private static val LOGGER = Logger.getLogger(ResponseLanguageScopeProviderDelegate)
	private static val String CHANGE_MM_URI = "http://edu.kit.ipd.sdq.vitruvius/Change/1.0";
	
	@Inject
	IQualifiedNameProvider qualifiedNameProvider;

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
		if ((reference.equals(FEATURE_OF_ELEMENT__FEATURE)))
			return createEStructuralFeatureScope(context as FeatureOfElement)
		else if (reference.equals(MODEL_CHANGE_EVENT__CHANGE))
			return createChangeEventsScope(context.eResource)
		else if (reference.equals(FEATURE_OF_ELEMENT__ELEMENT))
			return createQualifiedEClassScope(context.eResource)
		
		super.getScope(context, reference)
	}

	def hasQualifiedName(EObject eObject) {
		val qn = qualifiedNameProvider.getFullyQualifiedName(eObject)
		((qn != null) && (!qn.empty)
		)
	}
	
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
	def getNamespaceImports(Resource res) {
		var contents = res.getAllContentsOfEClass(ResponseLanguagePackage.eINSTANCE.getNamespaceImport, true).toList
		val validImports = contents.filter(NamespaceImport).filter[package != null].map[it.name = it.name ?: it.package.name; it]

		return validImports
	}

	/**
	 * Creates and returns a {@link EObjectDescription} with a
	 * qualified name that also includes the name of the given
	 * {@link Import}.
	 */
	def createEObjectDescription(EClassifier classifier, NamespaceImport imp) {
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
		val classifierDescriptions = res.namespaceImports.map [ import |
			import.package.EClassifiers.filter(EClass).map[it.createEObjectDescription(import)]
		].flatten

		var resultScope = new SimpleScope(IScope.NULLSCOPE, classifierDescriptions)
		return resultScope
	}
	
	
	def createChangeEventsScope(Resource res) {
		val resultScope = new SimpleScope(IScope.NULLSCOPE, changeMMObjects);
		return resultScope	
	}
	
	private final Iterable<IEObjectDescription> changeMMObjects = {
		val changePckg = EPackage.Registry.INSTANCE.getEPackage(CHANGE_MM_URI);
		collectObjectDescriptions(changePckg);
	}
	
	private def Iterable<IEObjectDescription> collectObjectDescriptions(EPackage pckg) {
		var recursiveResult = pckg.ESubpackages.map[it | collectObjectDescriptions(it)].flatten
		// Use the following for fully qualified change names
		// var result = pckg.EClassifiers.filter(EClass).map[EObjectDescription.create(qualifiedNameProvider.getFullyQualifiedName(it).skipFirst(1), it)];
		var result = pckg.EClassifiers.filter(EClass).map[EObjectDescription.create(it.name, it)];
		return recursiveResult + result;
	}
	
}
