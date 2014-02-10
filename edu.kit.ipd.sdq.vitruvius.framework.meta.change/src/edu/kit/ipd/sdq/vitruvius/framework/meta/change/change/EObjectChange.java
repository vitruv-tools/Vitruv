/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.meta.change.change;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>EObject Change</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.change.EObjectChange#getChangedEObject <em>Changed EObject</em>}</li>
 * </ul>
 * </p>
 *
 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.change.ChangePackage#getEObjectChange()
 * @model abstract="true"
 * @generated
 */
public interface EObjectChange extends EChange {
    /**
     * Returns the value of the '<em><b>Changed EObject</b></em>' reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Changed EObject</em>' reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Changed EObject</em>' reference.
     * @see #setChangedEObject(EObject)
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.change.ChangePackage#getEObjectChange_ChangedEObject()
     * @model required="true"
     * @generated
     */
    EObject getChangedEObject();

    /**
     * Sets the value of the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.change.EObjectChange#getChangedEObject <em>Changed EObject</em>}' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Changed EObject</em>' reference.
     * @see #getChangedEObject()
     * @generated
     */
    void setChangedEObject(EObject value);

} // EObjectChange
