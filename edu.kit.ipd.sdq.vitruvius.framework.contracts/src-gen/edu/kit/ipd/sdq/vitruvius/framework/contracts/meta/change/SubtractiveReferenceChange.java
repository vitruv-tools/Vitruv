/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TUID;

import java.util.Map;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Subtractive Reference Change</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.SubtractiveReferenceChange#getOldTUID <em>Old TUID</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.SubtractiveReferenceChange#getFeatureName2OldValueMap <em>Feature Name2 Old Value Map</em>}</li>
 * </ul>
 *
 * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.ChangePackage#getSubtractiveReferenceChange()
 * @model abstract="true" superTypes="edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.SubtractiveChange<edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.TUID>"
 * @generated
 */
public interface SubtractiveReferenceChange extends SubtractiveChange<TUID> {
    /**
     * Returns the value of the '<em><b>Old TUID</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Old TUID</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Old TUID</em>' attribute.
     * @see #setOldTUID(TUID)
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.ChangePackage#getSubtractiveReferenceChange_OldTUID()
     * @model dataType="edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.TUID" required="true"
     * @generated
     */
    TUID getOldTUID();

    /**
     * Sets the value of the '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.SubtractiveReferenceChange#getOldTUID <em>Old TUID</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Old TUID</em>' attribute.
     * @see #getOldTUID()
     * @generated
     */
    void setOldTUID(TUID value);

    /**
     * Returns the value of the '<em><b>Feature Name2 Old Value Map</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Feature Name2 Old Value Map</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Feature Name2 Old Value Map</em>' attribute.
     * @see #setFeatureName2OldValueMap(Map)
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.ChangePackage#getSubtractiveReferenceChange_FeatureName2OldValueMap()
     * @model required="true" transient="true"
     * @generated
     */
    Map<String, Object> getFeatureName2OldValueMap();

    /**
     * Sets the value of the '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.SubtractiveReferenceChange#getFeatureName2OldValueMap <em>Feature Name2 Old Value Map</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Feature Name2 Old Value Map</em>' attribute.
     * @see #getFeatureName2OldValueMap()
     * @generated
     */
    void setFeatureName2OldValueMap(Map<String, Object> value);

} // SubtractiveReferenceChange
