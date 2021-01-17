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

package tools.vitruv.domains.emf.monitorededitor.test.mocking;

import static org.easymock.EasyMock.createNiceMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.getCurrentArguments;
import static org.easymock.EasyMock.isA;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.reset;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.easymock.IAnswer;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IPageListener;
import org.eclipse.ui.IPartListener2;
import org.eclipse.ui.IWindowListener;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.IWorkbenchWindow;

import tools.vitruv.domains.emf.monitorededitor.test.mocking.EclipseMock.EditorPartMgmtEvent;

public class EclipseWorkbenchMock {
    private final IWorkbench workbench;
    private final Set<IWindowListener> workbenchListeners = new HashSet<>();
    
    private final Set<IWorkbenchWindow> workbenchWindows = new HashSet<>();
    private final Map<IWorkbenchWindow, Set<IPageListener>> pageListeners = new HashMap<>(); 
    
    private final Map<IWorkbenchWindow, Set<IWorkbenchPage>> workbenchPages = new HashMap<>();
    private final Map<IWorkbenchPage, Set<IPartListener2>> partListeners = new HashMap<>();
    
    
    private IWorkbenchPage activePage = null;
    
    public EclipseWorkbenchMock() {
        this.workbench = createWorkbenchMock();
        IWorkbenchWindow mainWnd = createWindow();
        activePage = createPage(mainWnd);
    }
        
    public IWorkbenchPage getActiveWorkbenchPage() {
        return activePage;
    }
    
    public IWorkbench getWorkbench() {
        return workbench;
    }
    
    public IWorkbenchWindow createWindow() {
        IWorkbenchWindow newWindow = createDefaultWorkspaceWindowMock();
        workbenchWindows.add(newWindow);
        for (IWindowListener wndListener : workbenchListeners) {
            wndListener.windowOpened(newWindow);
        }
        return newWindow;
    }
    
    public void disposeWindow(IWorkbenchWindow wnd) {
        workbenchWindows.remove(wnd);
        for (IWindowListener wndListener : workbenchListeners) {
            wndListener.windowClosed(wnd);
        }
        
        assert this.pageListeners.get(wnd).isEmpty() : "Window had page listeners when it was closed";
        this.pageListeners.remove(wnd);
    }
    
    public IWorkbenchPage createPage(IWorkbenchWindow parent) {
        IWorkbenchPage newPage = createWorkbenchPageMock();
        workbenchPages.get(parent).add(newPage);
        for (IPageListener pageListener : pageListeners.get(parent)) {
            pageListener.pageOpened(newPage);
        }
        return newPage;
    }
    
    public void disposePage(IWorkbenchWindow parent, IWorkbenchPage it) {
        workbenchPages.get(parent).remove(it);
        for (IPageListener pageListener : pageListeners.get(parent)) {
            pageListener.pageClosed(it);
        }
        
        assert this.partListeners.get(it).isEmpty() : "Page had part listeners when it was closed";
        this.partListeners.remove(it);
    }
    
    public Collection<IPartListener2> getPartListeners(IWorkbenchPage page) {
        return Collections.unmodifiableCollection(this.partListeners.get(page));
    }
    
    public Collection<IPageListener> getPageListeners(IWorkbenchWindow window) {
        return Collections.unmodifiableCollection(this.pageListeners.get(window));
    }
    
    public Collection<IWindowListener> getWindowListeners() {
        return Collections.unmodifiableCollection(this.workbenchListeners);
    }
    
    private IWorkbench createWorkbenchMock() {
        final IWorkbench workbench = createNiceMock(IWorkbench.class);
        reset(workbench);
        
        workbench.addWindowListener(isA(IWindowListener.class));
        expectLastCall().andAnswer(new IAnswer<Object>() {
            @Override
            public Object answer() throws Throwable {
                Object[] args = getCurrentArguments();
                IWindowListener listener = (IWindowListener) args[0];                
                EclipseWorkbenchMock.this.workbenchListeners.add(listener);
                return listener;
            }
        }).anyTimes();
        
        workbench.removeWindowListener(isA(IWindowListener.class));
        expectLastCall().andAnswer(new IAnswer<Object>() {
            @Override
            public Object answer() throws Throwable {
                Object[] args = getCurrentArguments();
                IWindowListener listener = (IWindowListener) args[0];                
                EclipseWorkbenchMock.this.workbenchListeners.remove(listener);
                return listener;
            }
        }).anyTimes();
        
        workbench.getWorkbenchWindows();
        expectLastCall().andAnswer(new IAnswer<Object>() {
            @Override
            public Object answer() throws Throwable {
                return (IWorkbenchWindow[]) workbenchWindows.toArray(new IWorkbenchWindow[workbenchWindows.size()]);
            }
        }).anyTimes();
        
        replay(workbench);
        return workbench;
    }
    
