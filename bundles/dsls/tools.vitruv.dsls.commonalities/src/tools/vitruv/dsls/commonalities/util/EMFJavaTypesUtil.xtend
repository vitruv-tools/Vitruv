package tools.vitruv.dsls.commonalities.util

import edu.kit.ipd.sdq.activextendannotations.Utility
import org.eclipse.emf.ecore.EClassifier
import org.eclipse.emf.ecore.EcorePackage

@Utility
class EMFJavaTypesUtil {

	/**
	 * If the given classifier corresponds to a Java primitive type we return
	 * the classifier of the corresponding wrapper type instead. Otherwise we
	 * return the given classifier without changes.
	 */
	static def EClassifier wrapJavaPrimitiveTypes(EClassifier eClassifier) {
		if (eClassifier === null) return null
		// We check the instance class rather than the EClassifier itself,
		// since it is theoretically possible to create new EDataTypes which
		// also map to Java primitives.
		val instanceClass = eClassifier.instanceClass
		if (instanceClass === null || !instanceClass.isPrimitive) return eClassifier

		switch (instanceClass) {
			case Boolean.TYPE: EcorePackage.eINSTANCE.EBooleanObject
			case Character.TYPE: EcorePackage.eINSTANCE.ECharacterObject
			case Byte.TYPE: EcorePackage.eINSTANCE.EByteObject
			case Short.TYPE: EcorePackage.eINSTANCE.EShortObject
			case Integer.TYPE: EcorePackage.eINSTANCE.EIntegerObject
			case Long.TYPE: EcorePackage.eINSTANCE.ELongObject
			case Float.TYPE: EcorePackage.eINSTANCE.EFloatObject
			case Double.TYPE: EcorePackage.eINSTANCE.EDoubleObject
			default: {
				return eClassifier
			}
		}
	}
}
