/**
 */
package tools.vitruv.framework.change.echange.compound;

import org.eclipse.emf.ecore.EObject;

import tools.vitruv.framework.change.echange.feature.reference.ReplaceSingleValuedEReference;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Create And Replace Non Root</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * *
 * EChange which creates a new EObject and replaces null in a single valued reference.
 * <!-- end-model-doc -->
 *
 *
 * @see tools.vitruv.framework.change.echange.compound.CompoundPackage#getCreateAndReplaceNonRoot()
 * @model
 * @generated
 */
public interface CreateAndReplaceNonRoot<A extends EObject, T extends EObject> extends CreateAndInsertEObject<T, ReplaceSingleValuedEReference<A, T>> {
} // CreateAndReplaceNonRoot
