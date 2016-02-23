/**
 */
package allElementTypes2.util;

import allElementTypes2.*;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see allElementTypes2.AllElementTypes2Package
 * @generated
 */
public class AllElementTypes2AdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static AllElementTypes2Package modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AllElementTypes2AdapterFactory() {
		if (modelPackage == null) {
			modelPackage = AllElementTypes2Package.eINSTANCE;
		}
	}

	/**
	 * Returns whether this factory is applicable for the type of the object.
	 * <!-- begin-user-doc -->
	 * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
	 * <!-- end-user-doc -->
	 * @return whether this factory is applicable for the type of the object.
	 * @generated
	 */
	@Override
	public boolean isFactoryForType(Object object) {
		if (object == modelPackage) {
			return true;
		}
		if (object instanceof EObject) {
			return ((EObject)object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

	/**
	 * The switch that delegates to the <code>createXXX</code> methods.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AllElementTypes2Switch<Adapter> modelSwitch =
		new AllElementTypes2Switch<Adapter>() {
			@Override
			public Adapter caseRoot2(Root2 object) {
				return createRoot2Adapter();
			}
			@Override
			public Adapter caseNonRoot2(NonRoot2 object) {
				return createNonRoot2Adapter();
			}
			@Override
			public Adapter caseNonRootObjectContainerHelper2(NonRootObjectContainerHelper2 object) {
				return createNonRootObjectContainerHelper2Adapter();
			}
			@Override
			public Adapter caseIdentified2(Identified2 object) {
				return createIdentified2Adapter();
			}
			@Override
			public Adapter defaultCase(EObject object) {
				return createEObjectAdapter();
			}
		};

	/**
	 * Creates an adapter for the <code>target</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param target the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
	@Override
	public Adapter createAdapter(Notifier target) {
		return modelSwitch.doSwitch((EObject)target);
	}


	/**
	 * Creates a new adapter for an object of class '{@link allElementTypes2.Root2 <em>Root2</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see allElementTypes2.Root2
	 * @generated
	 */
	public Adapter createRoot2Adapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link allElementTypes2.NonRoot2 <em>Non Root2</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see allElementTypes2.NonRoot2
	 * @generated
	 */
	public Adapter createNonRoot2Adapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link allElementTypes2.NonRootObjectContainerHelper2 <em>Non Root Object Container Helper2</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see allElementTypes2.NonRootObjectContainerHelper2
	 * @generated
	 */
	public Adapter createNonRootObjectContainerHelper2Adapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link allElementTypes2.Identified2 <em>Identified2</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see allElementTypes2.Identified2
	 * @generated
	 */
	public Adapter createIdentified2Adapter() {
		return null;
	}

	/**
	 * Creates a new adapter for the default case.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @generated
	 */
	public Adapter createEObjectAdapter() {
		return null;
	}

} //AllElementTypes2AdapterFactory
