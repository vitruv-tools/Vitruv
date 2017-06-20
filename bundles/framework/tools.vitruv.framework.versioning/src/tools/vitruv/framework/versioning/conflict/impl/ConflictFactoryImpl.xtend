/** 
 */
package tools.vitruv.framework.versioning.conflict.impl

import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EDataType
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EPackage
import org.eclipse.emf.ecore.impl.EFactoryImpl
import org.eclipse.emf.ecore.plugin.EcorePlugin
import tools.vitruv.framework.versioning.conflict.ConflictDetector
import tools.vitruv.framework.versioning.conflict.ConflictFactory
import tools.vitruv.framework.versioning.conflict.ConflictPackage
import tools.vitruv.framework.versioning.conflict.ConflictSolvability
import tools.vitruv.framework.versioning.conflict.ConflictType
import tools.vitruv.framework.versioning.conflict.MultiChangeConflict
import tools.vitruv.framework.versioning.conflict.SimpleChangeConflict

/** 
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
class ConflictFactoryImpl extends EFactoryImpl implements ConflictFactory {
	/** 
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	def static ConflictFactory init() {
		try {
			var ConflictFactory theConflictFactory = (EPackage.Registry::INSTANCE.getEFactory(
				ConflictPackage::eNS_URI) as ConflictFactory)
			if (theConflictFactory !== null) {
				return theConflictFactory
			}
		} catch (Exception exception) {
			EcorePlugin::INSTANCE.log(exception)
		}

		return new ConflictFactoryImpl()
	}

	/** 
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	new() {
		super()
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override EObject create(EClass eClass) {

		switch (eClass.getClassifierID()) {
			case ConflictPackage::SIMPLE_CHANGE_CONFLICT: {
				return createSimpleChangeConflict()
			}
			case ConflictPackage::MULTI_CHANGE_CONFLICT: {
				return createMultiChangeConflict()
			}
			case ConflictPackage::CONFLICT_DETECTOR: {
				return createConflictDetector()
			}
			default: {
				throw new IllegalArgumentException(
					'''The class '«»«eClass.getName()»' is not a valid classifier'''.toString);
			}
		}
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override Object createFromString(EDataType eDataType, String initialValue) {

		switch (eDataType.getClassifierID()) {
			case ConflictPackage::CONFLICT_TYPE: {
				return createConflictTypeFromString(eDataType, initialValue)
			}
			case ConflictPackage::CONFLICT_SOLVABILITY: {
				return createConflictSolvabilityFromString(eDataType, initialValue)
			}
			default: {
				throw new IllegalArgumentException(
					'''The datatype '«»«eDataType.getName()»' is not a valid classifier'''.toString);
			}
		}
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override String convertToString(EDataType eDataType, Object instanceValue) {

		switch (eDataType.getClassifierID()) {
			case ConflictPackage::CONFLICT_TYPE: {
				return convertConflictTypeToString(eDataType, instanceValue)
			}
			case ConflictPackage::CONFLICT_SOLVABILITY: {
				return convertConflictSolvabilityToString(eDataType, instanceValue)
			}
			default: {
				throw new IllegalArgumentException(
					'''The datatype '«»«eDataType.getName()»' is not a valid classifier'''.toString);
			}
		}
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override SimpleChangeConflict createSimpleChangeConflict() {
		var SimpleChangeConflictImpl simpleChangeConflict = new SimpleChangeConflictImpl()
		return simpleChangeConflict
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override MultiChangeConflict createMultiChangeConflict() {
		var MultiChangeConflictImpl multiChangeConflict = new MultiChangeConflictImpl()
		return multiChangeConflict
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override ConflictDetector createConflictDetector() {
		var ConflictDetectorImpl conflictDetector = new ConflictDetectorImpl()
		return conflictDetector
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	def ConflictType createConflictTypeFromString(EDataType eDataType, String initialValue) {
		var ConflictType result = ConflictType::get(initialValue)
		if (result === null)
			throw new IllegalArgumentException(
				'''The value '«»«initialValue»' is not a valid enumerator of '«»«eDataType.getName()»'«»'''.toString);
		return result
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	def String convertConflictTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue.toString()
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	def ConflictSolvability createConflictSolvabilityFromString(EDataType eDataType, String initialValue) {
		var ConflictSolvability result = ConflictSolvability::get(initialValue)
		if (result === null)
			throw new IllegalArgumentException(
				'''The value '«»«initialValue»' is not a valid enumerator of '«»«eDataType.getName()»'«»'''.toString);
		return result
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	def String convertConflictSolvabilityToString(EDataType eDataType, Object instanceValue) {
		instanceValue.toString()
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override ConflictPackage getConflictPackage() {
		return (getEPackage() as ConflictPackage)
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	def static ConflictPackage getPackage() {
		return ConflictPackage::eINSTANCE
	} // ConflictFactoryImpl
}
