package tools.vitruv.framework.userinteraction.impl

import tools.vitruv.framework.userinteraction.PredefinedInputHandler

class PredefinedInputHandlerImpl implements PredefinedInputHandler {
    
    override handleNotification(NormalUserInteractor<Void> notificationDialogBuilder) {
        throw new UnsupportedOperationException("TODO: auto-generated method stub")
    }
    
    override shouldShowNotifications() {
        throw new UnsupportedOperationException("TODO: auto-generated method stub")
    }
    
    override handleConfirmation(String message) {
        throw new UnsupportedOperationException("TODO: auto-generated method stub")
    }
    
    override hasMatchingConfirmationInputForReuse(String message) {
        throw new UnsupportedOperationException("TODO: auto-generated method stub")
    }
    
    override <T> handleNothingPredefined(NormalUserInteractor<T> dialogBuilder) {
        throw new UnsupportedOperationException("TODO: auto-generated method stub")
    }
    
    override handleTextInput(String message) {
        throw new UnsupportedOperationException("TODO: auto-generated method stub")
    }
    
    override hasMatchingTextInputForReuse(String message) {
        throw new UnsupportedOperationException("TODO: auto-generated method stub")
    }
    
    override handleSingleSelectionInput(String message, String[] choices) {
        throw new UnsupportedOperationException("TODO: auto-generated method stub")
    }
    
    override hasMatchingSingleSelectionInputForReuse(String message, String[] choices) {
        throw new UnsupportedOperationException("TODO: auto-generated method stub")
    }
    
    override handleMultiSelectionInput(String message, String[] choices) {
        throw new UnsupportedOperationException("TODO: auto-generated method stub")
    }
    
    override hasMatchingMultiSelectionInputForReuse(String message, String[] choices) {
        throw new UnsupportedOperationException("TODO: auto-generated method stub")
    }
    
}