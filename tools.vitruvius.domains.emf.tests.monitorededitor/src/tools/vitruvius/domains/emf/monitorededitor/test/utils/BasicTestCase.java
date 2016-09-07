/*******************************************************************************
 * Copyright (c) 2014 Felix Kutzner.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Felix Kutzner - initial implementation.
 ******************************************************************************/

package tools.vitruvius.domains.emf.monitorededitor.test.utils;

import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import tools.vitruvius.framework.change.description.EMFModelChange;
import tools.vitruvius.framework.change.preparation.ChangePreparing;
import tools.vitruvius.framework.change.preparation.ChangePreparingImpl;
import tools.vitruvius.framework.change.description.VitruviusChange;

public class BasicTestCase {

    private static boolean hasLoggerBeenInitialized = false;
    protected Logger LOGGER = Logger.getLogger(BasicTestCase.class);

    public BasicTestCase() {
        synchronized (BasicTestCase.class) {
            if (!hasLoggerBeenInitialized) {
                org.apache.log4j.BasicConfigurator.configure();
                hasLoggerBeenInitialized = true;
                Logger.getRootLogger().setLevel(Level.ALL);
            }
        }
    }

    protected void transformChanges(List<VitruviusChange> changes) {
        for (int i = changes.size() - 1; i >= 0; i--) {
            if (changes.get(i) instanceof EMFModelChange)
                ((EMFModelChange) changes.get(i)).getChangeDescription().applyAndReverse();
        }
        ChangePreparing preparer = new ChangePreparingImpl();
        for (VitruviusChange change : changes) {
            change.prepare(preparer);
            if (change instanceof EMFModelChange) {
                ((EMFModelChange) change).getChangeDescription().applyAndReverse();
            }
        }

        /*
         * List<VitruviusChange> transformedChanges = new ArrayList<VitruviusChange>(); for (int i =
         * changes.size() - 1; i >= 0; i--) { if (changes.get(i) instanceof EMFModelChange)
         * ((EMFModelChange) changes.get(i)).getChangeDescription().applyAndReverse(); } for (Change
         * change : changes) { if (change instanceof EMFModelChange) { transformedChanges.add(new
         * EMFModelChangeTransformation((EMFModelChange) change).getChange()); ((EMFModelChange)
         * change).getChangeDescription().applyAndReverse(); } } return transformedChanges;
         */
    }
}
