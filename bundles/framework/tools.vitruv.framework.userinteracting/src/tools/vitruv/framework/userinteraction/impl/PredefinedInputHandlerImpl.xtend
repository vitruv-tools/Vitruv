package tools.vitruv.framework.userinteraction.impl

import tools.vitruv.framework.userinteraction.PredefinedInputHandler
import tools.vitruv.framework.change.interaction.ConfirmationUserInput
import tools.vitruv.framework.userinteraction.UserInputListener
import tools.vitruv.framework.change.interaction.UserInputBase
import java.util.Collection
import org.eclipse.xtend.lib.annotations.Accessors
import tools.vitruv.framework.change.interaction.FreeTextUserInput
import tools.vitruv.framework.change.interaction.MultipleChoiceSelectionInputBase
import tools.vitruv.framework.change.interaction.MultipleChoiceSingleSelectionUserInput
import tools.vitruv.framework.change.interaction.MultipleChoiceMultiSelectionUserInput

class PredefinedInputHandlerImpl implements PredefinedInputHandler, UserInputListener {
    @Accessors private Collection<UserInputBase> userInputs = newArrayList() // TODO DK: Accessors? Delegate from Predef.UserInteractor to here?
    
    protected def <T extends UserInputBase> Iterable<T> getMatchingInput(String message, Class<T> type) {
        if (userInputs === null) {
            return #[]
        }
        val inputToReuse = userInputs.filter(type).filter[
            input | /* TODO DK: for testing*/ input.message === null || input.message.equals(message)
        ]
        return inputToReuse
    }
    
    protected def <T extends MultipleChoiceSelectionInputBase> Iterable<T> getMatchingMutltipleChoiceInput(String message,
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
    
    def <T> handleNothingPredefined(NormalUserInteractor<T> dialogBuilder) {
        return dialogBuilder.startNormalInteraction()
    }
    
    override handleNotification(NormalUserInteractor<Void> notificationDialogBuilder) { // TODO DK: revisit once UserInputs are refactored and contain a Notification... one.
        throw new UnsupportedOperationException("TODO: auto-generated method stub")
    }
    
    override handleConfirmation(String message, NormalUserInteractor<Boolean> confirmationDialogBuilder) {
        val inputToReuse = getMatchingInput(message, ConfirmationUserInput)
        if (inputToReuse.isEmpty()) {
            return handleNothingPredefined(confirmationDialogBuilder)
        }
        userInputs.remove(inputToReuse.head)
        return inputToReuse.head.confirmed
    }
    
    override handleTextInput(String message, NormalUserInteractor<String> textInputDialogBuilder) {
        val inputToReuse = getMatchingInput(message, FreeTextUserInput)
        if (inputToReuse.isEmpty()) {
            return handleNothingPredefined(textInputDialogBuilder)
        }
        userInputs.remove(inputToReuse.head)
        return inputToReuse.head.text
    }
    
    override handleSingleSelectionInput(String message, String[] choices, NormalUserInteractor<Integer> singleSelectionDialogBuilder) {
        val inputToReuseCandidates = getMatchingMutltipleChoiceInput(message, choices, MultipleChoiceSingleSelectionUserInput)
        if (inputToReuseCandidates.isEmpty()) {
            return handleNothingPredefined(singleSelectionDialogBuilder)
        }
        val inputToReuse = inputToReuseCandidates.head // TODO DK: WHY is it necessary, to use this extra variable ?!?
        // (feeding inputToReuseCandidates.head to remove below and using it in the return statement causes a NPE there)
        // TODO DK: ...also add this to the other handle-methods
        userInputs.remove(inputToReuse)
        
        return inputToReuse.selectedIndex
    }
    
    override handleMultiSelectionInput(String message, String[] choices, NormalUserInteractor<Collection<Integer>> multiSelectionDialogBuilder) {
        val inputToReuse = getMatchingMutltipleChoiceInput(message, choices, MultipleChoiceMultiSelectionUserInput)
        if (inputToReuse.isEmpty()) {
            return handleNothingPredefined(multiSelectionDialogBuilder)
        }
        userInputs.remove(inputToReuse.head)
        return inputToReuse.head.selectedIndices
    }
    
    override onUserInputReceived(UserInputBase input) {
        userInputs.add(input)
    }
    
}