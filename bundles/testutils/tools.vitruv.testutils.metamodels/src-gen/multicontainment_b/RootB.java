/**
 */
package multicontainment_b;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Root B</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link multicontainment_b.RootB#getChildrenB1a <em>Children B1a</em>}</li>
 *   <li>{@link multicontainment_b.RootB#getChildrenB1b <em>Children B1b</em>}</li>
 *   <li>{@link multicontainment_b.RootB#getChildrenB2a <em>Children B2a</em>}</li>
 * </ul>
 *
 * @see multicontainment_b.Multicontainment_bPackage#getRootB()
 * @model
 * @generated
 */
public interface RootB extends Identified {
	/**
	 * Returns the value of the '<em><b>Children B1a</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Children B1a</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Children B1a</em>' containment reference.
	 * @see #setChildrenB1a(ChildB1)
	 * @see multicontainment_b.Multicontainment_bPackage#getRootB_ChildrenB1a()
	 * @model containment="true"
	 * @generated
	 */
	ChildB1 getChildrenB1a();

	/**
	 * Sets the value of the '{@link multicontainment_b.RootB#getChildrenB1a <em>Children B1a</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Children B1a</em>' containment reference.
	 * @see #getChildrenB1a()
	 * @generated
	 */
	void setChildrenB1a(ChildB1 value);

	/**
	 * Returns the value of the '<em><b>Children B1b</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Children B1b</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Children B1b</em>' containment reference.
	 * @see #setChildrenB1b(ChildB1)
	 * @see multicontainment_b.Multicontainment_bPackage#getRootB_ChildrenB1b()
	 * @model containment="true"
	 * @generated
	 */
	ChildB1 getChildrenB1b();

	/**
	 * Sets the value of the '{@link multicontainment_b.RootB#getChildrenB1b <em>Children B1b</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Children B1b</em>' containment reference.
	 * @see #getChildrenB1b()
	 * @generated
	 */
	void setChildrenB1b(ChildB1 value);

	/**
	 * Returns the value of the '<em><b>Children B2a</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Children B2a</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Children B2a</em>' containment reference.
	 * @see #setChildrenB2a(ChildB2)
	 * @see multicontainment_b.Multicontainment_bPackage#getRootB_ChildrenB2a()
	 * @model containment="true"
	 * @generated
	 */
	ChildB2 getChildrenB2a();

	/**
	 * Sets the value of the '{@link multicontainment_b.RootB#getChildrenB2a <em>Children B2a</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Children B2a</em>' containment reference.
	 * @see #getChildrenB2a()
	 * @generated
	 */
	void setChildrenB2a(ChildB2 value);

} // RootB
