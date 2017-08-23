/**
 */
package tools.vitruv.framework.change.echange;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>EChange</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * *
 * Abstract change, which can be applied forward or backward, after it was resolved to a specific instance of a model.
 * <!-- end-model-doc -->
 *
 *
 * @see tools.vitruv.framework.change.echange.EChangePackage#getEChange()
 * @model abstract="true"
 * @generated
 */
public interface EChange extends EObject {
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
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return tools.vitruv.framework.change.echange.resolve.ResolutionChecker.isResolved(this);'"
	 * @generated
	 */
	boolean isResolved();

} // EChange
