/**
 */
package pcm_mockup;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Component</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link pcm_mockup.Component#getProvidedInterfaces <em>Provided Interfaces</em>}</li>
 * </ul>
 *
 * @see pcm_mockup.Pcm_mockupPackage#getComponent()
 * @model
 * @generated
 */
public interface Component extends Identified, PNamedElement {

	/**
	 * Returns the value of the '<em><b>Provided Interfaces</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Provided Interfaces</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Provided Interfaces</em>' containment reference.
	 * @see #setProvidedInterfaces(Interface)
	 * @see pcm_mockup.Pcm_mockupPackage#getComponent_ProvidedInterfaces()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
	Interface getProvidedInterfaces();

	/**
	 * Sets the value of the '{@link pcm_mockup.Component#getProvidedInterfaces <em>Provided Interfaces</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Provided Interfaces</em>' containment reference.
	 * @see #getProvidedInterfaces()
	 * @generated
	 */
	void setProvidedInterfaces(Interface value);
} // Component