    private IWorkbenchWindow createDefaultWorkspaceWindowMock() {
        final IWorkbenchWindow workbenchWnd = createNiceMock(IWorkbenchWindow.class);
        reset(workbenchWnd);
        
        workbenchWnd.addPageListener(isA(IPageListener.class));
        expectLastCall().andAnswer(new IAnswer<Object>() {
            @Override
            public Object answer() throws Throwable {
                Object[] args = getCurrentArguments();
                IPageListener listener = (IPageListener) args[0];                
                EclipseWorkbenchMock.this.pageListeners.get(workbenchWnd).add(listener);
                return listener;
            }
        }).anyTimes();
        
        workbenchWnd.removePageListener(isA(IPageListener.class));
        expectLastCall().andAnswer(new IAnswer<Object>() {
            @Override
            public Object answer() throws Throwable {
                Object[] args = getCurrentArguments();
                IPageListener listener = (IPageListener) args[0];                
                EclipseWorkbenchMock.this.pageListeners.get(workbenchWnd).remove(listener);
                return listener;
            }
        }).anyTimes();
        
        workbenchWnd.getPages();
        expectLastCall().andAnswer(new IAnswer<Object>() {
            @Override
            public Object answer() throws Throwable {
                return workbenchPages.get(workbenchWnd).toArray(new IWorkbenchPage[workbenchPages.get(workbenchWnd).size()]);
            }
        }).anyTimes();
        
        replay(workbenchWnd);
        
        pageListeners.put(workbenchWnd, new HashSet<IPageListener>());
        workbenchPages.put(workbenchWnd, new HashSet<IWorkbenchPage>());
        
        return workbenchWnd;
    }
    
    private IWorkbenchPage createWorkbenchPageMock() {
        final IWorkbenchPage workbenchPage = createNiceMock(IWorkbenchPage.class);
        reset(workbenchPage);

        workbenchPage.addPartListener(isA(IPartListener2.class));
        expectLastCall().andAnswer(new IAnswer<Object>() {
            @Override
            public Object answer() throws Throwable {
                Object[] args = getCurrentArguments();
                assert args.length == 1;
                assert args[0] instanceof IPartListener2;

                
                IPartListener2 listener = (IPartListener2) args[0];                
                EclipseWorkbenchMock.this.partListeners.get(workbenchPage).add(listener);
                return listener;
            }
        }).anyTimes();
        
        workbenchPage.removePartListener(isA(IPartListener2.class));
        expectLastCall().andAnswer(new IAnswer<Object>() {
            @Override
            public Object answer() throws Throwable {
                Object[] args = getCurrentArguments();
                assert args.length == 1;
                assert args[0] instanceof IPartListener2;

                
                IPartListener2 listener = (IPartListener2) args[0];                
                EclipseWorkbenchMock.this.partListeners.get(workbenchPage).remove(listener);
                return listener;
            }
        }).anyTimes();
        
        replay(workbenchPage);
        partListeners.put(workbenchPage, new HashSet<IPartListener2>());
        activePage = workbenchPage;
        
        return workbenchPage;
    }
    
    public Collection<IPartListener2> getPartListeners() {
        List<IPartListener2> result = new ArrayList<>();
        for (IWorkbenchPage page : this.partListeners.keySet()) {
            result.addAll(partListeners.get(page));
        }
        return result;
    }
    
    private final Map<IEditorPart, IWorkbenchPage> editors2wbPage = new HashMap<>();
    
    public void changedEditorPart(IEditorPart editorPart, EditorPartMgmtEvent eventKind) {
        assert getActiveWorkbenchPage() != null;
        changedEditorPart(editorPart, eventKind, getActiveWorkbenchPage());
    }
    
    public void changedEditorPart(IEditorPart editorPart, EditorPartMgmtEvent eventKind, IWorkbenchPage createInPage) {
        IWorkbenchPartReference wbPartRef = createNiceMock(IWorkbenchPartReference.class);
        reset(wbPartRef);
        expect(wbPartRef.getPart(false)).andReturn(editorPart).anyTimes();
        replay(wbPartRef);

        switch (eventKind) {
        case CLOSE:
            for (IPartListener2 listener : partListeners.get(editors2wbPage.get(editorPart))) {
                listener.partClosed(wbPartRef);
            }
            editors2wbPage.remove(editorPart);
            break;
        case OPEN:
            editors2wbPage.put(editorPart, createInPage);
            for (IPartListener2 listener : partListeners.get(createInPage)) {
                listener.partOpened(wbPartRef);
            }
            break;
        default:
            break;
        }
        
    }
    
    
}
