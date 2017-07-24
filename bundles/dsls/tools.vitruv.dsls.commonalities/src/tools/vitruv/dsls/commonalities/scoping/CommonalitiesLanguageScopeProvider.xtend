package tools.vitruv.dsls.commonalities.scoping

import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import com.google.inject.Inject
import org.eclipse.xtext.scoping.IGlobalScopeProvider
import tools.vitruv.dsls.commonalities.language.AttributeMappingSpecifiation
import com.google.inject.Provider
import static extension tools.vitruv.dsls.commonalities.language.extensions.CommonalitiesLanguageModelExtensions.*

/**
 * This class contains custom scoping description.
 * 
 * See https://www.eclipse.org/Xtext/documentation/303_runtime_concepts.html#scoping
 * on how and when to use it.
 */
class CommonalitiesLanguageScopeProvider extends AbstractCommonalitiesLanguageScopeProvider {
	
	@Inject IGlobalScopeProvider globalScopeProvider
	@Inject Provider<ParticipationAttributesScope> attributesScope

	override getScope(EObject context, EReference reference) {
		switch context {
			AttributeMappingSpecifiation:
				attributesScope.get.forCommonality(context.containingCommonalityFile.commonality)
			
			default:
				globalScopeProvider.getScope(context.eResource, reference, null)
		}
	}
}
