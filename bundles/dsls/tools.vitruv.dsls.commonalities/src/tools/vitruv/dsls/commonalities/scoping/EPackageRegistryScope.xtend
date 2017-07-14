package tools.vitruv.dsls.commonalities.scoping

import org.eclipse.xtext.scoping.IScope
import org.eclipse.xtext.naming.QualifiedName
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtext.resource.IEObjectDescription
import org.eclipse.emf.ecore.EPackage
import java.util.HashMap
import com.google.inject.Singleton
import org.eclipse.emf.common.util.URI
import edu.kit.ipd.sdq.activextendannotations.Lazy

@Singleton
class EPackageRegistryScope implements IScope {

	final HashMap<QualifiedName, EPackageDescription> descriptionCache = new HashMap

	override getAllElements() {
		allDescriptions.filter[exists].map[it]
	}
	
	def allEPackages() {
		allDescriptions.filter[exists].map[describedEPackage]
	}

	def private getAvailableEPackageUris() {
		EPackage.Registry.INSTANCE.keySet
	}
	
	def private getAllDescriptions() {
		availableEPackageUris.map[getDescription]
	}
	
	def private getDescription(String uri) {
		getDescription(QualifiedName.create(uri));
	}

	def private getDescription(QualifiedName name) {
		descriptionCache.computeIfAbsent(name, [new EPackageDescription(it)]);
	}

	override getElements(QualifiedName name) {
		val el = getSingleElement(name)
		if (el === null) #[] else #[el]
	}

	override getElements(EObject object) {
		allDescriptions.filter[matchingUri(object)].map[it]
	}

	override getSingleElement(QualifiedName name) {
		if (!EPackage.Registry.INSTANCE.containsKey(name.toString)) return null
		val package = getDescription(name)
		if (package.exists) package else null
	}

	override getSingleElement(EObject object) {
		allDescriptions.findFirst[matchingUri(object)]
	}

	private static class EPackageDescription implements IEObjectDescription {

		val QualifiedName uriName
		@Lazy val EPackage ePackage = EPackage::Registry.INSTANCE.getEPackage(uriName.toString)

		new(QualifiedName name) {
			this.uriName = name
		}
		
		def exists() {
			return EPackage !== null
		}

		override getEClass() {
			EPackage.eClass
		}

		override getEObjectOrProxy() {
			EPackage
		}
		
		def getDescribedEPackage() {
			EPackage
		}
		

		override getEObjectURI() {
			URI.createURI(EPackage.nsURI)
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

		def private matchingUri(EObject object) {
			if (_ePackage_isInitialised && object == EPackage) return true
			if (object instanceof EPackage) return object.nsURI == uriName.toString
			return false			
		}
		
		override toString() {
			'''desciption: <«uriName»> («IF !_ePackage_isInitialised»not loaded«ELSE»«IF exists»present«ELSE»absent«ENDIF»«ENDIF»)'''
		}
	}
}
