package tools.vitruv.framework.userinteraction.impl

import java.util.Collection
import tools.vitruv.framework.change.interaction.UserInputBase
import tools.vitruv.framework.userinteraction.InternalUserInteractor

class ReuseUserInputInteractor extends PredefinedInputInteractor {
    
    new(Collection<UserInputBase> userInputs, InternalUserInteractor normalUserInteractor) {
        super(userInputs, normalUserInteractor)
    }
}