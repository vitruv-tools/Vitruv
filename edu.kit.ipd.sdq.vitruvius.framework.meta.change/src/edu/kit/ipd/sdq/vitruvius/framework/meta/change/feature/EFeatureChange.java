/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature;

import edu.kit.ipd.sdq.vitruvius.framework.meta.change.EChange;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>EFeature Change</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.EFeatureChange#getAffectedFeature <em>Affected Feature</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.EFeatureChange#getOldAffectedEObject <em>Old Affected EObject</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.EFeatureChange#getNewAffectedEObject <em>New Affected EObject</em>}</li>
 * </ul>
 * </p>
 *
 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.FeaturePackage#getEFeatureChange()
 * @model abstract="true"
 * @generated
 */
public interface EFeatureChange<T extends EStructuralFeature> extends EChange {
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
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.FeaturePackage#getEFeatureChange_AffectedFeature()
     * @model required="true"
     * @generated
     */
	T getAffectedFeature();

	/**
     * Sets the value of the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.EFeatureChange#getAffectedFeature <em>Affected Feature</em>}' reference.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @param value the new value of the '<em>Affected Feature</em>' reference.
     * @see #getAffectedFeature()
     * @generated
     */
	void setAffectedFeature(T value);

	/**
     * Returns the value of the '<em><b>Old Affected EObject</b></em>' reference.
     * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Old Affected EObject</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
     * @return the value of the '<em>Old Affected EObject</em>' reference.
     * @see #setOldAffectedEObject(EObject)
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.FeaturePackage#getEFeatureChange_OldAffectedEObject()
     * @model required="true"
     * @generated
     */
	EObject getOldAffectedEObject();

	/**
     * Sets the value of the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.EFeatureChange#getOldAffectedEObject <em>Old Affected EObject</em>}' reference.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @param value the new value of the '<em>Old Affected EObject</em>' reference.
     * @see #getOldAffectedEObject()
     * @generated
     */
	void setOldAffectedEObject(EObject value);

	/**
     * Returns the value of the '<em><b>New Affected EObject</b></em>' reference.
     * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>New Affected EObject</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
     * @return the value of the '<em>New Affected EObject</em>' reference.
     * @see #setNewAffectedEObject(EObject)
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.FeaturePackage#getEFeatureChange_NewAffectedEObject()
     * @model required="true"
     * @generated
     */
	EObject getNewAffectedEObject();

	/**
     * Sets the value of the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.EFeatureChange#getNewAffectedEObject <em>New Affected EObject</em>}' reference.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @param value the new value of the '<em>New Affected EObject</em>' reference.
     * @see #getNewAffectedEObject()
     * @generated
     */
	void setNewAffectedEObject(EObject value);

} // EFeatureChange
