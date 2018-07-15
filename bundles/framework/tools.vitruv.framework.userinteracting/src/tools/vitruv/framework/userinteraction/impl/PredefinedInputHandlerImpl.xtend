package tools.vitruv.framework.userinteraction.impl

import tools.vitruv.framework.userinteraction.PredefinedInputHandler
import java.util.Collection
import org.eclipse.xtend.lib.annotations.Accessors
import tools.vitruv.framework.userinteraction.UserInteractionListener
import tools.vitruv.framework.change.interaction.UserInteractionBase
import tools.vitruv.framework.change.interaction.MultipleChoiceSelectionInteractionBase
import tools.vitruv.framework.change.interaction.ConfirmationUserInteraction
import tools.vitruv.framework.change.interaction.FreeTextUserInteraction
import tools.vitruv.framework.change.interaction.MultipleChoiceSingleSelectionUserInteraction
import tools.vitruv.framework.change.interaction.MultipleChoiceMultiSelectionUserInteraction

class PredefinedInputHandlerImpl implements PredefinedInputHandler, UserInteractionListener {
    @Accessors private Collection<UserInteractionBase> userInteractions = newArrayList() // TODO DK: Accessors? Delegate from Predef.UserInteractor to here?
    
    protected def <T extends UserInteractionBase> Iterable<T> getMatchingInput(String message, Class<T> type) {
        if (userInteractions === null) {
            return #[]
        }
        val inputToReuse = userInteractions.filter(type).filter[
            input | !input.isSetMessage() || input.message.equals(message)
        ]
        return inputToReuse
    }
    
    protected def <T extends MultipleChoiceSelectionInteractionBase> Iterable<T> getMatchingMutltipleChoiceInput(String message,
        String[] choices, Class<T> type
    ) {
        if (userInteractions === null) {
            return #[]
        }
        val inputToReuse = userInteractions.filter(type).filter[
            input | !input.isSetMessage() || (input.message.equals(message) && input.choices.containsAll(choices)) // TODO DK: better equality comparison
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
        val inputToReuse = getMatchingInput(message, ConfirmationUserInteraction)
        if (inputToReuse.isEmpty()) {
            return handleNothingPredefined(confirmationDialogBuilder)
        }
        userInteractions.remove(inputToReuse.head)
        return inputToReuse.head.confirmed
    }
    
    override handleTextInput(String message, NormalUserInteractor<String> textInputDialogBuilder) {
        val inputToReuse = getMatchingInput(message, FreeTextUserInteraction)
        if (inputToReuse.isEmpty()) {
            return handleNothingPredefined(textInputDialogBuilder)
        }
        userInteractions.remove(inputToReuse.head)
        return inputToReuse.head.text
    }
    
    override handleSingleSelectionInput(String message, String[] choices, NormalUserInteractor<Integer> singleSelectionDialogBuilder) {
        val inputToReuseCandidates = getMatchingMutltipleChoiceInput(message, choices, MultipleChoiceSingleSelectionUserInteraction)
        if (inputToReuseCandidates.isEmpty()) {
            return handleNothingPredefined(singleSelectionDialogBuilder)
        }
        val inputToReuse = inputToReuseCandidates.head // TODO DK: WHY is it necessary, to use this extra variable ?!?
        // (feeding inputToReuseCandidates.head to remove below and using it in the return statement causes a NPE there)
        // TODO DK: ...also add this to the other handle-methods
        userInteractions.remove(inputToReuse)
        
        return inputToReuse.selectedIndex
    }
    
    override handleMultiSelectionInput(String message, String[] choices, NormalUserInteractor<Collection<Integer>> multiSelectionDialogBuilder) {
        val inputToReuse = getMatchingMutltipleChoiceInput(message, choices, MultipleChoiceMultiSelectionUserInteraction)
        if (inputToReuse.isEmpty()) {
            return handleNothingPredefined(multiSelectionDialogBuilder)
        }
        userInteractions.remove(inputToReuse.head)
        return inputToReuse.head.selectedIndices
    }
    
    override onUserInteractionReceived(UserInteractionBase interaction) {
        userInteractions.add(interaction)
    }
    
}