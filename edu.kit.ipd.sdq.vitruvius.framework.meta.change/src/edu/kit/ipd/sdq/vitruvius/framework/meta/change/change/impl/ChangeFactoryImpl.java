/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.meta.change.change.impl;

import edu.kit.ipd.sdq.vitruvius.framework.meta.change.change.*;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class ChangeFactoryImpl extends EFactoryImpl implements ChangeFactory {
    /**
     * Creates the default factory implementation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static ChangeFactory init() {
        try {
            ChangeFactory theChangeFactory = (ChangeFactory)EPackage.Registry.INSTANCE.getEFactory(ChangePackage.eNS_URI);
            if (theChangeFactory != null) {
                return theChangeFactory;
            }
        }
        catch (Exception exception) {
            EcorePlugin.INSTANCE.log(exception);
        }
        return new ChangeFactoryImpl();
    }

    /**
     * Creates an instance of the factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ChangeFactoryImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EObject create(EClass eClass) {
        switch (eClass.getClassifierID()) {
            case ChangePackage.CREATE_NON_ROOT_EOBJECT: return createCreateNonRootEObject();
            case ChangePackage.CREATE_ROOT_EOBJECT: return createCreateRootEObject();
            case ChangePackage.UNSET_EFEATURE: return createUnsetEFeature();
            case ChangePackage.UPDATE_EATTRIBUTE: return createUpdateEAttribute();
            case ChangePackage.UPDATE_EREFERENCE: return createUpdateEReference();
            case ChangePackage.UPDATE_ECONTAINMENT_REFERENCE: return createUpdateEContainmentReference();
            case ChangePackage.DELETE_NON_ROOT_EOBJECT: return createDeleteNonRootEObject();
            case ChangePackage.DELETE_ROOT_EOBJECT: return createDeleteRootEObject();
            default:
                throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
        }
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public <T extends EObject, U extends EReference> CreateNonRootEObject<T, U> createCreateNonRootEObject() {
        CreateNonRootEObjectImpl<T, U> createNonRootEObject = new CreateNonRootEObjectImpl<T, U>();
        return createNonRootEObject;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public CreateRootEObject createCreateRootEObject() {
        CreateRootEObjectImpl createRootEObject = new CreateRootEObjectImpl();
        return createRootEObject;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public <T extends EStructuralFeature> UnsetEFeature<T> createUnsetEFeature() {
        UnsetEFeatureImpl<T> unsetEFeature = new UnsetEFeatureImpl<T>();
        return unsetEFeature;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public <T extends Object, U extends EAttribute> UpdateEAttribute<T, U> createUpdateEAttribute() {
        UpdateEAttributeImpl<T, U> updateEAttribute = new UpdateEAttributeImpl<T, U>();
        return updateEAttribute;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public <T extends EObject, U extends EReference> UpdateEReference<T, U> createUpdateEReference() {
        UpdateEReferenceImpl<T, U> updateEReference = new UpdateEReferenceImpl<T, U>();
        return updateEReference;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public <T extends EObject, U extends EReference> UpdateEContainmentReference<T, U> createUpdateEContainmentReference() {
        UpdateEContainmentReferenceImpl<T, U> updateEContainmentReference = new UpdateEContainmentReferenceImpl<T, U>();
        return updateEContainmentReference;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public <T extends EObject, U extends EReference> DeleteNonRootEObject<T, U> createDeleteNonRootEObject() {
        DeleteNonRootEObjectImpl<T, U> deleteNonRootEObject = new DeleteNonRootEObjectImpl<T, U>();
        return deleteNonRootEObject;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public DeleteRootEObject createDeleteRootEObject() {
        DeleteRootEObjectImpl deleteRootEObject = new DeleteRootEObjectImpl();
        return deleteRootEObject;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ChangePackage getChangePackage() {
        return (ChangePackage)getEPackage();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @deprecated
     * @generated
     */
    @Deprecated
    public static ChangePackage getPackage() {
        return ChangePackage.eINSTANCE;
    }

} //ChangeFactoryImpl
