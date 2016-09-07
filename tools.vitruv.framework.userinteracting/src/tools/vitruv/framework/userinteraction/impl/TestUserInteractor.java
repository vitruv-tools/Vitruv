package tools.vitruv.framework.userinteraction.impl;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * @author messinger
 * 
 *         designed for test purposes in non-external eclipse instance
 * 
 */
class TestUserInteractor extends UserInteractor {

    @Override
    public void init() {
        this.display = Display.getDefault();
        this.shell = new Shell(this.display);
    }

}