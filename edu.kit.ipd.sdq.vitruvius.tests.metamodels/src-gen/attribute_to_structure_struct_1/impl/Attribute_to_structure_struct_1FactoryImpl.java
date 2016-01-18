/**
 */
package attribute_to_structure_struct_1.impl;

import attribute_to_structure_struct_1.*;

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
public class Attribute_to_structure_struct_1FactoryImpl extends EFactoryImpl implements Attribute_to_structure_struct_1Factory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static Attribute_to_structure_struct_1Factory init() {
		try {
			Attribute_to_structure_struct_1Factory theAttribute_to_structure_struct_1Factory = (Attribute_to_structure_struct_1Factory)EPackage.Registry.INSTANCE.getEFactory(Attribute_to_structure_struct_1Package.eNS_URI);
			if (theAttribute_to_structure_struct_1Factory != null) {
				return theAttribute_to_structure_struct_1Factory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new Attribute_to_structure_struct_1FactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Attribute_to_structure_struct_1FactoryImpl() {
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
			case Attribute_to_structure_struct_1Package.MODEL_B: return createModelB();
			case Attribute_to_structure_struct_1Package.STRUCTURED: return createStructured();
			case Attribute_to_structure_struct_1Package.INT_CONTAINER: return createIntContainer();
			case Attribute_to_structure_struct_1Package.STR_CONTAINER: return createStrContainer();
			case Attribute_to_structure_struct_1Package.FLOAT_CONTAINER: return createFloatContainer();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ModelB createModelB() {
		ModelBImpl modelB = new ModelBImpl();
		return modelB;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Structured createStructured() {
		StructuredImpl structured = new StructuredImpl();
		return structured;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IntContainer createIntContainer() {
		IntContainerImpl intContainer = new IntContainerImpl();
		return intContainer;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StrContainer createStrContainer() {
		StrContainerImpl strContainer = new StrContainerImpl();
		return strContainer;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FloatContainer createFloatContainer() {
		FloatContainerImpl floatContainer = new FloatContainerImpl();
		return floatContainer;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Attribute_to_structure_struct_1Package getAttribute_to_structure_struct_1Package() {
		return (Attribute_to_structure_struct_1Package)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static Attribute_to_structure_struct_1Package getPackage() {
		return Attribute_to_structure_struct_1Package.eINSTANCE;
	}

} //Attribute_to_structure_struct_1FactoryImpl
