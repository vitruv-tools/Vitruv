package tools.vitruv.framework.userinteraction.impl

import java.util.Collection
import org.eclipse.swt.widgets.Display
import org.eclipse.swt.widgets.Shell
import org.eclipse.ui.PlatformUI
import org.eclipse.xtend.lib.annotations.Accessors
import tools.vitruv.framework.change.interaction.UserInputBase
import tools.vitruv.framework.userinteraction.InternalUserInteractor
import tools.vitruv.framework.userinteraction.PredefinedConfirmationHandler
import tools.vitruv.framework.userinteraction.PredefinedInputHandlerProvider
import tools.vitruv.framework.userinteraction.PredefinedMultiSelectionHandler
import tools.vitruv.framework.userinteraction.PredefinedNotificationHandler
import tools.vitruv.framework.userinteraction.PredefinedSingleSelectionHandler
import tools.vitruv.framework.userinteraction.PredefinedTextInputHandler
import tools.vitruv.framework.userinteraction.UserInputListener

abstract class PredefinedInputInteractor implements InternalUserInteractor, PredefinedInputHandlerProvider {
    @Accessors private Collection<UserInputBase> userInputs = #[]
    @Accessors private UserInputListener userInputListener
    private Shell shell
    private Display display
    
    new(Collection<UserInputBase> userInputs, Shell shell, Display display) {
        this.userInputs = userInputs
        this.shell = shell
        this.display = display
    }
    
    override getUserInputs() {
        return userInputs
    }
    
    override resetUserInputs() {
        userInputs.clear()
    }
    
    override getNotificationDialogBuilder() {
        return new PredefinedNotificationDialogBuilder(shell, display, getPredefinedInputHandler())
    }
    
    override getConfirmationDialogBuilder() {
        return new PredefinedConfimrationDialogBuilder(shell, display, getPredefinedInputHandler())
    }
    
    override getTextInputDialogBuilder() {
        return new PredefinedTextInputDialogBuilder(shell, display, getPredefinedInputHandler())
    }
    
    override getSingleSelectionDialogBuilder() {
        return new PredefinedSingleSelectionDialogBuilder(shell, display, getPredefinedInputHandler())
    }
    
    override getMultiSelectionDialogBuilder() {
        return new PredefinedMultiSelectionDialogBuilder(shell, display, getPredefinedInputHandler())
    }
    
    override selectURI(String message) {
        throw new UnsupportedOperationException("TODO: auto-generated method stub")
    }
    
    override getShell() {
        return shell
    }
    
    override getDisplay() {
        return display
    }    
}


class PredefinedNotificationDialogBuilder extends NotificationDialogBuilderImpl implements NormalUserInteractor<Void> {
    private PredefinedNotificationHandler notificationHandler;
    
    new(Shell shell, Display display, PredefinedNotificationHandler notificationHandler) {
        super(shell, display)
        this.notificationHandler = notificationHandler
    }
    
    override startInteraction() {
        //super.startInteraction() // TODO DK: sort out the handling of notifications
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
        return confirmationHandler.handleConfirmation(message, this)
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
        return textInputHandler.handleTextInput(message, this)
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
        return selectionHandler.handleSingleSelectionInput(message, choices, this)
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
        return selectionHandler.handleMultiSelectionInput(message, choices, this)
    }
    
    override startNormalInteraction() {
        super.startInteraction()
    }
}


interface NormalUserInteractor<T> {
    def T startNormalInteraction()
}
