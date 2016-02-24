/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.correspondence;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TUID;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Correspondence</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.correspondence.Correspondence#getParent <em>Parent</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.correspondence.Correspondence#getDependsOn <em>Depends On</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.correspondence.Correspondence#getDependedOnBy <em>Depended On By</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.correspondence.Correspondence#getATUIDs <em>ATUI Ds</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.correspondence.Correspondence#getBTUIDs <em>BTUI Ds</em>}</li>
 * </ul>
 *
 * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.correspondence.CorrespondencePackage#getCorrespondence()
 * @model
 * @generated
 */
public interface Correspondence extends EObject {
    /**
     * Returns the value of the '<em><b>Parent</b></em>' container reference.
     * It is bidirectional and its opposite is '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.correspondence.Correspondences#getCorrespondences <em>Correspondences</em>}'.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Parent</em>' container reference isn't clear, there really should
     * be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Parent</em>' container reference.
     * @see #setParent(Correspondences)
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.correspondence.CorrespondencePackage#getCorrespondence_Parent()
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.correspondence.Correspondences#getCorrespondences
     * @model opposite="correspondences" required="true" transient="false"
     * @generated
     */
    Correspondences getParent();

    /**
     * Sets the value of the '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.correspondence.Correspondence#getParent <em>Parent</em>}' container reference.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @param value the new value of the '<em>Parent</em>' container reference.
     * @see #getParent()
     * @generated
     */
    void setParent(Correspondences value);

    /**
     * Returns the value of the '<em><b>Depends On</b></em>' reference list.
     * The list contents are of type {@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.correspondence.Correspondence}.
     * It is bidirectional and its opposite is '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.correspondence.Correspondence#getDependedOnBy <em>Depended On By</em>}'.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Depends On</em>' reference list isn't clear, there really should
     * be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Depends On</em>' reference list.
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.correspondence.CorrespondencePackage#getCorrespondence_DependsOn()
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.correspondence.Correspondence#getDependedOnBy
     * @model opposite="dependedOnBy" ordered="false"
     * @generated
     */
    EList<Correspondence> getDependsOn();

    /**
     * Returns the value of the '<em><b>Depended On By</b></em>' reference list.
     * The list contents are of type {@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.correspondence.Correspondence}.
     * It is bidirectional and its opposite is '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.correspondence.Correspondence#getDependsOn <em>Depends On</em>}'.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Depended On By</em>' reference list isn't clear, there really
     * should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Depended On By</em>' reference list.
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.correspondence.CorrespondencePackage#getCorrespondence_DependedOnBy()
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.correspondence.Correspondence#getDependsOn
     * @model opposite="dependsOn" ordered="false"
     * @generated
     */
    EList<Correspondence> getDependedOnBy();

    /**
     * Returns the value of the '<em><b>ATUI Ds</b></em>' attribute list. The list contents are of
     * type {@link edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TUID}. <!--
     * begin-user-doc -->
     * <p>
     * If the meaning of the '<em>ATUI Ds</em>' attribute list isn't clear, there really should be
     * more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>ATUI Ds</em>' attribute list.
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.correspondence.CorrespondencePackage#getCorrespondence_ATUIDs()
     * @model dataType="edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.correspondence.TUID"
     * @generated
     */
    EList<TUID> getATUIDs();

    /**
     * Returns the value of the '<em><b>BTUI Ds</b></em>' attribute list. The list contents are of
     * type {@link edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TUID}. <!--
     * begin-user-doc -->
     * <p>
     * If the meaning of the '<em>BTUI Ds</em>' attribute list isn't clear, there really should be
     * more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>BTUI Ds</em>' attribute list.
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.correspondence.CorrespondencePackage#getCorrespondence_BTUIDs()
     * @model dataType="edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.correspondence.TUID"
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
     *        dataType="edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.correspondence.TUID"
     * @generated NOT
     * @deprecated
     */
    TUID getElementATUID();

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @model kind="operation"
     *        dataType="edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.correspondence.TUID"
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
