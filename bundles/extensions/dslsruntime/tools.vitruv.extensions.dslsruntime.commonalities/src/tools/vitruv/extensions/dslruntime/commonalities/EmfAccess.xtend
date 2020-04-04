package tools.vitruv.extensions.dslruntime.commonalities

import edu.kit.ipd.sdq.activextendannotations.Utility
import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EPackage
import org.eclipse.emf.ecore.EReference

/**
 * Helper methods for reflectively accessing EMF meta objects at runtime.
 */
@Utility
class EmfAccess {

	def static getEPackage(String nsURI) {
		return EPackage.Registry.INSTANCE.getEPackage(nsURI)
	}

	def static getEClass(String nsURI, String className) {
		val ePackage = getEPackage(nsURI)
		return ePackage.getEClass(className)
	}

	def static getEClass(EPackage ePackage, String className) {
		return ePackage.getEClassifier(className) as EClass
	}

	def static getEFeature(String nsURI, String className, String featureName) {
		val eClass = getEClass(nsURI, className)
		return eClass.getEFeature(featureName)
	}

	def static getEFeature(EClass eClass, String featureName) {
		return eClass.getEStructuralFeature(featureName)
	}

	def static getEReference(String nsURI, String className, String featureName) {
		return getEFeature(nsURI, className, featureName) as EReference
	}

	def static getEReference(EClass eClass, String featureName) {
		return getEFeature(eClass, featureName) as EReference
	}

	def static getEAttribute(String nsURI, String className, String featureName) {
		return getEFeature(nsURI, className, featureName) as EAttribute
	}

	def static getEAttribute(EClass eClass, String featureName) {
		return getEFeature(eClass, featureName) as EAttribute
	}
}
