/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence;

import org.eclipse.emf.ecore.EStructuralFeature;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>EFeature Correspondence</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Metaclass for cases where a complete feature of an eObject corresponds to a complete feature of another eObject and both features have the same type.
 * This means every value for the feature of the first eObject corresponds to a value for the feature of the second eObject and the other way round.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.EFeatureCorrespondence#getType <em>Type</em>}</li>
 * </ul>
 * </p>
 *
 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.CorrespondencePackage#getEFeatureCorrespondence()
 * @model abstract="true"
 * @generated
 */
public interface EFeatureCorrespondence<T extends EStructuralFeature> extends SameTypeCorrespondence<T> {
	/**
	 * Returns the value of the '<em><b>Type</b></em>' attribute.
	 * The literals are from the enumeration {@link edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.CorrespondenceType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' attribute.
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.CorrespondenceType
	 * @see #setType(CorrespondenceType)
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.CorrespondencePackage#getEFeatureCorrespondence_Type()
	 * @model required="true"
	 * @generated
	 */
	CorrespondenceType getType();

	/**
	 * Sets the value of the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.EFeatureCorrespondence#getType <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' attribute.
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.CorrespondenceType
	 * @see #getType()
	 * @generated
	 */
	void setType(CorrespondenceType value);

} // EFeatureCorrespondence
