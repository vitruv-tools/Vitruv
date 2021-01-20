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
	static def getEPackage(String nsURI) {
		EPackage.Registry.INSTANCE.getEPackage(nsURI)
	}

	static def getEClass(String nsURI, String className) {
		getEPackage(nsURI).getEClass(className)
	}

	static def getEClass(EPackage ePackage, String className) {
		ePackage.getEClassifier(className) as EClass
	}

	static def getEFeature(String nsURI, String className, String featureName) {
		getEClass(nsURI, className).getEFeature(featureName)
	}

	static def getEFeature(EClass eClass, String featureName) {
		eClass.getEStructuralFeature(featureName)
	}

	static def getEReference(String nsURI, String className, String featureName) {
		getEFeature(nsURI, className, featureName) as EReference
	}

	static def getEReference(EClass eClass, String featureName) {
		getEFeature(eClass, featureName) as EReference
	}

	static def getEAttribute(String nsURI, String className, String featureName) {
		getEFeature(nsURI, className, featureName) as EAttribute
	}

	static def getEAttribute(EClass eClass, String featureName) {
		getEFeature(eClass, featureName) as EAttribute
	}
}
