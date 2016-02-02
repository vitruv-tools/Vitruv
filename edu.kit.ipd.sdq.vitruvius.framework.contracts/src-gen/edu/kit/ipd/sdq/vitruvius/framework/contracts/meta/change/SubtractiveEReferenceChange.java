/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change;

import java.util.Map;

import org.eclipse.emf.ecore.EStructuralFeature;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TUID;

/**
 * <!-- begin-user-doc --> A representation of the model object '
 * <em><b>Subtractive EReference Change</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.SubtractiveEReferenceChange#getOldTUID <em>Old TUID</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.SubtractiveEReferenceChange#getFeature2OldValueMap <em>Feature2 Old Value Map</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.SubtractiveEReferenceChange#isIsDelete <em>Is Delete</em>}</li>
 * </ul>
 *
 * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.ChangePackage#getSubtractiveEReferenceChange()
 * @model abstract="true" superTypes="edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.SubtractiveEChange<edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.TUID>"
 * @generated
 */
public interface SubtractiveEReferenceChange extends SubtractiveEChange<TUID> {
    /**
     * Returns the value of the '<em><b>Old TUID</b></em>' attribute. <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Old TUID</em>' attribute isn't clear, there really should be more
     * of a description here...
     * </p>
     * <!-- end-user-doc -->
     * 
     * @return the value of the '<em>Old TUID</em>' attribute.
     * @see #setOldTUID(TUID)
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.ChangePackage#getSubtractiveEReferenceChange_OldTUID()
     * @model dataType="edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.TUID"
     *        required="true"
     * @generated NOT
     */
    default TUID getOldTUID() {
        return getOldValue();
    }

    /**
     * Sets the value of the '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.SubtractiveEReferenceChange#getOldTUID <em>Old TUID</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @param value the new value of the '<em>Old TUID</em>' attribute.
     * @see #getOldTUID()
     * @generated
     */
    void setOldTUID(TUID value);

    /**
     * Returns the value of the '<em><b>Feature2 Old Value Map</b></em>' attribute. <!--
     * begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Feature2 Old Value Map</em>' attribute isn't clear, there really
     * should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * 
     * @return the value of the '<em>Feature2 Old Value Map</em>' attribute.
     * @see #setFeature2OldValueMap(Map)
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.ChangePackage#getSubtractiveEReferenceChange_Feature2OldValueMap()
     * @model required="true" transient="true"
     * @generated
     */
    Map<EStructuralFeature, Object> getFeature2OldValueMap();

    /**
     * Sets the value of the '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.SubtractiveEReferenceChange#getFeature2OldValueMap <em>Feature2 Old Value Map</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @param value the new value of the '<em>Feature2 Old Value Map</em>' attribute.
     * @see #getFeature2OldValueMap()
     * @generated
     */
    void setFeature2OldValueMap(Map<EStructuralFeature, Object> value);

    /**
     * Returns the value of the '<em><b>Is Delete</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Is Delete</em>' attribute isn't clear, there really should be more
     * of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Is Delete</em>' attribute.
     * @see #setIsDelete(boolean)
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.ChangePackage#getSubtractiveEReferenceChange_IsDelete()
     * @model required="true"
     * @generated
     */
    boolean isIsDelete();

    /**
     * Sets the value of the '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.SubtractiveEReferenceChange#isIsDelete <em>Is Delete</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @param value the new value of the '<em>Is Delete</em>' attribute.
     * @see #isIsDelete()
     * @generated
     */
    void setIsDelete(boolean value);

} // SubtractiveEReferenceChange
