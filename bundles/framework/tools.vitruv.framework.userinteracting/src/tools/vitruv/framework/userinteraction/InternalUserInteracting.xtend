package tools.vitruv.framework.userinteraction

import tools.vitruv.framework.change.interaction.UserInputBase
import java.util.Collection
import org.eclipse.swt.widgets.Shell
import org.eclipse.swt.widgets.Display

/**
 * Internal version of the {@link UserInteracting} interface used to separate methods for internal "bookkeeping" from
 * methods for actual user interaction.
 */
interface InternalUserInteracting extends UserInteracting {
    
    /**
     * @return all user inputs made after the last call to {@link #resetUserInputs resetUserInputs()}.
     */
    def Collection<UserInputBase> getUserInputs();
    
    /**
     * Clears all recorded user inputs.
     */
    def void resetUserInputs();
    
    /**
     * Get the shell used by the dialogs.
     */
    def Shell getShell();
    
    /**
     * Get the display used by the dialogs.
     */
    def Display getDisplay();
}