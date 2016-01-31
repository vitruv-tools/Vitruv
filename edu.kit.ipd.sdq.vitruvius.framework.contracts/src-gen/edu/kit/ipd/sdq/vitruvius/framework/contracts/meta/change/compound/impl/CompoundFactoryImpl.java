/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.impl;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class CompoundFactoryImpl extends EFactoryImpl implements CompoundFactory {
    /**
     * Creates the default factory implementation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static CompoundFactory init() {
        try {
            CompoundFactory theCompoundFactory = (CompoundFactory)EPackage.Registry.INSTANCE.getEFactory(CompoundPackage.eNS_URI);
            if (theCompoundFactory != null) {
                return theCompoundFactory;
            }
        }
        catch (Exception exception) {
            EcorePlugin.INSTANCE.log(exception);
        }
        return new CompoundFactoryImpl();
    }

    /**
     * Creates an instance of the factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public CompoundFactoryImpl() {
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
            case CompoundPackage.CREATE_EOBJECT_AND_ADD: return createCreateEObjectAndAdd();
            case CompoundPackage.DELETE_EOBJECT_AND_SUBTRACT: return createDeleteEObjectAndSubtract();
            case CompoundPackage.DELETE_EOBJECT_CREATE_EOBJECT_AND_REPLACE_SINGLE: return createDeleteEObjectCreateEObjectAndReplaceSingle();
            case CompoundPackage.DELETE_EOBJECT_CREATE_EOBJECT_AND_REPLACE_IN_LIST: return createDeleteEObjectCreateEObjectAndReplaceInList();
            case CompoundPackage.MOVE_EOBJECT: return createMoveEObject();
            case CompoundPackage.REPLACE_IN_ELIST: return createReplaceInEList();
            default:
                throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
        }
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public <T extends EObject> CreateEObjectAndAdd<T> createCreateEObjectAndAdd() {
        CreateEObjectAndAddImpl<T> createEObjectAndAdd = new CreateEObjectAndAddImpl<T>();
        return createEObjectAndAdd;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public <T extends EObject> DeleteEObjectAndSubtract<T> createDeleteEObjectAndSubtract() {
        DeleteEObjectAndSubtractImpl<T> deleteEObjectAndSubtract = new DeleteEObjectAndSubtractImpl<T>();
        return deleteEObjectAndSubtract;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public <T extends EObject> DeleteEObjectCreateEObjectAndReplaceSingle<T> createDeleteEObjectCreateEObjectAndReplaceSingle() {
        DeleteEObjectCreateEObjectAndReplaceSingleImpl<T> deleteEObjectCreateEObjectAndReplaceSingle = new DeleteEObjectCreateEObjectAndReplaceSingleImpl<T>();
        return deleteEObjectCreateEObjectAndReplaceSingle;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public <T extends EObject> DeleteEObjectCreateEObjectAndReplaceInList<T> createDeleteEObjectCreateEObjectAndReplaceInList() {
        DeleteEObjectCreateEObjectAndReplaceInListImpl<T> deleteEObjectCreateEObjectAndReplaceInList = new DeleteEObjectCreateEObjectAndReplaceInListImpl<T>();
        return deleteEObjectCreateEObjectAndReplaceInList;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public <T extends EObject> MoveEObject<T> createMoveEObject() {
        MoveEObjectImpl<T> moveEObject = new MoveEObjectImpl<T>();
        return moveEObject;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public <T extends Object> ReplaceInEList<T> createReplaceInEList() {
        ReplaceInEListImpl<T> replaceInEList = new ReplaceInEListImpl<T>();
        return replaceInEList;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public CompoundPackage getCompoundPackage() {
        return (CompoundPackage)getEPackage();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @deprecated
     * @generated
     */
    @Deprecated
    public static CompoundPackage getPackage() {
        return CompoundPackage.eINSTANCE;
    }

} //CompoundFactoryImpl
