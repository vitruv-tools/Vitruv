/**
 */
package tools.vitruv.framework.change.echange.compound.util;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

import tools.vitruv.framework.change.echange.AdditiveEChange;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.SubtractiveEChange;

import tools.vitruv.framework.change.echange.compound.*;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see tools.vitruv.framework.change.echange.compound.CompoundPackage
 * @generated
 */
public class CompoundAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static CompoundPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CompoundAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = CompoundPackage.eINSTANCE;
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
	protected CompoundSwitch<Adapter> modelSwitch =
		new CompoundSwitch<Adapter>() {
			@Override
			public Adapter caseCompoundEChange(CompoundEChange object) {
				return createCompoundEChangeAdapter();
			}
			@Override
			public <A extends EObject, F extends EStructuralFeature> Adapter caseExplicitUnsetEFeature(ExplicitUnsetEFeature<A, F> object) {
				return createExplicitUnsetEFeatureAdapter();
			}
			@Override
			public <A extends EObject, T extends Object> Adapter caseExplicitUnsetEAttribute(ExplicitUnsetEAttribute<A, T> object) {
				return createExplicitUnsetEAttributeAdapter();
			}
			@Override
			public <A extends EObject> Adapter caseExplicitUnsetEReference(ExplicitUnsetEReference<A> object) {
				return createExplicitUnsetEReferenceAdapter();
			}
			@Override
			public <T extends Object, S extends SubtractiveEChange<T>> Adapter caseCompoundSubtraction(CompoundSubtraction<T, S> object) {
				return createCompoundSubtractionAdapter();
			}
			@Override
			public <T extends Object, S extends AdditiveEChange<T>> Adapter caseCompoundAddition(CompoundAddition<T, S> object) {
				return createCompoundAdditionAdapter();
			}
			@Override
			public Adapter caseEChange(EChange object) {
				return createEChangeAdapter();
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
	 * Creates a new adapter for an object of class '{@link tools.vitruv.framework.change.echange.compound.CompoundEChange <em>EChange</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see tools.vitruv.framework.change.echange.compound.CompoundEChange
	 * @generated
	 */
	public Adapter createCompoundEChangeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link tools.vitruv.framework.change.echange.compound.ExplicitUnsetEFeature <em>Explicit Unset EFeature</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see tools.vitruv.framework.change.echange.compound.ExplicitUnsetEFeature
	 * @generated
	 */
	public Adapter createExplicitUnsetEFeatureAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link tools.vitruv.framework.change.echange.compound.ExplicitUnsetEAttribute <em>Explicit Unset EAttribute</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see tools.vitruv.framework.change.echange.compound.ExplicitUnsetEAttribute
	 * @generated
	 */
	public Adapter createExplicitUnsetEAttributeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link tools.vitruv.framework.change.echange.compound.ExplicitUnsetEReference <em>Explicit Unset EReference</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see tools.vitruv.framework.change.echange.compound.ExplicitUnsetEReference
	 * @generated
	 */
	public Adapter createExplicitUnsetEReferenceAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link tools.vitruv.framework.change.echange.compound.CompoundSubtraction <em>Subtraction</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see tools.vitruv.framework.change.echange.compound.CompoundSubtraction
	 * @generated
	 */
	public Adapter createCompoundSubtractionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link tools.vitruv.framework.change.echange.compound.CompoundAddition <em>Addition</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see tools.vitruv.framework.change.echange.compound.CompoundAddition
	 * @generated
	 */
	public Adapter createCompoundAdditionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link tools.vitruv.framework.change.echange.EChange <em>EChange</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see tools.vitruv.framework.change.echange.EChange
	 * @generated
	 */
	public Adapter createEChangeAdapter() {
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

} //CompoundAdapterFactory
