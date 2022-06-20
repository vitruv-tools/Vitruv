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

import tools.vitruv.domains.emf.monitorededitor.IEditorPartAdapterFactory.IEditorPartAdapter;

/**
 * An interface for classes deciding which adaptable editors need to be monitored.
 */
public interface IMonitoringDecider {
    /**
     * Decides if the given adapted IEditorPart needs to be monitored.
     * 
     * @param editor
     *            The edited part's {@link IEditorPartAdapter}.
     * 
     * @return <code>true</code> iff the editor needs to be monitored.
     */
    public boolean isMonitoringEnabled(IEditorPartAdapter editor);

    /**
     * A convenience {@link IMonitoringDecider} implementation deciding to monitor all adapted
     * IEditorPart objects.
     */
    IMonitoringDecider MONITOR_ALL = new IMonitoringDecider() {
        @Override
        public boolean isMonitoringEnabled(IEditorPartAdapter editor) {
            return true;
        }
    };
}
