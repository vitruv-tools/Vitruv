/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.attribute;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.AdditiveEAttributeChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.SubtractiveEAttributeChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.UpdateSingleValuedEFeature;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Replace Single Valued EAttribute</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.attribute.AttributePackage#getReplaceSingleValuedEAttribute()
 * @model TBounds="org.eclipse.emf.ecore.EJavaObject"
 * @generated
 */
public interface ReplaceSingleValuedEAttribute<A extends EObject, T extends Object> extends UpdateSingleValuedEFeature, UpdateEAttribute<A>, SubtractiveEAttributeChange<T>, AdditiveEAttributeChange<T> {
} // ReplaceSingleValuedEAttribute
