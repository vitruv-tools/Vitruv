/**
 */
package allElementTypes2.impl;

import allElementTypes2.*;

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
public class AllElementTypes2FactoryImpl extends EFactoryImpl implements AllElementTypes2Factory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static AllElementTypes2Factory init() {
		try {
			AllElementTypes2Factory theAllElementTypes2Factory = (AllElementTypes2Factory)EPackage.Registry.INSTANCE.getEFactory(AllElementTypes2Package.eNS_URI);
			if (theAllElementTypes2Factory != null) {
				return theAllElementTypes2Factory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new AllElementTypes2FactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AllElementTypes2FactoryImpl() {
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
			case AllElementTypes2Package.ROOT2: return createRoot2();
			case AllElementTypes2Package.NON_ROOT2: return createNonRoot2();
			case AllElementTypes2Package.NON_ROOT_OBJECT_CONTAINER_HELPER2: return createNonRootObjectContainerHelper2();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Root2 createRoot2() {
		Root2Impl root2 = new Root2Impl();
		return root2;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NonRoot2 createNonRoot2() {
		NonRoot2Impl nonRoot2 = new NonRoot2Impl();
		return nonRoot2;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NonRootObjectContainerHelper2 createNonRootObjectContainerHelper2() {
		NonRootObjectContainerHelper2Impl nonRootObjectContainerHelper2 = new NonRootObjectContainerHelper2Impl();
		return nonRootObjectContainerHelper2;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AllElementTypes2Package getAllElementTypes2Package() {
		return (AllElementTypes2Package)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static AllElementTypes2Package getPackage() {
		return AllElementTypes2Package.eINSTANCE;
	}

} //AllElementTypes2FactoryImpl
