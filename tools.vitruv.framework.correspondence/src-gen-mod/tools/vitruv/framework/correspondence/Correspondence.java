/**
 */
package tools.vitruv.framework.correspondence;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import tools.vitruv.framework.tuid.TUID;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Correspondence</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruv.framework.correspondence.Correspondence#getParent <em>Parent</em>}</li>
 *   <li>{@link tools.vitruv.framework.correspondence.Correspondence#getDependsOn <em>Depends On</em>}</li>
 *   <li>{@link tools.vitruv.framework.correspondence.Correspondence#getDependedOnBy <em>Depended On By</em>}</li>
 *   <li>{@link tools.vitruv.framework.correspondence.Correspondence#getATUIDs <em>ATUI Ds</em>}</li>
 *   <li>{@link tools.vitruv.framework.correspondence.Correspondence#getBTUIDs <em>BTUI Ds</em>}</li>
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
     * If the meaning of the '<em>Parent</em>' container reference isn't clear, there really should
     * be more of a description here...
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
     * <!-- begin-user-doc --> <!-- end-user-doc -->
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
     * If the meaning of the '<em>Depends On</em>' reference list isn't clear, there really should
     * be more of a description here...
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
     * If the meaning of the '<em>Depended On By</em>' reference list isn't clear, there really
     * should be more of a description here...
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
     * Returns the value of the '<em><b>ATUI Ds</b></em>' attribute list. The list contents are of
     * type {@link tools.vitruv.framework.tuid.TUID}. <!--
     * begin-user-doc -->
     * <p>
     * If the meaning of the '<em>ATUI Ds</em>' attribute list isn't clear, there really should be
     * more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>ATUI Ds</em>' attribute list.
     * @see tools.vitruv.framework.correspondence.CorrespondencePackage#getCorrespondence_ATUIDs()
     * @model dataType="tools.vitruv.framework.correspondence.TUID"
     * @generated
     */
    EList<TUID> getATUIDs();

    /**
     * Returns the value of the '<em><b>BTUI Ds</b></em>' attribute list. The list contents are of
     * type {@link tools.vitruv.framework.tuid.TUID}. <!--
     * begin-user-doc -->
     * <p>
     * If the meaning of the '<em>BTUI Ds</em>' attribute list isn't clear, there really should be
     * more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>BTUI Ds</em>' attribute list.
     * @see tools.vitruv.framework.correspondence.CorrespondencePackage#getCorrespondence_BTUIDs()
     * @model dataType="tools.vitruv.framework.correspondence.TUID"
     * @generated
     */
    EList<TUID> getBTUIDs();

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @model kind="operation"
     * @generated
     */
    EList<EObject> getAs();

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @model kind="operation"
     * @generated
     */
    EList<EObject> getBs();

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @model kind="operation"
     *        dataType="tools.vitruv.framework.correspondence.TUID"
     * @generated NOT
     * @deprecated
     */
    TUID getElementATUID();

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @model kind="operation"
     *        dataType="tools.vitruv.framework.correspondence.TUID"
     * @generated NOT
     * @deprecated
     */
    TUID getElementBTUID();

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @model
     * @generated
     */
    EList<EObject> getElementsForMetamodel(String metamodelNamespaceUri);

} // Correspondence
