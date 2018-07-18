package tools.vitruv.framework.userinteraction.impl

import java.util.Collection
import tools.vitruv.framework.userinteraction.InternalUserInteractor
import org.eclipse.ui.PlatformUI
import tools.vitruv.framework.userinteraction.UserInteractionListener
import tools.vitruv.framework.change.interaction.UserInteractionBase

class ReuseUserInputInteractor extends PredefinedInputInteractor {
    private ReusePredefinedInputHandler predefinedInputHandler
    
    new(Collection<UserInteractionBase> userInteractions, InternalUserInteractor normalUserInteractor) {
        super(userInteractions, normalUserInteractor.shell, normalUserInteractor.display ?: if (PlatformUI.workbenchRunning) PlatformUI.getWorkbench().getDisplay() else PlatformUI.createDisplay()) // TODO DK: workbench has not been created yet!
        predefinedInputHandler = new ReusePredefinedInputHandler()
        predefinedInputHandler.userInteractions = userInteractions
    }
    
    override getPredefinedInputHandler() {
        return predefinedInputHandler
    }
    
    override registerUserInputListener(UserInteractionListener listener) {
        // do nothing, the ReuseUserInputInteractor doesn't need to register UserInputListeners.
    }
    
}


class ReusePredefinedInputHandler extends PredefinedInputHandlerImpl {
    override <T> handleNothingPredefined(NormalUserInteractor<T> dialogBuilder) {
        return dialogBuilder.startNormalInteraction()
    }
}