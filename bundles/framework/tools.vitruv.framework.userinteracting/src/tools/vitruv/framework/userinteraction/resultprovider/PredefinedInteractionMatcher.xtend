package tools.vitruv.framework.userinteraction.resultprovider

import tools.vitruv.framework.change.interaction.UserInteractionBase
import tools.vitruv.framework.change.interaction.MultipleChoiceSelectionInteractionBase
import tools.vitruv.framework.change.interaction.ConfirmationUserInteraction
import tools.vitruv.framework.change.interaction.FreeTextUserInteraction
import tools.vitruv.framework.change.interaction.MultipleChoiceSingleSelectionUserInteraction
import tools.vitruv.framework.change.interaction.MultipleChoiceMultiSelectionUserInteraction
import java.util.List
import java.util.ArrayList

/**
 * @author Dominik Klooz
 * @author Heiko Klare
 */
class PredefinedInteractionMatcher {
	private List<UserInteractionBase> userInteractions = newArrayList()
    
    new () {
    	this.userInteractions = new ArrayList<UserInteractionBase>();
    }
    
    public def void addInteraction(UserInteractionBase interaction) {
    	userInteractions += interaction;
    }
    
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
            // TODO DK make ^ comparison adjustable to allow reuse when only a subset of options is available when desired
        ]
        return inputToReuse
    }
    
    def Boolean getNotificationResult(String message) {
        val inputToReuseCandidates = getMatchingInput(message, ConfirmationUserInteraction)
        if (inputToReuseCandidates.isEmpty()) {
            return null;
        }
        val inputToReuse = inputToReuseCandidates.head // this local variable is necessary, for some reason, directly 
        // using inputToReuseCandidates.head in the remove() call below causes a NPE
        userInteractions.remove(inputToReuse)
        return true;
    }
    
    def Boolean getConfirmationResult(String message) {
        val inputToReuseCandidates = getMatchingInput(message, ConfirmationUserInteraction)
        if (inputToReuseCandidates.isEmpty()) {
            return null
        }
        val inputToReuse = inputToReuseCandidates.head // this local variable is necessary, for some reason, directly 
        // using inputToReuseCandidates.head in the remove() call below and the return statement causes a NPE
        userInteractions.remove(inputToReuse)
        
        return inputToReuse.confirmed
    }
    
    def String getTextInputResult(String message) {
        val inputToReuseCandidates = getMatchingInput(message, FreeTextUserInteraction)
        if (inputToReuseCandidates.isEmpty()) {
            return null
        }
        val inputToReuse = inputToReuseCandidates.head // this local variable is necessary, for some reason, directly 
        // using inputToReuseCandidates.head in the remove() call below and the return statement causes a NPE
        userInteractions.remove(inputToReuse)
        
        return inputToReuse.text
    }
    
    def Integer getSingleSelectionResult(String message, Iterable<String> choices) {
        val inputToReuseCandidates = getMatchingMutltipleChoiceInput(message, choices, MultipleChoiceSingleSelectionUserInteraction)
        if (inputToReuseCandidates.isEmpty()) {
            return null
        }
        val inputToReuse = inputToReuseCandidates.head // this local variable is necessary, for some reason, directly 
        // using inputToReuseCandidates.head in the remove() call below and the return statement causes a NPE
        userInteractions.remove(inputToReuse)
        
        return inputToReuse.selectedIndex
    }
    
    def Iterable<Integer> getMultiSelectionResult(String message, Iterable<String> choices) {
        val inputToReuseCandidates = getMatchingMutltipleChoiceInput(message, choices, MultipleChoiceMultiSelectionUserInteraction)
        if (inputToReuseCandidates.isEmpty()) {
            return null
        }
        val inputToReuse = inputToReuseCandidates.head // this local variable is necessary, for some reason, directly 
        // using inputToReuseCandidates.head in the remove() call below and the return statement causes a NPE
        userInteractions.remove(inputToReuse)
        
        return inputToReuse.selectedIndices
    }
    
}