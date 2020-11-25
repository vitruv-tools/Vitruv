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

import java.io.IOException;

import org.eclipse.emf.ecore.resource.Resource;
import org.junit.jupiter.api.Test;

import tools.vitruv.domains.emf.monitorededitor.test.testmodels.Files;
import tools.vitruv.domains.emf.monitorededitor.test.testmodels.Models;
import tools.vitruv.domains.emf.monitorededitor.test.utils.EnsureExecuted;
import tools.vitruv.domains.emf.monitorededitor.test.utils.EnsureNotExecuted;

public class ResourceReloadListenerTests {
    @Test
    public void detectsUnload() throws IOException {
        Resource ecoreRes = Models.loadModel(Files.EXAMPLEMODEL_ECORE);
        final EnsureExecuted ensureExecuted = new EnsureExecuted();
        final EnsureNotExecuted ensureNotExecuted = new EnsureNotExecuted();

        ResourceReloadListener listener = new ResourceReloadListener(ecoreRes) {

            @Override
            protected void onResourceUnloaded() {
                ensureExecuted.markExecuted();
            }

            @Override
            protected void onResourceLoaded() {
                ensureNotExecuted.markExecuted();
            }
        };

        ecoreRes.eAdapters().add(listener);
        ecoreRes.unload();

        assert !ensureExecuted.isIndicatingFail() : "Listener was not executed on unload.";
        assert !ensureNotExecuted
                .isIndicatingFail() : "Listener was erroneously executed for a load operation which did not happen.";
    }

    @Test
    public void detectsLoad() throws IOException {
        Resource ecoreRes = Models.loadModel(Files.EXAMPLEMODEL_ECORE);
        final EnsureExecuted ensureExecuted = new EnsureExecuted();
        final EnsureNotExecuted ensureNotExecuted = new EnsureNotExecuted();

        ResourceReloadListener listener = new ResourceReloadListener(ecoreRes) {

            @Override
            protected void onResourceUnloaded() {
                ensureNotExecuted.markExecuted();
            }

            @Override
            protected void onResourceLoaded() {
                ensureExecuted.markExecuted();
            }
        };

        ecoreRes.unload();
        ecoreRes.eAdapters().add(listener);
        ecoreRes.load(null);

        assert !ensureExecuted.isIndicatingFail() : "Listener was not executed after loading.";
        assert !ensureNotExecuted
                .isIndicatingFail() : "Listener was erroneously executed for an unload operation which did not happen.";
    }

    @Test
    public void detectsReload() throws IOException {
        Resource ecoreRes = Models.loadModel(Files.EXAMPLEMODEL_ECORE);
        final EnsureExecuted ensureExecutedOnLoad = new EnsureExecuted();
        final EnsureExecuted ensureExecutedOnUnload = new EnsureExecuted();

        ResourceReloadListener listener = new ResourceReloadListener(ecoreRes) {

            @Override
            protected void onResourceUnloaded() {
                ensureExecutedOnUnload.markExecuted();
            }

            @Override
            protected void onResourceLoaded() {
                ensureExecutedOnLoad.markExecuted();
            }
        };

        ecoreRes.eAdapters().add(listener);
        ecoreRes.unload();
        ecoreRes.load(null);

        assert !ensureExecutedOnLoad.isIndicatingFail() : "Listener was not executed after loading.";
        assert !ensureExecutedOnUnload.isIndicatingFail() : "Listener was not executed after unloading.";

    }
}
