/**
 */
package tools.vitruv.framework.change.interaction;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Confirmation User Interaction</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruv.framework.change.interaction.ConfirmationUserInteraction#isConfirmed <em>Confirmed</em>}</li>
 * </ul>
 *
 * @see tools.vitruv.framework.change.interaction.InteractionPackage#getConfirmationUserInteraction()
 * @model
 * @generated
 */
public interface ConfirmationUserInteraction extends UserInteractionBase {
	/**
	 * Returns the value of the '<em><b>Confirmed</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Confirmed</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Confirmed</em>' attribute.
	 * @see #setConfirmed(boolean)
	 * @see tools.vitruv.framework.change.interaction.InteractionPackage#getConfirmationUserInteraction_Confirmed()
	 * @model
	 * @generated
	 */
	boolean isConfirmed();

	/**
	 * Sets the value of the '{@link tools.vitruv.framework.change.interaction.ConfirmationUserInteraction#isConfirmed <em>Confirmed</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Confirmed</em>' attribute.
	 * @see #isConfirmed()
	 * @generated
	 */
	void setConfirmed(boolean value);

} // ConfirmationUserInteraction
