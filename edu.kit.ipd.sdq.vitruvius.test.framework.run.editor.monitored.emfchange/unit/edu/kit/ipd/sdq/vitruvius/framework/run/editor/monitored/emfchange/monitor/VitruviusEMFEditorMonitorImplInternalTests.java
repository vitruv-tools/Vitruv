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

package edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.monitor;

import java.util.Set;

import org.eclipse.ui.IEditorPart;
import org.junit.Before;
import org.junit.Test;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.IEditorPartAdapterFactory;
import edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.test.mocking.EclipseMock;
import edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.test.testmodels.Files;
import edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.test.utils.BasicTestCase;
import edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.test.utils.DefaultImplementations;
import edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.tools.EclipseAdapterProvider;
import edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.tools.IEclipseAdapter;

public class VitruviusEMFEditorMonitorImplInternalTests extends BasicTestCase {
    private EclipseMock eclipseMockCtrl;
    private IEclipseAdapter eclipseUtils;
    private IEditorPartAdapterFactory factory;

    @Before
    public void setUp() {
        this.eclipseMockCtrl = new EclipseMock();
        this.eclipseUtils = eclipseMockCtrl.getEclipseUtils();
        EclipseAdapterProvider.getInstance().setProvidedEclipseAdapter(eclipseUtils);
        this.factory = new DefaultEditorPartAdapterFactoryImpl(Files.ECORE_FILE_EXTENSION);
    }

    @Test
    public void EMFEditorsCanBeFoundByVURI() {
        VitruviusEMFEditorMonitorImpl syncMgr = new VitruviusEMFEditorMonitorImpl(factory,
                DefaultImplementations.EFFECTLESS_EXTERNAL_CHANGESYNC,
                DefaultImplementations.DEFAULT_MODEL_COPY_PROVIDING,
                DefaultImplementations.ALL_ACCEPTING_VITRUV_ACCESSOR);
        syncMgr.initialize();

        IEditorPart exampleEditor = eclipseMockCtrl.openNewEMFDiagramEditorPart(Files.EXAMPLEMODEL_ECORE,
                Files.EXAMPLEMODEL_ECOREDIAG);
        IEditorPart datatypeEditor = eclipseMockCtrl.openNewEMFTreeEditorPart(Files.DATATYPE_ECORE);
        eclipseMockCtrl.openNewNonEMFEditorPart();

        VURI exampleVURI = VURI.getInstance(Files.EXAMPLEMODEL_ECORE.getFile());
        VURI datatypeVURI = VURI.getInstance(Files.DATATYPE_ECORE.getFile());

        Set<IEditorPart> foundExampleEditors = syncMgr.findEditorsForModel(exampleVURI);
        Set<IEditorPart> foundDatatypeEditors = syncMgr.findEditorsForModel(datatypeVURI);

        assert foundExampleEditors != null;
        assert foundDatatypeEditors != null;

        assert foundExampleEditors.size() == 1;
        assert foundExampleEditors.contains(exampleEditor);

        assert foundDatatypeEditors.size() == 1;
        assert foundDatatypeEditors.contains(datatypeEditor);

        syncMgr.dispose();
    }
}
