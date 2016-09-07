/**
 */
package allElementTypes;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Non Root Object Container Helper</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link allElementTypes.NonRootObjectContainerHelper#getNonRootObjectsContainment <em>Non Root Objects Containment</em>}</li>
 * </ul>
 *
 * @see allElementTypes.AllElementTypesPackage#getNonRootObjectContainerHelper()
 * @model
 * @generated
 */
public interface NonRootObjectContainerHelper extends Identified {
	/**
	 * Returns the value of the '<em><b>Non Root Objects Containment</b></em>' containment reference list.
	 * The list contents are of type {@link allElementTypes.NonRoot}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Non Root Objects Containment</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Non Root Objects Containment</em>' containment reference list.
	 * @see allElementTypes.AllElementTypesPackage#getNonRootObjectContainerHelper_NonRootObjectsContainment()
	 * @model containment="true"
	 * @generated
	 */
	EList<NonRoot> getNonRootObjectsContainment();

} // NonRootObjectContainerHelper
