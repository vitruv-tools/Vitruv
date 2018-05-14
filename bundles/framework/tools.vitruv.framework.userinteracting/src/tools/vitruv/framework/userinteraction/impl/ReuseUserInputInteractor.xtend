package tools.vitruv.framework.userinteraction.impl

import java.util.Collection
import tools.vitruv.framework.change.interaction.UserInputBase
import tools.vitruv.framework.userinteraction.InternalUserInteractor
import org.eclipse.ui.PlatformUI
import tools.vitruv.framework.userinteraction.UserInputListener

class ReuseUserInputInteractor extends PredefinedInputInteractor {
    private ReusePredefinedInputHandler predefinedInputHandler
    
    new(Collection<UserInputBase> userInputs, InternalUserInteractor normalUserInteractor) {
        super(userInputs, normalUserInteractor.shell, normalUserInteractor.display ?: PlatformUI.getWorkbench().getDisplay())
        predefinedInputHandler = new ReusePredefinedInputHandler()
        predefinedInputHandler.userInputs = userInputs
    }
    
    override getPredefinedInputHandler() {
        return predefinedInputHandler
    }
    
    override registerUserInputListener(UserInputListener listener) {
        // do nothing, the ReuseUserInputInteractor doesn't need to register UserInputListeners.
    }
    
}


class ReusePredefinedInputHandler extends PredefinedInputHandlerImpl {
    override <T> handleNothingPredefined(NormalUserInteractor<T> dialogBuilder) {
        return dialogBuilder.startNormalInteraction()
    }
}