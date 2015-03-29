package edu.kit.ipd.sdq.vitruvius.framework.mir.helpers

import java.util.ArrayList
import java.util.List
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EPackage
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.emf.ecore.EClassifier

class EMFHelper {
	/**
	 * returns a list of all ancestors of obj, starting with
	 * the object itself or its container
	 */
	static def getContainerHierarchy(EObject obj, boolean includeObject) {
		val List<EObject> result = new ArrayList<EObject>();
		var EObject iterator = obj;
		
		if (!includeObject) {
			iterator = iterator.eContainer
		}
		
		while (iterator != null) {
			result.add(iterator)
			iterator = iterator.eContainer
		}
		return result
	}
	
	static def getJavaExpressionThatReturns(EClassifier classifier) {
		val classifierID = classifier.classifierID
		val ePackage = classifier.EPackage
		
		val packageExpression = getJavaExpressionThatReturns(ePackage)
		
		return '''((org.eclipse.emf.ecore.EClass) «packageExpression».getEClassifiers().get(«classifierID»))'''
	}
	
	/**
	 * Returns a Java expression that when evaluated returns the given
	 * EStructuralFeature.
	 */
	static def getJavaExpressionThatReturns(EStructuralFeature feature) {
		val featureID = feature.featureID
		val containerClass = feature.EContainingClass
		
		return '''«getJavaExpressionThatReturns(containerClass)».getEStructuralFeature(«featureID»)'''
	}
	
	/**
	 * Returns a Java expression that evaluates to the given EPackage
	 */
	static def getJavaExpressionThatReturns(EPackage ePackage) {
		'''org.eclipse.emf.ecore.EPackage.Registry.INSTANCE.getEPackage("«ePackage.nsURI»")'''
	}
}