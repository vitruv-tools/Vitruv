package edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.junit.Before;
import org.junit.Test;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.RepositoryFactory;

import edu.kit.ipd.sdq.vitruvius.framework.changes.changepreparer.ChangePreparingImpl;
import edu.kit.ipd.sdq.vitruvius.framework.changes.changerecorder.AtomicEMFChangeRecorder;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.change.EMFModelChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VitruviusChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.ChangePreparing;
import edu.kit.ipd.sdq.vitruvius.framework.util.bridges.EcoreResourceBridge;
import edu.kit.ipd.sdq.vitruvius.tests.util.TestUtil;

public class VitruviusEMFEditorMonitorImplTestPlugin {

    private static final Logger logger = Logger
            .getLogger(VitruviusEMFEditorMonitorImplTestPlugin.class.getSimpleName());

    // private DefaultEditorPartAdapterFactoryImpl factory;
    int detectedChanges = 0;

    @Before
    public void setUp() throws Exception {
        TestUtil.initializeLogger();
    }

    @Test
    public void testChangeDescription2Change() throws Throwable {
        final AtomicEMFChangeRecorder changeRecorder = new AtomicEMFChangeRecorder();
        final ResourceSet rs = new ResourceSetImpl();
        final VURI vuri = VURI.getInstance(TestUtil.PROJECT_URI + "/modelTest/repo.repository");
        final Resource resource = rs.createResource(vuri.getEMFUri());
        final Repository repo = RepositoryFactory.eINSTANCE.createRepository();
        repo.setEntityName("name");
        EcoreResourceBridge.saveEObjectAsOnlyContent(repo, resource);
        changeRecorder.beginRecording(VURI.getInstance(resource), Collections.singletonList(resource));
        repo.setEntityName("TestNewName");
        final List<EMFModelChange> recordedChanges = changeRecorder.endRecording();
        ChangePreparing changePreparer = new ChangePreparingImpl(null);
        for (VitruviusChange change : recordedChanges) {
        	change.prepare(changePreparer);
        }
        for (final VitruviusChange change : recordedChanges) {
            logger.warn(change);
        }
        assertTrue("No changes detected ", 0 < recordedChanges.size());
    }
}
