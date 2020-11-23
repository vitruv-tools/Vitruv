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

package tools.vitruv.domains.emf.monitorededitor.monitor;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.net.URISyntaxException;

import org.eclipse.ui.IEditorPart;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import tools.vitruv.domains.emf.monitorededitor.EditorNotMonitorableException;
import tools.vitruv.domains.emf.monitorededitor.IEditorPartAdapterFactory;
import tools.vitruv.domains.emf.monitorededitor.IEditorPartAdapterFactory.IEditorPartAdapter;
import tools.vitruv.domains.emf.monitorededitor.test.mocking.EclipseMock;
import tools.vitruv.domains.emf.monitorededitor.test.testmodels.Files;
import tools.vitruv.domains.emf.monitorededitor.test.utils.BasicTestCase;
import tools.vitruv.domains.emf.monitorededitor.test.utils.EnsureExecuted;

public class DefaultEditorPartAdapterFactoryImplTests extends BasicTestCase {
    private IEditorPartAdapterFactory factory;
    private EclipseMock eclipseMock;

    @BeforeEach
    public void setUp() {
        this.factory = new DefaultEditorPartAdapterFactoryImpl(Files.ECORE_FILE_EXTENSION);
        this.eclipseMock = new EclipseMock();
    }

    @Test
    public void createsAdapterForEMFTreeEditors() {
        IEditorPart emfEditor = eclipseMock.openNewEMFTreeEditorPart(Files.EXAMPLEMODEL_ECORE);
        assert factory.createAdapter(emfEditor) != null;
    }

    @Test
    public void createsAdapterForGMFTreeEditors() {
        IEditorPart gmfEditor = eclipseMock.openNewEMFDiagramEditorPart(Files.EXAMPLEMODEL_ECORE,
                Files.EXAMPLEMODEL_ECOREDIAG);
        assert factory.createAdapter(gmfEditor) != null;
    }

    @Test
    public void refusesToCreateAdapterForNonEMFEditor() {
        IEditorPart otherEditor = eclipseMock.openNewNonEMFEditorPart();
        assertThrows(EditorNotMonitorableException.class, () -> factory.createAdapter(otherEditor));
    }

    @Test
    public void refusesToCreateAdapterForEditorWithUnexpectedResourceURISuffix() {
        factory = new DefaultEditorPartAdapterFactoryImpl("foo");
        IEditorPart emfEditor = eclipseMock.openNewEMFTreeEditorPart(Files.EXAMPLEMODEL_ECORE);
        assertThrows(EditorNotMonitorableException.class, () -> factory.createAdapter(emfEditor));
    }

    @Test
    public void EMFTreeEditorAdapterProperties() throws URISyntaxException {
        IEditorPart emfEditor = eclipseMock.openNewEMFTreeEditorPart(Files.EXAMPLEMODEL_ECORE);
        IEditorPartAdapter adapter = factory.createAdapter(emfEditor);
        assert adapter.getEditedModelResource().getURI().equals(getURI(Files.EXAMPLEMODEL_ECORE));
        assert adapter.getEditingDomain() != null;
        assert adapter.getEditorPart() == emfEditor;
    }

    @Test
    public void EMFTreeEditorExecutionInEditorContext() {
        IEditorPart emfEditor = eclipseMock.openNewEMFTreeEditorPart(Files.EXAMPLEMODEL_ECORE);
        IEditorPartAdapter adapter = factory.createAdapter(emfEditor);

        final EnsureExecuted ensureExecuted = new EnsureExecuted();
        adapter.executeCommand(new Runnable() {
            @Override
            public void run() {
                ensureExecuted.markExecuted();
            }
        });

        assert !ensureExecuted.isIndicatingFail();
    }

    @Test
    public void GMFTreeEditorAdapterProperties() throws URISyntaxException {
        IEditorPart gmfEditor = eclipseMock.openNewEMFDiagramEditorPart(Files.EXAMPLEMODEL_ECORE,
                Files.EXAMPLEMODEL_ECOREDIAG);
        IEditorPartAdapter adapter = factory.createAdapter(gmfEditor);
        assert adapter.getEditedModelResource().getURI().equals(getURI(Files.EXAMPLEMODEL_ECORE));
        assert adapter.getEditingDomain() != null;
        assert adapter.getEditorPart() == gmfEditor;
    }

    @Test
    public void GMFTreeEditorExecutionInEditorContext() {
        IEditorPart gmfEditor = eclipseMock.openNewEMFDiagramEditorPart(Files.EXAMPLEMODEL_ECORE,
                Files.EXAMPLEMODEL_ECOREDIAG);
        IEditorPartAdapter adapter = factory.createAdapter(gmfEditor);

        final EnsureExecuted ensureExecuted = new EnsureExecuted();
        adapter.executeCommand(new Runnable() {
            @Override
            public void run() {
                ensureExecuted.markExecuted();
            }
        });

        assert !ensureExecuted.isIndicatingFail();
    }
}
