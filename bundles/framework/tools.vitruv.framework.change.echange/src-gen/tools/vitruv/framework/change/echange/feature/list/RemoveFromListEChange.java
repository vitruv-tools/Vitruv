/**
 */
package tools.vitruv.framework.change.echange.feature.list;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

import tools.vitruv.framework.change.echange.SubtractiveEChange;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Remove From List EChange</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * *
 * Abstract EChange which removes a value from an EList.
 * <!-- end-model-doc -->
 *
 *
 * @see tools.vitruv.framework.change.echange.feature.list.ListPackage#getRemoveFromListEChange()
 * @model abstract="true" ABounds="tools.vitruv.framework.change.echange.feature.list.EObj" FBounds="tools.vitruv.framework.change.echange.feature.list.EFeat" TBounds="org.eclipse.emf.ecore.EJavaObject"
 * @generated
 */
public interface RemoveFromListEChange<A extends EObject, F extends EStructuralFeature, T extends Object> extends UpdateSingleListEntryEChange<A, F>, SubtractiveEChange<T> {
} // RemoveFromListEChange
