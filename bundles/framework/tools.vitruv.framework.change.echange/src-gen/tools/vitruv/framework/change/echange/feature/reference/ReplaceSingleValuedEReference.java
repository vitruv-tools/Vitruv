/**
 */
package tools.vitruv.framework.change.echange.feature.reference;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;

import tools.vitruv.framework.change.echange.feature.single.ReplaceSingleValuedFeatureEChange;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Replace Single Valued EReference</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see tools.vitruv.framework.change.echange.feature.reference.ReferencePackage#getReplaceSingleValuedEReference()
 * @model ABounds="tools.vitruv.framework.change.echange.feature.reference.EObj" TBounds="tools.vitruv.framework.change.echange.feature.reference.EObj"
 * @generated
 */
public interface ReplaceSingleValuedEReference<A extends EObject, T extends EObject> extends ReplaceSingleValuedFeatureEChange<A, EReference, T>, AdditiveReferenceEChange<A, T>, SubtractiveReferenceEChange<A, T> {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Returns if all proxy EObjects of the change are resolved to concrete EObjects of a resource set.
	 * Needs to be true to apply the change.
	 * @return	All proxy EObjects are resolved to concrete EObjects.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return ((super.isResolved() && (<%com.google.common.base.Objects%>.equal(this.getOldValue(), null) || (!this.getOldValue().eIsProxy()))) && (<%com.google.common.base.Objects%>.equal(this.getNewValue(), null) || (!this.getNewValue().eIsProxy())));'"
	 * @generated
	 */
	boolean isResolved();

} // ReplaceSingleValuedEReference
