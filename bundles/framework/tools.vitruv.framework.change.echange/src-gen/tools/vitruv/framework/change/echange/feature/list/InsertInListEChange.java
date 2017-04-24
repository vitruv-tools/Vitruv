/**
 */
package tools.vitruv.framework.change.echange.feature.list;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

import tools.vitruv.framework.change.echange.AdditiveEChange;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Insert In List EChange</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * *
 * Abstract EChange which inserts a value into an EList.
 * <!-- end-model-doc -->
 *
 *
 * @see tools.vitruv.framework.change.echange.feature.list.ListPackage#getInsertInListEChange()
 * @model abstract="true" ABounds="tools.vitruv.framework.change.echange.feature.list.EObj" FBounds="tools.vitruv.framework.change.echange.feature.list.EFeat" TBounds="org.eclipse.emf.ecore.EJavaObject"
 * @generated
 */
public interface InsertInListEChange<A extends EObject, F extends EStructuralFeature, T extends Object> extends UpdateSingleListEntryEChange<A, F>, AdditiveEChange<T> {
} // InsertInListEChange
