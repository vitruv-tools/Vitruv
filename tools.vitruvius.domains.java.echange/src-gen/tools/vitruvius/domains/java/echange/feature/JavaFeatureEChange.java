/**
 */
package tools.vitruvius.domains.java.echange.feature;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

import tools.vitruvius.framework.change.echange.feature.FeatureEChange;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Java Feature EChange</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruvius.domains.java.echange.feature.JavaFeatureEChange#getOldAffectedEObject <em>Old Affected EObject</em>}</li>
 * </ul>
 *
 * @see tools.vitruvius.domains.java.echange.feature.FeaturePackage#getJavaFeatureEChange()
 * @model
 * @generated
 */
public interface JavaFeatureEChange<A extends EObject, F extends EStructuralFeature> extends FeatureEChange<A, F> {
	/**
	 * Returns the value of the '<em><b>Old Affected EObject</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Old Affected EObject</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Old Affected EObject</em>' reference.
	 * @see #setOldAffectedEObject(EObject)
	 * @see tools.vitruvius.domains.java.echange.feature.FeaturePackage#getJavaFeatureEChange_OldAffectedEObject()
	 * @model required="true"
	 * @generated
	 */
	A getOldAffectedEObject();

	/**
	 * Sets the value of the '{@link tools.vitruvius.domains.java.echange.feature.JavaFeatureEChange#getOldAffectedEObject <em>Old Affected EObject</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Old Affected EObject</em>' reference.
	 * @see #getOldAffectedEObject()
	 * @generated
	 */
	void setOldAffectedEObject(A value);

} // JavaFeatureEChange
