package tools.vitruv.framework.userinteraction.impl

import java.util.Collection
import org.eclipse.swt.widgets.Display
import org.eclipse.swt.widgets.Shell
import tools.vitruv.framework.change.interaction.ConfirmationUserInput
import tools.vitruv.framework.change.interaction.FreeTextUserInput
import tools.vitruv.framework.change.interaction.MultipleChoiceMultiSelectionUserInput
import tools.vitruv.framework.change.interaction.MultipleChoiceSingleSelectionUserInput
import tools.vitruv.framework.change.interaction.UserInputBase
import tools.vitruv.framework.change.interaction.MultipleChoiceSelectionInputBase
import org.eclipse.xtend.lib.annotations.Accessors
import org.eclipse.ui.PlatformUI
import tools.vitruv.framework.userinteraction.UserInputListener
import tools.vitruv.framework.userinteraction.PredefinedInputHandler
import tools.vitruv.framework.userinteraction.PredefinedNotificationHandler
import tools.vitruv.framework.userinteraction.PredefinedConfirmationHandler
import tools.vitruv.framework.userinteraction.PredefinedTextInputHandler
import tools.vitruv.framework.userinteraction.PredefinedSingleSelectionHandler
import tools.vitruv.framework.userinteraction.PredefinedMultiSelectionHandler
import tools.vitruv.framework.userinteraction.PredefinedInputHandlerProvider
import tools.vitruv.framework.userinteraction.InternalUserInteractor

class PredefinedInputInteractor implements InternalUserInteractor, PredefinedInputHandler, PredefinedInputHandlerProvider {
    @Accessors private Collection<UserInputBase> userInputs = #[]
    @Accessors private UserInputListener userInputListener
    private InternalUserInteractor normalUserInteractor // TODO DK: only used to access shell and display, pass them in separately instead?
    
    new(Collection<UserInputBase> userInputs, InternalUserInteractor normalUserInteractor) {
        this.userInputs = userInputs
        this.normalUserInteractor = normalUserInteractor
    }
    
    override void registerUserInputListener(UserInputListener userInputListener) {
        this.userInputListener = userInputListener;
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
    
    override <T> handleNothingPredefined(NormalUserInteractor<T> dialogBuilder) {
        /*if (normalUserInteractor.class.name.contains("TestUserInteractor")) {
            throw new Exception("normalUserInteractor is a TestUserInteractor without anything predefined") // TODO DK
        }*/
        return (normalUserInteractor as PredefinedInputInteractor).handleNothingPredefined(dialogBuilder)//dialogBuilder.startNormalInteraction()
    }
    
    override boolean shouldShowNotifications() {
        return false
    }
    
    override void handleNotification(NormalUserInteractor<Void> notificationBuilder) {
        // TODO DK: use shouldShowNotifications?
        notificationBuilder.startNormalInteraction()
    }
    
    /*private*/ def <T extends UserInputBase> Iterable<T> getMatchingInput(String message, Class<T> type) {
        if (userInputs === null) {
            return #[]
        }
        val inputToReuse = userInputs.filter(type).filter[
            input | /* TODO DK: for testing*/ input.message === null || input.message.equals(message)
        ]
        return inputToReuse
    }
    
    override boolean hasMatchingConfirmationInputForReuse(String message) {
        val inputToReuse = getMatchingInput(message, ConfirmationUserInput)
        return inputToReuse.length > 0
    }
    
    override boolean handleConfirmation(String message) {
        val inputToReuse = getMatchingInput(message, ConfirmationUserInput)
        userInputs.remove(inputToReuse.head)
        return inputToReuse.head.confirmed
    }
    
    override boolean hasMatchingTextInputForReuse(String message) {
        val inputToReuse = getMatchingInput(message, FreeTextUserInput)
        return inputToReuse.length > 0
    }
    
    override String handleTextInput(String message) {
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
            input | input.message === null /* TODO DK: added for quick and dirty test */ || (input.message.equals(message) && input.choices.containsAll(choices)) // TODO DK: better equality comparison
        ]
        return inputToReuse
    }
    
    override boolean hasMatchingSingleSelectionInputForReuse(String message, String[] choices) {
        val inputToReuse = getMatchingMutltipleChoiceInput(message, choices, MultipleChoiceSingleSelectionUserInput)
        return inputToReuse.length > 0
    }
    
    override int handleSingleSelectionInput(String message, String[] choices) {
        val inputToReuse = getMatchingMutltipleChoiceInput(message, choices, MultipleChoiceSingleSelectionUserInput)
        userInputs.remove(inputToReuse.head)
        return inputToReuse.head.selectedIndex
    }
    
