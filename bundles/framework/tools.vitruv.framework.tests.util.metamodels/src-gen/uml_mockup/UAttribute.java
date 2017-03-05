/**
 */
package uml_mockup;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>UAttribute</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link uml_mockup.UAttribute#getAttributeName <em>Attribute Name</em>}</li>
 * </ul>
 *
 * @see uml_mockup.Uml_mockupPackage#getUAttribute()
 * @model
 * @generated
 */
public interface UAttribute extends EObject {
	/**
	 * Returns the value of the '<em><b>Attribute Name</b></em>' attribute.
	 * The default value is <code>""</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Attribute Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Attribute Name</em>' attribute.
	 * @see #isSetAttributeName()
	 * @see #unsetAttributeName()
	 * @see #setAttributeName(String)
	 * @see uml_mockup.Uml_mockupPackage#getUAttribute_AttributeName()
	 * @model default="" unsettable="true"
	 * @generated
	 */
	String getAttributeName();

	/**
	 * Sets the value of the '{@link uml_mockup.UAttribute#getAttributeName <em>Attribute Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Attribute Name</em>' attribute.
	 * @see #isSetAttributeName()
	 * @see #unsetAttributeName()
	 * @see #getAttributeName()
	 * @generated
	 */
	void setAttributeName(String value);

	/**
	 * Unsets the value of the '{@link uml_mockup.UAttribute#getAttributeName <em>Attribute Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetAttributeName()
	 * @see #getAttributeName()
	 * @see #setAttributeName(String)
	 * @generated
	 */
	void unsetAttributeName();

	/**
	 * Returns whether the value of the '{@link uml_mockup.UAttribute#getAttributeName <em>Attribute Name</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Attribute Name</em>' attribute is set.
	 * @see #unsetAttributeName()
	 * @see #getAttributeName()
	 * @see #setAttributeName(String)
	 * @generated
	 */
	boolean isSetAttributeName();

} // UAttribute
