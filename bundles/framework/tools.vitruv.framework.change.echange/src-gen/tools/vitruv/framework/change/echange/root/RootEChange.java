/**
 */
package tools.vitruv.framework.change.echange.root;

import tools.vitruv.framework.change.echange.AtomicEChange;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>EChange</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruv.framework.change.echange.root.RootEChange#getUri <em>Uri</em>}</li>
 * </ul>
 *
 * @see tools.vitruv.framework.change.echange.root.RootPackage#getRootEChange()
 * @model abstract="true"
 * @generated
 */
public interface RootEChange extends AtomicEChange {
	/**
	 * Returns the value of the '<em><b>Uri</b></em>' attribute.
	 * The default value is <code>""</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Uri</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Uri</em>' attribute.
	 * @see #setUri(String)
	 * @see tools.vitruv.framework.change.echange.root.RootPackage#getRootEChange_Uri()
	 * @model default="" unique="false" required="true"
	 * @generated
	 */
	String getUri();

	/**
	 * Sets the value of the '{@link tools.vitruv.framework.change.echange.root.RootEChange#getUri <em>Uri</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Uri</em>' attribute.
	 * @see #getUri()
	 * @generated
	 */
	void setUri(String value);

} // RootEChange
