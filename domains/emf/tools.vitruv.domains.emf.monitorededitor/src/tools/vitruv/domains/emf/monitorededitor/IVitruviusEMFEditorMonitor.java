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

package tools.vitruv.domains.emf.monitorededitor;

import tools.vitruv.framework.util.datatypes.VURI;

/**
 * An interface for front-ends of the EMF editor monitor system providing convenient usage within
 * the rest of the Vitruvius framework. Implementations of this interface can be used to monitor EMF
 * editors editing PCM models.
 * 
 * Implementations of this interface are responsible for managing the monitor system, i.e. issuing
 * its initialization/dispose and deciding which editors should be monitored by keeping track of
 * which EMF models need to be synchronized.
 */
public interface IVitruviusEMFEditorMonitor {

    /**
     * Initializes the monitoring of suitable Eclipse EMF/GMF editors.
     */
    public void initialize();

    /**
     * Stops the monitoring of Eclipse EMF/GMF editors.
     */
    public void dispose();

    /**
     * Explicitly adds an EMF model to the set of EMF models whose editors are monitored, to be
     * called when a model is added to the set of monitored EMF models after calling initialize().
     * 
     * @param uri
     *            The respective EMF model's {@link VURI}.
     */
    public void addModel(VURI uri);

    /**
     * Removes an EMF model from the set of EMF models whose editors are monitored for changes. If
     * the respective model is not part of that set, this method has no effect.
     * 
     * @param uri
     *            The respective EMF model's {@link VURI}.
     */
    public void removeModel(VURI uri);

    /**
     * An interface for classes deciding whether a model's editors need to be monitored for changes.
     */
    public interface IVitruviusAccessor {
        /**
         * @param modelUri
         *            An EMF model's {@link VURI}.
         * 
         * @return <code>true</code> if the model's editors need to be monitored for changes.
         */
        public boolean isModelMonitored(VURI modelUri);
    }

    /**
     * Synchronizes the collected changes for a given resource URI. The resource's time stamp is
     * recorded, with all subsequently registered changes up to that time stamp being automatically
     * synchronized.
     * 
     * @param resourceURI
     *            The URI of the model whose changes are to be synchronized.
     */
    public void triggerSynchronisation(final VURI resourceURI);

    public void setReportChanges(boolean b);
}
