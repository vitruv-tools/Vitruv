/**
 */
package SimpleAtoBTest_SimplestB.impl;

import SimpleAtoBTest_SimplestB.*;

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
public class SimpleAtoBTest_SimplestBFactoryImpl extends EFactoryImpl implements SimpleAtoBTest_SimplestBFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static SimpleAtoBTest_SimplestBFactory init() {
		try {
			SimpleAtoBTest_SimplestBFactory theSimpleAtoBTest_SimplestBFactory = (SimpleAtoBTest_SimplestBFactory)EPackage.Registry.INSTANCE.getEFactory(SimpleAtoBTest_SimplestBPackage.eNS_URI);
			if (theSimpleAtoBTest_SimplestBFactory != null) {
				return theSimpleAtoBTest_SimplestBFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new SimpleAtoBTest_SimplestBFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SimpleAtoBTest_SimplestBFactoryImpl() {
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
			case SimpleAtoBTest_SimplestBPackage.B: return createB();
			case SimpleAtoBTest_SimplestBPackage.BCHILD: return createBChild();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public B createB() {
		BImpl b = new BImpl();
		return b;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BChild createBChild() {
		BChildImpl bChild = new BChildImpl();
		return bChild;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SimpleAtoBTest_SimplestBPackage getSimpleAtoBTest_SimplestBPackage() {
		return (SimpleAtoBTest_SimplestBPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static SimpleAtoBTest_SimplestBPackage getPackage() {
		return SimpleAtoBTest_SimplestBPackage.eINSTANCE;
	}

} //SimpleAtoBTest_SimplestBFactoryImpl
