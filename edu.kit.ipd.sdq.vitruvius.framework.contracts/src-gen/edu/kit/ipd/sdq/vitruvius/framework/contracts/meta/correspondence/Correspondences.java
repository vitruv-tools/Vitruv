/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.correspondence;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Correspondences</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.correspondence.Correspondences#getCorrespondences <em>Correspondences</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.correspondence.Correspondences#getCorrespondenceInstance <em>Correspondence Instance</em>}</li>
 * </ul>
 *
 * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.correspondence.CorrespondencePackage#getCorrespondences()
 * @model
 * @generated
 */
public interface Correspondences extends EObject {
    /**
     * Returns the value of the '<em><b>Correspondences</b></em>' containment reference list.
     * The list contents are of type {@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.correspondence.Correspondence}.
     * It is bidirectional and its opposite is '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.correspondence.Correspondence#getParent <em>Parent</em>}'.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Correspondences</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Correspondences</em>' containment reference list.
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.correspondence.CorrespondencePackage#getCorrespondences_Correspondences()
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.correspondence.Correspondence#getParent
     * @model opposite="parent" containment="true"
     * @generated
     */
    EList<Correspondence> getCorrespondences();

    /**
     * Returns the value of the '<em><b>Correspondence Instance</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Correspondence Instance</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Correspondence Instance</em>' attribute.
     * @see #setCorrespondenceInstance(CorrespondenceInstance)
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.correspondence.CorrespondencePackage#getCorrespondences_CorrespondenceInstance()
     * @model dataType="edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.correspondence.CorrespondenceInstance" required="true" transient="true"
     * @generated
     */
    CorrespondenceInstance getCorrespondenceInstance();

    /**
     * Sets the value of the '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.correspondence.Correspondences#getCorrespondenceInstance <em>Correspondence Instance</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Correspondence Instance</em>' attribute.
     * @see #getCorrespondenceInstance()
     * @generated
     */
    void setCorrespondenceInstance(CorrespondenceInstance value);

} // Correspondences
