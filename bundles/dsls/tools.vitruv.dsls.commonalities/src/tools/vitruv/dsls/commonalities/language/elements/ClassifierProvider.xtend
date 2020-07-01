package tools.vitruv.dsls.commonalities.language.elements

import java.util.HashMap
import java.util.Map
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EClassifier
import org.eclipse.emf.ecore.EDataType
import org.eclipse.emf.ecore.EPackage
import org.eclipse.emf.ecore.EcorePackage
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl

class ClassifierProvider {

	val static CONTAINER_RESOURCE_URI = URI.createURI('synthetic:/commonalities/ecoreAdapters')
	public static val ClassifierProvider INSTANCE = new ClassifierProvider

	/*
	 * In order to be referenced from a Xtext language, EObjects must be
	 * contained in a resource. So we create a fake resource to put our EClass
	 * and EDataType adapters in. This resource is never serialized and has no
	 * other purpose.
	 */
	val container = createContainerResource
	val Map<Domain, Map<EClass, Metaclass>> metaclasses = new HashMap
	val Map<EDataType, EDataTypeClassifier> dataTypes = new HashMap

	private new() {
	}

	private def createContainerResource() {
		val resourceSet = new ResourceSetImpl
		return resourceSet.createResource(CONTAINER_RESOURCE_URI)
	}

	def dispatch Classifier toClassifier(EDataType eDataType, Domain containingDomain) {
		eDataType.toDataTypeAdapter
	}

	def dispatch Classifier toClassifier(EClass eClass, Domain containingDomain) {
		eClass.toMetaclass(containingDomain)
	}

	def toMetaclass(EClass eClass, Domain containingDomain) {
		metaclasses.computeIfAbsent(containingDomain, [new HashMap]).computeIfAbsent(eClass, [
			val adapter = LanguageElementsFactory.eINSTANCE.createEClassMetaclass
				.withClassifierProvider(this).forEClass(eClass).fromDomain(containingDomain)
			container.contents += adapter
			return adapter
		])
	}

	def toDataTypeAdapter(EDataType eDataType) {
		dataTypes.computeIfAbsent(eDataType, [
			val adapter = LanguageElementsFactory.eINSTANCE.createEDataTypeClassifier.forEDataType(eDataType)
			container.contents += adapter
			return adapter
		])
	}

	// Returns null if no corresponding EClassifier is found
	def Classifier findClassifier(Domain containingDomain, String qualifiedInstanceClassName) {
		if (qualifiedInstanceClassName.nullOrEmpty) return null
		val eClassifier = containingDomain.findEClassifier(qualifiedInstanceClassName)
		return eClassifier.toClassifier(containingDomain)
	}

	// Searches the Ecore package and the domain specific packages for a matching EClassifier:
	private static def EClassifier findEClassifier(Domain containingDomain, String qualifiedInstanceClassName) {
		if (qualifiedInstanceClassName.nullOrEmpty) return null
		var Iterable<EPackage> domainPackages = #[]
		if (containingDomain instanceof VitruvDomainAdapter) {
			domainPackages = containingDomain.allPackages
		}
		val relevantPackages = #[EcorePackage.eINSTANCE] + domainPackages
		return relevantPackages.map[findEClassifier(qualifiedInstanceClassName)].filterNull.head
	}

	private static def EClassifier findEClassifier(EPackage ePackage, String qualifiedInstanceClassName) {
		if (qualifiedInstanceClassName.nullOrEmpty) return null
		return ePackage.EClassifiers.filter[it.instanceClassName == qualifiedInstanceClassName].head
	}
}
