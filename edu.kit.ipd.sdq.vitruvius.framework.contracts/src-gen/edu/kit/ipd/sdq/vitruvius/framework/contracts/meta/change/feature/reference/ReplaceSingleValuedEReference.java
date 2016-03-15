/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.AdditiveEReferenceChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.SubtractiveEReferenceChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.UpdateSingleValuedEFeature;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Replace Single Valued EReference</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.ReferencePackage#getReplaceSingleValuedEReference()
 * @model
 * @generated
 */
public interface ReplaceSingleValuedEReference<A extends EObject, T extends EObject> extends UpdateSingleValuedEFeature, UpdateEReference<A>, SubtractiveEReferenceChange<T>, AdditiveEReferenceChange<T> {
} // ReplaceSingleValuedEReference
