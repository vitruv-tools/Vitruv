/**
 */
package tools.vitruv.framework.change.echange.feature.attribute;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;

import tools.vitruv.framework.change.echange.feature.list.RemoveFromListEChange;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Remove EAttribute Value</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * *
 * EChange which removes the value of a many valued attribute.
 * <!-- end-model-doc -->
 *
 *
 * @see tools.vitruv.framework.change.echange.feature.attribute.AttributePackage#getRemoveEAttributeValue()
 * @model TBounds="org.eclipse.emf.ecore.EJavaObject"
 * @generated
 */
public interface RemoveEAttributeValue<A extends EObject, T extends Object> extends RemoveFromListEChange<A, EAttribute, T>, SubtractiveAttributeEChange<A, T> {
} // RemoveEAttributeValue
