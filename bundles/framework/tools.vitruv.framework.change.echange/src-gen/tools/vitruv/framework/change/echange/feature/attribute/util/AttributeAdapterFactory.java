/**
 */
package tools.vitruv.framework.change.echange.feature.attribute.util;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

import tools.vitruv.framework.change.echange.AdditiveEChange;
import tools.vitruv.framework.change.echange.AtomicEChange;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.SubtractiveEChange;

import tools.vitruv.framework.change.echange.feature.FeatureEChange;
import tools.vitruv.framework.change.echange.feature.UpdateMultiValuedFeatureEChange;
import tools.vitruv.framework.change.echange.feature.UpdateSingleValuedFeatureEChange;

import tools.vitruv.framework.change.echange.feature.attribute.*;

import tools.vitruv.framework.change.echange.feature.list.InsertInListEChange;
import tools.vitruv.framework.change.echange.feature.list.RemoveFromListEChange;
import tools.vitruv.framework.change.echange.feature.list.UpdateSingleListEntryEChange;

import tools.vitruv.framework.change.echange.feature.single.ReplaceSingleValuedFeatureEChange;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see tools.vitruv.framework.change.echange.feature.attribute.AttributePackage
 * @generated
 */
public class AttributeAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static AttributePackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AttributeAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = AttributePackage.eINSTANCE;
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
	protected AttributeSwitch<Adapter> modelSwitch =
		new AttributeSwitch<Adapter>() {
			@Override
			public <A extends EObject> Adapter caseUpdateAttributeEChange(UpdateAttributeEChange<A> object) {
				return createUpdateAttributeEChangeAdapter();
			}
			@Override
			public <A extends EObject, T extends Object> Adapter caseAdditiveAttributeEChange(AdditiveAttributeEChange<A, T> object) {
				return createAdditiveAttributeEChangeAdapter();
			}
			@Override
			public <A extends EObject, T extends Object> Adapter caseSubtractiveAttributeEChange(SubtractiveAttributeEChange<A, T> object) {
				return createSubtractiveAttributeEChangeAdapter();
			}
			@Override
			public <A extends EObject, T extends Object> Adapter caseInsertEAttributeValue(InsertEAttributeValue<A, T> object) {
				return createInsertEAttributeValueAdapter();
			}
			@Override
			public <A extends EObject, T extends Object> Adapter caseRemoveEAttributeValue(RemoveEAttributeValue<A, T> object) {
				return createRemoveEAttributeValueAdapter();
			}
			@Override
			public <A extends EObject, T extends Object> Adapter caseReplaceSingleValuedEAttribute(ReplaceSingleValuedEAttribute<A, T> object) {
				return createReplaceSingleValuedEAttributeAdapter();
			}
			@Override
			public Adapter caseEChange(EChange object) {
				return createEChangeAdapter();
			}
			@Override
			public Adapter caseAtomicEChange(AtomicEChange object) {
				return createAtomicEChangeAdapter();
			}
			@Override
			public <A extends EObject, F extends EStructuralFeature> Adapter caseFeatureEChange(FeatureEChange<A, F> object) {
				return createFeatureEChangeAdapter();
			}
			@Override
			public <T extends Object> Adapter caseAdditiveEChange(AdditiveEChange<T> object) {
				return createAdditiveEChangeAdapter();
			}
			@Override
			public <T extends Object> Adapter caseSubtractiveEChange(SubtractiveEChange<T> object) {
				return createSubtractiveEChangeAdapter();
			}
			@Override
			public <A extends EObject, F extends EStructuralFeature> Adapter caseUpdateMultiValuedFeatureEChange(UpdateMultiValuedFeatureEChange<A, F> object) {
				return createUpdateMultiValuedFeatureEChangeAdapter();
			}
			@Override
			public <A extends EObject, F extends EStructuralFeature> Adapter caseUpdateSingleListEntryEChange(UpdateSingleListEntryEChange<A, F> object) {
				return createUpdateSingleListEntryEChangeAdapter();
			}
			@Override
			public <A extends EObject, F extends EStructuralFeature, T extends Object> Adapter caseInsertInListEChange(InsertInListEChange<A, F, T> object) {
				return createInsertInListEChangeAdapter();
			}
			@Override
			public <A extends EObject, F extends EStructuralFeature, T extends Object> Adapter caseRemoveFromListEChange(RemoveFromListEChange<A, F, T> object) {
				return createRemoveFromListEChangeAdapter();
			}
			@Override
			public <A extends EObject, F extends EStructuralFeature> Adapter caseUpdateSingleValuedFeatureEChange(UpdateSingleValuedFeatureEChange<A, F> object) {
				return createUpdateSingleValuedFeatureEChangeAdapter();
			}
			@Override
			public <A extends EObject, F extends EStructuralFeature, T extends Object> Adapter caseReplaceSingleValuedFeatureEChange(ReplaceSingleValuedFeatureEChange<A, F, T> object) {
				return createReplaceSingleValuedFeatureEChangeAdapter();
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
	 * Creates a new adapter for an object of class '{@link tools.vitruv.framework.change.echange.feature.attribute.UpdateAttributeEChange <em>Update Attribute EChange</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see tools.vitruv.framework.change.echange.feature.attribute.UpdateAttributeEChange
	 * @generated
	 */
	public Adapter createUpdateAttributeEChangeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link tools.vitruv.framework.change.echange.feature.attribute.AdditiveAttributeEChange <em>Additive Attribute EChange</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see tools.vitruv.framework.change.echange.feature.attribute.AdditiveAttributeEChange
	 * @generated
	 */
	public Adapter createAdditiveAttributeEChangeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link tools.vitruv.framework.change.echange.feature.attribute.SubtractiveAttributeEChange <em>Subtractive Attribute EChange</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see tools.vitruv.framework.change.echange.feature.attribute.SubtractiveAttributeEChange
	 * @generated
	 */
	public Adapter createSubtractiveAttributeEChangeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link tools.vitruv.framework.change.echange.feature.attribute.InsertEAttributeValue <em>Insert EAttribute Value</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see tools.vitruv.framework.change.echange.feature.attribute.InsertEAttributeValue
	 * @generated
	 */
	public Adapter createInsertEAttributeValueAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link tools.vitruv.framework.change.echange.feature.attribute.RemoveEAttributeValue <em>Remove EAttribute Value</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see tools.vitruv.framework.change.echange.feature.attribute.RemoveEAttributeValue
	 * @generated
	 */
	public Adapter createRemoveEAttributeValueAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link tools.vitruv.framework.change.echange.feature.attribute.ReplaceSingleValuedEAttribute <em>Replace Single Valued EAttribute</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see tools.vitruv.framework.change.echange.feature.attribute.ReplaceSingleValuedEAttribute
	 * @generated
	 */
	public Adapter createReplaceSingleValuedEAttributeAdapter() {
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
	 * Creates a new adapter for an object of class '{@link tools.vitruv.framework.change.echange.AtomicEChange <em>Atomic EChange</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see tools.vitruv.framework.change.echange.AtomicEChange
	 * @generated
	 */
	public Adapter createAtomicEChangeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link tools.vitruv.framework.change.echange.feature.FeatureEChange <em>EChange</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see tools.vitruv.framework.change.echange.feature.FeatureEChange
	 * @generated
	 */
	public Adapter createFeatureEChangeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link tools.vitruv.framework.change.echange.AdditiveEChange <em>Additive EChange</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see tools.vitruv.framework.change.echange.AdditiveEChange
	 * @generated
	 */
	public Adapter createAdditiveEChangeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link tools.vitruv.framework.change.echange.SubtractiveEChange <em>Subtractive EChange</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see tools.vitruv.framework.change.echange.SubtractiveEChange
	 * @generated
	 */
	public Adapter createSubtractiveEChangeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link tools.vitruv.framework.change.echange.feature.UpdateMultiValuedFeatureEChange <em>Update Multi Valued Feature EChange</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see tools.vitruv.framework.change.echange.feature.UpdateMultiValuedFeatureEChange
	 * @generated
	 */
	public Adapter createUpdateMultiValuedFeatureEChangeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link tools.vitruv.framework.change.echange.feature.list.UpdateSingleListEntryEChange <em>Update Single List Entry EChange</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see tools.vitruv.framework.change.echange.feature.list.UpdateSingleListEntryEChange
	 * @generated
	 */
	public Adapter createUpdateSingleListEntryEChangeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link tools.vitruv.framework.change.echange.feature.list.InsertInListEChange <em>Insert In List EChange</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see tools.vitruv.framework.change.echange.feature.list.InsertInListEChange
	 * @generated
	 */
	public Adapter createInsertInListEChangeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link tools.vitruv.framework.change.echange.feature.list.RemoveFromListEChange <em>Remove From List EChange</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see tools.vitruv.framework.change.echange.feature.list.RemoveFromListEChange
	 * @generated
	 */
	public Adapter createRemoveFromListEChangeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link tools.vitruv.framework.change.echange.feature.UpdateSingleValuedFeatureEChange <em>Update Single Valued Feature EChange</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see tools.vitruv.framework.change.echange.feature.UpdateSingleValuedFeatureEChange
	 * @generated
	 */
	public Adapter createUpdateSingleValuedFeatureEChangeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link tools.vitruv.framework.change.echange.feature.single.ReplaceSingleValuedFeatureEChange <em>Replace Single Valued Feature EChange</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see tools.vitruv.framework.change.echange.feature.single.ReplaceSingleValuedFeatureEChange
	 * @generated
	 */
	public Adapter createReplaceSingleValuedFeatureEChangeAdapter() {
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

} //AttributeAdapterFactory
