package tools.vitruv.dsls.common.elements

import org.eclipse.xtext.scoping.IScope
import org.eclipse.xtext.naming.QualifiedName
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtext.resource.IEObjectDescription
import org.eclipse.emf.ecore.EPackage
import com.google.inject.Singleton
import org.eclipse.emf.common.util.URI
import org.eclipse.xtext.resource.EObjectDescription
import com.google.inject.Inject
import org.eclipse.xtext.naming.IQualifiedNameConverter
import edu.kit.ipd.sdq.activextendannotations.Lazy

@Singleton
class EPackageRegistryScope implements IScope {
	@Inject
	extension IQualifiedNameConverter qualifiedNameConverter

	override getAllElements() {
		allDescriptions.filter[exists]
	}

	def allEPackages() {
		allDescriptions.filter[exists].map[EObjectOrProxy]
	}

	def private getAvailableEPackageUris() {
		EPackage.Registry.INSTANCE.keySet
	}

	def private getAllDescriptions() {
		availableEPackageUris.map[getDescription]
	}

	def private IEObjectDescription getDescription(String uri) {
		new EPackageDescription(uri.toQualifiedName)
	}

	override getElements(QualifiedName name) {
		val el = getSingleElement(name)
		if(el === null) #[] else #[el]
	}

	override getElements(EObject object) {
		#[getSingleElement(object)].filterNull
	}

	override getSingleElement(QualifiedName name) {
		if(!EPackage.Registry.INSTANCE.containsKey(name.toString)) return null
		val package = new EPackageDescription(name)
		if(package.exists) package else null
	}

	override getSingleElement(EObject object) {
		// allow using existing, not registered packages too (only relevant
		// when artificially creating language files)
		if (object instanceof EPackage) {
			return EObjectDescription.create(object.nsURI.toQualifiedName, object)
		}
		return null
	}

	def private static exists(IEObjectDescription description) {
		description.EObjectOrProxy !== null
	}

	private static class EPackageDescription implements IEObjectDescription {

		val QualifiedName uriName
		@Lazy val EPackage ePackage = EPackage::Registry.INSTANCE.getEPackage(uriName.toString)

		new(QualifiedName name) {
			this.uriName = name
		}

		override getEClass() {
			getEPackage.eClass
		}

		override getEObjectOrProxy() {
			getEPackage
		}

		override getEObjectURI() {
			URI.createURI(getEPackage.nsURI)
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
	}

}
