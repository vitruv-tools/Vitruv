/**
 */
package attribute_to_structure_attr.impl;

import attribute_to_structure_attr.*;

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
public class Attribute_to_structure_attrFactoryImpl extends EFactoryImpl implements Attribute_to_structure_attrFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static Attribute_to_structure_attrFactory init() {
		try {
			Attribute_to_structure_attrFactory theAttribute_to_structure_attrFactory = (Attribute_to_structure_attrFactory)EPackage.Registry.INSTANCE.getEFactory(Attribute_to_structure_attrPackage.eNS_URI);
			if (theAttribute_to_structure_attrFactory != null) {
				return theAttribute_to_structure_attrFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new Attribute_to_structure_attrFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Attribute_to_structure_attrFactoryImpl() {
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
			case Attribute_to_structure_attrPackage.MODEL_A: return createModelA();
			case Attribute_to_structure_attrPackage.ATTRIBUTED: return createAttributed();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ModelA createModelA() {
		ModelAImpl modelA = new ModelAImpl();
		return modelA;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Attributed createAttributed() {
		AttributedImpl attributed = new AttributedImpl();
		return attributed;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Attribute_to_structure_attrPackage getAttribute_to_structure_attrPackage() {
		return (Attribute_to_structure_attrPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static Attribute_to_structure_attrPackage getPackage() {
		return Attribute_to_structure_attrPackage.eINSTANCE;
	}

} //Attribute_to_structure_attrFactoryImpl
