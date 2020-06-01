package tools.vitruv.dsls.commonalities.language.elements

import java.util.HashMap
import java.util.Map
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EClassifier
import org.eclipse.emf.ecore.EDataType
import org.eclipse.emf.ecore.EPackage
import org.eclipse.emf.ecore.EcorePackage

class ClassifierProvider {

	public static val ClassifierProvider INSTANCE = new ClassifierProvider

	val Map<Domain, Map<EClass, Metaclass>> metaclasses = new HashMap
	val Map<EDataType, EDataTypeClassifier> dataTypes = new HashMap => [
		put(EcorePackage.eINSTANCE.EJavaObject, WellKnownClassifiers.JAVA_OBJECT)
	]

	private new() {
	}

	def dispatch Classifier toClassifier(EDataType eDataType, Domain containingDomain) {
		eDataType.toDataTypeAdapter
	}

	def dispatch Classifier toClassifier(EClass eClass, Domain containingDomain) {
		eClass.toMetaclass(containingDomain)
	}

	def toMetaclass(EClass eClass, Domain containingDomain) {
		metaclasses.computeIfAbsent(containingDomain, [new HashMap]).computeIfAbsent(eClass, [
			LanguageElementsFactory.eINSTANCE.createEClassMetaclass
				.withClassifierProvider(this).forEClass(eClass).fromDomain(containingDomain)
		])
	}

	def toDataTypeAdapter(EDataType eDataType) {
		dataTypes.computeIfAbsent(eDataType, [
			LanguageElementsFactory.eINSTANCE.createEDataTypeClassifier.forEDataType(eDataType)
		])
	}

	// Returns null if no corresponding EClassifier is found
	def Classifier findClassifier(Domain containingDomain, Class<?> instanceClass) {
		if (instanceClass === null) return null
		val eClassifier = containingDomain.findEClassifier(instanceClass)
		return eClassifier.toClassifier(containingDomain)
	}

	// Searches the Ecore package and the domain specific packages for a matching EClassifier:
	private def static EClassifier findEClassifier(Domain containingDomain, Class<?> instanceClass) {
		if (instanceClass === null) return null
		var Iterable<EPackage> domainPackages = #[]
		if (containingDomain instanceof VitruvDomainAdapter) {
			domainPackages = containingDomain.allPackages
		}
		val relevantPackages = #[EcorePackage.eINSTANCE] + domainPackages
		return relevantPackages.map[findEClassifier(instanceClass)].filterNull.head
	}

	private def static EClassifier findEClassifier(EPackage ePackage, Class<?> instanceClass) {
		if (instanceClass === null) return null
		return ePackage.EClassifiers.filter[it.instanceClass == instanceClass].head
	}
}
