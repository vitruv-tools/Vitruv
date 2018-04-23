package tools.vitruv.framework.userinteraction.impl

import java.util.Collection
import tools.vitruv.framework.change.interaction.UserInputBase
import tools.vitruv.framework.userinteraction.InternalUserInteracting

class ReuseUserInputInteractor extends PredefinedInputInteractor {
    
    new(Collection<UserInputBase> userInputs, InternalUserInteracting normalUserInteractor) {
        super(userInputs, normalUserInteractor)
    }
}