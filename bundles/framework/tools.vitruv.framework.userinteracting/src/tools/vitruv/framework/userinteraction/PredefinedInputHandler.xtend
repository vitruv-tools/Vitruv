package tools.vitruv.framework.userinteraction

import java.util.Collection
import tools.vitruv.framework.userinteraction.impl.NormalUserInteractor

interface PredefinedInputHandler
    extends PredefinedNotificationHandler, 
            PredefinedConfirmationHandler,
            PredefinedTextInputHandler, 
            PredefinedSingleSelectionHandler,
            PredefinedMultiSelectionHandler { }
            
            
interface PredefinedInputHandlerProvider {
    def PredefinedInputHandler getPredefinedInputHandler()
}
            

interface PredefinedNotificationHandler {
    def void handleNotification(String message, NormalUserInteractor<Void> notificationDialogBuilder)
}

interface PredefinedConfirmationHandler {
    def boolean handleConfirmation(String message, NormalUserInteractor<Boolean> confirmationDialogBuilder)
}

interface PredefinedTextInputHandler {
    def String handleTextInput(String message, NormalUserInteractor<String> textInputDialogBuilder)
}

interface PredefinedSingleSelectionHandler {
    def int handleSingleSelectionInput(String message, String[] choices, NormalUserInteractor<Integer> singleSelectionDialogBuilder)
}

interface PredefinedMultiSelectionHandler {
    def Collection<Integer> handleMultiSelectionInput(String message, String[] choices, NormalUserInteractor<Collection<Integer>> multiSelectionDialogBuilder)
}
