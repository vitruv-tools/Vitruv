package edu.kit.ipd.sdq.vitruvius.framework.model.monitor.userinteractor;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.UserInteractionType;

/**
 * @author messinger
 * 
 *         designed for test purposes in non-external eclipse instance
 * 
 */
class TestUserInteractor extends UserInteractor {

    public static void main(String[] args) {
        TestUserInteractor tui = new TestUserInteractor();

        for (int i = 0; i < 2; i++) {

            int result = tui.selectFromMessage(UserInteractionType.MODAL_POSTPONABLE, "What should I do?", "A2", "B",
                    "C");
            System.out.println("User selected " + result);
        }
    }

    @Override
    public void init() {
        this.display = Display.getDefault();
        this.shell = new Shell(this.display);
    }

}