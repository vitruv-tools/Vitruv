package tools.vitruv.dsls.mapping.helpers

import java.util.ArrayList
import java.util.List
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EPackage
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.emf.ecore.EClassifier
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EDataType

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
		
		while (iterator !== null) {
			result.add(iterator)
			iterator = iterator.eContainer
		}
		return result
	}
	
	static def <T> T getContainerOfType(EObject obj, Class<T> containerType) {
		var EObject iterator = obj;
		
		while (iterator !== null) {
			if (containerType.isInstance(iterator))
				return containerType.cast(iterator)
			iterator = iterator.eContainer
		}
		
		return null
		
	}
	
	static def hasContainer(EObject obj, EObject container) {
		// TODO: possibly optimize, so iteration is stopped
		// when container is found
		
		return obj.getContainerHierarchy(true).contains(container)
	}
	
	static def getJavaExpressionThatReturns(EClassifier classifier, boolean fqn) {
		val classifierID = classifier.classifierID
		val ePackage = classifier.EPackage
		
		val eClassName = if (fqn) EClass.name else EClass.simpleName
		
		val packageExpression = getJavaExpressionThatReturns(ePackage, fqn)
		
		return '''((«eClassName») «packageExpression».getEClassifiers().get(«classifierID»))'''
	}
	
	static def getJavaExpressionThatCreates(EClass eClass) {
		val eClassCode = "(" + eClass.getJavaExpressionThatReturns(true) + ")"
		val ePackageCode = "(" + eClass.EPackage.getJavaExpressionThatReturns(true) + ")"
		
		return '''((«eClass.instanceTypeName») «ePackageCode».getEFactoryInstance().create(«eClassCode»))'''
	}
	
	/**
	 * Returns a Java expression that when evaluated returns the given
	 * EStructuralFeature.
	 */
	static def getJavaExpressionThatReturns(EStructuralFeature feature, boolean fqn) {
		val featureID = feature.featureID
		val containerClass = feature.EContainingClass
		
		return '''«getJavaExpressionThatReturns(containerClass, fqn)».getEStructuralFeature(«featureID»)'''
	}
	
	/**
	 * Returns a Java expression that evaluates to the given EPackage
	 */
	static def getJavaExpressionThatReturns(EPackage ePackage, boolean fqn) {
		val ePackageName = if (fqn) EPackage.name else EPackage.simpleName
		'''«ePackageName».Registry.INSTANCE.getEPackage("«ePackage.nsURI»")'''
	}
	
	/**
	 * Returns true if the given type is a primitive type or an enumerable
	 */
	 static def isEDataType(EClassifier eClassifier) {
	 	return (eClassifier instanceof EDataType)
	 }
}