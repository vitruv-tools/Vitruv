package tools.vitruv.change.interaction;

import tools.vitruv.change.interaction.builder.ConfirmationInteractionBuilder;
import tools.vitruv.change.interaction.builder.MultipleChoiceMultiSelectionInteractionBuilder;
import tools.vitruv.change.interaction.builder.MultipleChoiceSingleSelectionInteractionBuilder;
import tools.vitruv.change.interaction.builder.NotificationInteractionBuilder;
import tools.vitruv.change.interaction.builder.TextInputInteractionBuilder;

/**
 * Central interface providing dialog builders to build different types of dialogs to notify the
 * user or get input in different forms.
 *
 * @author Dominik Klooz
 * @author Heiko Klare
 */
// TODO Rename all "dialog" to "Interaction" -> also adapt application projects
public interface UserInteractor {

  /**
   * get a builder for notification dialogs.
   *
   * @return a {@link NotificationInteractionBuilder} used to configure, build and show notification
   *     dialogs to inform the user about something.
   */
  NotificationInteractionBuilder getNotificationDialogBuilder();

  /**
   * get a builder for confirmation dialogs.
   *
   * @return a {@link ConfirmationInteractionBuilder} used to configure, build and show confirmation
   *     dialogs to prompt the user to answer a question in the positive or negative.
   */
  ConfirmationInteractionBuilder getConfirmationDialogBuilder();

  /**
   * get a builder for text input dialogs.
   *
   * @return a {@link TextInputInteractionBuilder} used to configure, build and show text input
   *     dialogs to prompt the user to input free text optionally restricted by a {@link
   *     tools.vitruv.change.interaction.UserInteractionOptions.InputValidator}. Can be configured
   *     to take single- or multi-line input.
   */
  TextInputInteractionBuilder getTextInputDialogBuilder();

  /**
   * get a builder for multiple choice dialogs.
   *
   * @return a {@link MultipleChoiceSingleSelectionInteractionBuilder} used to configure, build and
   *     show multiple choice input dialogs to prompt the user to choose from a list of choices.
   *     This one allows the user to select one single item.
   */
  MultipleChoiceSingleSelectionInteractionBuilder getSingleSelectionDialogBuilder();

  /**
   * get a builder for multiple choice dialogs.
   *
   * @return a {@link MultipleChoiceMultiSelectionInteractionBuilder} used to configure, build and
   *     show multiple choice input dialogs to prompt the user to choose from a list of choices.
   *     This one allows the user to select multiple items.
   */
  MultipleChoiceMultiSelectionInteractionBuilder getMultiSelectionDialogBuilder();
}
