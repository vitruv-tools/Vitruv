/**
 */
package tools.vitruv.framework.change.interaction;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Multiple Choice Multi Selection User Interaction</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruv.framework.change.interaction.MultipleChoiceMultiSelectionUserInteraction#getSelectedIndices <em>Selected Indices</em>}</li>
 * </ul>
 *
 * @see tools.vitruv.framework.change.interaction.InteractionPackage#getMultipleChoiceMultiSelectionUserInteraction()
 * @model
 * @generated
 */
public interface MultipleChoiceMultiSelectionUserInteraction extends MultipleChoiceSelectionInteractionBase {
	/**
	 * Returns the value of the '<em><b>Selected Indices</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.Integer}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Selected Indices</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Selected Indices</em>' attribute list.
	 * @see tools.vitruv.framework.change.interaction.InteractionPackage#getMultipleChoiceMultiSelectionUserInteraction_SelectedIndices()
	 * @model
	 * @generated
	 */
	EList<Integer> getSelectedIndices();

} // MultipleChoiceMultiSelectionUserInteraction
