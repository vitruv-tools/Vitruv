/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence;

import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.datatypes.TUIDCalculatorAndResolver;

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
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.Correspondences#getCorrespondences <em>Correspondences</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.Correspondences#getTuidCARForAs <em>Tuid CAR For As</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.Correspondences#getTuidCARForBs <em>Tuid CAR For Bs</em>}</li>
 * </ul>
 *
 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.CorrespondencePackage#getCorrespondences()
 * @model
 * @generated
 */
public interface Correspondences extends EObject {
    /**
     * Returns the value of the '<em><b>Correspondences</b></em>' containment reference list.
     * The list contents are of type {@link edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.Correspondence}.
     * It is bidirectional and its opposite is '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.Correspondence#getParent <em>Parent</em>}'.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Correspondences</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Correspondences</em>' containment reference list.
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.CorrespondencePackage#getCorrespondences_Correspondences()
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.Correspondence#getParent
     * @model opposite="parent" containment="true"
     * @generated
     */
    EList<Correspondence> getCorrespondences();

    /**
     * Returns the value of the '<em><b>Tuid CAR For As</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Tuid CAR For As</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Tuid CAR For As</em>' attribute.
     * @see #setTuidCARForAs(TUIDCalculatorAndResolver)
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.CorrespondencePackage#getCorrespondences_TuidCARForAs()
     * @model dataType="edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.TUIDCalculatorAndResolver" required="true" transient="true"
     * @generated
     */
    TUIDCalculatorAndResolver getTuidCARForAs();

    /**
     * Sets the value of the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.Correspondences#getTuidCARForAs <em>Tuid CAR For As</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Tuid CAR For As</em>' attribute.
     * @see #getTuidCARForAs()
     * @generated
     */
    void setTuidCARForAs(TUIDCalculatorAndResolver value);

    /**
     * Returns the value of the '<em><b>Tuid CAR For Bs</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Tuid CAR For Bs</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Tuid CAR For Bs</em>' attribute.
     * @see #setTuidCARForBs(TUIDCalculatorAndResolver)
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.CorrespondencePackage#getCorrespondences_TuidCARForBs()
     * @model dataType="edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.TUIDCalculatorAndResolver" required="true" transient="true"
     * @generated
     */
    TUIDCalculatorAndResolver getTuidCARForBs();

    /**
     * Sets the value of the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.Correspondences#getTuidCARForBs <em>Tuid CAR For Bs</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Tuid CAR For Bs</em>' attribute.
     * @see #getTuidCARForBs()
     * @generated
     */
    void setTuidCARForBs(TUIDCalculatorAndResolver value);

} // Correspondences
