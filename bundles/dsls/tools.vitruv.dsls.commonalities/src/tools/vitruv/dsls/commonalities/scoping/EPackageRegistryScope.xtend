package tools.vitruv.dsls.commonalities.scoping

import org.eclipse.xtext.scoping.IScope
import org.eclipse.xtext.naming.QualifiedName
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtext.resource.IEObjectDescription
import org.eclipse.emf.ecore.EPackage
import java.util.HashMap
import com.google.inject.Singleton
import org.eclipse.emf.ecore.InternalEObject
import org.eclipse.emf.ecore.EcoreFactory
import org.eclipse.emf.common.util.URI
import org.eclipse.xtext.EcoreUtil2

@Singleton
class EPackageRegistryScope implements IScope {

	final HashMap<QualifiedName, EPackageDescription> descriptionCache = new HashMap

	override getAllElements() {
		allDescriptions.map[it]
	}
	
	def getAvailableEPackageUris() {
		EPackage.Registry.INSTANCE.keySet
	}

	def private getAllDescriptions() {
		availableEPackageUris.map[uri|getDescription(uri)]
	}

	def private getDescription(String uri) {
		getDescription(QualifiedName.create(uri));
	}

	def private getDescription(QualifiedName name) {
		descriptionCache.computeIfAbsent(name, [new EPackageDescription(it)]);
	}

	override getElements(QualifiedName name) {
		return #[getDescription(name)]
	}

	override getElements(EObject object) {
		allDescriptions.filter[it.matchesObject(object)].map[it]
	}

	override getSingleElement(QualifiedName name) {
		getDescription(name);
	}

	override getSingleElement(EObject object) {
		allDescriptions.findFirst[it.matchesObject(object)]
	}

	private static class EPackageDescription implements IEObjectDescription {

		final InternalEObject proxy
		final QualifiedName uriName

		new(QualifiedName name) {
			val uri = name.toString
			this.uriName = name

			this.proxy = EcoreFactory.eINSTANCE.createEPackage as InternalEObject
			this.proxy.eSetProxyURI(URI.createURI(uri))
		}

		override getEClass() {
			proxy.eClass
		}

		override getEObjectOrProxy() {
			proxy
		}

		override getEObjectURI() {
			proxy.eProxyURI
		}

		override getQualifiedName() {
			uriName
		}

		override getUserData(String key) {
			null
		}

		override getUserDataKeys() {
			#[]
		}

		override getName() {
			uriName
		}

		def private matchesObject(EObject object) {
			return object == proxy || EcoreUtil2.getPlatformResourceOrNormalizedURI(object) == EObjectURI
		}
	}
}
