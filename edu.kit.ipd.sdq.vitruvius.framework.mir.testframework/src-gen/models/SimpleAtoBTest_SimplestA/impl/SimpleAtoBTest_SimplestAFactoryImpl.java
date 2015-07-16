/**
 */
package SimpleAtoBTest_SimplestA.impl;

import SimpleAtoBTest_SimplestA.*;

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
public class SimpleAtoBTest_SimplestAFactoryImpl extends EFactoryImpl implements SimpleAtoBTest_SimplestAFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static SimpleAtoBTest_SimplestAFactory init() {
		try {
			SimpleAtoBTest_SimplestAFactory theSimpleAtoBTest_SimplestAFactory = (SimpleAtoBTest_SimplestAFactory)EPackage.Registry.INSTANCE.getEFactory(SimpleAtoBTest_SimplestAPackage.eNS_URI);
			if (theSimpleAtoBTest_SimplestAFactory != null) {
				return theSimpleAtoBTest_SimplestAFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new SimpleAtoBTest_SimplestAFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SimpleAtoBTest_SimplestAFactoryImpl() {
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
			case SimpleAtoBTest_SimplestAPackage.A: return createA();
			case SimpleAtoBTest_SimplestAPackage.ACHILD: return createAChild();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public A createA() {
		AImpl a = new AImpl();
		return a;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AChild createAChild() {
		AChildImpl aChild = new AChildImpl();
		return aChild;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SimpleAtoBTest_SimplestAPackage getSimpleAtoBTest_SimplestAPackage() {
		return (SimpleAtoBTest_SimplestAPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static SimpleAtoBTest_SimplestAPackage getPackage() {
		return SimpleAtoBTest_SimplestAPackage.eINSTANCE;
	}

} //SimpleAtoBTest_SimplestAFactoryImpl
