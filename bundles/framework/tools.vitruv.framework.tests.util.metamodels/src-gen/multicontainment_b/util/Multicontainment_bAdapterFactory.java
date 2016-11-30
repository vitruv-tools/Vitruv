/**
 */
package multicontainment_b.util;

import multicontainment_b.*;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see multicontainment_b.Multicontainment_bPackage
 * @generated
 */
public class Multicontainment_bAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static Multicontainment_bPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Multicontainment_bAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = Multicontainment_bPackage.eINSTANCE;
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
	protected Multicontainment_bSwitch<Adapter> modelSwitch =
		new Multicontainment_bSwitch<Adapter>() {
			@Override
			public Adapter caseRootB(RootB object) {
				return createRootBAdapter();
			}
			@Override
			public Adapter caseChildB1(ChildB1 object) {
				return createChildB1Adapter();
			}
			@Override
			public Adapter caseChildB2(ChildB2 object) {
				return createChildB2Adapter();
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
	 * Creates a new adapter for an object of class '{@link multicontainment_b.RootB <em>Root B</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see multicontainment_b.RootB
	 * @generated
	 */
	public Adapter createRootBAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link multicontainment_b.ChildB1 <em>Child B1</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see multicontainment_b.ChildB1
	 * @generated
	 */
	public Adapter createChildB1Adapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link multicontainment_b.ChildB2 <em>Child B2</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see multicontainment_b.ChildB2
	 * @generated
	 */
	public Adapter createChildB2Adapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link multicontainment_b.Identified <em>Identified</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see multicontainment_b.Identified
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

} //Multicontainment_bAdapterFactory
