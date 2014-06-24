/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.meta.change;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Delete Non Root EObject</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.DeleteNonRootEObject#getListUpdate <em>List Update</em>}</li>
 * </ul>
 * </p>
 *
 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.ChangePackage#getDeleteNonRootEObject()
 * @model TBounds="org.eclipse.emf.ecore.EJavaObject"
 * @generated
 */
public interface DeleteNonRootEObject<T extends Object> extends DeleteEObject, UpdateEContainmentReference<T> {

    /**
     * Returns the value of the '<em><b>List Update</b></em>' reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>List Update</em>' reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>List Update</em>' reference.
     * @see #setListUpdate(RemoveFromEList)
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.ChangePackage#getDeleteNonRootEObject_ListUpdate()
     * @model required="true"
     * @generated
     */
    RemoveFromEList<EReference> getListUpdate();

    /**
     * Sets the value of the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.DeleteNonRootEObject#getListUpdate <em>List Update</em>}' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>List Update</em>' reference.
     * @see #getListUpdate()
     * @generated
     */
    void setListUpdate(RemoveFromEList<EReference> value);
} // DeleteNonRootEObject
