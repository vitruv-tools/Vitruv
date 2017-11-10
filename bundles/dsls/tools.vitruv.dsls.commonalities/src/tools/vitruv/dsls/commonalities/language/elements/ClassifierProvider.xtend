package tools.vitruv.dsls.commonalities.language.elements

import java.util.HashMap
import java.util.Map
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EDataType
import org.eclipse.emf.ecore.EcorePackage

class ClassifierProvider {
	val Map<Domain, Map<EClass, Metaclass>> metaclasses = new HashMap
	val Map<EDataType, EDataTypeClassifier> dataTypes = new HashMap => [
		put(EcorePackage.eINSTANCE.EJavaObject, WellKnownClassifiers.JAVA_OBJECT)
	]
	
	def dispatch Classifier toClassifier(EDataType eDataType, Domain containingDomain) {
		eDataType.toDataTypeAdapter
	}
	
	def dispatch Classifier toClassifier(EClass eClass, Domain containingDomain) {
		eClass.toMetaclass(containingDomain)
	}
	
	def toMetaclass(EClass eClass, Domain containingDomain) {
		metaclasses.computeIfAbsent(containingDomain, [new HashMap])
			.computeIfAbsent(eClass, [
				LanguageElementsFactory.eINSTANCE.createEClassMetaclass
					.withClassifierProvider(this).forEClass(eClass).fromDomain(containingDomain)
			])
	}
	
	def toDataTypeAdapter(EDataType eDataType) {
		dataTypes.computeIfAbsent(eDataType, [
			LanguageElementsFactory.eINSTANCE.createEDataTypeClassifier.forEDataType(eDataType)
		])
	}
}