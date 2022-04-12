package tools.vitruv.dsls.commonalities.scoping

import com.google.common.base.Predicate
import com.google.common.base.Predicates
import com.google.inject.Inject
import com.google.inject.Provider
import java.util.Collections
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.xtext.common.types.xtext.TypesAwareDefaultGlobalScopeProvider
import org.eclipse.xtext.resource.IEObjectDescription
import org.eclipse.xtext.scoping.IScope
import org.eclipse.xtext.scoping.impl.FilteringScope
import org.eclipse.xtext.scoping.impl.SimpleScope
import tools.vitruv.dsls.commonalities.names.IEObjectDescriptionProvider

import static tools.vitruv.dsls.commonalities.language.LanguagePackage.Literals.*

import static extension tools.vitruv.dsls.commonalities.language.extensions.CommonalitiesLanguageModelExtensions.*
import static tools.vitruv.dsls.common.elements.ElementsPackage.Literals.METAMODEL_IMPORT__PACKAGE
import tools.vitruv.dsls.common.elements.EPackageRegistryScope
import tools.vitruv.dsls.commonalities.language.elements.MetamodelProvider

class CommonalitiesLanguageGlobalScopeProvider extends TypesAwareDefaultGlobalScopeProvider {
	@Inject extension IEObjectDescriptionProvider descriptionProvider
	@Inject Provider<EPackageRegistryScope> packagesScope
	@Inject MetamodelProvider metamodelProvider
	
	override getScope(Resource resource, EReference reference, Predicate<IEObjectDescription> filter) {
		new ComposedScope(
			// Delegating to the default global scope provider first ensures that we get actual Concept and
			// Commonality instances for commonality participation domains and participation classes, rather than
			// EClassAdapters as they would get created by the MetamodelMetaclassesScope.
			super.getScope(resource, reference, filter),
			new FilteringScope(_getScope(resource, reference), filter ?: Predicates.alwaysTrue)
		)
	}

	private def _getScope(Resource resource, EReference reference) {
		switch (reference) {
			case METAMODEL_IMPORT__PACKAGE:
				return packagesScope.get()
			case PARTICIPATION_CLASS__SUPER_METACLASS: 
				new MetamodelMetaclassesScope(resource, descriptionProvider, metamodelProvider)
			case COMMONALITY_REFERENCE__REFERENCE_TYPE: // self scope
				resource.localCommonalityScope
			case COMMONALITY_ATTRIBUTE_REFERENCE__COMMONALITY: // self scope
				resource.localCommonalityScope
			default:
				IScope.NULLSCOPE
		}
	}

	private def getLocalCommonalityScope(Resource resource) {
		return new SimpleScope(IScope.NULLSCOPE, Collections.singleton(
			resource.containedCommonalityFile.commonality.describe()
		))
	}
}
