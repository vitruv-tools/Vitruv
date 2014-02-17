/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Correspondence</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.Correspondence#getDependentCorrespondences <em>Dependent Correspondences</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.Correspondence#getParent <em>Parent</em>}</li>
 * </ul>
 * </p>
 *
 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.CorrespondencePackage#getCorrespondence()
 * @model abstract="true"
 * @generated
 */
public interface Correspondence extends EObject {
	/**
	 * Returns the value of the '<em><b>Dependent Correspondences</b></em>' reference list.
	 * The list contents are of type {@link edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.Correspondence}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Dependent Correspondences</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Dependent Correspondences</em>' reference list.
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.CorrespondencePackage#getCorrespondence_DependentCorrespondences()
	 * @model
	 * @generated
	 */
	EList<Correspondence> getDependentCorrespondences();

	/**
	 * Returns the value of the '<em><b>Parent</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parent</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parent</em>' reference.
	 * @see #setParent(Correspondence)
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.CorrespondencePackage#getCorrespondence_Parent()
	 * @model
	 * @generated
	 */
	Correspondence getParent();

	/**
	 * Sets the value of the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.Correspondence#getParent <em>Parent</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Parent</em>' reference.
	 * @see #getParent()
	 * @generated
	 */
	void setParent(Correspondence value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" lower="2" ordered="false"
	 * @generated
	 */
	EList<EObject> getAllInvolvedObjects();

} // Correspondence
