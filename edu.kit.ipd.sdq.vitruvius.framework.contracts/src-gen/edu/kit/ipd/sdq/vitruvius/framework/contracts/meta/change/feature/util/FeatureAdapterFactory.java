/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.util;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.EChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.SubtractiveAttributeChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.SubtractiveChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.SubtractiveReferenceChange;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.*;

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
 * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.FeaturePackage
 * @generated
 */
public class FeatureAdapterFactory extends AdapterFactoryImpl {
    /**
     * The cached model package.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected static FeaturePackage modelPackage;

    /**
     * Creates an instance of the adapter factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public FeatureAdapterFactory() {
        if (modelPackage == null) {
            modelPackage = FeaturePackage.eINSTANCE;
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
    protected FeatureSwitch<Adapter> modelSwitch =
        new FeatureSwitch<Adapter>() {
            @Override
            public <T extends EStructuralFeature> Adapter caseEFeatureChange(EFeatureChange<T> object) {
                return createEFeatureChangeAdapter();
            }
            @Override
            public <T extends Object> Adapter caseUpdateEFeature(UpdateEFeature<T> object) {
                return createUpdateEFeatureAdapter();
            }
            @Override
            public <T extends Object> Adapter caseUpdateMultiValuedEFeature(UpdateMultiValuedEFeature<T> object) {
                return createUpdateMultiValuedEFeatureAdapter();
            }
            @Override
            public <T extends Object> Adapter caseUpdateSingleValuedEFeature(UpdateSingleValuedEFeature<T> object) {
                return createUpdateSingleValuedEFeatureAdapter();
            }
            @Override
            public <T extends EStructuralFeature> Adapter caseUnsetEFeature(UnsetEFeature<T> object) {
                return createUnsetEFeatureAdapter();
            }
            @Override
            public <T extends Object> Adapter caseUnsetEAttribute(UnsetEAttribute<T> object) {
                return createUnsetEAttributeAdapter();
            }
            @Override
            public <T extends EObject> Adapter caseUnsetEReference(UnsetEReference<T> object) {
                return createUnsetEReferenceAdapter();
            }
            @Override
            public Adapter caseEChange(EChange object) {
                return createEChangeAdapter();
            }
            @Override
            public <T extends Object> Adapter caseSubtractiveChange(SubtractiveChange<T> object) {
                return createSubtractiveChangeAdapter();
            }
            @Override
            public <T extends Object> Adapter caseSubtractiveAttributeChange(SubtractiveAttributeChange<T> object) {
                return createSubtractiveAttributeChangeAdapter();
            }
            @Override
            public Adapter caseSubtractiveReferenceChange(SubtractiveReferenceChange object) {
                return createSubtractiveReferenceChangeAdapter();
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
     * Creates a new adapter for an object of class '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.EFeatureChange <em>EFeature Change</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.EFeatureChange
     * @generated
     */
    public Adapter createEFeatureChangeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.UpdateEFeature <em>Update EFeature</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.UpdateEFeature
     * @generated
     */
    public Adapter createUpdateEFeatureAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.UpdateMultiValuedEFeature <em>Update Multi Valued EFeature</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.UpdateMultiValuedEFeature
     * @generated
     */
    public Adapter createUpdateMultiValuedEFeatureAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.UpdateSingleValuedEFeature <em>Update Single Valued EFeature</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.UpdateSingleValuedEFeature
     * @generated
     */
    public Adapter createUpdateSingleValuedEFeatureAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.UnsetEFeature <em>Unset EFeature</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.UnsetEFeature
     * @generated
     */
    public Adapter createUnsetEFeatureAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.UnsetEAttribute <em>Unset EAttribute</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.UnsetEAttribute
     * @generated
     */
    public Adapter createUnsetEAttributeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.UnsetEReference <em>Unset EReference</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.UnsetEReference
     * @generated
     */
    public Adapter createUnsetEReferenceAdapter() {
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
     * Creates a new adapter for an object of class '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.SubtractiveChange <em>Subtractive Change</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.SubtractiveChange
     * @generated
     */
    public Adapter createSubtractiveChangeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.SubtractiveAttributeChange <em>Subtractive Attribute Change</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.SubtractiveAttributeChange
     * @generated
     */
    public Adapter createSubtractiveAttributeChangeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.SubtractiveReferenceChange <em>Subtractive Reference Change</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.SubtractiveReferenceChange
     * @generated
     */
    public Adapter createSubtractiveReferenceChangeAdapter() {
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

} //FeatureAdapterFactory
