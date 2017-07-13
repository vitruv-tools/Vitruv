/**
 */
package tools.vitruv.framework.change.echange.feature.reference;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;

import tools.vitruv.framework.change.echange.feature.list.InsertInListEChange;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Insert EReference</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * *
 * EChange which inserts an EObject into a many valued reference.
 * If the reference is a containment reference, the inserted object will be taken from the staging area.
 * There it must be placed by a {@link CreateEObject} EChange or by removing it from another reference.
 * <!-- end-model-doc -->
 *
 *
 * @see tools.vitruv.framework.change.echange.feature.reference.ReferencePackage#getInsertEReference()
 * @model
 * @generated
 */
public interface InsertEReference<A extends EObject, T extends EObject> extends InsertInListEChange<A, EReference, T>, AdditiveReferenceEChange<A, T> {

} // InsertEReference
