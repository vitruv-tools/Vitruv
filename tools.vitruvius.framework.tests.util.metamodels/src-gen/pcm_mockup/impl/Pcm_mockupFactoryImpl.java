/**
 */
package pcm_mockup.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import pcm_mockup.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class Pcm_mockupFactoryImpl extends EFactoryImpl implements Pcm_mockupFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static Pcm_mockupFactory init() {
		try {
			Pcm_mockupFactory thePcm_mockupFactory = (Pcm_mockupFactory)EPackage.Registry.INSTANCE.getEFactory(Pcm_mockupPackage.eNS_URI);
			if (thePcm_mockupFactory != null) {
				return thePcm_mockupFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new Pcm_mockupFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Pcm_mockupFactoryImpl() {
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
			case Pcm_mockupPackage.REPOSITORY: return createRepository();
			case Pcm_mockupPackage.PINTERFACE: return createPInterface();
			case Pcm_mockupPackage.COMPONENT: return createComponent();
			case Pcm_mockupPackage.PMETHOD: return createPMethod();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Repository createRepository() {
		RepositoryImpl repository = new RepositoryImpl();
		return repository;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PInterface createPInterface() {
		PInterfaceImpl pInterface = new PInterfaceImpl();
		return pInterface;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Component createComponent() {
		ComponentImpl component = new ComponentImpl();
		return component;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PMethod createPMethod() {
		PMethodImpl pMethod = new PMethodImpl();
		return pMethod;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Pcm_mockupPackage getPcm_mockupPackage() {
		return (Pcm_mockupPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static Pcm_mockupPackage getPackage() {
		return Pcm_mockupPackage.eINSTANCE;
	}

} //Pcm_mockupFactoryImpl
