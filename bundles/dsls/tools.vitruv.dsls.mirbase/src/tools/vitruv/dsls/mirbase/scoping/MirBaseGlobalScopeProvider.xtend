package tools.vitruv.dsls.mirbase.scoping

import com.google.common.base.Predicate
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.xtext.resource.IEObjectDescription

import static tools.vitruv.dsls.mirbase.mirBase.MirBasePackage.Literals.*
import org.eclipse.xtext.common.types.xtext.TypesAwareDefaultGlobalScopeProvider
import com.google.inject.Inject
import com.google.inject.Provider

class MirBaseGlobalScopeProvider extends TypesAwareDefaultGlobalScopeProvider {
	
	@Inject Provider<EPackageRegistryScope> packagesScope
	
	override getScope(Resource resource, EReference reference) {
		super.getScope(resource, reference)
	}
	
	override getScope(Resource resource, EReference ref, Predicate<IEObjectDescription> filter) {
		if (ref.equals(METAMODEL_IMPORT__PACKAGE)) {
			return packagesScope.get()
		}
		
		return super.getScope(resource, ref, filter)
	}
	
}