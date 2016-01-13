package edu.kit.ipd.sdq.vitruvius.dsls.response.scoping

import com.google.common.base.Predicate
import com.google.inject.Inject
import java.util.Collections
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EPackage
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.EcoreFactory
import org.eclipse.emf.ecore.InternalEObject
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.xtext.naming.IQualifiedNameConverter
import org.eclipse.xtext.resource.EObjectDescription
import org.eclipse.xtext.resource.IEObjectDescription
import org.eclipse.xtext.scoping.IScope
import org.eclipse.xtext.scoping.impl.SimpleScope

import static edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.ResponseLanguagePackage.Literals.*
import org.eclipse.xtext.common.types.xtext.TypesAwareDefaultGlobalScopeProvider

/**
 * Copy of edu.kit.ipd.sdq.vitruvius.dsls.mapping.scoping.MappingLanguageGlobalScopeProvider by Dominik Werle
 */
// TODO HK refactor to only one implementation
class ResponseLanguageGlobalScopeProvider extends TypesAwareDefaultGlobalScopeProvider {
	@Inject
	IQualifiedNameConverter qualifiedNameConverter
	
	IScope packageScope = null;
	
	override getScope(Resource resource, EReference reference) {
		super.getScope(resource, reference)
	}
	
	private def getPackageScope() {
		if (packageScope == null)
			packageScope = new SimpleScope(IScope.NULLSCOPE,
				EPackage.Registry.INSTANCE.keySet.map [
					from |
					val InternalEObject proxyPackage = EcoreFactory.eINSTANCE.createEPackage as InternalEObject;
					proxyPackage.eSetProxyURI(URI.createURI(from));
					return EObjectDescription.create(
						qualifiedNameConverter.toQualifiedName(from),
						proxyPackage, Collections.singletonMap("nsURI", "true")
					);
				])
		
		return packageScope
	}
	
	override getScope(Resource resource, EReference ref, Predicate<IEObjectDescription> filter) {
		if (ref.equals(NAMESPACE_IMPORT__PACKAGE)) {
			return getPackageScope
		}
			
		super.getScope(resource, ref, filter)
	}
	
}
