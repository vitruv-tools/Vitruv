/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Correspondences</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.Correspondences#getCorrespondences <em>Correspondences</em>}</li>
 * </ul>
 * </p>
 *
 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.CorrespondencePackage#getCorrespondences()
 * @model
 * @generated
 */
public interface Correspondences extends EObject {
	/**
	 * Returns the value of the '<em><b>Correspondences</b></em>' containment reference list.
	 * The list contents are of type {@link edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.Correspondence}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Correspondences</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Correspondences</em>' containment reference list.
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.CorrespondencePackage#getCorrespondences_Correspondences()
	 * @model containment="true"
	 * @generated
	 */
	EList<Correspondence> getCorrespondences();

} // Correspondences
