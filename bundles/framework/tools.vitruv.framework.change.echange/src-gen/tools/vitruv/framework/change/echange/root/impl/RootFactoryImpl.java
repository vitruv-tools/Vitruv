/**
 */
package tools.vitruv.framework.change.echange.root.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import tools.vitruv.framework.change.echange.root.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class RootFactoryImpl extends EFactoryImpl implements RootFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static RootFactory init() {
		try {
			RootFactory theRootFactory = (RootFactory)EPackage.Registry.INSTANCE.getEFactory(RootPackage.eNS_URI);
			if (theRootFactory != null) {
				return theRootFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new RootFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RootFactoryImpl() {
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
			case RootPackage.INSERT_ROOT_EOBJECT: return createInsertRootEObject();
			case RootPackage.REMOVE_ROOT_EOBJECT: return createRemoveRootEObject();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public <T extends EObject> InsertRootEObject<T> createInsertRootEObject() {
		InsertRootEObjectImpl<T> insertRootEObject = new InsertRootEObjectImpl<T>();
		return insertRootEObject;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public <T extends EObject> RemoveRootEObject<T> createRemoveRootEObject() {
		RemoveRootEObjectImpl<T> removeRootEObject = new RemoveRootEObjectImpl<T>();
		return removeRootEObject;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RootPackage getRootPackage() {
		return (RootPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static RootPackage getPackage() {
		return RootPackage.eINSTANCE;
	}

} //RootFactoryImpl
