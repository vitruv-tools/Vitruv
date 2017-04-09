/**
 */
package tools.vitruv.dsls.reactions.reactionsLanguage.inputTypes.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import tools.vitruv.dsls.reactions.reactionsLanguage.inputTypes.InputTypesFactory;
import tools.vitruv.dsls.reactions.reactionsLanguage.inputTypes.InputTypesPackage;

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
			case InputTypesPackage.INTEGER: return createInteger();
			case InputTypesPackage.SHORT: return createShort();
			case InputTypesPackage.BOOLEAN: return createBoolean();
			case InputTypesPackage.LONG: return createLong();
			case InputTypesPackage.CHARACTER: return createCharacter();
			case InputTypesPackage.BYTE: return createByte();
			case InputTypesPackage.FLOAT: return createFloat();
			case InputTypesPackage.DOUBLE: return createDouble();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public tools.vitruv.dsls.reactions.reactionsLanguage.inputTypes.String createString() {
		StringImpl string = new StringImpl();
		return string;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public tools.vitruv.dsls.reactions.reactionsLanguage.inputTypes.Integer createInteger() {
		IntegerImpl integer = new IntegerImpl();
		return integer;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public tools.vitruv.dsls.reactions.reactionsLanguage.inputTypes.Short createShort() {
		ShortImpl short_ = new ShortImpl();
		return short_;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public tools.vitruv.dsls.reactions.reactionsLanguage.inputTypes.Boolean createBoolean() {
		BooleanImpl boolean_ = new BooleanImpl();
		return boolean_;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public tools.vitruv.dsls.reactions.reactionsLanguage.inputTypes.Long createLong() {
		LongImpl long_ = new LongImpl();
		return long_;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public tools.vitruv.dsls.reactions.reactionsLanguage.inputTypes.Character createCharacter() {
		CharacterImpl character = new CharacterImpl();
		return character;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public tools.vitruv.dsls.reactions.reactionsLanguage.inputTypes.Byte createByte() {
		ByteImpl byte_ = new ByteImpl();
		return byte_;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public tools.vitruv.dsls.reactions.reactionsLanguage.inputTypes.Float createFloat() {
		FloatImpl float_ = new FloatImpl();
		return float_;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public tools.vitruv.dsls.reactions.reactionsLanguage.inputTypes.Double createDouble() {
		DoubleImpl double_ = new DoubleImpl();
		return double_;
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
