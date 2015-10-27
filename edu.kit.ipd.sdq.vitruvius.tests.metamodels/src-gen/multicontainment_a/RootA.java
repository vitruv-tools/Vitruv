/**
 */
package multicontainment_a;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Root A</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link multicontainment_a.RootA#getChildrenA1a <em>Children A1a</em>}</li>
 *   <li>{@link multicontainment_a.RootA#getChildrenA1b <em>Children A1b</em>}</li>
 *   <li>{@link multicontainment_a.RootA#getChildrenA2a <em>Children A2a</em>}</li>
 * </ul>
 *
 * @see multicontainment_a.Multicontainment_aPackage#getRootA()
 * @model
 * @generated
 */
public interface RootA extends Identified {
	/**
	 * Returns the value of the '<em><b>Children A1a</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Children A1a</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Children A1a</em>' containment reference.
	 * @see #setChildrenA1a(ChildA1)
	 * @see multicontainment_a.Multicontainment_aPackage#getRootA_ChildrenA1a()
	 * @model containment="true"
	 * @generated
	 */
	ChildA1 getChildrenA1a();

	/**
	 * Sets the value of the '{@link multicontainment_a.RootA#getChildrenA1a <em>Children A1a</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Children A1a</em>' containment reference.
	 * @see #getChildrenA1a()
	 * @generated
	 */
	void setChildrenA1a(ChildA1 value);

	/**
	 * Returns the value of the '<em><b>Children A1b</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Children A1b</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Children A1b</em>' containment reference.
	 * @see #setChildrenA1b(ChildA1)
	 * @see multicontainment_a.Multicontainment_aPackage#getRootA_ChildrenA1b()
	 * @model containment="true"
	 * @generated
	 */
	ChildA1 getChildrenA1b();

	/**
	 * Sets the value of the '{@link multicontainment_a.RootA#getChildrenA1b <em>Children A1b</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Children A1b</em>' containment reference.
	 * @see #getChildrenA1b()
	 * @generated
	 */
	void setChildrenA1b(ChildA1 value);

	/**
	 * Returns the value of the '<em><b>Children A2a</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Children A2a</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Children A2a</em>' containment reference.
	 * @see #setChildrenA2a(ChildA2)
	 * @see multicontainment_a.Multicontainment_aPackage#getRootA_ChildrenA2a()
	 * @model containment="true"
	 * @generated
	 */
	ChildA2 getChildrenA2a();

	/**
	 * Sets the value of the '{@link multicontainment_a.RootA#getChildrenA2a <em>Children A2a</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Children A2a</em>' containment reference.
	 * @see #getChildrenA2a()
	 * @generated
	 */
	void setChildrenA2a(ChildA2 value);

} // RootA
