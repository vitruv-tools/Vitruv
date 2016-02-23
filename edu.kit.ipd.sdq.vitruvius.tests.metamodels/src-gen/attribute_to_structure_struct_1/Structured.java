/**
 */
package attribute_to_structure_struct_1;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Structured</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link attribute_to_structure_struct_1.Structured#getName <em>Name</em>}</li>
 *   <li>{@link attribute_to_structure_struct_1.Structured#getIntContainer <em>Int Container</em>}</li>
 *   <li>{@link attribute_to_structure_struct_1.Structured#getStrContainer <em>Str Container</em>}</li>
 *   <li>{@link attribute_to_structure_struct_1.Structured#getFloatContainer <em>Float Container</em>}</li>
 * </ul>
 *
 * @see attribute_to_structure_struct_1.Attribute_to_structure_struct_1Package#getStructured()
 * @model
 * @generated
 */
public interface Structured extends Identified {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see attribute_to_structure_struct_1.Attribute_to_structure_struct_1Package#getStructured_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link attribute_to_structure_struct_1.Structured#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Int Container</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Int Container</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Int Container</em>' containment reference.
	 * @see #setIntContainer(IntContainer)
	 * @see attribute_to_structure_struct_1.Attribute_to_structure_struct_1Package#getStructured_IntContainer()
	 * @model containment="true"
	 * @generated
	 */
	IntContainer getIntContainer();

	/**
	 * Sets the value of the '{@link attribute_to_structure_struct_1.Structured#getIntContainer <em>Int Container</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Int Container</em>' containment reference.
	 * @see #getIntContainer()
	 * @generated
	 */
	void setIntContainer(IntContainer value);

	/**
	 * Returns the value of the '<em><b>Str Container</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Str Container</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Str Container</em>' containment reference.
	 * @see #setStrContainer(StrContainer)
	 * @see attribute_to_structure_struct_1.Attribute_to_structure_struct_1Package#getStructured_StrContainer()
	 * @model containment="true"
	 * @generated
	 */
	StrContainer getStrContainer();

	/**
	 * Sets the value of the '{@link attribute_to_structure_struct_1.Structured#getStrContainer <em>Str Container</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Str Container</em>' containment reference.
	 * @see #getStrContainer()
	 * @generated
	 */
	void setStrContainer(StrContainer value);

	/**
	 * Returns the value of the '<em><b>Float Container</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Float Container</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Float Container</em>' containment reference.
	 * @see #setFloatContainer(FloatContainer)
	 * @see attribute_to_structure_struct_1.Attribute_to_structure_struct_1Package#getStructured_FloatContainer()
	 * @model containment="true"
	 * @generated
	 */
	FloatContainer getFloatContainer();

	/**
	 * Sets the value of the '{@link attribute_to_structure_struct_1.Structured#getFloatContainer <em>Float Container</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Float Container</em>' containment reference.
	 * @see #getFloatContainer()
	 * @generated
	 */
	void setFloatContainer(FloatContainer value);

} // Structured
