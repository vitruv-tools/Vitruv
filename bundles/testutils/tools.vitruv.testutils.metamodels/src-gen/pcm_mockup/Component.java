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
 *   <li>{@link pcm_mockup.Component#getProvidedInterface <em>Provided Interface</em>}</li>
 *   <li>{@link pcm_mockup.Component#getRepositoryFactor <em>Repository Factor</em>}</li>
 *   <li>{@link pcm_mockup.Component#getComponentExclusive <em>Component Exclusive</em>}</li>
 * </ul>
 *
 * @see pcm_mockup.Pcm_mockupPackage#getComponent()
 * @model
 * @generated
 */
public interface Component extends Identified, PNamedElement {

	/**
	 * Returns the value of the '<em><b>Provided Interface</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Provided Interface</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Provided Interface</em>' reference.
	 * @see #setProvidedInterface(PInterface)
	 * @see pcm_mockup.Pcm_mockupPackage#getComponent_ProvidedInterface()
	 * @model ordered="false"
	 * @generated
	 */
	PInterface getProvidedInterface();

	/**
	 * Sets the value of the '{@link pcm_mockup.Component#getProvidedInterface <em>Provided Interface</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Provided Interface</em>' reference.
	 * @see #getProvidedInterface()
	 * @generated
	 */
	void setProvidedInterface(PInterface value);

	/**
	 * Returns the value of the '<em><b>Repository Factor</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Repository Factor</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Repository Factor</em>' attribute.
	 * @see #setRepositoryFactor(int)
	 * @see pcm_mockup.Pcm_mockupPackage#getComponent_RepositoryFactor()
	 * @model required="true"
	 * @generated
	 */
	int getRepositoryFactor();

	/**
	 * Sets the value of the '{@link pcm_mockup.Component#getRepositoryFactor <em>Repository Factor</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Repository Factor</em>' attribute.
	 * @see #getRepositoryFactor()
	 * @generated
	 */
	void setRepositoryFactor(int value);

	/**
	 * Returns the value of the '<em><b>Component Exclusive</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Component Exclusive</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Component Exclusive</em>' attribute.
	 * @see #setComponentExclusive(String)
	 * @see pcm_mockup.Pcm_mockupPackage#getComponent_ComponentExclusive()
	 * @model
	 * @generated
	 */
	String getComponentExclusive();

	/**
	 * Sets the value of the '{@link pcm_mockup.Component#getComponentExclusive <em>Component Exclusive</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Component Exclusive</em>' attribute.
	 * @see #getComponentExclusive()
	 * @generated
	 */
	void setComponentExclusive(String value);
} // Component
