/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.correspondence;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceModel;
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
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.correspondence.Correspondences#getCorrespondenceModel <em>Correspondence Model</em>}</li>
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
     * Returns the value of the '<em><b>Correspondence Model</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Correspondence Model</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Correspondence Model</em>' attribute.
     * @see #setCorrespondenceModel(CorrespondenceModel)
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.correspondence.CorrespondencePackage#getCorrespondences_CorrespondenceModel()
     * @model dataType="edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.correspondence.CorrespondenceModel" required="true" transient="true"
     * @generated
     */
    CorrespondenceModel getCorrespondenceModel();

    /**
     * Sets the value of the '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.correspondence.Correspondences#getCorrespondenceModel <em>Correspondence Model</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Correspondence Model</em>' attribute.
     * @see #getCorrespondenceModel()
     * @generated
     */
    void setCorrespondenceModel(CorrespondenceModel value);

} // Correspondences
