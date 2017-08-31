/**
 */
package tools.vitruv.framework.change.echange.eobject;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import tools.vitruv.framework.change.echange.EChange;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Delete EObject</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * *
 * EChange which deletes a EObject from the staging area.
 * The object must be placed in the staging area by another change.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruv.framework.change.echange.eobject.DeleteEObject#getConsequentialRemoveChanges <em>Consequential Remove Changes</em>}</li>
 * </ul>
 *
 * @see tools.vitruv.framework.change.echange.eobject.EobjectPackage#getDeleteEObject()
 * @model
 * @generated
 */
public interface DeleteEObject<A extends EObject> extends EObjectExistenceEChange<A> {

	/**
	 * Returns the value of the '<em><b>Consequential Remove Changes</b></em>' reference list.
	 * The list contents are of type {@link tools.vitruv.framework.change.echange.EChange}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Consequential Remove Changes</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Consequential Remove Changes</em>' reference list.
	 * @see tools.vitruv.framework.change.echange.eobject.EobjectPackage#getDeleteEObject_ConsequentialRemoveChanges()
	 * @model
	 * @generated
	 */
	EList<EChange> getConsequentialRemoveChanges();
} // DeleteEObject
