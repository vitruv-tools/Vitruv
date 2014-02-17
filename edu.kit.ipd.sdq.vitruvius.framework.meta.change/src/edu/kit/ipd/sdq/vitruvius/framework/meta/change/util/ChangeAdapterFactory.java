/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.meta.change.util;

import edu.kit.ipd.sdq.vitruvius.framework.meta.change.*;

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
 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.ChangePackage
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
            public Adapter caseEObjectChange(EObjectChange object) {
                return createEObjectChangeAdapter();
            }
            @Override
            public <T extends EStructuralFeature> Adapter caseEFeatureChange(EFeatureChange<T> object) {
                return createEFeatureChangeAdapter();
            }
            @Override
            public Adapter caseEAttributeChange(EAttributeChange object) {
                return createEAttributeChangeAdapter();
            }
            @Override
            public Adapter caseEReferenceChange(EReferenceChange object) {
                return createEReferenceChangeAdapter();
            }
            @Override
            public Adapter caseCreateEObject(CreateEObject object) {
                return createCreateEObjectAdapter();
            }
            @Override
            public <T extends EObject> Adapter caseCreateNonRootEObject(CreateNonRootEObject<T> object) {
                return createCreateNonRootEObjectAdapter();
            }
            @Override
            public Adapter caseCreateRootEObject(CreateRootEObject object) {
                return createCreateRootEObjectAdapter();
            }
            @Override
            public <T extends Object> Adapter caseUpdateEFeature(UpdateEFeature<T> object) {
                return createUpdateEFeatureAdapter();
            }
            @Override
            public <T extends EStructuralFeature> Adapter caseUnsetEFeature(UnsetEFeature<T> object) {
                return createUnsetEFeatureAdapter();
            }
            @Override
            public <T extends Object> Adapter caseUpdateEAttribute(UpdateEAttribute<T> object) {
                return createUpdateEAttributeAdapter();
            }
            @Override
            public <T extends EObject> Adapter caseUpdateEReference(UpdateEReference<T> object) {
                return createUpdateEReferenceAdapter();
            }
            @Override
            public <T extends EObject> Adapter caseUpdateEContainmentReference(UpdateEContainmentReference<T> object) {
                return createUpdateEContainmentReferenceAdapter();
            }
            @Override
            public Adapter caseDeleteEObject(DeleteEObject object) {
                return createDeleteEObjectAdapter();
            }
            @Override
            public <T extends EObject> Adapter caseDeleteNonRootEObject(DeleteNonRootEObject<T> object) {
                return createDeleteNonRootEObjectAdapter();
            }
            @Override
            public Adapter caseDeleteRootEObject(DeleteRootEObject object) {
                return createDeleteRootEObjectAdapter();
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
     * Creates a new adapter for an object of class '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.EChange <em>EChange</em>}'.
     * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
     * @return the new adapter.
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.EChange
     * @generated
     */
	public Adapter createEChangeAdapter() {
        return null;
    }

	/**
     * Creates a new adapter for an object of class '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.EObjectChange <em>EObject Change</em>}'.
     * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
     * @return the new adapter.
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.EObjectChange
     * @generated
     */
	public Adapter createEObjectChangeAdapter() {
        return null;
    }

	/**
     * Creates a new adapter for an object of class '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.EFeatureChange <em>EFeature Change</em>}'.
     * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
     * @return the new adapter.
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.EFeatureChange
     * @generated
     */
	public Adapter createEFeatureChangeAdapter() {
        return null;
    }

	/**
     * Creates a new adapter for an object of class '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.EAttributeChange <em>EAttribute Change</em>}'.
     * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
     * @return the new adapter.
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.EAttributeChange
     * @generated
     */
	public Adapter createEAttributeChangeAdapter() {
        return null;
    }

	/**
     * Creates a new adapter for an object of class '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.EReferenceChange <em>EReference Change</em>}'.
     * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
     * @return the new adapter.
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.EReferenceChange
     * @generated
     */
	public Adapter createEReferenceChangeAdapter() {
        return null;
    }

	/**
     * Creates a new adapter for an object of class '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.CreateEObject <em>Create EObject</em>}'.
     * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
     * @return the new adapter.
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.CreateEObject
     * @generated
     */
	public Adapter createCreateEObjectAdapter() {
        return null;
    }

	/**
     * Creates a new adapter for an object of class '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.CreateNonRootEObject <em>Create Non Root EObject</em>}'.
     * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
     * @return the new adapter.
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.CreateNonRootEObject
     * @generated
     */
	public Adapter createCreateNonRootEObjectAdapter() {
        return null;
    }

	/**
     * Creates a new adapter for an object of class '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.CreateRootEObject <em>Create Root EObject</em>}'.
     * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
     * @return the new adapter.
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.CreateRootEObject
     * @generated
     */
	public Adapter createCreateRootEObjectAdapter() {
        return null;
    }

	/**
     * Creates a new adapter for an object of class '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.UpdateEFeature <em>Update EFeature</em>}'.
     * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
     * @return the new adapter.
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.UpdateEFeature
     * @generated
     */
	public Adapter createUpdateEFeatureAdapter() {
        return null;
    }

	/**
     * Creates a new adapter for an object of class '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.UnsetEFeature <em>Unset EFeature</em>}'.
     * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
     * @return the new adapter.
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.UnsetEFeature
     * @generated
     */
	public Adapter createUnsetEFeatureAdapter() {
        return null;
    }

	/**
     * Creates a new adapter for an object of class '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.UpdateEAttribute <em>Update EAttribute</em>}'.
     * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
     * @return the new adapter.
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.UpdateEAttribute
     * @generated
     */
	public Adapter createUpdateEAttributeAdapter() {
        return null;
    }

	/**
     * Creates a new adapter for an object of class '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.UpdateEReference <em>Update EReference</em>}'.
     * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
     * @return the new adapter.
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.UpdateEReference
     * @generated
     */
	public Adapter createUpdateEReferenceAdapter() {
        return null;
    }

	/**
     * Creates a new adapter for an object of class '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.UpdateEContainmentReference <em>Update EContainment Reference</em>}'.
     * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
     * @return the new adapter.
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.UpdateEContainmentReference
     * @generated
     */
	public Adapter createUpdateEContainmentReferenceAdapter() {
        return null;
    }

	/**
     * Creates a new adapter for an object of class '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.DeleteEObject <em>Delete EObject</em>}'.
     * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
     * @return the new adapter.
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.DeleteEObject
     * @generated
     */
	public Adapter createDeleteEObjectAdapter() {
        return null;
    }

	/**
     * Creates a new adapter for an object of class '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.DeleteNonRootEObject <em>Delete Non Root EObject</em>}'.
     * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
     * @return the new adapter.
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.DeleteNonRootEObject
     * @generated
     */
	public Adapter createDeleteNonRootEObjectAdapter() {
        return null;
    }

	/**
     * Creates a new adapter for an object of class '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.DeleteRootEObject <em>Delete Root EObject</em>}'.
     * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
     * @return the new adapter.
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.DeleteRootEObject
     * @generated
     */
	public Adapter createDeleteRootEObjectAdapter() {
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
