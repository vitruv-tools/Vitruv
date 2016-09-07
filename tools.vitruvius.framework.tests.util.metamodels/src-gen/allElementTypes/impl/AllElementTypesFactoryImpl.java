/**
 */
package allElementTypes.impl;

import allElementTypes.*;

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
public class AllElementTypesFactoryImpl extends EFactoryImpl implements AllElementTypesFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static AllElementTypesFactory init() {
		try {
			AllElementTypesFactory theAllElementTypesFactory = (AllElementTypesFactory)EPackage.Registry.INSTANCE.getEFactory(AllElementTypesPackage.eNS_URI);
			if (theAllElementTypesFactory != null) {
				return theAllElementTypesFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new AllElementTypesFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AllElementTypesFactoryImpl() {
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
			case AllElementTypesPackage.ROOT: return createRoot();
			case AllElementTypesPackage.NON_ROOT: return createNonRoot();
			case AllElementTypesPackage.NON_ROOT_OBJECT_CONTAINER_HELPER: return createNonRootObjectContainerHelper();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Root createRoot() {
		RootImpl root = new RootImpl();
		return root;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NonRoot createNonRoot() {
		NonRootImpl nonRoot = new NonRootImpl();
		return nonRoot;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NonRootObjectContainerHelper createNonRootObjectContainerHelper() {
		NonRootObjectContainerHelperImpl nonRootObjectContainerHelper = new NonRootObjectContainerHelperImpl();
		return nonRootObjectContainerHelper;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AllElementTypesPackage getAllElementTypesPackage() {
		return (AllElementTypesPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static AllElementTypesPackage getPackage() {
		return AllElementTypesPackage.eINSTANCE;
	}

} //AllElementTypesFactoryImpl
