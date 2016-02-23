/**
 */
package pcm_mockup;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>PInterface</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link pcm_mockup.PInterface#getMethods <em>Methods</em>}</li>
 * </ul>
 *
 * @see pcm_mockup.Pcm_mockupPackage#getPInterface()
 * @model
 * @generated
 */
public interface PInterface extends Identified, PNamedElement {
	/**
	 * Returns the value of the '<em><b>Methods</b></em>' containment reference list.
	 * The list contents are of type {@link pcm_mockup.PMethod}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Methods</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Methods</em>' containment reference list.
	 * @see pcm_mockup.Pcm_mockupPackage#getPInterface_Methods()
	 * @model containment="true"
	 * @generated
	 */
	EList<PMethod> getMethods();

} // PInterface
