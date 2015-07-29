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
 * </p>
 * <ul>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.EFeatureCorrespondence#getType <em>Type</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.EFeatureCorrespondence#getFeatureA <em>Feature A</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.EFeatureCorrespondence#getFeatureB <em>Feature B</em>}</li>
 * </ul>
 *
 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.CorrespondencePackage#getEFeatureCorrespondence()
 * @model abstract="true"
 * @generated
 */
public interface EFeatureCorrespondence<TFeature extends EStructuralFeature> extends SameTypeCorrespondence {
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

	/**
	 * Returns the value of the '<em><b>Feature A</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Feature A</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Feature A</em>' reference.
	 * @see #setFeatureA(EStructuralFeature)
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.CorrespondencePackage#getEFeatureCorrespondence_FeatureA()
	 * @model required="true"
	 * @generated
	 */
	TFeature getFeatureA();

	/**
	 * Sets the value of the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.EFeatureCorrespondence#getFeatureA <em>Feature A</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Feature A</em>' reference.
	 * @see #getFeatureA()
	 * @generated
	 */
	void setFeatureA(TFeature value);

	/**
	 * Returns the value of the '<em><b>Feature B</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Feature B</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Feature B</em>' reference.
	 * @see #setFeatureB(EStructuralFeature)
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.CorrespondencePackage#getEFeatureCorrespondence_FeatureB()
	 * @model required="true"
	 * @generated
	 */
	TFeature getFeatureB();

	/**
	 * Sets the value of the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.EFeatureCorrespondence#getFeatureB <em>Feature B</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Feature B</em>' reference.
	 * @see #getFeatureB()
	 * @generated
	 */
	void setFeatureB(TFeature value);

} // EFeatureCorrespondence
