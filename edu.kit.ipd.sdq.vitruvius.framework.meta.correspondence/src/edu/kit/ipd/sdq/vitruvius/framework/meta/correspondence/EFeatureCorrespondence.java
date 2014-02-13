/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence;

import org.eclipse.emf.ecore.EStructuralFeature;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>EFeature Correspondence</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.EFeatureCorrespondence#getMappedFeature <em>Mapped Feature</em>}</li>
 * </ul>
 * </p>
 *
 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.CorrespondencePackage#getEFeatureCorrespondence()
 * @model abstract="true" TBounds="org.eclipse.emf.ecore.EJavaObject"
 * @generated
 */
public interface EFeatureCorrespondence<T extends Object, TFeature extends EStructuralFeature> extends SameTypeCorrespondence<T> {
	/**
	 * Returns the value of the '<em><b>Mapped Feature</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Mapped Feature</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Mapped Feature</em>' reference.
	 * @see #setMappedFeature(EAttributeCorrespondence)
	 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.CorrespondencePackage#getEFeatureCorrespondence_MappedFeature()
	 * @model
	 * @generated
	 */
	EAttributeCorrespondence getMappedFeature();

	/**
	 * Sets the value of the '{@link edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.EFeatureCorrespondence#getMappedFeature <em>Mapped Feature</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Mapped Feature</em>' reference.
	 * @see #getMappedFeature()
	 * @generated
	 */
	void setMappedFeature(EAttributeCorrespondence value);

} // EFeatureCorrespondence
