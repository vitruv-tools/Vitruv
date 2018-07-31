/**
 */
package tools.vitruv.framework.change.interaction;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Multiple Choice Selection Interaction Base</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruv.framework.change.interaction.MultipleChoiceSelectionInteractionBase#getChoices <em>Choices</em>}</li>
 * </ul>
 *
 * @see tools.vitruv.framework.change.interaction.InteractionPackage#getMultipleChoiceSelectionInteractionBase()
 * @model abstract="true"
 * @generated
 */
public interface MultipleChoiceSelectionInteractionBase extends UserInteractionBase {
	/**
	 * Returns the value of the '<em><b>Choices</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Choices</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Choices</em>' attribute list.
	 * @see #isSetChoices()
	 * @see #unsetChoices()
	 * @see tools.vitruv.framework.change.interaction.InteractionPackage#getMultipleChoiceSelectionInteractionBase_Choices()
	 * @model unsettable="true"
	 * @generated
	 */
	EList<String> getChoices();

	/**
	 * Unsets the value of the '{@link tools.vitruv.framework.change.interaction.MultipleChoiceSelectionInteractionBase#getChoices <em>Choices</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetChoices()
	 * @see #getChoices()
	 * @generated
	 */
	void unsetChoices();

	/**
	 * Returns whether the value of the '{@link tools.vitruv.framework.change.interaction.MultipleChoiceSelectionInteractionBase#getChoices <em>Choices</em>}' attribute list is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Choices</em>' attribute list is set.
	 * @see #unsetChoices()
	 * @see #getChoices()
	 * @generated
	 */
	boolean isSetChoices();

} // MultipleChoiceSelectionInteractionBase
