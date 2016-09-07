/**
 */
package multicontainment_b.impl;

import multicontainment_b.*;

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
public class Multicontainment_bFactoryImpl extends EFactoryImpl implements Multicontainment_bFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static Multicontainment_bFactory init() {
		try {
			Multicontainment_bFactory theMulticontainment_bFactory = (Multicontainment_bFactory)EPackage.Registry.INSTANCE.getEFactory(Multicontainment_bPackage.eNS_URI);
			if (theMulticontainment_bFactory != null) {
				return theMulticontainment_bFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new Multicontainment_bFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Multicontainment_bFactoryImpl() {
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
			case Multicontainment_bPackage.ROOT_B: return createRootB();
			case Multicontainment_bPackage.CHILD_B1: return createChildB1();
			case Multicontainment_bPackage.CHILD_B2: return createChildB2();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RootB createRootB() {
		RootBImpl rootB = new RootBImpl();
		return rootB;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ChildB1 createChildB1() {
		ChildB1Impl childB1 = new ChildB1Impl();
		return childB1;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ChildB2 createChildB2() {
		ChildB2Impl childB2 = new ChildB2Impl();
		return childB2;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Multicontainment_bPackage getMulticontainment_bPackage() {
		return (Multicontainment_bPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static Multicontainment_bPackage getPackage() {
		return Multicontainment_bPackage.eINSTANCE;
	}

} //Multicontainment_bFactoryImpl
