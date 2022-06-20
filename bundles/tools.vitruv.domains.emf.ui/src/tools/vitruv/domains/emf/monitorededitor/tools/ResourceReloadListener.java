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

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.resource.Resource;

/**
 * {@link ResourceReloadListener} objects can be attached to monitor {@link Resource} objects for
 * load/unload events.
 */
public abstract class ResourceReloadListener extends AdapterImpl {
    private boolean wasLoading;
    private boolean wasLoaded;
    private final Resource observedResource;

    /**
     * Constructs a new {@link ResourceReloadListener} for the given {@link Resource}.
     * 
     * @param observedResource
     *            The EMF resource to be monitored.
     */
    public ResourceReloadListener(Resource observedResource) {
        this.observedResource = observedResource;
        wasLoaded = isResourceLoaded();
        wasLoading = isResourceLoading();
    }

    private boolean isResourceLoading() {
        return ((Resource.Internal) observedResource).isLoading();
    }

    private boolean isResourceLoaded() {
        return ((Resource.Internal) observedResource).isLoaded();
    }

    @Override
    public void notifyChanged(Notification notification) {
        Resource.Internal internalRes = (Resource.Internal) observedResource;

        if (!isResourceLoading() && wasLoading) {
            onResourceLoaded();
        }

        if (!isResourceLoaded() && wasLoaded) {
            onResourceUnloaded();
        }

        wasLoading = internalRes.isLoading();
        wasLoaded = internalRes.isLoaded();
    }

    /**
     * This method gets called when the monitored resource finished loading.
     */
    protected abstract void onResourceLoaded();

    /**
     * This method gets called when the monitored resource gets unloaded.
     */
    protected abstract void onResourceUnloaded();
}
