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

import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.commands.IExecutionListener;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.commands.ICommandService;

/**
 * {@link EclipseAdapterImpl} is a default implementation of {@link IEclipseAdapter}, implementing
 * the interface to Eclipse facilities.
 */
class EclipseAdapterImpl implements IEclipseAdapter {

    /**
     * Constructs a new {@link EclipseAdapterImpl} instance.
     */
    protected EclipseAdapterImpl() {
    }

    @Override
    public IWorkbench getWorkbench() {
        return PlatformUI.getWorkbench();
    }

    @Override
    public IWorkbenchPage getActiveWorkbenchPage() {
        return PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
    }

    @Override
    public IEditorPart getActiveEditorPart() {
        return getActiveWorkbenchPage().getActiveEditor();
    }

    @Override
    public void addCommandServiceListener(IExecutionListener iel) {
        final ICommandService service = (ICommandService) PlatformUI.getWorkbench().getService(ICommandService.class);
        service.addExecutionListener(iel);
    }

    @Override
    public void removeCommandServiceListener(IExecutionListener iel) {
        final ICommandService service = (ICommandService) PlatformUI.getWorkbench().getService(ICommandService.class);
        service.removeExecutionListener(iel);
    }

    @Override
    public Set<IEditorPart> getCurrentlyActiveEditors() {
        HashSet<IEditorPart> result = new HashSet<>();

        for (IWorkbenchWindow window : PlatformUI.getWorkbench().getWorkbenchWindows()) {
            for (IWorkbenchPage page : window.getPages()) {
                IEditorPart activeEditor = page.getActiveEditor();
                if (activeEditor != null) {
                    result.add(activeEditor);
                }
            }
        }

        return result;
    }

    @Override
    public void executeSynchronous(final Runnable r) {
        Display.getDefault().syncExec(new Runnable() {
            @Override
            public void run() {
                r.run();
            }
        });
    }
}