    override boolean hasMatchingMultiSelectionInputForReuse(String message, String[] choices) {
        val inputToReuse = getMatchingMutltipleChoiceInput(message, choices, MultipleChoiceMultiSelectionUserInput)
        return inputToReuse.length > 0
    }
    
    override Collection<Integer> handleMultiSelectionInput(String message, String[] choices) {
        val inputToReuse = getMatchingMutltipleChoiceInput(message, choices, MultipleChoiceMultiSelectionUserInput)
        userInputs.remove(inputToReuse.head)
        return inputToReuse.head.selectedIndices
    }
    
    override getShell() {
        return normalUserInteractor?.shell
    }
    
    override getDisplay() {
        return normalUserInteractor?.display ?: PlatformUI.getWorkbench().getDisplay() // TODO DK: null in TestUserInteractor, get it here somehow
    }
    
    override getPredefinedInputHandler() {
        // TODO DK: return PredefinedInputHandlerImpl here once it's implemented and override this method in TestUserInteractor to allow it to provide its own handle methods via its own separate subclass of PredefinedInputHandlerImpl
    }
    
}


class PredefinedNotificationDialogBuilder extends NotificationDialogBuilderImpl implements NormalUserInteractor<Void> {
    private PredefinedNotificationHandler notificationHandler;
    
    new(Shell shell, Display display, PredefinedNotificationHandler notificationHandler) {
        super(shell, display)
        this.notificationHandler = notificationHandler
    }
    
    override startInteraction() {
        if (notificationHandler.shouldShowNotifications()) {
            super.startInteraction()
        }
    }
    
    override startNormalInteraction() {
        super.startInteraction()
    }
}


class PredefinedConfimrationDialogBuilder extends ConfirmationDialogBuilderImpl implements NormalUserInteractor<Boolean> {
    private PredefinedConfirmationHandler confirmationHandler;
    
    new(Shell shell, Display display, PredefinedConfirmationHandler confirmationHandler) {
        super(shell, display, null) // TODO DK: make PredefinedInputInteractor implement UserInputListener and pass it along here? (=> future reuse when in reuse scenario?!)
        this.confirmationHandler = confirmationHandler
    }
    
    override startInteraction() {
        if (confirmationHandler.hasMatchingConfirmationInputForReuse(message)) {
            return confirmationHandler.handleConfirmation(message)
        } else {
            return confirmationHandler.handleNothingPredefined(this)
        }
    }
    
    override startNormalInteraction() {
        super.startInteraction()
    }
}


class PredefinedTextInputDialogBuilder extends TextInputDialogBuilderImpl implements NormalUserInteractor<String> {
    private PredefinedTextInputHandler textInputHandler
    
    new(Shell shell, Display display, PredefinedTextInputHandler textInputHandler) {
        super(shell, display, null)
        this.textInputHandler = textInputHandler
    }
    
    override startInteraction() {
        if (textInputHandler.hasMatchingTextInputForReuse(message)) {
            return textInputHandler.handleTextInput(message)
        } else {
            return textInputHandler.handleNothingPredefined(this)
        }
    }
    
    override startNormalInteraction() {
        super.startInteraction()
    }
}


class PredefinedSingleSelectionDialogBuilder extends MultipleChoiceSingleSelectionDialogBuilderImpl
        implements NormalUserInteractor<Integer> {
    private PredefinedSingleSelectionHandler selectionHandler
    
    new(Shell shell, Display display, PredefinedSingleSelectionHandler selectionHandler) {
        super(shell, display, null)
        this.selectionHandler = selectionHandler
    }
    
    override startInteraction() {
        if (selectionHandler.hasMatchingSingleSelectionInputForReuse(message, choices)) {
            return selectionHandler.handleSingleSelectionInput(message, choices)
        } else {
            return selectionHandler.handleNothingPredefined(this)
        }
    }
    
    override startNormalInteraction() {
        super.startInteraction()
    }
}


class PredefinedMultiSelectionDialogBuilder extends MultipleChoiceMultiSelectionDialogBuilderImpl
        implements NormalUserInteractor<Collection<Integer>> {
    private PredefinedMultiSelectionHandler selectionHandler
    
    new(Shell shell, Display display, PredefinedMultiSelectionHandler selectionHandler) {
        super(shell, display, null)
        this.selectionHandler = selectionHandler
    }
    
    override startInteraction() {
        if (selectionHandler.hasMatchingMultiSelectionInputForReuse(message, choices)) {
            return selectionHandler.handleMultiSelectionInput(message, choices)
        } else {
            return selectionHandler.handleNothingPredefined(this)
        }
    }
    
    override startNormalInteraction() {
        super.startInteraction()
    }
}


interface NormalUserInteractor<T> {
    def T startNormalInteraction()
}
