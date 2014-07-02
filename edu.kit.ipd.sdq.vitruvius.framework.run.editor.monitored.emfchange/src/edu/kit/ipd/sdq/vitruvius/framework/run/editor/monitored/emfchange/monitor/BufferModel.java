package edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.monitor;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.change.ChangeDescription;
import org.eclipse.emf.ecore.change.util.ChangeRecorder;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change;
import edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.changeapplication.ChangeApplicator;
import edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.changedescription2change.ChangeDescription2ChangeConverter;

/**
 * Given a model M, {@link BufferModel} buffers sequential changes to any amount of instances of M
 * and produces lists of changes to the buffered model instance.
 */
class BufferModel {
    private static final Logger LOGGER = Logger.getLogger(BufferModel.class);
    private final Resource bufferResource;
    private ChangeRecorder bufferInstanceChangeRecorder;

    private boolean isDisposed = false;

    /**
     * Constructs a new {@link BufferModel} instance.
     * 
     * @param modelToBeBuffered
     *            The URI of the model which needs to be buffered.
     */
    public BufferModel(URI modelToBeBuffered) {
        this.bufferResource = setupResource(modelToBeBuffered);
        reInitialize();
    }

    private Resource setupResource(URI resURI) {
        EcoreResourceFactoryImpl ecoreResFact = new EcoreResourceFactoryImpl();
        Resource ecoreRes = ecoreResFact.createResource(resURI);
        return ecoreRes;
    }

    /**
     * Adds the given changes to the buffer model instance. The changes must be relative to the
     * current state of the buffer model instance.
     * 
     * @param changes
     *            The changes which need to be added to the buffer model instance.
     * @param changesOrigin
     *            The model resource to which <code>changes</code> pertains.
     */
    public synchronized void incorporateChanges(List<Change> changes, Resource changesOrigin) {
        if (isDisposed) {
            throw new BufferModelDisposedException("Object is disposed");
        }

        ChangeApplicator ca = new ChangeApplicator(changesOrigin, changes);
        ca.applyChanges(bufferResource);
    }

    /**
     * Makes a snapshot of the changes incorporated into the
     * 
     * @return
     */
    public synchronized List<Change> createBufferChangeSnapshot() {
        if (isDisposed) {
            throw new BufferModelDisposedException("Object is disposed");
        }

        ChangeDescription cd = bufferInstanceChangeRecorder.endRecording();
        bufferInstanceChangeRecorder.dispose();
        bufferInstanceChangeRecorder = new ChangeRecorder(bufferResource);
        ChangeDescription2ChangeConverter changeConverter = new ChangeDescription2ChangeConverter();
        cd.applyAndReverse();
        List<Change> changes = changeConverter.getChanges(cd);
        cd.applyAndReverse();
        return changes;
    }

    public synchronized void reInitialize() {
        try {
            LOGGER.trace("Trying to load buffer model instance from " + bufferResource.getURI());
            bufferResource.load(Collections.EMPTY_MAP);
            bufferInstanceChangeRecorder = new ChangeRecorder(bufferResource);
        } catch (IOException e) {
            isDisposed = true;
            throw new BufferModelDisposedException("Could not load " + bufferResource.getURI(), e);
        }
        isDisposed = false;
    }

    public synchronized void dispose() {
        if (!isDisposed) {
            bufferInstanceChangeRecorder.dispose();
            bufferResource.unload();
            isDisposed = true;
        }
    }

    @SuppressWarnings("serial")
    public class BufferModelDisposedException extends RuntimeException {
        private final Exception innerException;
        private final String error;

        public BufferModelDisposedException(String error) {
            innerException = null;
            this.error = error;
        }

        public BufferModelDisposedException(String error, Exception innerException) {
            this.innerException = innerException;
            this.error = error;
        }

        @Override
        public String toString() {
            String result = "" + error;
            if (innerException != null) {
                result += ": " + innerException;
            } else {
                result += ".";
            }
            return result;
        }
    }
}
