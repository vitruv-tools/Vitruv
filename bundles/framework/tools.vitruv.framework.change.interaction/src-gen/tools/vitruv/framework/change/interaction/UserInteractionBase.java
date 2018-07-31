/**
 */
package tools.vitruv.framework.change.interaction;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>User Interaction Base</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruv.framework.change.interaction.UserInteractionBase#getMessage <em>Message</em>}</li>
 * </ul>
 *
 * @see tools.vitruv.framework.change.interaction.InteractionPackage#getUserInteractionBase()
 * @model abstract="true"
 * @generated
 */
public interface UserInteractionBase extends EObject {
	/**
	 * Returns the value of the '<em><b>Message</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Message</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Message</em>' attribute.
	 * @see #isSetMessage()
	 * @see #unsetMessage()
	 * @see #setMessage(String)
	 * @see tools.vitruv.framework.change.interaction.InteractionPackage#getUserInteractionBase_Message()
	 * @model unsettable="true"
	 * @generated
	 */
	String getMessage();

	/**
	 * Sets the value of the '{@link tools.vitruv.framework.change.interaction.UserInteractionBase#getMessage <em>Message</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Message</em>' attribute.
	 * @see #isSetMessage()
	 * @see #unsetMessage()
	 * @see #getMessage()
	 * @generated
	 */
	void setMessage(String value);

	/**
	 * Unsets the value of the '{@link tools.vitruv.framework.change.interaction.UserInteractionBase#getMessage <em>Message</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetMessage()
	 * @see #getMessage()
	 * @see #setMessage(String)
	 * @generated
	 */
	void unsetMessage();

	/**
	 * Returns whether the value of the '{@link tools.vitruv.framework.change.interaction.UserInteractionBase#getMessage <em>Message</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Message</em>' attribute is set.
	 * @see #unsetMessage()
	 * @see #getMessage()
	 * @see #setMessage(String)
	 * @generated
	 */
	boolean isSetMessage();

} // UserInteractionBase
