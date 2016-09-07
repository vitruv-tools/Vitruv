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

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import org.apache.log4j.Logger;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IPageListener;
import org.eclipse.ui.IPartListener2;
import org.eclipse.ui.IWindowListener;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.IWorkbenchWindow;

/**
 * {@link EditorManagementListenerMgr} is repsonsible for installing, firing and disposing
 * {@link IEditorManagementListener}s.
 */
public class EditorManagementListenerMgr {

    private static final Logger LOGGER = Logger.getLogger(EditorManagementListenerMgr.class);

    private final Map<IEditorManagementListener, IPartListener2> listeners = new HashMap<>();
    private final IEclipseAdapter eclipseAdapter = EclipseAdapterProvider.getInstance().getEclipseAdapter();

    private IWindowListener workbenchListener;
    private final Map<IWorkbenchWindow, IPageListener> workbenchWindowListeners = new HashMap<>();

    /**
     * Initializes the {@link EditorManagementListenerMgr} instance.
     */
    public void initialize() {
        eclipseAdapter.executeSynchronous(new Runnable() {
            @Override
            public void run() {
                initializeWorkbenchListener(eclipseAdapter.getWorkbench());
            }
        });
    }

    /**
     * Attaches a listener to the workbench notifiying the {@link EditorManagementListenerMgr} of
     * opened and closed workbench windows.
     * 
     * @param workbench
     *            The Eclipse workbench.
     */
    private void initializeWorkbenchListener(IWorkbench workbench) {
        workbenchListener = new IWindowListener() {

            @Override
            public void windowOpened(IWorkbenchWindow window) {
                initializeWindowListener(window);
            }

            @Override
            public void windowDeactivated(IWorkbenchWindow window) {
            }

            @Override
            public void windowClosed(IWorkbenchWindow window) {
                disposeWindowListener(window);
            }

            @Override
            public void windowActivated(IWorkbenchWindow window) {
            }
        };

        for (IWorkbenchWindow existingWindow : workbench.getWorkbenchWindows()) {
            initializeWindowListener(existingWindow);
        }

        workbench.addWindowListener(workbenchListener);
    }

    /**
     * Attaches a listener to the given workbench window notifying the
     * {@link EditorManagementListenerMgr} of opened and closed workbench pages.
     * 
     * @param window
     *            The Eclipse workbench window.
     */
    private void initializeWindowListener(IWorkbenchWindow window) {
        IPageListener pageListener = new IPageListener() {

            @Override
            public void pageOpened(IWorkbenchPage page) {
                addEditorManagementListenersToPage(page);
            }

            @Override
            public void pageClosed(IWorkbenchPage page) {
                removeEditorManagementListenersFromPage(page);
            }

            @Override
            public void pageActivated(IWorkbenchPage page) {
            }
        };

        for (IWorkbenchPage page : window.getPages()) {
            addEditorManagementListenersToPage(page);
        }

        workbenchWindowListeners.put(window, pageListener);
        window.addPageListener(pageListener);
    }

    private void disposeWindowListener(IWorkbenchWindow window) {
        IPageListener pageListener = workbenchWindowListeners.get(window);
        try {
            window.removePageListener(pageListener);
        } catch (IllegalArgumentException e) {
            LOGGER.trace("A workbench page listener could not be removed from a workbench window "
                    + "since the listener was not present anymore.");
        }
        workbenchWindowListeners.remove(window);
    }

    private void addEditorManagementListenersToPage(IWorkbenchPage page) {
        for (IEditorManagementListener listener : getEditorManagementListeners()) {
            page.addPartListener(getEclipseListener(listener));
        }
    }

    private void removeEditorManagementListenersFromPage(IWorkbenchPage page) {
        for (IEditorManagementListener listener : getEditorManagementListeners()) {
            page.removePartListener(getEclipseListener(listener));
        }
    }

    private void registerEclipseListener(IEditorManagementListener listener, IPartListener2 eclipseListener) {
        listeners.put(listener, eclipseListener);
    }

