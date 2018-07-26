/**
 */
package tools.vitruv.framework.change.interaction;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Free Text User Interaction</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruv.framework.change.interaction.FreeTextUserInteraction#getText <em>Text</em>}</li>
 * </ul>
 *
 * @see tools.vitruv.framework.change.interaction.InteractionPackage#getFreeTextUserInteraction()
 * @model
 * @generated
 */
public interface FreeTextUserInteraction extends UserInteractionBase {
	/**
	 * Returns the value of the '<em><b>Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Text</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Text</em>' attribute.
	 * @see #setText(String)
	 * @see tools.vitruv.framework.change.interaction.InteractionPackage#getFreeTextUserInteraction_Text()
	 * @model
	 * @generated
	 */
	String getText();

	/**
	 * Sets the value of the '{@link tools.vitruv.framework.change.interaction.FreeTextUserInteraction#getText <em>Text</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Text</em>' attribute.
	 * @see #getText()
	 * @generated
	 */
	void setText(String value);

} // FreeTextUserInteraction
