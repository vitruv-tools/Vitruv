/**
 */
package tools.vitruv.framework.change.uuid;

import org.eclipse.emf.common.util.EMap;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>To EObject Repository</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruv.framework.change.uuid.UuidToEObjectRepository#getUuidToEObject <em>Uuid To EObject</em>}</li>
 *   <li>{@link tools.vitruv.framework.change.uuid.UuidToEObjectRepository#getEObjectToUuid <em>EObject To Uuid</em>}</li>
 * </ul>
 *
 * @see tools.vitruv.framework.change.uuid.UuidPackage#getUuidToEObjectRepository()
 * @model
 * @generated
 */
public interface UuidToEObjectRepository extends EObject {
	/**
	 * Returns the value of the '<em><b>Uuid To EObject</b></em>' map.
	 * The key is of type {@link java.lang.String},
	 * and the value is of type {@link org.eclipse.emf.ecore.EObject},
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Uuid To EObject</em>' map isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Uuid To EObject</em>' map.
	 * @see tools.vitruv.framework.change.uuid.UuidPackage#getUuidToEObjectRepository_UuidToEObject()
	 * @model mapType="tools.vitruv.framework.change.uuid.UuidToEObjectMapEntry&lt;tools.vitruv.framework.change.uuid.Uuid, org.eclipse.emf.ecore.EObject&gt;"
	 * @generated
	 */
	EMap<String, EObject> getUuidToEObject();

	/**
	 * Returns the value of the '<em><b>EObject To Uuid</b></em>' map.
	 * The key is of type {@link org.eclipse.emf.ecore.EObject},
	 * and the value is of type {@link java.lang.String},
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>EObject To Uuid</em>' map isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>EObject To Uuid</em>' map.
	 * @see tools.vitruv.framework.change.uuid.UuidPackage#getUuidToEObjectRepository_EObjectToUuid()
	 * @model mapType="tools.vitruv.framework.change.uuid.EObjectToUuidMapEntry&lt;org.eclipse.emf.ecore.EObject, tools.vitruv.framework.change.uuid.Uuid&gt;"
	 * @generated
	 */
	EMap<EObject, String> getEObjectToUuid();

} // UuidToEObjectRepository
