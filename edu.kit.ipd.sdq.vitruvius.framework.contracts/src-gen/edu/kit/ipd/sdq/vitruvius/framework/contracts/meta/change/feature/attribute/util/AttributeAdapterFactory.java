/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.attribute.util;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.AdditiveEChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.AtomicEChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.EChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.SubtractiveEChange;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.FeatureEChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.UpdateMultiValuedFeatureEChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.UpdateSingleValuedFeatureEChange;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.attribute.*;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.list.InsertInListEChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.list.PermuteListEChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.list.RemoveFromListEChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.list.UpdateSingleListEntryEChange;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.attribute.AttributePackage
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
            public <A extends EObject, T extends Object> Adapter caseReplaceSingleValuedEAttribute(ReplaceSingleValuedEAttribute<A, T> object) {
                return createReplaceSingleValuedEAttributeAdapter();
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
            public <A extends EObject> Adapter casePermuteEAttributeValues(PermuteEAttributeValues<A> object) {
                return createPermuteEAttributeValuesAdapter();
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
            public Adapter caseUpdateSingleValuedFeatureEChange(UpdateSingleValuedFeatureEChange object) {
                return createUpdateSingleValuedFeatureEChangeAdapter();
            }
            @Override
            public Adapter caseUpdateMultiValuedFeatureEChange(UpdateMultiValuedFeatureEChange object) {
                return createUpdateMultiValuedFeatureEChangeAdapter();
            }
            @Override
            public Adapter caseUpdateSingleListEntryEChange(UpdateSingleListEntryEChange object) {
                return createUpdateSingleListEntryEChangeAdapter();
            }
            @Override
            public Adapter caseInsertInListEChange(InsertInListEChange object) {
                return createInsertInListEChangeAdapter();
            }
            @Override
            public Adapter caseRemoveFromListEChange(RemoveFromListEChange object) {
                return createRemoveFromListEChangeAdapter();
            }
            @Override
            public Adapter casePermuteListEChange(PermuteListEChange object) {
                return createPermuteListEChangeAdapter();
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
     * Creates a new adapter for an object of class '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.attribute.UpdateAttributeEChange <em>Update Attribute EChange</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.attribute.UpdateAttributeEChange
     * @generated
     */
    public Adapter createUpdateAttributeEChangeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.attribute.AdditiveAttributeEChange <em>Additive Attribute EChange</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.attribute.AdditiveAttributeEChange
     * @generated
     */
    public Adapter createAdditiveAttributeEChangeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.attribute.SubtractiveAttributeEChange <em>Subtractive Attribute EChange</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.attribute.SubtractiveAttributeEChange
     * @generated
     */
    public Adapter createSubtractiveAttributeEChangeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.attribute.ReplaceSingleValuedEAttribute <em>Replace Single Valued EAttribute</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.attribute.ReplaceSingleValuedEAttribute
     * @generated
     */
    public Adapter createReplaceSingleValuedEAttributeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.attribute.InsertEAttributeValue <em>Insert EAttribute Value</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.attribute.InsertEAttributeValue
     * @generated
     */
    public Adapter createInsertEAttributeValueAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.attribute.RemoveEAttributeValue <em>Remove EAttribute Value</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.attribute.RemoveEAttributeValue
     * @generated
     */
    public Adapter createRemoveEAttributeValueAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.attribute.PermuteEAttributeValues <em>Permute EAttribute Values</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.attribute.PermuteEAttributeValues
     * @generated
     */
    public Adapter createPermuteEAttributeValuesAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.EChange <em>EChange</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.EChange
     * @generated
     */
    public Adapter createEChangeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.AtomicEChange <em>Atomic EChange</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.AtomicEChange
     * @generated
     */
    public Adapter createAtomicEChangeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.FeatureEChange <em>EChange</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.FeatureEChange
     * @generated
     */
    public Adapter createFeatureEChangeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.AdditiveEChange <em>Additive EChange</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.AdditiveEChange
     * @generated
     */
    public Adapter createAdditiveEChangeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.SubtractiveEChange <em>Subtractive EChange</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.SubtractiveEChange
     * @generated
     */
    public Adapter createSubtractiveEChangeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.UpdateSingleValuedFeatureEChange <em>Update Single Valued Feature EChange</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.UpdateSingleValuedFeatureEChange
     * @generated
     */
    public Adapter createUpdateSingleValuedFeatureEChangeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.UpdateMultiValuedFeatureEChange <em>Update Multi Valued Feature EChange</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.UpdateMultiValuedFeatureEChange
     * @generated
     */
    public Adapter createUpdateMultiValuedFeatureEChangeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.list.UpdateSingleListEntryEChange <em>Update Single List Entry EChange</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.list.UpdateSingleListEntryEChange
     * @generated
     */
    public Adapter createUpdateSingleListEntryEChangeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.list.InsertInListEChange <em>Insert In List EChange</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.list.InsertInListEChange
     * @generated
     */
    public Adapter createInsertInListEChangeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.list.RemoveFromListEChange <em>Remove From List EChange</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.list.RemoveFromListEChange
     * @generated
     */
    public Adapter createRemoveFromListEChangeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.list.PermuteListEChange <em>Permute List EChange</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.list.PermuteListEChange
     * @generated
     */
    public Adapter createPermuteListEChangeAdapter() {
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
