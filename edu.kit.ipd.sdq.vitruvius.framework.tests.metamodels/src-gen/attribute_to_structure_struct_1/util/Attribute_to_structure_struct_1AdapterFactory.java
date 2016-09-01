/**
 */
package attribute_to_structure_struct_1.util;

import attribute_to_structure_struct_1.*;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see attribute_to_structure_struct_1.Attribute_to_structure_struct_1Package
 * @generated
 */
public class Attribute_to_structure_struct_1AdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static Attribute_to_structure_struct_1Package modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Attribute_to_structure_struct_1AdapterFactory() {
		if (modelPackage == null) {
			modelPackage = Attribute_to_structure_struct_1Package.eINSTANCE;
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
	protected Attribute_to_structure_struct_1Switch<Adapter> modelSwitch =
		new Attribute_to_structure_struct_1Switch<Adapter>() {
			@Override
			public Adapter caseIdentified(Identified object) {
				return createIdentifiedAdapter();
			}
			@Override
			public Adapter caseModelB(ModelB object) {
				return createModelBAdapter();
			}
			@Override
			public Adapter caseStructured(Structured object) {
				return createStructuredAdapter();
			}
			@Override
			public Adapter caseIntContainer(IntContainer object) {
				return createIntContainerAdapter();
			}
			@Override
			public Adapter caseStrContainer(StrContainer object) {
				return createStrContainerAdapter();
			}
			@Override
			public Adapter caseFloatContainer(FloatContainer object) {
				return createFloatContainerAdapter();
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
	 * Creates a new adapter for an object of class '{@link attribute_to_structure_struct_1.Identified <em>Identified</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see attribute_to_structure_struct_1.Identified
	 * @generated
	 */
	public Adapter createIdentifiedAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link attribute_to_structure_struct_1.ModelB <em>Model B</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see attribute_to_structure_struct_1.ModelB
	 * @generated
	 */
	public Adapter createModelBAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link attribute_to_structure_struct_1.Structured <em>Structured</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see attribute_to_structure_struct_1.Structured
	 * @generated
	 */
	public Adapter createStructuredAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link attribute_to_structure_struct_1.IntContainer <em>Int Container</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see attribute_to_structure_struct_1.IntContainer
	 * @generated
	 */
	public Adapter createIntContainerAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link attribute_to_structure_struct_1.StrContainer <em>Str Container</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see attribute_to_structure_struct_1.StrContainer
	 * @generated
	 */
	public Adapter createStrContainerAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link attribute_to_structure_struct_1.FloatContainer <em>Float Container</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see attribute_to_structure_struct_1.FloatContainer
	 * @generated
	 */
	public Adapter createFloatContainerAdapter() {
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

} //Attribute_to_structure_struct_1AdapterFactory
