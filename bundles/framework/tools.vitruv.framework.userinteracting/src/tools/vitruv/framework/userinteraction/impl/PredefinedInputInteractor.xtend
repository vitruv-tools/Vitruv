package tools.vitruv.framework.userinteraction.impl

import java.util.Collection
import org.eclipse.swt.widgets.Display
import org.eclipse.swt.widgets.Shell
import tools.vitruv.framework.change.interaction.ConfirmationUserInput
import tools.vitruv.framework.change.interaction.FreeTextUserInput
import tools.vitruv.framework.change.interaction.MultipleChoiceMultiSelectionUserInput
import tools.vitruv.framework.change.interaction.MultipleChoiceSingleSelectionUserInput
import tools.vitruv.framework.change.interaction.UserInputBase
import tools.vitruv.framework.userinteraction.InternalUserInteracting
import tools.vitruv.framework.change.interaction.MultipleChoiceSelectionInputBase

class PredefinedInputInteractor implements InternalUserInteracting {
    protected Collection<UserInputBase> userInputs = #[]
    private InternalUserInteracting normalUserInteractor
    /*private Shell shell;
    private Display display;*/
    
    new(Collection<UserInputBase> userInputs, InternalUserInteracting normalUserInteractor) {
        this.userInputs = userInputs
        this.normalUserInteractor = normalUserInteractor
    }
    
    override getUserInputs() {
        return userInputs
    }
    
    override resetUserInputs() {
        userInputs.clear()
    }
    
    override getNotificationDialogBuilder() {
        return new PredefinedNotificationDialogBuilder(shell, display, this)
    }
    
    override getConfirmationDialogBuilder() {
        return new PredefinedConfimrationDialogBuilder(shell, display, this)
    }
    
    override getTextInputDialogBuilder() {
        return new PredefinedTextInputDialogBuilder(shell, display, this)
    }
    
    override getSingleSelectionDialogBuilder() {
        return new PredefinedSingleSelectionDialogBuilder(shell, display, this)
    }
    
    override getMultiSelectionDialogBuilder() {
        return new PredefinedMultiSelectionDialogBuilder(shell, display, this)
    }
    
    override selectURI(String message) {
        throw new UnsupportedOperationException("TODO: auto-generated method stub")
    }
    
    def <T> handleNothingPredefined(NormalUserInteracting<T> dialogBuilder) {
        return dialogBuilder.startNormalInteraction()
    }
    
    def boolean shouldShowNotifications() {
        return false
    }
    
    def void handleNotification(NormalUserInteracting<Void> notificationBuilder) {
        notificationBuilder.startNormalInteraction()
    }
    
    private def <T extends UserInputBase> Iterable<T> getMatchingInput(String message, Class<T> type) {
        if (userInputs === null) {
            return #[]
        }
        val inputToReuse = userInputs.filter(type).filter[
            input | input.message.equals(message)
        ]
        return inputToReuse
    }
    
    def boolean hasMatchingConfirmationInputForReuse(String message) {
        val inputToReuse = getMatchingInput(message, ConfirmationUserInput)
        return inputToReuse.length > 0
    }
    
    def boolean handleConfirmation(String message) {
        val inputToReuse = getMatchingInput(message, ConfirmationUserInput)
        userInputs.remove(inputToReuse.head)
        return inputToReuse.head.confirmed
    }
    
    def boolean hasMatchingTextInputForReuse(String message) {
        val inputToReuse = getMatchingInput(message, FreeTextUserInput)
        return inputToReuse.length > 0
    }
    
    def String handleTextInput(String message) {
        val inputToReuse = getMatchingInput(message, FreeTextUserInput)
        userInputs.remove(inputToReuse.head)
        return inputToReuse.head.text
    }
    
    private def <T extends MultipleChoiceSelectionInputBase> Iterable<T> getMatchingMutltipleChoiceInput(String message,
        String[] choices, Class<T> type
    ) {
        if (userInputs === null) {
            return #[]
        }
        val inputToReuse = userInputs.filter(type).filter[
            input | input.message.equals(message) && input.choices.containsAll(choices) // TODO DK: better equality comparison
        ]
        return inputToReuse
    }
    
    def boolean hasMatchingSingleSelectionInputForReuse(String message, String[] choices) {
        val inputToReuse = getMatchingMutltipleChoiceInput(message, choices, MultipleChoiceSingleSelectionUserInput)
        return inputToReuse.length > 0
    }
    
