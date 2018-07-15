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
    @Accessors private Collection<UserInteractionBase> userInteractions = newArrayList()
    
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
            input | !input.isSetMessage() || (input.message.equals(message) && input.choices.containsAll(choices))
            // TODO make ^ comparison adjustable to allow reuse when only a subset of options is available when desired
        ]
        return inputToReuse
    }
    
    def <T> handleNothingPredefined(NormalUserInteractor<T> dialogBuilder) {
        return dialogBuilder.startNormalInteraction()
    }
    
    override handleNotification(String message, NormalUserInteractor<Void> notificationDialogBuilder) {
        val inputToReuseCandidates = getMatchingInput(message, ConfirmationUserInteraction)
        if (inputToReuseCandidates.isEmpty()) {
            handleNothingPredefined(notificationDialogBuilder)
        }
        val inputToReuse = inputToReuseCandidates.head // this local variable is necessary, for some reason, directly 
        // using inputToReuseCandidates.head in the remove() call below causes a NPE
        userInteractions.remove(inputToReuse)
    }
    
    override handleConfirmation(String message, NormalUserInteractor<Boolean> confirmationDialogBuilder) {
        val inputToReuseCandidates = getMatchingInput(message, ConfirmationUserInteraction)
        if (inputToReuseCandidates.isEmpty()) {
            return handleNothingPredefined(confirmationDialogBuilder)
        }
        val inputToReuse = inputToReuseCandidates.head // this local variable is necessary, for some reason, directly 
        // using inputToReuseCandidates.head in the remove() call below and the return statement causes a NPE
        userInteractions.remove(inputToReuse)
        
        return inputToReuse.confirmed
    }
    
    override handleTextInput(String message, NormalUserInteractor<String> textInputDialogBuilder) {
        val inputToReuseCandidates = getMatchingInput(message, FreeTextUserInteraction)
        if (inputToReuseCandidates.isEmpty()) {
            return handleNothingPredefined(textInputDialogBuilder)
        }
        val inputToReuse = inputToReuseCandidates.head // this local variable is necessary, for some reason, directly 
        // using inputToReuseCandidates.head in the remove() call below and the return statement causes a NPE
        userInteractions.remove(inputToReuse)
        
        return inputToReuse.text
    }
    
    override handleSingleSelectionInput(String message, String[] choices, NormalUserInteractor<Integer> singleSelectionDialogBuilder) {
        val inputToReuseCandidates = getMatchingMutltipleChoiceInput(message, choices, MultipleChoiceSingleSelectionUserInteraction)
        if (inputToReuseCandidates.isEmpty()) {
            return handleNothingPredefined(singleSelectionDialogBuilder)
        }
        val inputToReuse = inputToReuseCandidates.head // this local variable is necessary, for some reason, directly 
        // using inputToReuseCandidates.head in the remove() call below and the return statement causes a NPE
        userInteractions.remove(inputToReuse)
        
        return inputToReuse.selectedIndex
    }
    
    override handleMultiSelectionInput(String message, String[] choices, NormalUserInteractor<Collection<Integer>> multiSelectionDialogBuilder) {
        val inputToReuseCandidates = getMatchingMutltipleChoiceInput(message, choices, MultipleChoiceMultiSelectionUserInteraction)
        if (inputToReuseCandidates.isEmpty()) {
            return handleNothingPredefined(multiSelectionDialogBuilder)
        }
        val inputToReuse = inputToReuseCandidates.head // this local variable is necessary, for some reason, directly 
        // using inputToReuseCandidates.head in the remove() call below and the return statement causes a NPE
        userInteractions.remove(inputToReuse)
        
        return inputToReuse.selectedIndices
    }
    
    override onUserInteractionReceived(UserInteractionBase interaction) {
        userInteractions.add(interaction)
    }
    
}