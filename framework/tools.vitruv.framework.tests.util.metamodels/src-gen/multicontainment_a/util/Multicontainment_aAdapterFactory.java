/**
 */
package multicontainment_a.util;

import multicontainment_a.*;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see multicontainment_a.Multicontainment_aPackage
 * @generated
 */
public class Multicontainment_aAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static Multicontainment_aPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Multicontainment_aAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = Multicontainment_aPackage.eINSTANCE;
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
	protected Multicontainment_aSwitch<Adapter> modelSwitch =
		new Multicontainment_aSwitch<Adapter>() {
			@Override
			public Adapter caseRootA(RootA object) {
				return createRootAAdapter();
			}
			@Override
			public Adapter caseChildA1(ChildA1 object) {
				return createChildA1Adapter();
			}
			@Override
			public Adapter caseChildA2(ChildA2 object) {
				return createChildA2Adapter();
			}
			@Override
			public Adapter caseIdentified(Identified object) {
				return createIdentifiedAdapter();
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
	 * Creates a new adapter for an object of class '{@link multicontainment_a.RootA <em>Root A</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see multicontainment_a.RootA
	 * @generated
	 */
	public Adapter createRootAAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link multicontainment_a.ChildA1 <em>Child A1</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see multicontainment_a.ChildA1
	 * @generated
	 */
	public Adapter createChildA1Adapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link multicontainment_a.ChildA2 <em>Child A2</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see multicontainment_a.ChildA2
	 * @generated
	 */
	public Adapter createChildA2Adapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link multicontainment_a.Identified <em>Identified</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see multicontainment_a.Identified
	 * @generated
	 */
	public Adapter createIdentifiedAdapter() {
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

} //Multicontainment_aAdapterFactory
