package tools.vitruv.framework.userinteraction

import java.util.Collection
import org.eclipse.swt.widgets.Shell
import org.eclipse.swt.widgets.Display
import tools.vitruv.framework.change.interaction.UserInteractionBase

/**
 * Internal version of the {@link UserInteractor} interface used to separate methods for internal "bookkeeping" from
 * methods for actual user interaction.
 */
interface InternalUserInteractor extends UserInteractor {
    
    /**
     * @return all user inputs made after the last call to {@link #resetUserInteractions resetUserInteractions()}.
     */
    def Collection<UserInteractionBase> getUserInteractions()
    
    /**
     * Clears all recorded user inputs.
     */
    def void resetUserInteractions()
    
    /**
     * Get the shell used by the dialogs.
     */
    def Shell getShell()
    
    /**
     * Get the display used by the dialogs.
     */
    def Display getDisplay()
    
    def void registerUserInputListener(UserInteractionListener listener)
}