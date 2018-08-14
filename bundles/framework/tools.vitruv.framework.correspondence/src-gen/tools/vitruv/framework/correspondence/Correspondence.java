/**
 */
package tools.vitruv.framework.correspondence;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

import tools.vitruv.framework.tuid.Tuid;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Correspondence</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruv.framework.correspondence.Correspondence#getParent <em>Parent</em>}</li>
 *   <li>{@link tools.vitruv.framework.correspondence.Correspondence#getDependsOn <em>Depends On</em>}</li>
 *   <li>{@link tools.vitruv.framework.correspondence.Correspondence#getDependedOnBy <em>Depended On By</em>}</li>
 *   <li>{@link tools.vitruv.framework.correspondence.Correspondence#getATuids <em>ATuids</em>}</li>
 *   <li>{@link tools.vitruv.framework.correspondence.Correspondence#getBTuids <em>BTuids</em>}</li>
 *   <li>{@link tools.vitruv.framework.correspondence.Correspondence#getTag <em>Tag</em>}</li>
 * </ul>
 *
 * @see tools.vitruv.framework.correspondence.CorrespondencePackage#getCorrespondence()
 * @model abstract="true"
 * @generated
 */
public interface Correspondence extends EObject {
	/**
	 * Returns the value of the '<em><b>Parent</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link tools.vitruv.framework.correspondence.Correspondences#getCorrespondences <em>Correspondences</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parent</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parent</em>' container reference.
	 * @see #setParent(Correspondences)
	 * @see tools.vitruv.framework.correspondence.CorrespondencePackage#getCorrespondence_Parent()
	 * @see tools.vitruv.framework.correspondence.Correspondences#getCorrespondences
	 * @model opposite="correspondences" required="true" transient="false"
	 * @generated
	 */
	Correspondences getParent();

	/**
	 * Sets the value of the '{@link tools.vitruv.framework.correspondence.Correspondence#getParent <em>Parent</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Parent</em>' container reference.
	 * @see #getParent()
	 * @generated
	 */
	void setParent(Correspondences value);

	/**
	 * Returns the value of the '<em><b>Depends On</b></em>' reference list.
	 * The list contents are of type {@link tools.vitruv.framework.correspondence.Correspondence}.
	 * It is bidirectional and its opposite is '{@link tools.vitruv.framework.correspondence.Correspondence#getDependedOnBy <em>Depended On By</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Depends On</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Depends On</em>' reference list.
	 * @see tools.vitruv.framework.correspondence.CorrespondencePackage#getCorrespondence_DependsOn()
	 * @see tools.vitruv.framework.correspondence.Correspondence#getDependedOnBy
	 * @model opposite="dependedOnBy" ordered="false"
	 * @generated
	 */
	EList<Correspondence> getDependsOn();

	/**
	 * Returns the value of the '<em><b>Depended On By</b></em>' reference list.
	 * The list contents are of type {@link tools.vitruv.framework.correspondence.Correspondence}.
	 * It is bidirectional and its opposite is '{@link tools.vitruv.framework.correspondence.Correspondence#getDependsOn <em>Depends On</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Depended On By</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Depended On By</em>' reference list.
	 * @see tools.vitruv.framework.correspondence.CorrespondencePackage#getCorrespondence_DependedOnBy()
	 * @see tools.vitruv.framework.correspondence.Correspondence#getDependsOn
	 * @model opposite="dependsOn" ordered="false"
	 * @generated
	 */
	EList<Correspondence> getDependedOnBy();

	/**
	 * Returns the value of the '<em><b>ATuids</b></em>' attribute list.
	 * The list contents are of type {@link tools.vitruv.framework.tuid.Tuid}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>ATuids</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>ATuids</em>' attribute list.
	 * @see tools.vitruv.framework.correspondence.CorrespondencePackage#getCorrespondence_ATuids()
	 * @model dataType="tools.vitruv.framework.correspondence.Tuid"
	 * @generated
	 */
	EList<Tuid> getATuids();

	/**
	 * Returns the value of the '<em><b>BTuids</b></em>' attribute list.
	 * The list contents are of type {@link tools.vitruv.framework.tuid.Tuid}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>BTuids</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>BTuids</em>' attribute list.
	 * @see tools.vitruv.framework.correspondence.CorrespondencePackage#getCorrespondence_BTuids()
	 * @model dataType="tools.vitruv.framework.correspondence.Tuid"
	 * @generated
	 */
	EList<Tuid> getBTuids();

	/**
	 * Returns the value of the '<em><b>Tag</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Tag</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Tag</em>' attribute.
	 * @see #setTag(String)
	 * @see tools.vitruv.framework.correspondence.CorrespondencePackage#getCorrespondence_Tag()
	 * @model
	 * @generated
	 */
	String getTag();

	/**
	 * Sets the value of the '{@link tools.vitruv.framework.correspondence.Correspondence#getTag <em>Tag</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Tag</em>' attribute.
	 * @see #getTag()
	 * @generated
	 */
	void setTag(String value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation"
	 * @generated
	 */
	EList<EObject> getAs();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation"
	 * @generated
	 */
	EList<EObject> getBs();

} // Correspondence
