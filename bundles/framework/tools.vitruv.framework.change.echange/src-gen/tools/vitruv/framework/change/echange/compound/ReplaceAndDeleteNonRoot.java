/**
 */
package tools.vitruv.framework.change.echange.compound;

import org.eclipse.emf.ecore.EObject;

import tools.vitruv.framework.change.echange.feature.reference.ReplaceSingleValuedEReference;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Replace And Delete Non Root</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * *
 * EChange which replaces an existing EObject with null in a single valued reference and deletes it.
 * <!-- end-model-doc -->
 *
 *
 * @see tools.vitruv.framework.change.echange.compound.CompoundPackage#getReplaceAndDeleteNonRoot()
 * @model
 * @generated
 */
public interface ReplaceAndDeleteNonRoot<A extends EObject, T extends EObject> extends RemoveAndDeleteEObject<T, ReplaceSingleValuedEReference<A, T>> {
} // ReplaceAndDeleteNonRoot