    private Set<IEditorManagementListener> getEditorManagementListeners() {
        return listeners.keySet();
    }

    private IPartListener2 getEclipseListener(IEditorManagementListener listener) {
        return listeners.get(listener);
    }

    private void addPartListenerToAllExistingPages(IPartListener2 eclipseListener) {
        for (IWorkbenchWindow window : eclipseAdapter.getWorkbench().getWorkbenchWindows()) {
            for (IWorkbenchPage page : window.getPages()) {
                page.addPartListener(eclipseListener);
            }
        }
    }

    private void removePartListenerFromAllExistingPages(IPartListener2 eclipseListener) {
        for (IWorkbenchWindow window : eclipseAdapter.getWorkbench().getWorkbenchWindows()) {
            for (IWorkbenchPage page : window.getPages()) {
                page.removePartListener(eclipseListener);
            }
        }
    }

    /**
     * Adds an {@link IEditorManagementListener}, calling it whenever an {@link IEditorPart} is
     * opened or closed.
     * 
     * @param listener
     *            The new listener.
     */
    public void addEditorManagementListener(final IEditorManagementListener listener) {
        eclipseAdapter.executeSynchronous(new Runnable() {
            @Override
            public void run() {
                IPartListener2 eclipseListener = new IPartListener2() {
                    @Override
                    public void partVisible(IWorkbenchPartReference partRef) {
                    }

                    @Override
                    public void partOpened(IWorkbenchPartReference partRef) {
                        IWorkbenchPart part = partRef.getPart(false);
                        if (part instanceof IEditorPart) {
                            IEditorPart editorPart = (IEditorPart) part;
                            listener.onCreated(editorPart);
                        }
                    }

                    @Override
                    public void partInputChanged(IWorkbenchPartReference partRef) {
                    }

                    @Override
                    public void partHidden(IWorkbenchPartReference partRef) {
                    }

                    @Override
                    public void partDeactivated(IWorkbenchPartReference partRef) {
                    }

                    @Override
                    public void partClosed(IWorkbenchPartReference partRef) {
                        IWorkbenchPart part = partRef.getPart(false);
                        if (part instanceof IEditorPart) {
                            IEditorPart editorPart = (IEditorPart) part;
                            listener.onClosed(editorPart);
                        }
                    }

                    @Override
                    public void partBroughtToTop(IWorkbenchPartReference partRef) {
                    }

                    @Override
                    public void partActivated(IWorkbenchPartReference partRef) {
                    }
                };
                addPartListenerToAllExistingPages(eclipseListener);
                registerEclipseListener(listener, eclipseListener);
            }
        });
    }

    /**
     * Removes an {@link IEditorManagementListener} from the set of listeners getting notified of
     * opened and closed {@link IEditorPart}s.
     * 
     * @param listener
     *            The listener to be removed.
     */
    public void removeEditorManagementListener(final IEditorManagementListener listener) {
        if (!listeners.containsKey(listener)) {
            throw new NoSuchElementException(listener.toString());
        } else {
            final IPartListener2 eclipseListener = listeners.get(listener);
            eclipseAdapter.executeSynchronous(new Runnable() {
                @Override
                public void run() {
                    removePartListenerFromAllExistingPages(eclipseListener);
                }
            });
            listeners.remove(listener);
        }
    }

    /**
     * Removes all {@link IEditorManagementListener}s and shuts down the
     * {@link IEditorManagementListener}.
     */
    public void dispose() {
        for (IEditorManagementListener listener : getEditorManagementListeners()) {
            removeEditorManagementListener(listener);
        }
        if (null != workbenchListener) {
            eclipseAdapter.getWorkbench().removeWindowListener(workbenchListener);
            for (IWorkbenchWindow window : workbenchWindowListeners.keySet()) {
                window.removePageListener(workbenchWindowListeners.get(window));
            }
        }
    }
}
