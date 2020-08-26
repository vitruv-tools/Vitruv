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

class CommonalitiesLanguageGlobalScopeProvider extends TypesAwareDefaultGlobalScopeProvider {

	@Inject ParticipationRelationOperatorScopeProvider relationOperatorScopeProvider
	@Inject ParticipationConditionOperatorScopeProvider conditionOperatorScopeProvider
	@Inject AttributeMappingOperatorScopeProvider attributeMappingOperatorScopeProvider
	@Inject ReferenceMappingOperatorScopeProvider referenceMappingOperatorScopeProvider
	@Inject Provider<VitruvDomainMetaclassesScope> allMetaclassesScope
	@Inject extension IEObjectDescriptionProvider descriptionProvider

	override getScope(Resource resource, EReference reference, Predicate<IEObjectDescription> filter) {
		switch (reference) {
			case ATTRIBUTE_MAPPING_OPERATOR__JVM_TYPE:
				attributeMappingOperatorScopeProvider.getScope(resource, reference, filter)
			case REFERENCE_MAPPING_OPERATOR__JVM_TYPE:
				referenceMappingOperatorScopeProvider.getScope(resource, reference, filter)
			case PARTICIPATION_RELATION_OPERATOR__JVM_TYPE:
				relationOperatorScopeProvider.getScope(resource, reference, filter)
			case PARTICIPATION_CONDITION_OPERATOR__JVM_TYPE:
				conditionOperatorScopeProvider.getScope(resource, reference, filter)
			default: {
				new ComposedScope(
					// Note: Delegating to the default global scope provider first ensures that we get actual Concept and
					// Commonality instances for commonality participation domains and participation classes, rather than
					// EClassAdapters as they would get created by the VitruvDomainMetaclassesScope.
					super.getScope(resource, reference, filter),
					new FilteringScope(_getScope(resource, reference), filter ?: Predicates.alwaysTrue)
				)
			}
		}
	}

	private def _getScope(Resource resource, EReference reference) {
		switch (reference) {
			case PARTICIPATION_CLASS__SUPER_METACLASS:
				allMetaclassesScope.get()
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
