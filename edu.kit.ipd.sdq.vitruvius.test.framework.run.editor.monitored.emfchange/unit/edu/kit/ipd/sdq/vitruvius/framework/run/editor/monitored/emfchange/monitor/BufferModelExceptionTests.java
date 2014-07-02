package edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.monitor;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.junit.Test;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change;
import edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.monitor.BufferModel.BufferModelDisposedException;
import edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.test.testmodels.Files;
import edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.test.testmodels.Models;
import edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.test.utils.BasicTestCase;

public class BufferModelExceptionTests extends BasicTestCase {

    @Test(expected = BufferModelDisposedException.class)
    public void bufferModelCannotBeUpdatedWhenDisposed() {
        BufferModel bm = new BufferModel(URI.createFileURI(Files.EXAMPLEMODEL_ECORE.getFile()));
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
        BufferModel bm = new BufferModel(URI.createFileURI(Files.EXAMPLEMODEL_ECORE.getFile()));
        bm.dispose();
        bm.createBufferChangeSnapshot();
    }

    @Test(expected = BufferModelDisposedException.class)
    public void bufferModelIsDisposedWhenResourceCannotBeLoaded() {
        BufferModel bm = new BufferModel(URI.createFileURI("/"));
    }

    @Test
    public void bufferModelCanBeReinitialized() {
        BufferModel bm = new BufferModel(URI.createFileURI(Files.EXAMPLEMODEL_ECORE.getFile()));
        bm.dispose();
        bm.reInitialize();
        List<Change> changes = bm.createBufferChangeSnapshot();
        assert changes.isEmpty();
    }
}
