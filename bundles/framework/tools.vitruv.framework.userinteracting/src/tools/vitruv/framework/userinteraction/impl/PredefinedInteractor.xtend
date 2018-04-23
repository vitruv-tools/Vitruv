package tools.vitruv.framework.userinteraction.impl

import tools.vitruv.framework.userinteraction.InternalUserInteracting
import java.util.Collection
import tools.vitruv.framework.change.interaction.UserInputBase
import tools.vitruv.framework.userinteraction.MultipleChoiceSelectionDialogBuilder
import tools.vitruv.framework.userinteraction.NotificationDialogBuilder
import java.util.Map
import tools.vitruv.framework.userinteraction.TextInputDialogBuilder
import tools.vitruv.framework.userinteraction.DialogBuilder
import tools.vitruv.framework.userinteraction.WindowModality
import tools.vitruv.framework.userinteraction.MultipleChoiceMultiSelectionDialogBuilder
import tools.vitruv.framework.userinteraction.ConfirmationDialogBuilder
import tools.vitruv.framework.userinteraction.MultipleChoiceSingleSelectionDialogBuilder
import tools.vitruv.framework.userinteraction.impl.TextInputDialog.InputValidator
import tools.vitruv.framework.userinteraction.NotificationType
import tools.vitruv.framework.userinteraction.InputFieldType
import java.util.HashMap
import java.util.function.Function

abstract class PredefinedInteractor implements InternalUserInteracting {
    protected Collection<UserInputBase> userInputs = #[]
    
    def abstract void handleNotification(Map<String, String> dialogProperties)
    
    def abstract boolean handleConfirmation(Map<String, String> dialogProperties)
    
    def abstract String handleFreeText(Map<String, String> dialogProperties)
    
    def abstract int handleSingleSelection(Map<String, String> dialogProperties)
    
    def abstract Collection<Integer> handleMultiSelection(Map<String, String> dialogProperties)
    
    override getUserInputs() {
        return userInputs
    }
    
    override resetUserInputs() {
        userInputs.clear()
    }
    
    override getConfirmationDialogBuilder() {
        return new RecordingConfirmationDummyDialogBuilder(this)
    }
    
    override getMultiSelectionDialogBuilder() {
        return new RecordingMultiSelectionDummyDialogBuilder(this)
    }
    
    override getNotificationDialogBuilder() {
        return new RecordingNotificationDummyDialogBuilder(this)
    }
    
    override getSingleSelectionDialogBuilder() {
        return new RecordingSingleSelectionDummyDialogBuilder(this)
    }
    
    override getTextInputDialogBuilder() {
        return new RecordingTextInputDummyDialogBuilder(this)
    }
    
    override selectURI(String message) {
        // TODO
    }
    
}


abstract class RecordingDummyDialogBuilder<V, T extends DialogBuilder<V, T>> implements DialogBuilder<V, T> {
    protected Map<String, String> dialogProperties = new HashMap()
    protected PredefinedInteractor parentInteractor
    
    new(PredefinedInteractor interactor) {
        parentInteractor = interactor
    }
    
    //def abstract public V startInteraction()
    
    protected def void openDialog() { }
    
    override T title(String title) {
        dialogProperties.put("title", title)
        return this as T
    }
    
    override T windowModality(WindowModality windowModality) {
        dialogProperties.put("window modality", windowModality.name())
        return this as T
    }
    
    override T positiveButtonText(String text) {
        dialogProperties.put("positive button text", text)
        return this as T
    }
    
    override T negativeButtonText(String text) {
        dialogProperties.put("negative button text", text)
        return this as T
    }
    
    override T cancelButtonText(String text) {
        dialogProperties.put("cancel button text", text)
        return this as T
    }
}


class RecordingNotificationDummyDialogBuilder extends RecordingDummyDialogBuilder<Void, NotificationDialogBuilder.OptionalSteps> implements NotificationDialogBuilder,
        NotificationDialogBuilder.OptionalSteps {
    
    new(PredefinedInteractor interactor) {
        super(interactor)
    }
    
    override notificationType(NotificationType notificationType) {
        dialogProperties.put("notification type", notificationType.name())
        return this
    }

    override startInteraction() {
        parentInteractor.handleNotification(dialogProperties)
        return null // notifications don't have any form of user input
    }
    
    override message(String message) {
        dialogProperties.put("message", message)
        return this
    }
}


class RecordingConfirmationDummyDialogBuilder extends RecordingDummyDialogBuilder<Boolean, ConfirmationDialogBuilder.OptionalSteps>
        implements ConfirmationDialogBuilder, ConfirmationDialogBuilder.OptionalSteps {
    
    new(PredefinedInteractor interactor) {
        super(interactor)
    }
    
    override startInteraction() {
        return parentInteractor.handleConfirmation(dialogProperties)
    }
    
    override message(String message) {
        dialogProperties.put("message", message)
        return this
    }
}


class RecordingTextInputDummyDialogBuilder extends RecordingDummyDialogBuilder<String, TextInputDialogBuilder.OptionalSteps>
        implements TextInputDialogBuilder, TextInputDialogBuilder.OptionalSteps {
    
    new(PredefinedInteractor interactor) {
        super(interactor)
    }
    
    override startInteraction() {
        return parentInteractor.handleFreeText(dialogProperties)
    }
    
    override message(String message) {
        dialogProperties.put("message", message)
        return this
    }

    override inputValidator(InputValidator inputValidator) {
        dialogProperties.put("inputValidator", inputValidator.getInvalidInputMessage("[input]"))
        return this
    }

    override inputValidator(Function<String, Boolean> validatorFunction, String invalidInputMessage) {
        dialogProperties.put("inputValidator", invalidInputMessage)
        return this
    }

    override inputFieldType(InputFieldType inputFieldType) {
        dialogProperties.put("inputFieldType", inputFieldType.name())
        return this
    }
}


class RecordingSingleSelectionDummyDialogBuilder extends RecordingDummyDialogBuilder<Integer,
        MultipleChoiceSelectionDialogBuilder.OptionalSteps<Integer>> implements MultipleChoiceSingleSelectionDialogBuilder,
        MultipleChoiceSelectionDialogBuilder.ChoicesStep<Integer>, MultipleChoiceSelectionDialogBuilder.OptionalSteps<Integer> {
    private String[] choices
    
    new(PredefinedInteractor interactor) {
        super(interactor)
    }
    
    override startInteraction() {
        return parentInteractor.handleSingleSelection(dialogProperties)
    }
    
    override message(String message) {
        dialogProperties.put("message", message)
        return this
    }
    
    override choices(String[] choices) {
        this.choices = choices
        dialogProperties.put("choices", choices.toString())
        return this
    }
}


class RecordingMultiSelectionDummyDialogBuilder extends RecordingDummyDialogBuilder<Collection<Integer>,
        MultipleChoiceSelectionDialogBuilder.OptionalSteps<Collection<Integer>>> implements MultipleChoiceMultiSelectionDialogBuilder,
        MultipleChoiceSelectionDialogBuilder.ChoicesStep<Collection<Integer>>, MultipleChoiceSelectionDialogBuilder.OptionalSteps<Collection<Integer>> {
    private String[] choices
    
    new(PredefinedInteractor interactor) {
        super(interactor)
    }
    
    override startInteraction() {
        return parentInteractor.handleMultiSelection(dialogProperties)
    }
    
    override message(String message) {
        dialogProperties.put("message", message)
        return this
    }
    
    override choices(String[] choices) {
        this.choices = choices
        dialogProperties.put("choices", choices.toString())
        return this
    }
}