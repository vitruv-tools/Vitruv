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
    def void handleNotification(NormalUserInteractor<Void> notificationDialogBuilder)
    def boolean shouldShowNotifications()
}

interface PredefinedConfirmationHandler {
    def boolean handleConfirmation(String message)
    def boolean hasMatchingConfirmationInputForReuse(String message)
    def <T> T handleNothingPredefined(NormalUserInteractor<T> dialogBuilder)
}

interface PredefinedTextInputHandler {
    def String handleTextInput(String message)
    def boolean hasMatchingTextInputForReuse(String message)
    def <T> T handleNothingPredefined(NormalUserInteractor<T> dialogBuilder)
}

interface PredefinedSingleSelectionHandler {
    def int handleSingleSelectionInput(String message, String[] choices)
    def boolean hasMatchingSingleSelectionInputForReuse(String message, String[] choices)
    def <T> T handleNothingPredefined(NormalUserInteractor<T> dialogBuilder)
}

interface PredefinedMultiSelectionHandler {
    def Collection<Integer> handleMultiSelectionInput(String message, String[] choices)
    def boolean hasMatchingMultiSelectionInputForReuse(String message, String[] choices)
    def <T> T handleNothingPredefined(NormalUserInteractor<T> dialogBuilder)
}
