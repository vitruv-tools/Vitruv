/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.util;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.*;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.ChangePackage
 * @generated
 */
public class ChangeAdapterFactory extends AdapterFactoryImpl {
    /**
     * The cached model package.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected static ChangePackage modelPackage;

    /**
     * Creates an instance of the adapter factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ChangeAdapterFactory() {
        if (modelPackage == null) {
            modelPackage = ChangePackage.eINSTANCE;
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
    protected ChangeSwitch<Adapter> modelSwitch =
        new ChangeSwitch<Adapter>() {
            @Override
            public Adapter caseEChange(EChange object) {
                return createEChangeAdapter();
            }
            @Override
            public <T extends Object> Adapter caseAdditiveChange(AdditiveChange<T> object) {
                return createAdditiveChangeAdapter();
            }
            @Override
            public <T extends Object> Adapter caseSubtractiveChange(SubtractiveChange<T> object) {
                return createSubtractiveChangeAdapter();
            }
            @Override
            public <T extends Object> Adapter caseReplaciveChange(ReplaciveChange<T> object) {
                return createReplaciveChangeAdapter();
            }
            @Override
            public <T extends Object> Adapter caseAdditiveAttributeChange(AdditiveAttributeChange<T> object) {
                return createAdditiveAttributeChangeAdapter();
            }
            @Override
            public <T extends Object> Adapter caseSubtractiveAttributeChange(SubtractiveAttributeChange<T> object) {
                return createSubtractiveAttributeChangeAdapter();
            }
            @Override
            public <T extends Object> Adapter caseReplaciveAttributeChange(ReplaciveAttributeChange<T> object) {
                return createReplaciveAttributeChangeAdapter();
            }
            @Override
            public <T extends EObject> Adapter caseAdditiveReferenceChange(AdditiveReferenceChange<T> object) {
                return createAdditiveReferenceChangeAdapter();
            }
            @Override
            public Adapter caseSubtractiveReferenceChange(SubtractiveReferenceChange object) {
                return createSubtractiveReferenceChangeAdapter();
            }
            @Override
            public <T extends EObject> Adapter caseReplaciveReferenceChange(ReplaciveReferenceChange<T> object) {
                return createReplaciveReferenceChangeAdapter();
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
     * Creates a new adapter for an object of class '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.AdditiveChange <em>Additive Change</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.AdditiveChange
     * @generated
     */
    public Adapter createAdditiveChangeAdapter() {
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
     * Creates a new adapter for an object of class '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.ReplaciveChange <em>Replacive Change</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.ReplaciveChange
     * @generated
     */
    public Adapter createReplaciveChangeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.AdditiveAttributeChange <em>Additive Attribute Change</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.AdditiveAttributeChange
     * @generated
     */
    public Adapter createAdditiveAttributeChangeAdapter() {
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
     * Creates a new adapter for an object of class '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.ReplaciveAttributeChange <em>Replacive Attribute Change</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.ReplaciveAttributeChange
     * @generated
     */
    public Adapter createReplaciveAttributeChangeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.AdditiveReferenceChange <em>Additive Reference Change</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.AdditiveReferenceChange
     * @generated
     */
    public Adapter createAdditiveReferenceChangeAdapter() {
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
     * Creates a new adapter for an object of class '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.ReplaciveReferenceChange <em>Replacive Reference Change</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.ReplaciveReferenceChange
     * @generated
     */
    public Adapter createReplaciveReferenceChangeAdapter() {
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

} //ChangeAdapterFactory
