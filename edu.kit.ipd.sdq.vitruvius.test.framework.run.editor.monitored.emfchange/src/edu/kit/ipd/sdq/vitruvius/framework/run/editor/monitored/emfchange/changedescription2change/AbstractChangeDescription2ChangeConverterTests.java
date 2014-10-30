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

package edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.changedescription2change;

import java.net.URL;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.change.ChangeDescription;
import org.eclipse.emf.ecore.change.util.ChangeRecorder;
import org.eclipse.emf.ecore.resource.Resource;
import org.junit.After;
import org.junit.Before;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change;
import edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.test.testmodels.Models;
import edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.test.utils.BasicTestCase;

public abstract class AbstractChangeDescription2ChangeConverterTests<T extends EObject> extends BasicTestCase {
    private boolean hasRegisteredMetaModels = false;

    protected Resource sourceRes;
    protected T sourceRoot;

    private ChangeRecorder changeRecorder;

    private ChangeRecorder sourceResettingCR;

    protected static final int CHANGES_PER_CREATEREMOVE = 1;
    protected static final int CHANGES_PER_LIST_ADD_DELOP = 1;
    protected static final int CHANGES_PER_LIST_MOVE_OP = 2;
    protected static final int CHANGES_PER_SET = 1;
    protected static final int CHANGES_PER_UNSET = 1;

    @Before
    public void setUp() {
        System.err.println("setting up");
        if (!hasRegisteredMetaModels) {
            registerMetamodels();
            hasRegisteredMetaModels = true;
        }
        sourceRes = Models.loadModel(getModel());
        sourceRoot = (T) sourceRes.getContents().get(0);
        sourceResettingCR = new ChangeRecorder(sourceRes);
        changeRecorder = new ChangeRecorder(sourceRes);
    }

    protected abstract URL getModel();

    protected void registerMetamodels() {

    };

    @After
    public void tearDown() {
        System.err.println("tearing down");
        sourceResettingCR.endRecording().apply();
        sourceRes.unload();
    }

    protected List<Change> getChangesAndEndRecording() {
        ChangeDescription2ChangeConverter converter = new ChangeDescription2ChangeConverter();

        ChangeDescription cd = changeRecorder.endRecording();
        cd.applyAndReverse();
        List<Change> changes = converter.getChanges(cd);
        cd.applyAndReverse();

        return changes;
    }
}
