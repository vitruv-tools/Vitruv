/**
 */
package tools.vitruv.framework.correspondence;

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
 *   <li>{@link tools.vitruv.framework.correspondence.Correspondences#getCorrespondences <em>Correspondences</em>}</li>
 * </ul>
 *
 * @see tools.vitruv.framework.correspondence.CorrespondencePackage#getCorrespondences()
 * @model
 * @generated
 */
public interface Correspondences extends EObject {
	/**
	 * Returns the value of the '<em><b>Correspondences</b></em>' containment reference list.
	 * The list contents are of type {@link tools.vitruv.framework.correspondence.Correspondence}.
	 * It is bidirectional and its opposite is '{@link tools.vitruv.framework.correspondence.Correspondence#getParent <em>Parent</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Correspondences</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Correspondences</em>' containment reference list.
	 * @see tools.vitruv.framework.correspondence.CorrespondencePackage#getCorrespondences_Correspondences()
	 * @see tools.vitruv.framework.correspondence.Correspondence#getParent
	 * @model opposite="parent" containment="true"
	 * @generated
	 */
	EList<Correspondence> getCorrespondences();

} // Correspondences
