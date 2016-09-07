/**
 */
package tools.vitruvius.dsls.response.responseLanguage.inputTypes.impl;

import tools.vitruvius.dsls.response.responseLanguage.inputTypes.InputTypesFactory;
import tools.vitruvius.dsls.response.responseLanguage.inputTypes.InputTypesPackage;
import tools.vitruvius.dsls.response.responseLanguage.inputTypes.Int;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class InputTypesFactoryImpl extends EFactoryImpl implements InputTypesFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static InputTypesFactory init() {
		try {
			InputTypesFactory theInputTypesFactory = (InputTypesFactory)EPackage.Registry.INSTANCE.getEFactory(InputTypesPackage.eNS_URI);
			if (theInputTypesFactory != null) {
				return theInputTypesFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new InputTypesFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InputTypesFactoryImpl() {
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
			case InputTypesPackage.STRING: return createString();
			case InputTypesPackage.INT: return createInt();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public tools.vitruvius.dsls.response.responseLanguage.inputTypes.String createString() {
		StringImpl string = new StringImpl();
		return string;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Int createInt() {
		IntImpl int_ = new IntImpl();
		return int_;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InputTypesPackage getInputTypesPackage() {
		return (InputTypesPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static InputTypesPackage getPackage() {
		return InputTypesPackage.eINSTANCE;
	}

} //InputTypesFactoryImpl
