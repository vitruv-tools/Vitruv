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
import org.eclipse.xtend.lib.annotations.Accessors

class PredefinedInputInteractor implements InternalUserInteracting, PredefinedInputHandler {
    @Accessors private Collection<UserInputBase> userInputs = #[]
    private InternalUserInteracting normalUserInteractor // TODO DK: only used to access shell and display, pass them in separately instead?
    
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
    
    override <T> handleNothingPredefined(NormalUserInteracting<T> dialogBuilder) {
        return dialogBuilder.startNormalInteraction()
    }
    
    override boolean shouldShowNotifications() {
        return false
    }
    
    override void handleNotification(NormalUserInteracting<Void> notificationBuilder) {
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
            input | input.message.equals(message) && input.choices.containsAll(choices) // TODO DK: better equality comparison
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
        return normalUserInteractor.shell
    }
    
    override getDisplay() {
        return normalUserInteractor.display
    }    
}


class PredefinedNotificationDialogBuilder extends NotificationDialogBuilderImpl implements NormalUserInteracting<Void> {
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


class PredefinedConfimrationDialogBuilder extends ConfirmationDialogBuilderImpl implements NormalUserInteracting<Boolean> {
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


class PredefinedTextInputDialogBuilder extends TextInputDialogBuilderImpl implements NormalUserInteracting<String> {
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
        implements NormalUserInteracting<Integer> {
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
        implements NormalUserInteracting<Collection<Integer>> {
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


interface NormalUserInteracting<T> {
    def T startNormalInteraction()
}

interface PredefinedNotificationHandler {
    def void handleNotification(NormalUserInteracting<Void> notificationDialogBuilder)
    def boolean shouldShowNotifications()
}

interface PredefinedConfirmationHandler {
    def boolean handleConfirmation(String message)
    def boolean hasMatchingConfirmationInputForReuse(String message)
    def <T> T handleNothingPredefined(NormalUserInteracting<T> dialogBuilder)
}

interface PredefinedTextInputHandler {
    def String handleTextInput(String message)
    def boolean hasMatchingTextInputForReuse(String message)
    def <T> T handleNothingPredefined(NormalUserInteracting<T> dialogBuilder)
}

interface PredefinedSingleSelectionHandler {
    def int handleSingleSelectionInput(String message, String[] choices)
    def boolean hasMatchingSingleSelectionInputForReuse(String message, String[] choices)
    def <T> T handleNothingPredefined(NormalUserInteracting<T> dialogBuilder)
}

interface PredefinedMultiSelectionHandler {
    def Collection<Integer> handleMultiSelectionInput(String message, String[] choices)
    def boolean hasMatchingMultiSelectionInputForReuse(String message, String[] choices)
    def <T> T handleNothingPredefined(NormalUserInteracting<T> dialogBuilder)
}

interface PredefinedInputHandler extends PredefinedNotificationHandler, PredefinedConfirmationHandler,
    PredefinedTextInputHandler, PredefinedSingleSelectionHandler, PredefinedMultiSelectionHandler { }
