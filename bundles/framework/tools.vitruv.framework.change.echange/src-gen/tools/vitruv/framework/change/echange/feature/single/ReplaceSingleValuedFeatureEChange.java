/**
 */
package tools.vitruv.framework.change.echange.feature.single;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

import tools.vitruv.framework.change.echange.AdditiveEChange;
import tools.vitruv.framework.change.echange.SubtractiveEChange;

import tools.vitruv.framework.change.echange.feature.UpdateSingleValuedFeatureEChange;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Replace Single Valued Feature EChange</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see tools.vitruv.framework.change.echange.feature.single.SinglePackage#getReplaceSingleValuedFeatureEChange()
 * @model abstract="true" TBounds="org.eclipse.emf.ecore.EJavaObject"
 * @generated
 */
public interface ReplaceSingleValuedFeatureEChange<A extends EObject, F extends EStructuralFeature, T extends Object> extends UpdateSingleValuedFeatureEChange<A, F>, AdditiveEChange<T>, SubtractiveEChange<T> {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return !java.util.Objects.equals(getOldValue(), getAffectedFeature().getDefaultValue());'"
	 * @generated
	 */
	boolean isFromNonDefaultValue();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return !java.util.Objects.equals(getNewValue(), getAffectedFeature().getDefaultValue());'"
	 * @generated
	 */
	boolean isToNonDefaultValue();

} // ReplaceSingleValuedFeatureEChange
