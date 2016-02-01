/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TUID;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.EAtomicChange;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>EFeature Change</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.EFeatureChange#getAffectedFeature <em>Affected Feature</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.EFeatureChange#getAffectedEObject <em>Affected EObject</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.EFeatureChange#getOldTUIDOfAffectedEObject <em>Old TUID Of Affected EObject</em>}</li>
 * </ul>
 *
 * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.FeaturePackage#getEFeatureChange()
 * @model abstract="true"
 * @generated
 */
public interface EFeatureChange<T extends EStructuralFeature> extends EAtomicChange {
    /**
     * Returns the value of the '<em><b>Affected Feature</b></em>' reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Affected Feature</em>' reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Affected Feature</em>' reference.
     * @see #setAffectedFeature(EStructuralFeature)
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.FeaturePackage#getEFeatureChange_AffectedFeature()
     * @model required="true"
     * @generated
     */
    T getAffectedFeature();

    /**
     * Sets the value of the '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.EFeatureChange#getAffectedFeature <em>Affected Feature</em>}' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Affected Feature</em>' reference.
     * @see #getAffectedFeature()
     * @generated
     */
    void setAffectedFeature(T value);

    /**
     * Returns the value of the '<em><b>Affected EObject</b></em>' reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Affected EObject</em>' reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Affected EObject</em>' reference.
     * @see #setAffectedEObject(EObject)
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.FeaturePackage#getEFeatureChange_AffectedEObject()
     * @model required="true"
     * @generated
     */
    EObject getAffectedEObject();

    /**
     * Sets the value of the '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.EFeatureChange#getAffectedEObject <em>Affected EObject</em>}' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Affected EObject</em>' reference.
     * @see #getAffectedEObject()
     * @generated
     */
    void setAffectedEObject(EObject value);

    /**
     * Returns the value of the '<em><b>Old TUID Of Affected EObject</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Old TUID Of Affected EObject</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Old TUID Of Affected EObject</em>' attribute.
     * @see #setOldTUIDOfAffectedEObject(TUID)
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.FeaturePackage#getEFeatureChange_OldTUIDOfAffectedEObject()
     * @model dataType="edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.TUID" required="true"
     * @generated
     */
    TUID getOldTUIDOfAffectedEObject();

    /**
     * Sets the value of the '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.EFeatureChange#getOldTUIDOfAffectedEObject <em>Old TUID Of Affected EObject</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Old TUID Of Affected EObject</em>' attribute.
     * @see #getOldTUIDOfAffectedEObject()
     * @generated
     */
    void setOldTUIDOfAffectedEObject(TUID value);

} // EFeatureChange
