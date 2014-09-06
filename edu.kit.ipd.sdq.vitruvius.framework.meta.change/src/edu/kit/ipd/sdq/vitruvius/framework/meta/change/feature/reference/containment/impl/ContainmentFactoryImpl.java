/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.impl;

import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.*;

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
public class ContainmentFactoryImpl extends EFactoryImpl implements ContainmentFactory {
	/**
     * Creates the default factory implementation.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public static ContainmentFactory init() {
        try {
            ContainmentFactory theContainmentFactory = (ContainmentFactory)EPackage.Registry.INSTANCE.getEFactory(ContainmentPackage.eNS_URI);
            if (theContainmentFactory != null) {
                return theContainmentFactory;
            }
        }
        catch (Exception exception) {
            EcorePlugin.INSTANCE.log(exception);
        }
        return new ContainmentFactoryImpl();
    }

	/**
     * Creates an instance of the factory.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public ContainmentFactoryImpl() {
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
            case ContainmentPackage.CREATE_NON_ROOT_EOBJECT_SINGLE: return createCreateNonRootEObjectSingle();
            case ContainmentPackage.REPLACE_NON_ROOT_EOBJECT_SINGLE: return createReplaceNonRootEObjectSingle();
            case ContainmentPackage.DELETE_NON_ROOT_EOBJECT_SINGLE: return createDeleteNonRootEObjectSingle();
            case ContainmentPackage.CREATE_NON_ROOT_EOBJECT_IN_LIST: return createCreateNonRootEObjectInList();
            case ContainmentPackage.REPLACE_NON_ROOT_EOBJECT_IN_LIST: return createReplaceNonRootEObjectInList();
            case ContainmentPackage.DELETE_NON_ROOT_EOBJECT_IN_LIST: return createDeleteNonRootEObjectInList();
            case ContainmentPackage.PERMUTE_CONTAINMENT_EREFERENCE_VALUES: return createPermuteContainmentEReferenceValues();
            case ContainmentPackage.INSERT_NON_ROOT_EOBJECT_IN_CONTAINMENT_LIST: return createInsertNonRootEObjectInContainmentList();
            case ContainmentPackage.REMOVE_NON_ROOT_EOBJECT_FROM_CONTAINMENT_LIST: return createRemoveNonRootEObjectFromContainmentList();
            default:
                throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
        }
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public <T extends EObject> CreateNonRootEObjectSingle<T> createCreateNonRootEObjectSingle() {
        CreateNonRootEObjectSingleImpl<T> createNonRootEObjectSingle = new CreateNonRootEObjectSingleImpl<T>();
        return createNonRootEObjectSingle;
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public <T extends EObject> ReplaceNonRootEObjectSingle<T> createReplaceNonRootEObjectSingle() {
        ReplaceNonRootEObjectSingleImpl<T> replaceNonRootEObjectSingle = new ReplaceNonRootEObjectSingleImpl<T>();
        return replaceNonRootEObjectSingle;
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public <T extends EObject> DeleteNonRootEObjectSingle<T> createDeleteNonRootEObjectSingle() {
        DeleteNonRootEObjectSingleImpl<T> deleteNonRootEObjectSingle = new DeleteNonRootEObjectSingleImpl<T>();
        return deleteNonRootEObjectSingle;
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public <T extends EObject> CreateNonRootEObjectInList<T> createCreateNonRootEObjectInList() {
        CreateNonRootEObjectInListImpl<T> createNonRootEObjectInList = new CreateNonRootEObjectInListImpl<T>();
        return createNonRootEObjectInList;
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public <T extends EObject> ReplaceNonRootEObjectInList<T> createReplaceNonRootEObjectInList() {
        ReplaceNonRootEObjectInListImpl<T> replaceNonRootEObjectInList = new ReplaceNonRootEObjectInListImpl<T>();
        return replaceNonRootEObjectInList;
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public <T extends EObject> DeleteNonRootEObjectInList<T> createDeleteNonRootEObjectInList() {
        DeleteNonRootEObjectInListImpl<T> deleteNonRootEObjectInList = new DeleteNonRootEObjectInListImpl<T>();
        return deleteNonRootEObjectInList;
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public <T extends EObject> PermuteContainmentEReferenceValues<T> createPermuteContainmentEReferenceValues() {
        PermuteContainmentEReferenceValuesImpl<T> permuteContainmentEReferenceValues = new PermuteContainmentEReferenceValuesImpl<T>();
        return permuteContainmentEReferenceValues;
    }

	/**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public <T extends EObject> InsertNonRootEObjectInContainmentList<T> createInsertNonRootEObjectInContainmentList() {
        InsertNonRootEObjectInContainmentListImpl<T> insertNonRootEObjectInContainmentList = new InsertNonRootEObjectInContainmentListImpl<T>();
        return insertNonRootEObjectInContainmentList;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public <T extends EObject> RemoveNonRootEObjectFromContainmentList<T> createRemoveNonRootEObjectFromContainmentList() {
        RemoveNonRootEObjectFromContainmentListImpl<T> removeNonRootEObjectFromContainmentList = new RemoveNonRootEObjectFromContainmentListImpl<T>();
        return removeNonRootEObjectFromContainmentList;
    }

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public ContainmentPackage getContainmentPackage() {
        return (ContainmentPackage)getEPackage();
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @deprecated
     * @generated
     */
	@Deprecated
	public static ContainmentPackage getPackage() {
        return ContainmentPackage.eINSTANCE;
    }

} //ContainmentFactoryImpl
