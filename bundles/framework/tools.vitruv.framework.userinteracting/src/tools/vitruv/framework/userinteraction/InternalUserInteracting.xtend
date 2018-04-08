package tools.vitruv.framework.userinteraction

import tools.vitruv.framework.change.interaction.UserInputBase
import java.util.Collection

interface InternalUserInteracting extends UserInteracting {
    
    def Collection<UserInputBase> getUserInputs();
    
    def void resetUserInputs();
}