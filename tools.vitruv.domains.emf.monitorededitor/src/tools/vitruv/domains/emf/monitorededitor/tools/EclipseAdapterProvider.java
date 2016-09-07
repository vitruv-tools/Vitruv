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

package tools.vitruv.domains.emf.monitorededitor.tools;

import org.apache.log4j.Logger;

/**
 * {@link EclipseAdapterProvider} provides the {@link IEclipseAdapter} objects used to access
 * Eclipse.
 */
public final class EclipseAdapterProvider {
    private static final Logger LOGGER = Logger.getLogger(EclipseAdapterImpl.class);
    private static final IEclipseAdapter DEFAULT_ADAPTER = new EclipseAdapterImpl();
    private static final EclipseAdapterProvider INSTANCE = new EclipseAdapterProvider();

    private IEclipseAdapter eclipseAdapter = DEFAULT_ADAPTER;

    private EclipseAdapterProvider() {
    }

    /**
     * @return The singleton {@link EclipseAdapterProvider} instance.
     */
    public static EclipseAdapterProvider getInstance() {
        return INSTANCE;
    }

    /**
     * @return The {@link IEclipseAdapter} instance which should be used to access Eclipse.
     */
    public IEclipseAdapter getEclipseAdapter() {
        return eclipseAdapter;
    }

    /**
     * Sets the {@link IEclipseAdapter} instance provided by {@link EclipseAdapterProvider}. By
     * default, {@link EclipseAdapterProvider} provides an {@link IEclipseAdapter} adapter to
     * various Eclipse facilities; This adapter should ONLY be replaced for testing purposes, NOT in
     * non-testing production code.
     * 
     * @param adapter
     *            The new {@link IEclipseAdapter} object.
     */
    public void setProvidedEclipseAdapter(IEclipseAdapter adapter) {
        if (adapter != DEFAULT_ADAPTER) {
            LOGGER.warn("Overriding the default Eclipse adapter. This should only happen in testing environments.");
        }
        eclipseAdapter = adapter;
    }
}
