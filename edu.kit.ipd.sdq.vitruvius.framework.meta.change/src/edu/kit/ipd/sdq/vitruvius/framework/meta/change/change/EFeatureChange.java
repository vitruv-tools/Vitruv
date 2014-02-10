/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.meta.change.change;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>EFeature Change</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.change.EFeatureChange#getChangedFeatureType <em>Changed Feature Type</em>}</li>
 * </ul>
 * </p>
 *
 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.change.ChangePackage#getEFeatureChange()
 * @model abstract="true"
 * @generated
 */
public interface EFeatureChange extends EChange {

    /**
     * Returns the value of the '<em><b>Changed Feature Type</b></em>' reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Changed Feature Type</em>' reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Changed Feature Type</em>' reference.
     * @see #setChangedFeatureType(EObject)
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.change.ChangePackage#getEFeatureChange_ChangedFeatureType()
     * @model
     * @generated
     */
    EObject getChangedFeatureType();

    /**
     * Sets the value of the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.change.EFeatureChange#getChangedFeatureType <em>Changed Feature Type</em>}' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Changed Feature Type</em>' reference.
     * @see #getChangedFeatureType()
     * @generated
     */
    void setChangedFeatureType(EObject value);
} // EFeatureChange
