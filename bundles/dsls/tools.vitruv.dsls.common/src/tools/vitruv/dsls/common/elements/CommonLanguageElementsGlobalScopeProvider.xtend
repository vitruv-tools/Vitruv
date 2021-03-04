package tools.vitruv.dsls.common.elements

import com.google.common.base.Predicate
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.xtext.resource.IEObjectDescription

import static tools.vitruv.dsls.common.elements.ElementsPackage.Literals.*
import com.google.inject.Inject
import com.google.inject.Provider

class CommonLanguageElementsGlobalScopeProvider {
	@Inject Provider<EPackageRegistryScope> packagesScope

	def getScope(Resource resource, EReference ref, Predicate<IEObjectDescription> filter) {
		if (ref.equals(METAMODEL_IMPORT__PACKAGE)) {
			return packagesScope.get()
		}
		return null
	}

}
