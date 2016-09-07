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

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.gmf.runtime.diagram.ui.resources.editor.parts.DiagramDocumentEditor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IPropertyListener;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.PartInitException;

public class IGMFEditor extends DiagramDocumentEditor implements IEditorPart {

    public IGMFEditor(boolean hasFlyoutPalette) {
        super(hasFlyoutPalette);
    }

    @Override
    public void addPropertyListener(IPropertyListener listener) {

    }

    @Override
    public void createPartControl(Composite parent) {

    }

    @Override
    public void dispose() {

    }

    @Override
    public IWorkbenchPartSite getSite() {

        return null;
    }

    @Override
    public String getTitle() {

        return null;
    }

    @Override
    public Image getTitleImage() {

        return null;
    }

    @Override
    public String getTitleToolTip() {

        return null;
    }

    @Override
    public void removePropertyListener(IPropertyListener listener) {

    }

    @Override
    public void setFocus() {

    }

    @SuppressWarnings("rawtypes")
    @Override
    public Object getAdapter(Class adapter) {

        return null;
    }

    @Override
    public void doSave(IProgressMonitor monitor) {

    }

    @Override
    public void doSaveAs() {

    }

    @Override
    public boolean isDirty() {

        return false;
    }

    @Override
    public boolean isSaveAsAllowed() {

        return false;
    }

    @Override
    public boolean isSaveOnCloseNeeded() {

        return false;
    }

    @Override
    public IEditorInput getEditorInput() {

        return null;
    }

    @Override
    public IEditorSite getEditorSite() {

        return null;
    }

    @Override
    public void init(IEditorSite site, IEditorInput input) throws PartInitException {

    }

}
