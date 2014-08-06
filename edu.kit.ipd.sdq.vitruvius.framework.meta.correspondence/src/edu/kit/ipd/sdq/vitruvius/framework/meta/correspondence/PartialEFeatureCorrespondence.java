/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Partial EFeature Correspondence</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Metaclass for cases where a feature of an eObject corresponds partially to a feature of another eObject and both features have the same type.
 * This means that at most one value for the feature of the first eObject may but does not need to correspond to at most one value for the feature of the second eObject and the other way round.
 * <!-- end-model-doc -->
 *
 *
 * @see edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.CorrespondencePackage#getPartialEFeatureCorrespondence()
 * @model abstract="true" TValueBounds="org.eclipse.emf.ecore.EJavaObject"
 * @generated
 */
public interface PartialEFeatureCorrespondence<TValue extends Object> extends EObject {
} // PartialEFeatureCorrespondence
