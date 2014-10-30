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

package edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.monitor;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.junit.Test;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change;
import edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.monitor.BufferModel.BufferModelDisposedException;
import edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.test.testmodels.Files;
import edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.test.testmodels.Models;
import edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.test.utils.BasicTestCase;

public class BufferModelExceptionTests extends BasicTestCase {

    @Test(expected = BufferModelDisposedException.class)
    public void bufferModelCannotBeUpdatedWhenDisposed() {
        BufferModel bm = new BufferModel(Models.loadModel(Files.EXAMPLEMODEL_ECORE));
        bm.dispose();
        Resource r = Models.loadModel(Files.EXAMPLEMODEL_ECORE);
        try {
            bm.incorporateChanges(new ArrayList<Change>(), r);
        } finally {
            r.unload();
        }
    }

    @Test(expected = BufferModelDisposedException.class)
    public void changesCannotBeObtainedWhenDisposed() {
        BufferModel bm = new BufferModel(Models.loadModel(Files.EXAMPLEMODEL_ECORE));
        bm.dispose();
        bm.createBufferChangeSnapshot();
    }

    @Test(expected = BufferModelDisposedException.class)
    public void bufferModelIsDisposedWhenResourceCannotBeLoaded() {
        EcoreResourceFactoryImpl ecoreResFact = new EcoreResourceFactoryImpl();
        URI fileName = URI.createFileURI("/");
        ResourceSet rs = new ResourceSetImpl();
        Resource ecoreRes = ecoreResFact.createResource(fileName);
        rs.getResources().add(ecoreRes);
        BufferModel bm = new BufferModel(ecoreRes);
    }

    @Test
    public void bufferModelCanBeReinitialized() {
        BufferModel bm = new BufferModel(Models.loadModel(Files.EXAMPLEMODEL_ECORE));
        bm.dispose();
        bm.reInitialize();
        List<Change> changes = bm.createBufferChangeSnapshot();
        assert changes.isEmpty();
    }
}
