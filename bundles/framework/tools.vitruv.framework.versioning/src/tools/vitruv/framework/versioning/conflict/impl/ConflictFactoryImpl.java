/**
 */
package tools.vitruv.framework.versioning.conflict.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import tools.vitruv.framework.versioning.conflict.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class ConflictFactoryImpl extends EFactoryImpl implements ConflictFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static ConflictFactory init() {
		try {
			ConflictFactory theConflictFactory = (ConflictFactory)EPackage.Registry.INSTANCE.getEFactory(ConflictPackage.eNS_URI);
			if (theConflictFactory != null) {
				return theConflictFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new ConflictFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ConflictFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case ConflictPackage.SIMPLE_CHANGE_CONFLICT: return createSimpleChangeConflict();
			case ConflictPackage.MULTI_CHANGE_CONFLICT: return createMultiChangeConflict();
			case ConflictPackage.CONFLICT_DETECTOR: return createConflictDetector();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object createFromString(EDataType eDataType, String initialValue) {
		switch (eDataType.getClassifierID()) {
			case ConflictPackage.CONFLICT_TYPE:
				return createConflictTypeFromString(eDataType, initialValue);
			case ConflictPackage.CONFLICT_SOLVABILITY:
				return createConflictSolvabilityFromString(eDataType, initialValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String convertToString(EDataType eDataType, Object instanceValue) {
		switch (eDataType.getClassifierID()) {
			case ConflictPackage.CONFLICT_TYPE:
				return convertConflictTypeToString(eDataType, instanceValue);
			case ConflictPackage.CONFLICT_SOLVABILITY:
				return convertConflictSolvabilityToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SimpleChangeConflict createSimpleChangeConflict() {
		SimpleChangeConflictImpl simpleChangeConflict = new SimpleChangeConflictImpl();
		return simpleChangeConflict;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MultiChangeConflict createMultiChangeConflict() {
		MultiChangeConflictImpl multiChangeConflict = new MultiChangeConflictImpl();
		return multiChangeConflict;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ConflictDetector createConflictDetector() {
		ConflictDetectorImpl conflictDetector = new ConflictDetectorImpl();
		return conflictDetector;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ConflictType createConflictTypeFromString(EDataType eDataType, String initialValue) {
		ConflictType result = ConflictType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertConflictTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ConflictSolvability createConflictSolvabilityFromString(EDataType eDataType, String initialValue) {
		ConflictSolvability result = ConflictSolvability.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertConflictSolvabilityToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ConflictPackage getConflictPackage() {
		return (ConflictPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static ConflictPackage getPackage() {
		return ConflictPackage.eINSTANCE;
	}

} //ConflictFactoryImpl
