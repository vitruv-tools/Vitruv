/**
 */
package tools.vitruv.framework.change.echange.feature;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

import tools.vitruv.framework.change.echange.EChange;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>EChange</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * *
 * Abstract EChange which changes an attribute or reference of an EObject.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruv.framework.change.echange.feature.FeatureEChange#getAffectedFeature <em>Affected Feature</em>}</li>
 *   <li>{@link tools.vitruv.framework.change.echange.feature.FeatureEChange#getAffectedEObject <em>Affected EObject</em>}</li>
 *   <li>{@link tools.vitruv.framework.change.echange.feature.FeatureEChange#getAffectedEObjectID <em>Affected EObject ID</em>}</li>
 * </ul>
 *
 * @see tools.vitruv.framework.change.echange.feature.FeaturePackage#getFeatureEChange()
 * @model abstract="true"
 * @generated
 */
public interface FeatureEChange<A extends EObject, F extends EStructuralFeature> extends EChange {
	/**
	 * Returns the value of the '<em><b>Affected Feature</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * The affected attribute or reference.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Affected Feature</em>' reference.
	 * @see #setAffectedFeature(EStructuralFeature)
	 * @see tools.vitruv.framework.change.echange.feature.FeaturePackage#getFeatureEChange_AffectedFeature()
	 * @model required="true"
	 * @generated
	 */
	F getAffectedFeature();

	/**
	 * Sets the value of the '{@link tools.vitruv.framework.change.echange.feature.FeatureEChange#getAffectedFeature <em>Affected Feature</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Affected Feature</em>' reference.
	 * @see #getAffectedFeature()
	 * @generated
	 */
	void setAffectedFeature(F value);

	/**
	 * Returns the value of the '<em><b>Affected EObject</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * The affected EObject of the change.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Affected EObject</em>' reference.
	 * @see #setAffectedEObject(EObject)
	 * @see tools.vitruv.framework.change.echange.feature.FeaturePackage#getFeatureEChange_AffectedEObject()
	 * @model required="true"
	 * @generated
	 */
	A getAffectedEObject();

	/**
	 * Sets the value of the '{@link tools.vitruv.framework.change.echange.feature.FeatureEChange#getAffectedEObject <em>Affected EObject</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Affected EObject</em>' reference.
	 * @see #getAffectedEObject()
	 * @generated
	 */
	void setAffectedEObject(A value);

	/**
	 * Returns the value of the '<em><b>Affected EObject ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Affected EObject ID</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Affected EObject ID</em>' attribute.
	 * @see #setAffectedEObjectID(String)
	 * @see tools.vitruv.framework.change.echange.feature.FeaturePackage#getFeatureEChange_AffectedEObjectID()
	 * @model dataType="tools.vitruv.framework.change.uuid.Uuid"
	 * @generated
	 */
	String getAffectedEObjectID();

	/**
	 * Sets the value of the '{@link tools.vitruv.framework.change.echange.feature.FeatureEChange#getAffectedEObjectID <em>Affected EObject ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Affected EObject ID</em>' attribute.
	 * @see #getAffectedEObjectID()
	 * @generated
	 */
	void setAffectedEObjectID(String value);

} // FeatureEChange