    def int handleSingleSelectionInput(String message, String[] choices) {
        val inputToReuse = getMatchingMutltipleChoiceInput(message, choices, MultipleChoiceSingleSelectionUserInput)
        userInputs.remove(inputToReuse.head)
        return inputToReuse.head.selectedIndex
    }
    
    def boolean hasMatchingMultiSelectionInputForReuse(String message, String[] choices) {
        val inputToReuse = getMatchingMutltipleChoiceInput(message, choices, MultipleChoiceMultiSelectionUserInput)
        return inputToReuse.length > 0
    }
    
    def Collection<Integer> handleMultiSelectionInput(String message, String[] choices) {
        val inputToReuse = getMatchingMutltipleChoiceInput(message, choices, MultipleChoiceMultiSelectionUserInput)
        userInputs.remove(inputToReuse.head)
        return inputToReuse.head.selectedIndices
    }
    
    override getShell() {
        return shell
    }
    
    override getDisplay() {
        return display
    }
}


class PredefinedNotificationDialogBuilder extends NotificationDialogBuilderImpl implements NormalUserInteracting<Void> {
    private PredefinedInputInteractor interactor;
    
    new(Shell shell, Display display, PredefinedInputInteractor interactor) {
        super(shell, display)
        this.interactor = interactor
    }
    
    override startInteraction() {
        if (interactor.shouldShowNotifications()) {
            super.startInteraction()
        }
    }
    
    override startNormalInteraction() {
        super.startInteraction()
    }
}


class PredefinedConfimrationDialogBuilder extends ConfirmationDialogBuilderImpl implements NormalUserInteracting<Boolean> {
    private PredefinedInputInteractor interactor
    
    new(Shell shell, Display display, PredefinedInputInteractor interactor) {
        super(shell, display, null) // TODO DK: make PredefinedInputInteractor implement UserInputListener and pass it along here? (=> future reuse when in reuse scenario?!)
        this.interactor = interactor
    }
    
    override startInteraction() {
        if (interactor.hasMatchingConfirmationInputForReuse(message)) {
            return interactor.handleConfirmation(message)
        } else {
            return interactor.handleNothingPredefined(this)
        }
    }
    
    override startNormalInteraction() {
        super.startInteraction()
    }
}


class PredefinedTextInputDialogBuilder extends TextInputDialogBuilderImpl implements NormalUserInteracting<String> {
    private PredefinedInputInteractor interactor
    
    new(Shell shell, Display display, PredefinedInputInteractor interactor) {
        super(shell, display, null)
        this.interactor = interactor
    }
    
    override startInteraction() {
        if (interactor.hasMatchingTextInputForReuse(message)) {
            return interactor.handleTextInput(message)
        } else {
            return interactor.handleNothingPredefined(this)
        }
    }
    
    override startNormalInteraction() {
        super.startInteraction()
    }
}


class PredefinedSingleSelectionDialogBuilder extends MultipleChoiceSingleSelectionDialogBuilderImpl
        implements NormalUserInteracting<Integer> {
    private PredefinedInputInteractor interactor
    
    new(Shell shell, Display display, PredefinedInputInteractor interactor) {
        super(shell, display, null)
        this.interactor = interactor
    }
    
    override startInteraction() {
        if (interactor.hasMatchingSingleSelectionInputForReuse(message, choices)) {
            return interactor.handleSingleSelectionInput(message, choices)
        } else {
            return interactor.handleNothingPredefined(this)
        }
    }
    
    override startNormalInteraction() {
        super.startInteraction()
    }
}


class PredefinedMultiSelectionDialogBuilder extends MultipleChoiceMultiSelectionDialogBuilderImpl
        implements NormalUserInteracting<Collection<Integer>> {
    private PredefinedInputInteractor interactor
    
    new(Shell shell, Display display, PredefinedInputInteractor interactor) {
        super(shell, display, null)
        this.interactor = interactor
    }
    
    override startInteraction() {
        if (interactor.hasMatchingMultiSelectionInputForReuse(message, choices)) {
            return interactor.handleMultiSelectionInput(message, choices)
        } else {
            return interactor.handleNothingPredefined(this)
        }
    }
    
    override startNormalInteraction() {
        super.startInteraction()
    }
}


interface NormalUserInteracting<T> {
    def T startNormalInteraction()
}
