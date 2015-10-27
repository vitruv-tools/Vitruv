/**
 */
package multicontainment_a.impl;

import multicontainment_a.*;

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
public class Multicontainment_aFactoryImpl extends EFactoryImpl implements Multicontainment_aFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static Multicontainment_aFactory init() {
		try {
			Multicontainment_aFactory theMulticontainment_aFactory = (Multicontainment_aFactory)EPackage.Registry.INSTANCE.getEFactory(Multicontainment_aPackage.eNS_URI);
			if (theMulticontainment_aFactory != null) {
				return theMulticontainment_aFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new Multicontainment_aFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Multicontainment_aFactoryImpl() {
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
			case Multicontainment_aPackage.ROOT_A: return createRootA();
			case Multicontainment_aPackage.CHILD_A1: return createChildA1();
			case Multicontainment_aPackage.CHILD_A2: return createChildA2();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RootA createRootA() {
		RootAImpl rootA = new RootAImpl();
		return rootA;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ChildA1 createChildA1() {
		ChildA1Impl childA1 = new ChildA1Impl();
		return childA1;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ChildA2 createChildA2() {
		ChildA2Impl childA2 = new ChildA2Impl();
		return childA2;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Multicontainment_aPackage getMulticontainment_aPackage() {
		return (Multicontainment_aPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static Multicontainment_aPackage getPackage() {
		return Multicontainment_aPackage.eINSTANCE;
	}

} //Multicontainment_aFactoryImpl
