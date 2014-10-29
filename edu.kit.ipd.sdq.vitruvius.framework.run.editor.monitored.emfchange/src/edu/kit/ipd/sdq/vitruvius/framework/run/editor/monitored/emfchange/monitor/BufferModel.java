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

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.change.ChangeDescription;
import org.eclipse.emf.ecore.change.util.ChangeRecorder;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
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

    private boolean isDisposed = true;
    private final VURI modelResourceURI;

    /**
     * Constructs a new {@link BufferModel} instance.
     * 
     * @param modelToBeBuffered
     *            The URI of the model which needs to be buffered.
     * 
     * @throws BufferModelDisposedException
     *             The buffer resource could not be loaded.
     */
    public BufferModel(Resource modelToBeBuffered) {
        this.bufferResource = setupResource(modelToBeBuffered);
        this.modelResourceURI = VURI.getInstance(modelToBeBuffered);
        reInitialize();
    }

    private Resource setupResource(Resource res) {
        ResourceSet rs = new ResourceSetImpl();

        assert res.getResourceSet() != null;

        rs.setPackageRegistry(res.getResourceSet().getPackageRegistry());
        rs.setResourceFactoryRegistry(res.getResourceSet().getResourceFactoryRegistry());
        rs.setURIConverter(res.getResourceSet().getURIConverter());

        Resource ecoreRes = rs.createResource(res.getURI());

        if (ecoreRes == null) {
            isDisposed = true;
            throw new BufferModelDisposedException("Could not load resource " + res.getURI());
        }

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
     * 
     * @throws BufferModelDisposedException
     *             The {@link BufferModel} is in a disposed state.
     */
    public synchronized void incorporateChanges(List<Change> changes, Resource changesOrigin) {
        if (isDisposed) {
            throw new BufferModelDisposedException("Object is disposed");
        }

        ChangeApplicator ca = new ChangeApplicator(changesOrigin, changes);
        ca.applyChanges(bufferResource);
    }

    /**
     * Makes a snapshot of the changes incorporated into the buffer model since last making a
     * snapshot.
     * 
     * @return The changes made to the buffer model since last making such a snapshot, represented
     *         as a list of {@link Change} objects.
     * 
     * @throws BufferModelDisposedException
     *             The {@link BufferModel} is in a disposed state.
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
        List<Change> changes = changeConverter.getChanges(cd, modelResourceURI);
        cd.applyAndReverse();
        return changes;
    }

    /**
     * Reinitializes the {@link BufferModel} instance. This method only has an effect when the
     * instance is in a disposed state when calling this method.
     * 
     * @throws BufferModelDisposedException
     *             Loading the buffer model resource failed.
     */
    public synchronized void reInitialize() {
        if (!isDisposed) {
            return;
        }

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

    /**
     * Releases the resources held by the {@link BufferModel} instance.
     */
    public synchronized void dispose() {
        if (!isDisposed) {
            bufferInstanceChangeRecorder.dispose();
            bufferResource.unload();
            isDisposed = true;
        }
    }

    /**
     * {@link BufferModelDisposedException} indicates that the buffer model has been disposed and
     * thus cannot be used.
     */
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
